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

import com.springboot.springredisidempotence.annotation.AccessLimit;
import com.springboot.springredisidempotence.common.Constant;
import com.springboot.springredisidempotence.common.ResponseCode;
import com.springboot.springredisidempotence.exception.ServiceException;
import com.springboot.springredisidempotence.util.IpUtil;
import com.springboot.springredisidempotence.util.JedisUtil;
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
 * @create: 2019/6/24 11:28
 *
 * 接口防刷限流拦截器
 *
 */
public class AccessLimitInterceptor implements HandlerInterceptor {
	@Autowired
	private JedisUtil jedisUtil;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		if (!(handler instanceof HandlerMethod)) {
			return true;
		}

		// 获取使用注解时的方法
		HandlerMethod handlerMethod = (HandlerMethod) handler;
		Method method = handlerMethod.getMethod();

		AccessLimit annotation = method.getAnnotation(AccessLimit.class);

		if (annotation != null) {
			check(annotation, request);
		}
		return true;
	}

	private void check(AccessLimit annotation, HttpServletRequest request) {
		int maxCount = annotation.maxCount();
		int seconds = annotation.seconds();

		// 获取访问链接的具体ip+访问网址路径
		// 将其记录到redis中
		StringBuffer sb = new StringBuffer();
		sb.append(Constant.Redis.ACCESSS_LIMIT_PREFIX).append(IpUtil.getIpAddress(request))
				.append(request.getRequestURI());

		String key = sb.toString();
		// 判断是否存在这个key，存在，增加，判断有效时间和访问最大次数等逻辑
		Boolean exists = jedisUtil.exists(key);

		// 存在
		if (exists) {
			// 获取已经访问次数
			int count = Integer.valueOf(jedisUtil.get(key));

			// 没达到最大限制
			if (count < maxCount) {
				// 获取ttl， 如果过期了，重现设置
				Long ttl = jedisUtil.ttl(key);
				if (ttl <= 0) {
					jedisUtil.set(key, String.valueOf(1), seconds);
				} else {
					// 没过期，增加1
					jedisUtil.set(key, String.valueOf(++count), ttl.intValue());
				}
			} else {
				// 超过最大访问， 抛出异常
				throw new ServiceException(ResponseCode.ACCESS_LIMIT.getMsg());
			}

		} else {
			// 不存在，新增
			jedisUtil.set(key, String.valueOf(1), seconds);
		}

	}


	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

	}
}