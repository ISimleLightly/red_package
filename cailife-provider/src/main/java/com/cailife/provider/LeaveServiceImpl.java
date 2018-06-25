package com.cailife.provider;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;

import com.alibaba.dubbo.config.annotation.Service;
import com.cailife.dao.CailifeSignLeaveMapper;
import com.cailife.pojo.CailifeSignLeave;
import com.cailife.pojo.CailifeSignLeaveExample;
import com.cailife.service.LeaveService;
import com.github.pagehelper.PageHelper;


@CacheConfig(cacheNames = "leave")
@Service(version="1.1.1")
public class LeaveServiceImpl implements LeaveService{
	
	@Autowired
	private CailifeSignLeaveMapper cailifeSignLeaveMapper;

	@Override
	public List<CailifeSignLeave> findAllLeave(int pageNum, int pageSize) {
		PageHelper.startPage(1, 10);
		CailifeSignLeaveExample example = new CailifeSignLeaveExample();
		List<CailifeSignLeave> list = cailifeSignLeaveMapper.selectByExample(example);
		return list;
	}

	@Cacheable(key = "#id")
	@Override
	public CailifeSignLeave getLeaveById(String id) {
		return cailifeSignLeaveMapper.selectByPrimaryKey(id);
	}

	@CachePut(key = "#leave.id")
	@Override
	public CailifeSignLeave saveLeave(CailifeSignLeave leave) {
		cailifeSignLeaveMapper.insert(leave);
		return leave;
		
	}

}
