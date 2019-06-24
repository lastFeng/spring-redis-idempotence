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
package com.springboot.springredisidempotence.interceptor;

import com.springboot.springredisidempotence.annotation.ApiIdempotent;
import com.springboot.springredisidempotence.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * <p> Title: </p>
 *
 * <p> Description: </p>
 *
 * @author: Guo.Weifeng
 * @version: 1.0
 * @create: 2019/6/24 11:29
 */
public class ApiIdempotentInterceptor implements HandlerInterceptor {

	@Autowired
	private TokenService tokenService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		// 没有使用该拦截器，放行
		if (!(handler instanceof HandlerMethod)) {
			return true;
		}

		// 使用了该拦截器，进行方法运行前的操作
		HandlerMethod handlerMethod = (HandlerMethod) handler;
		// 获取标注的方法
		Method method = handlerMethod.getMethod();
		// 获取注解
		ApiIdempotent methodAnnotation = method.getAnnotation(ApiIdempotent.class);

		if (methodAnnotation != null) {
			// 幂等性校验，校验通过放行，否则抛出异常
			check(request);
		}
		return true;
	}

	/**
	 * 幂等性校验，如果通过，放行，不通过，抛出自定义异常
	 * */
	private void check(HttpServletRequest request) {
		tokenService.checkToken(request);
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

	}
}