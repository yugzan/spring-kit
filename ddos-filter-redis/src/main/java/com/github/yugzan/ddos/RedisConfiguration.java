package com.github.yugzan.ddos;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @author yongzan
 * @date 2018/04/26
 */
@Configuration
public class RedisConfiguration {
	@Value("${spring.redis.host:127.0.0.1}")
	private String host;

	@Value("${spring.redis.port:6379}")
	private int port;

	@Value("${spring.redis.timeout:1000}")
	private int timeout;

	@Value("${spring.redis.database:1}")
	private int database;

	@Value("${spring.redis.password:#{null}}")
	private String password;

	/**
	 * jedis
	 */
//	@Bean
//	public RedisConnectionFactory jedisSentinelConnectionFactory() {
//		RedisSentinelConfiguration sentinelConfig = new RedisSentinelConfiguration().master("mymaster")
//				.sentinel("127.0.0.1", 26379);
//		return new JedisConnectionFactory(sentinelConfig);
//	}

	@Bean
	@ConditionalOnMissingBean
	public JedisConnectionFactory jedisConnectionFactory() {
		JedisConnectionFactory factory = new JedisConnectionFactory();

		factory.setHostName(host);
		factory.setPort(port);
		factory.setTimeout(timeout);
		factory.setDatabase(database);
		if(password != null) {
			factory.setPassword(password);			
		}
		return factory;
	}
	
	// stringRedisTemplate: defined by method 'stringRedisTemplate'
		@Bean("ddosRedisTemplate")
		public RedisTemplate<String, String> redisTemplate() {
			RedisTemplate<String, String> redisTemplate = new RedisTemplate<String, String>();
			redisTemplate.setConnectionFactory(jedisConnectionFactory());
			redisTemplate.setKeySerializer(new StringRedisSerializer());
			redisTemplate.setValueSerializer(new StringRedisSerializer());
			return redisTemplate;
		}
}
