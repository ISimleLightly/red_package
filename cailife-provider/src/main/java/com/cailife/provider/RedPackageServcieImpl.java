package com.cailife.provider;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.BoundListOperations;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.cailife.dao.RedPackageMapper;
import com.cailife.dao.UserRedPackageMapper;
import com.cailife.model.LeftMoneyPackage;
import com.cailife.pojo.RedPackage;
import com.cailife.pojo.RedPackageExample;
import com.cailife.pojo.UserRedPackage;
import com.cailife.service.RedPackageService;
import com.cailife.timers.RetrieveRedPackageTimer;

import ch.qos.logback.classic.Logger;

@Service(version="1.1.1")
public class RedPackageServcieImpl implements RedPackageService {
	
	@Autowired
	private RedPackageMapper redPackageMapper;
	
	@Autowired
	private UserRedPackageMapper userRedPackageMapper;
	
	/**
     * 注入任务调度器
     */
    @Autowired @Qualifier("Scheduler")
    private Scheduler scheduler;
	
	@Autowired
	private RedisTemplate<String,String> redisTemplate;
	
	private final static Logger logger = (Logger) LoggerFactory.getLogger(RedPackageServcieImpl.class);

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	@Transactional
	public Map<String, String> grabRedPackage(String redPackageId, String userId) {
		Map result = new HashMap<>();
		RedPackage redPackage = new RedPackage();
		//标识红包是否抢成功
		boolean flag = false;
		//重入锁，重复三次
		for (int i = 0; i < 5; i ++) {
			redPackage = redPackageMapper.selectByPrimaryKey(redPackageId);
			//如果红包不为空，并且该红包剩余的个数不为0
			if (redPackage != null && redPackage.getStock() != 0) {
				RedPackageExample example = new RedPackageExample();
				example.createCriteria().andVersionEqualTo(redPackage.getVersion()).andIdEqualTo(redPackageId);
				redPackage.setVersion(redPackage.getVersion() + 1);
				redPackage.setStock(redPackage.getStock() - 1);
				int updateByExampleSelective = redPackageMapper.updateByExampleSelective(redPackage, example);
				//抢红包成功，保存用户抢红包记录
				if (updateByExampleSelective != 0) {
					flag = true;
					UserRedPackage userRedPackage = new UserRedPackage();
					userRedPackage.setId(UUID.randomUUID().toString());
					userRedPackage.setGrabMoney(redPackage.getUnitMoney());
					userRedPackage.setGrabTime(new Date());
					userRedPackage.setUserId(userId);
					userRedPackage.setRedPackageId(redPackageId);
					userRedPackageMapper.insert(userRedPackage);
					break;
				}
			}
		}
		if (flag) {
			result.put("code", "0");
			result.put("msg", "抢红包成功");
			return result;
		}
		result.put("code", "1");
		result.put("msg", "抢红包失败");
		return result;
	}
	
