package com.cailife.service;

import java.util.Map;

public interface RedPackageService {
	
	Map<String, String> grabRedPackage(String redPackageId, String userId);

	Map grabRedPackageByRedis(String redPackageId, String userId);
	
	Map sendRedPackage(String userId, Double amount, int number);
	
	void updateRedPackageById(String redPackageId);
}
