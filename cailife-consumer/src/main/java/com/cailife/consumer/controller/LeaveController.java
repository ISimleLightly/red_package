package com.cailife.consumer.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.cailife.pojo.CailifeSignLeave;
import com.cailife.service.LeaveService;


@RestController
@EnableAutoConfiguration
@RequestMapping("/leave")
public class LeaveController {

	@Reference(version = "1.1.1", timeout = 120000)
	private LeaveService leaveService;
	
	@RequestMapping("list")
	@ResponseBody
	public List<CailifeSignLeave> list() {
		List<CailifeSignLeave> list = leaveService.findAllLeave(0, 0);
		return list;
	}
	
	@RequestMapping("/get/{id}")
	@ResponseBody
	public CailifeSignLeave get(@PathVariable("id") String id) {
		CailifeSignLeave leave = leaveService.getLeaveById(id);
		return leave;
	}
	
	@RequestMapping("addLeave")
	@ResponseBody
	public Map<String, Object> addLeave() {
		CailifeSignLeave leave = new CailifeSignLeave();
		String uuid = UUID.randomUUID().toString();
		leave.setId(uuid);
		leave.setBeizhu("springboot");
		leave.setStatus(Byte.valueOf("0"));
		leaveService.saveLeave(leave);
		Map<String , Object> map = new HashMap<>();
		map.put("code", 200);
		map.put("msg", "添加成功");
		return map;
	}
}
