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
package com.springboot.springredisidempotence.controller;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * <p> Title: </p>
 *
 * <p> Description: </p>
 *
 * @author: Guo.Weifeng
 * @version: 1.0
 * @create: 2019/6/24 13:54
 */
@RestController
@RequestMapping("/redisson")
public class RedissonController {
	@Autowired
	private RedissonClient redissonClient;

	@RequestMapping("testRedisson")
	public String testRedisson() {
		RLock lock = redissonClient.getLock("testRedissonLock");
		boolean locked = false;

		try {
			locked = lock.tryLock(0, 10, TimeUnit.SECONDS);

			if (locked) {
				Thread.sleep(30000);
				return "ok..................";
			}
			else {
				return "获取锁失败............";
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
			return "获取锁异常...........";
		} finally {
			if (!locked) {
				return "获取锁失败";
			}
			lock.unlock();
		}
	}
}