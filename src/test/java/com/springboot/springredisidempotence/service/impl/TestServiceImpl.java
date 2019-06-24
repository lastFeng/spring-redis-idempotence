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
package com.springboot.springredisidempotence.service.impl;

import com.springboot.springredisidempotence.common.ServerResponse;
import com.springboot.springredisidempotence.service.TestService;
import org.springframework.stereotype.Service;

/**
 * <p> Title: </p>
 *
 * <p> Description: </p>
 *
 * @author: Guo.Weifeng
 * @version: 1.0
 * @create: 2019/6/24 14:10
 */
@Service
public class TestServiceImpl implements TestService {
	@Override
	public ServerResponse testIdempotence() {
		return ServerResponse.success("testIdempotence: success");
	}

	@Override
	public ServerResponse accessLimit() {
		return ServerResponse.success("accessLimit: success");
	}
}