	@SuppressWarnings("rawtypes")
	@Async
	private void saveUserPackageFromRedis(String redPackageId, Double unitMoney) throws Exception {
		long start = System.currentTimeMillis();
		//每次最多获取1000条记录
		final int TIME_SIZE = 1000;
		BoundListOperations ops = redisTemplate.boundListOps("redPack:redPackageList_" + redPackageId);
		Long size = ops.size();
		int count = 0;
		long times = size % TIME_SIZE == 0 ? size / TIME_SIZE : size / TIME_SIZE + 1;
		List<String> redPackageList = null;
		for (long i = 0; i < times; i ++) {
			if (i == 0) {
				redPackageList = ops.range(i * TIME_SIZE, (i + 1) * TIME_SIZE);
			} else {
				redPackageList = ops.range(TIME_SIZE * i + 1, TIME_SIZE * (i + 1));
			}
			List<UserRedPackage> userRedPackList = new ArrayList<>();
			for (String redPackage : redPackageList) {
				String[] args = redPackage.split("-");
				UserRedPackage userRedPackage = new UserRedPackage();
				userRedPackage.setUserId(args[0]);
				userRedPackage.setId(UUID.randomUUID().toString());
				Timestamp ts = new Timestamp(Long.parseLong(args[1]));
				userRedPackage.setGrabTime(ts);
				userRedPackage.setRedPackageId(redPackageId);
				userRedPackage.setGrabMoney(Double.parseDouble(args[2]));
				userRedPackList.add(userRedPackage);
			}
			count += userRedPackageMapper.batchInsertUserRedPack(userRedPackList);
		}
		RedPackage redPackage = new RedPackage();
		redPackage.setId(redPackageId);
		redPackage.setStock(Integer.parseInt((String) redisTemplate.opsForHash().get("redPack:redPackage_" + redPackageId, "stock")));
		redPackageMapper.updateByPrimaryKeySelective(redPackage);
		//删除缓存中用户抢红包的记录
		redisTemplate.delete("redPack:redPackageList_" + redPackageId);
		long end = System.currentTimeMillis();
		logger.info("保存成功");
	}
	
	@SuppressWarnings("unchecked")
	public Map grabRedPackageByRedis(String redPackageId, String userId) {
		Map resultMap = new HashMap<>();
		//Lua脚本，原子性
		String script = "redis.replicate_commands() \n" +
						"local listkey = 'redPack:redPackageList_'..KEYS[1] \n" +
						"local a=redis.call('TIME') \n" +
						"local time = a[1]*1000000+a[2] \n" +
						"local redPackage = 'redPack:redPackage_'..KEYS[1] \n" +
						"local stock = tonumber(redis.call('hget',redPackage,'stock')) \n" +
						"local remainMoney = tonumber(redis.call('hget',redPackage,'remainMoney')) \n" +
						"local min = 0.01 \n" +
						"local max = remainMoney / stock * 2 \n" +
						"math.randomseed(tonumber(time)) \n" +
						"local money = max * math.random()" +
						"if stock<=0 then \n" +
						"return 0 end \n" +
						"if stock==1 then money= math.floor((remainMoney * 100) + 0.5) / 100 \n" +
						"elseif (money<=0.01) then money = 0.01 \n" +
						"else money = math.floor(money * 100) / 100 end \n" +
						"remainMoney = remainMoney - money \n" +
						"stock = stock - 1 \n" +
						//"remainMoney = remainMoney - ARGV[2] \n" +
						"redis.call('hset',redPackage,'stock',tostring(stock)) \n" + 
						"redis.call('hset',redPackage,'remainMoney',tostring(remainMoney)) \n" + 
						"redis.call('rpush',listkey,ARGV[1]..'-'..tostring(money)) \n" +
						"if stock==0 then \n" + 
						"return 2 end \n" +
						"return 1 \n";
		/*HashOperations<String, Object, Object> opsForHash = redisTemplate.opsForHash();
		int remainSize = Integer.parseInt((String) opsForHash.get("redPack:redPackage_" + redPackageId, "stock"));
		double remainMoney = Double.parseDouble((String) opsForHash.get("redPack:redPackage_" + redPackageId, "remainMoney"));
		if (remainMoney <= 0) {
			return null;
		}
		LeftMoneyPackage leftMoneyPackage = new LeftMoneyPackage(remainSize, remainMoney);
		double randomMoney = getRandomMoney(leftMoneyPackage);
		String grabMoney = String.valueOf(randomMoney);*/
		List<String> keys = new ArrayList<>();
		keys.add(redPackageId);
		String args [] = new String[2];
		args[0] = userId + "-" + System.currentTimeMillis() ;
		//args[1] = grabMoney;
		try {
			//Object object = redisTemplate.opsForHash().get("redPackage_dafecd3e-c963-4734-8e49-be99559d7178", "stock");
			DefaultRedisScript<Long> defaultRedisScript = new DefaultRedisScript<>(script,Long.class);
			Long result = (Long) redisTemplate.execute(defaultRedisScript, keys, args);
			if (result == 0) {
				resultMap.put("code", 0);
				resultMap.put("msg", "红包已被抢光");
				return resultMap;
			}
			if (result == 2) {
				String unitAmount = (String) redisTemplate.opsForHash().get("redPack:redPackage_" + redPackageId, "unitMoney");
				double unitMoney = Double.parseDouble(unitAmount);
				saveUserPackageFromRedis(redPackageId, unitMoney);
			}
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("code", 1);
			resultMap.put("msg", "内部服务器异常");
		}
		resultMap.put("code", 0);
		resultMap.put("msg", "抢红包成功");
		return resultMap;
	}
	
