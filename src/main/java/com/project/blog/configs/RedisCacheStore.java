package com.project.blog.configs;

import java.util.concurrent.TimeUnit;

import org.hibernate.sql.Template;
import org.springframework.data.redis.core.RedisTemplate;

import lombok.AllArgsConstructor;
import lombok.experimental.NonFinal;


@AllArgsConstructor
public class RedisCacheStore {

private final RedisTemplate deneme;
	
	public void put (final Object key,final Object value,final long expiration) {
		deneme.opsForValue().set(key,value,expiration,TimeUnit.SECONDS);
	}
	
	public Object get(final Object key){
		
		return deneme.opsForValue().get(key);
		
	}
	
}
