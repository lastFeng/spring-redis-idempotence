/*
 * Copyright 2001-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.springboot.springredisidempotence.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * <p> Title: </p>
 *
 * <p> Description: </p>
 *
 * @author: Guo.Weifeng
 * @version: 1.0
 * @create: 2019/6/24 9:26
 */
@Component
@Slf4j
public class JedisUtil {

	@Autowired
	private JedisPool jedisPool;

	/**
	 * 获取Jedis客户端
	 *
	 * @return jedis client
	 */
	private Jedis getJedis() {
		return jedisPool.getResource();
	}

	/**
	 * 设置Redis值
	 *
	 * @param key   redis key
	 * @param value redis value
	 * @return the operation status
	 */
	public String set(String key, String value) {
		Jedis jedis = null;

		try {
			jedis = getJedis();
			return jedis.set(key, value);
		} catch (Exception e) {
			log.error("set key: {} value: {} error", key, value, e);
			return null;
		} finally {
			close(jedis);
		}
	}

	/**
	 * 设置， 并设置过期时间
	 * @param key redis key
	 * @param value redis value
	 * @param expiredTime the key's expired time
	 * @return the status of operation
	 * */
	public String set(String key, String value, int expiredTime) {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			return jedis.setex(key, expiredTime, value);
		} catch (Exception e) {
			log.error("set key:{} value:{} expiredTime:{} error",
				key, value, expiredTime, e);
			return null;
		} finally {
			close(jedis);
		}
	}

	/**
	 * 获取key值
	 * @param key redis key
	 * @return the key's value
	 * */
	public String get(String key){
		Jedis jedis = null;
		try {
			jedis = getJedis();
			return jedis.get(key);
		} catch (Exception e){
			log.error("get key:{} error", key, e);
			return null;
		} finally {
			close(jedis);
		}
	}

	/**
	 * 删除redis中的值
	 * @param key redis key
	 * @return del the data count
	 * */
	public Long del(String key) {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			return jedis.del(key);
		} catch (Exception e) {
			log.error("del key:{} error", key, e);
			return null;
		} finally {
			close(jedis);
		}
	}

	/**
	 * 判断key是否存在
	 * @param key redis key
	 * @return false: not exists; true: exists
	 * */
	public Boolean exists(String key) {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			return jedis.exists(key);
		} catch (Exception e) {
			log.error("exists key:{} error", key, e);
			return null;
		} finally {
			close(jedis);
		}
	}

	/**
	 * 设置key的过期时间
	 * @param key redis key
	 * @param expiredTime key's expired time
	 * */
	public Long expire(String key, int expiredTime) {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			return jedis.expire(key, expiredTime);
		} catch (Exception e) {
			log.error("expire key:{} error", key, e);
			return null;
		} finally {
			close(jedis);
		}
	}

	/**
	 * 获取key的过期时间
	 * @param key redis key
	 * @return the rest of expire time
	 * */
	public Long ttl(String key) {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			return jedis.ttl(key);
		} catch (Exception e) {
			log.error("ttl key:{} error", key, e);
			return null;
		} finally {
			close(jedis);
		}
	}

	/**
	 * close the jedis client
	 * @param jedis jedis client
	 * */
	private void close(Jedis jedis) {
		if (null != jedis) {
			jedis.close();
		}
	}
}