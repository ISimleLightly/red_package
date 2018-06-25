package com.cailife.service;

import java.util.List;

import com.cailife.pojo.CailifeSignLeave;


public interface LeaveService {

	List<CailifeSignLeave> findAllLeave(int pageNum, int pageSize);
	
	CailifeSignLeave getLeaveById(String id);
	
	CailifeSignLeave saveLeave(CailifeSignLeave leave);
}
