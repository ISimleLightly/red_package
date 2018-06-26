package com.cailife.consumer.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.cailife.service.RedPackageService;

@Controller
@RequestMapping("redPackage")
public class RedPackageController {

	@Reference(version = "1.1.1", timeout = 120000)
	private RedPackageService redPackageService;
	
	@RequestMapping("grab")
	@ResponseBody
	public Map grabRedPackage(String redPackageId, String userId) {
		try {
			Map<String, String> map = redPackageService.grabRedPackage(redPackageId, userId);
			return map;
		} catch (Exception e) {
			return null;
		}
	}
	
	@RequestMapping("grab/redis")
	@ResponseBody
	public Map grabRedPackageByRedis(String redPackageId, String userId) {
		try {
			Map<String, String> map = redPackageService.grabRedPackageByRedis(redPackageId, userId);
			return map;
		} catch (Exception e) {
			return null;
		}
	}
}
