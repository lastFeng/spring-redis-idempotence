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

import com.springboot.springredisidempotence.annotation.AccessLimit;
import com.springboot.springredisidempotence.annotation.ApiIdempotent;
import com.springboot.springredisidempotence.common.ServerResponse;
import com.springboot.springredisidempotence.service.TestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p> Title: </p>
 *
 * <p> Description: </p>
 *
 * @author: Guo.Weifeng
 * @version: 1.0
 * @create: 2019/6/24 14:08
 */
@RestController
@RequestMapping("/test")
@Slf4j
public class TestController {
	@Autowired
	private TestService testService;

	@Autowired
	private AmqpTemplate rabbitTemplate;

	@ApiIdempotent
	@PostMapping("testIdempotence")
	public ServerResponse testIdempotence() {
		return testService.testIdempotence();
	}

	@AccessLimit(maxCount = 5, seconds = 5)
	@PostMapping("accessLimit")
	public ServerResponse accessLimit() {
		return testService.accessLimit();
	}
}