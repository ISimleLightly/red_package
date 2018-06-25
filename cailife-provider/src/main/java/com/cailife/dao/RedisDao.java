package com.cailife.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class RedisDao {

	@Autowired
	private StringRedisTemplate stringRedisTemplate;
	
	public void set(String key, String value) {
		stringRedisTemplate.opsForValue().set(key, value);
	}
	
	public String get(String key) {
		String value = stringRedisTemplate.opsForValue().get(key);
		return value;
	}
	
	public void delete(String key) {
		stringRedisTemplate.delete(key);
	}
}
