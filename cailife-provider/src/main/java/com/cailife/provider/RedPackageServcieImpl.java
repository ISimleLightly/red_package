package com.cailife.provider;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.assertj.core.util.Strings.StringToAppend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties.Jedis;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration.JedisPoolingClientConfigurationBuilder;
import org.springframework.data.redis.connection.jedis.JedisConnection;
import org.springframework.data.redis.core.BoundListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.cailife.dao.RedPackageMapper;
import com.cailife.dao.UserRedPackageMapper;
import com.cailife.pojo.RedPackage;
import com.cailife.pojo.RedPackageExample;
import com.cailife.pojo.UserRedPackage;
import com.cailife.service.RedPackageService;

@Service(version="1.1.1")
public class RedPackageServcieImpl implements RedPackageService {
	
	@Autowired
	private RedPackageMapper redPackageMapper;
	
	@Autowired
	private UserRedPackageMapper userRedPackageMapper;
	
	@Autowired
	private RedisTemplate redisTemplate;

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
	private void saveUserPackageFromRedis(String redPackageId, Double unitMoney) throws Exception {
		final int TIME_SIZE = 1000;
		BoundListOperations ops = redisTemplate.boundListOps("redPackageList_" + redPackageId);
		Long size = ops.size();
		int count = 0;
		long times = size % TIME_SIZE == 0 ? size / TIME_SIZE : size / TIME_SIZE + 1;
		List<String> redPackageList = null;
		for (long i = 0; i < times; i ++) {
			if (i == 0) {
				redPackageList = ops.range(0, size);
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
				userRedPackage.setGrabMoney(unitMoney);
				userRedPackList.add(userRedPackage);
			}
			count += userRedPackageMapper.batchInsertUserRedPack(userRedPackList);
		}
	}
	
	@SuppressWarnings("unchecked")
	public Map grabRedPackageByRedis(String redPackageId, String userId) {
		String script = "local listkey = 'redPackageList_'..KEYS[1] \n" + 
						"local stock = tonumber(reids.call('hget','redPackage_'..KEYS[1],'stock')) \n" + 
						"IF stock <= 0 then \n" +
						"return 0 end \n" +
						"stock = stock - 1 \n" +
						"redis.call('hset','redPackage','stock',tostring(stock)) \n" + 
						"redis.call('rpush',listkey,ARGV[1]) \n" +
						"IF stock == 0 then \n" + 
						"return 2 end \n" +
						"return 1 \n";
		String arg = userId + "-" + System.currentTimeMillis();
		List<String> keys = new ArrayList<>();
		keys.add(redPackageId);
		try {
			DefaultRedisScript<String> defaultRedisScript = new DefaultRedisScript<>(script);
			defaultRedisScript.setScriptText(script);
			defaultRedisScript.setResultType(String.class);
			Long result = (Long) redisTemplate.execute(defaultRedisScript, keys, arg);
			if (result == 2) {
				Double unitMoney = (Double) redisTemplate.opsForHash().get("redPackage_" + redPackageId, "unitMoney");
				saveUserPackageFromRedis(redPackageId, unitMoney);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
