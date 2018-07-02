package com.cailife.consumer.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.cailife.model.QuartzEntity;
import com.cailife.service.JobService;

@Controller
@RequestMapping("job")
public class JobController {
	
	@Reference(version="1.1.1")
	private JobService jobService;

	@RequestMapping("add")
	@ResponseBody
	public Map addJob(QuartzEntity quartz) {
		Map addJob = jobService.addJob(quartz);
		return addJob;
	}
}
