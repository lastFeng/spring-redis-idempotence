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
package com.springboot.springredisidempotence.exception;

import com.springboot.springredisidempotence.common.ResponseCode;
import com.springboot.springredisidempotence.common.ServerResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <p> Title: </p>
 *
 * <p> Description: </p>
 *
 * @author: Guo.Weifeng
 * @version: 1.0
 * @create: 2019/6/24 13:33
 */
@ControllerAdvice
@Slf4j
public class MyControllerAdvice {
	@ResponseBody
	@ExceptionHandler(ServiceException.class)
	public ServerResponse serviceExceptionHandler(ServiceException se) {
		return ServerResponse.error(se.getMsg());
	}

	@ResponseBody
	@ExceptionHandler(Exception.class)
	public ServerResponse exceptionHandler(Exception e) {
		log.error("Exception: ", e);
		return ServerResponse.error(ResponseCode.SERVER_ERROR.getMsg());
	}
}