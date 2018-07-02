package com.cailife.jobs;

import java.io.Serializable;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import com.cailife.service.RedPackageService;

public class TestJob implements Job, Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private RedPackageService redPackageService;

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		System.out.println("----------测试定时任务----------");
		System.out.println(redPackageService);
	}

}
