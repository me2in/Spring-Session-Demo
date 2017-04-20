package com.wong.learn.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.session.data.redis.RedisFlushMode;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

import redis.clients.jedis.JedisPoolConfig;

/**
 * 相当于原先的applicationcontext.xml
 * 
 * @author wangjuntao
 * @date 2017-4-19
 * @since
 */
@Configuration
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 18000, redisNamespace = "myblog", redisFlushMode = RedisFlushMode.ON_SAVE)
@PropertySource("classpath:appconfig.properties")
//@ComponentScan("com.wong.learn")
public class ApplicationConfig {
	
//	@Resource
//	private Environment env;
	
	//spring会按照properties文件中的key-value自动注入
	@Value("${spring.redis.hostname}")
	private String hostName;
	@Value("${spring.redis.port}")
	private Integer port;
	@Value("${spring.redis.password}")
	private String password;
	@Value("${spring.redis.timeout}")
	private Integer timeOut;
	@Value("${spring.redis.userpool}")
	private Boolean userPool;
	
	/**
	 * spring-session用不到这个bean
	 * @return
	 */
	@Bean
	public RedisTemplate<Object,Object> redisTemplate() {
		RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<Object, Object>();
		redisTemplate.setConnectionFactory(redisConnectionFactory());
		redisTemplate.setKeySerializer(new StringRedisSerializer());
		redisTemplate.setHashKeySerializer(new StringRedisSerializer());
		return redisTemplate;
	}

	/**
	 * EnableRedisHttpSession注释要求必须要有一个RedisConnectionFactory实例
	 * @return
	 */
	@Bean
	public RedisConnectionFactory redisConnectionFactory() {
		JedisConnectionFactory factory = new JedisConnectionFactory();
		factory.setHostName(hostName);
		factory.setPort(port);
		factory.setPassword(password);
		factory.setTimeout(timeOut);
		factory.setUsePool(userPool);
		factory.setPoolConfig(new JedisPoolConfig());
		return factory;
	}


}