	public static double getRandomMoney(LeftMoneyPackage _leftMoneyPackage) {
	    // remainSize 剩余的红包数量
	    // remainMoney 剩余的钱
	    if (_leftMoneyPackage.getRemainSize() == 1) {
	        //_leftMoneyPackage.setRemainSize(_leftMoneyPackage.getRemainSize() - 1);
	        return (double) Math.round(_leftMoneyPackage.getRemainMoney() * 100) / 100;
	    }
	    Random r     = new Random();
	    double min   = 0.01; //
	    double max   = _leftMoneyPackage.getRemainMoney() / _leftMoneyPackage.getRemainSize() * 2;
	    double money = r.nextDouble() * max;
	    money = money <= min ? 0.01: money;
	    money = Math.floor(money * 100) / 100;
	    /*_leftMoneyPackage.setRemainSize(_leftMoneyPackage.getRemainSize() - 1);
	    _leftMoneyPackage.setRemainMoney(_leftMoneyPackage.getRemainMoney() - money);*/
	    return money;
	}

	@Override
	public Map sendRedPackage(String userId, Double amount, int number) {
		Map result = new HashMap<>();
		String redPackageId = UUID.randomUUID().toString();
		try {
			RedPackage redPackage = new RedPackage();
			redPackage.setId(redPackageId);
			redPackage.setAmount(amount);
			redPackage.setStock(number);
			redPackage.setCreated(new Date());
			int insert = redPackageMapper.insert(redPackage);
			if (insert <= 0) {
				result.put("code", "1");
				result.put("msg", "红包发送失败");
				return result;
			}
			//存入缓存
			HashOperations<String, Object, Object> opsForHash = redisTemplate.opsForHash();
			opsForHash.put("redPack:redPackage_" + redPackageId, "stock", String.valueOf(number));
			opsForHash.put("redPack:redPackage_" + redPackageId, "remainMoney", String.valueOf(amount));
			buildRetrieveRedPackageTimer(redPackageId);
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result.put("code", "1");
			result.put("msg", "内部服务器异常");
			return result;
		}
		result.put("code", "0");
		result.put("msg", "红包发送成功");
		return result;
	}

	@Override
	public void updateRedPackageById(String redPackageId) {
		RedPackage redPackage = new RedPackage();
		redPackage.setId(redPackageId);
		redPackage.setVersion(-1);
		redPackageMapper.updateByPrimaryKeySelective(redPackage);
	}
	
	private void buildRetrieveRedPackageTimer(String redPackageId) throws SchedulerException {
		long startTime = System.currentTimeMillis() + 60 * 1000;
		String name = UUID.randomUUID().toString();
		String group = RetrieveRedPackageTimer.class.getName();
		JobDetail jobDetail = JobBuilder.newJob(RetrieveRedPackageTimer.class).withIdentity(name, group).build();
		jobDetail.getJobDataMap().put("redPackageId", redPackageId);
		Trigger trigger = TriggerBuilder.newTrigger().withIdentity(name, group).startAt(new Date(startTime)).build();
		scheduler.scheduleJob(jobDetail, trigger);
	}


}
