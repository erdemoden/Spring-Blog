package com.project.blog.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.types.RedisClientInfo;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Connection;


@Configuration
public class RedisConfig {

	@Bean
	public JedisConnectionFactory connectionFactory() {
		RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
		configuration.setHostName("redis-12282.c1.us-east1-2.gce.cloud.redislabs.com");
		configuration.setPort(12282);
		configuration.setUsername("default");
		configuration.setPassword("u5mCEow4zv1gq0JVWUXzyXJMyTtDn43z");
		return new JedisConnectionFactory(configuration);
	}
	
	@Bean
	public RedisTemplate<String, Object> redisTemplate(){
		RedisTemplate<String,Object> template = new RedisTemplate<>();
		template.setConnectionFactory(connectionFactory());
		template.setKeySerializer(new StringRedisSerializer());
		//template.setHashKeySerializer(new StringRedisSerializer());
		//template.setHashKeySerializer(new JdkSerializationRedisSerializer());
		//template.setValueSerializer(new JdkSerializationRedisSerializer());
		//template.setEnableTransactionSupport(true);
		//template.afterPropertiesSet();
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
		return template;
		
	}

	@Bean
	public RedisCacheStore redisCacheStore() {
		return new RedisCacheStore(redisTemplate());
	}
	
	
	
	
}
