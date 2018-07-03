package com.cailife.provider;

import java.util.HashMap;
import java.util.Map;

import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.alibaba.dubbo.config.annotation.Service;
import com.cailife.model.QuartzEntity;
import com.cailife.service.JobService;

@Service(version = "1.1.1")
public class JobServiceImpl implements JobService {

	private final static Logger logger = LoggerFactory.getLogger(JobServiceImpl.class);
	
	@Autowired @Qualifier("Scheduler")
	private Scheduler scheduler;

	@Override
	public Map addJob(QuartzEntity quartz) {
		String className = quartz.getJobClassName();
		String group = quartz.getJobGroup();
		String cronExpression = quartz.getCronExpression();
		String description = quartz.getDescription();
		Map result = new HashMap<>();
		try {
			Class cls = Class.forName(className);
			cls.newInstance();
			JobDetail jobDetail = JobBuilder.newJob(cls).withIdentity(className, group).build();
			CronScheduleBuilder cronSchedule = CronScheduleBuilder.cronSchedule(cronExpression);
			CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(className, group).startNow().withSchedule(cronSchedule).build();
			scheduler.scheduleJob(jobDetail, trigger);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("code", "1");
			result.put("msg", "内部服务器异常");
		}
		result.put("code", "0");
		result.put("msg", "添加任务成功");
		return result;
	}

	@Override
	public Map pauseJob(QuartzEntity quartzEntity) {
		logger.info("停止定时任务：" + quartzEntity.getJobClassName());
		Map result = new HashMap<>();
		String jobClassName = quartzEntity.getJobClassName();
		String jobGroup = quartzEntity.getJobGroup();
		JobKey key = new JobKey(jobClassName, jobGroup);
		try {
			scheduler.pauseJob(key);
		} catch (SchedulerException e) {
			e.printStackTrace();
			result.put("code", "1");
			result.put("msg", "内部服务器异常");
		}
		result.put("code", "0");
		result.put("msg", "停止任务成功");
		return result;
	}

    @Override
    public Map resumeJob(QuartzEntity quartzEntity) {
        logger.info("恢复定时任务：" + quartzEntity.getJobClassName());
        Map result = new HashMap<>();
        String jobClassName = quartzEntity.getJobClassName();
        String jobGroup = quartzEntity.getJobGroup();
        JobKey key = new JobKey(jobClassName, jobGroup);
        try {
            scheduler.resumeJob(key);
        } catch (SchedulerException e) {
            e.printStackTrace();
            result.put("code", "1");
            result.put("msg", "内部服务器异常,请联系管理员");
        }
        result.put("code", "0");
        result.put("msg", "恢复任务成功");
        return result;
    }

}
