package com.cailife.service;

import java.util.Map;

import com.cailife.model.QuartzEntity;

public interface JobService {

	Map addJob(QuartzEntity quartz);

	Map pauseJob(QuartzEntity quartzEntity);

	Map resumeJob(QuartzEntity quartzEntity);
}
