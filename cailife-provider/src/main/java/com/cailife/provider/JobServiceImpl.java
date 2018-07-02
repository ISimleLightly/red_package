package com.cailife.provider;

import java.util.HashMap;
import java.util.Map;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.TriggerBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.alibaba.dubbo.config.annotation.Service;
import com.cailife.model.QuartzEntity;
import com.cailife.service.JobService;

@Service(version = "1.1.1")
public class JobServiceImpl implements JobService {
	
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

}
