package com.cailife.timers;

import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.cailife.service.RedPackageService;

public class RetrieveRedPackageTimer extends QuartzJobBean{
	
	private final static Logger logger = LoggerFactory.getLogger(RetrieveRedPackageTimer.class);
	
	@Autowired
	private RedPackageService redPackageService;

	@Override
	protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
		logger.info("【进入红包回收定时任务】");
		JobDataMap jobDataMap = jobExecutionContext.getJobDetail().getJobDataMap();
		String redPackageId = (String) jobDataMap.get("redPackageId");
		redPackageService.updateRedPackageById(redPackageId);
		logger.info("【红包回收成功】");
	}

}
