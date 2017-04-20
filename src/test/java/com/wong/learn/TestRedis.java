package com.wong.learn;

import java.util.Set;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.BoundSetOperations;
import org.springframework.data.redis.core.RedisTemplate;

public class TestRedis extends BaseTest{
	
	@Resource
	private RedisTemplate<Object,Object> redisTemplate;
	
	@Test
	public void testRead(){
		
		BoundHashOperations<Object,Object,Object> opearot = redisTemplate.boundHashOps("test");
		
		opearot.put("wang", 12122);
		
		BoundSetOperations<Object,Object> set = redisTemplate.boundSetOps("testSet");
		
		long addcount = set.add(1,2,3,4,5,6,7);
		System.out.println(addcount);
		
		Set<Object> values = set.members();
		
		for(Object oo : values){
			System.out.println(oo.toString());
		}
		
//		System.out.println(JSON.toJSONString(set));
		
		
	}
}
