package com.aia.portal.redis;

/**
 * 使用接口的方式、方便在单机版的redis和集群版的redis进行切换
 * 使用spring进行bean的注入
 * @author Administrator
 *
 */
public interface JedisClient {
	String set(String key, String value);
	String get(String key);
	Boolean exists(String key);
	Long expire(String key, int seconds);
	Long ttl(String key);
	Long incr(String key);
	Long hset(String key, String field, String value);
	String hget(String key, String field);
	Long hdel(String key, String... field);
}
