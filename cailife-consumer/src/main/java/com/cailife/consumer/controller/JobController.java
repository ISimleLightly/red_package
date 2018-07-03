package com.cailife.consumer.controller;

import java.util.HashMap;
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
		Map map = new HashMap<> ();
		if (quartz != null) {
			map = jobService.addJob(quartz);
		}
		return map;
	}

	@RequestMapping("pause")
	@ResponseBody
	public Map pause(QuartzEntity quartzEntity) {
		Map map = new HashMap<> ();
		if (quartzEntity != null) {
			map = jobService.pauseJob(quartzEntity);
		}
		return map;
	}

	@RequestMapping("resume")
	@ResponseBody
	public Map resume(QuartzEntity quartzEntity) {
		Map map = new HashMap<> ();
		if (quartzEntity != null) map = jobService.resumeJob(quartzEntity);
		return map;
	}
}
