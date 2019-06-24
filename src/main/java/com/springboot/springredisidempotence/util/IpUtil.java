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

import javax.servlet.http.HttpServletRequest;

/**
 * <p> Title: </p>
 *
 * <p> Description: </p>
 *
 * @author: Guo.Weifeng
 * @version: 1.0
 * @create: 2019/6/24 9:16
 * 获取Ip地址的工具类
 */
public class IpUtil {

	/**
	 * 获取客户真实ip地址
	 * @param request 客户端请求
	 * @return ip address string
	 * */
	public static String getIpAddress(HttpServletRequest request) {
		// 多个途径获取ip地址，由于不确定请求头的内容，直接包装成一个方法
		String ip = request.getHeader("x-forwarded-for");

		// IP地址在“Proxy-Client-IP”属性上
		if (ip == null || ip.length()==0 || "unknow".equalsIgnoreCase(ip)){
			ip = request.getHeader("Proxy-Client-IP");
		}

		// IP地址在“WL-Proxy-Client-IP”属性上
		if (ip == null || ip.length()==0 || "unknow".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}

		// IP地址在“HTTP_CLIENT_IP”属性上
		if (ip == null || ip.length()==0 || "unknow".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}

		// IP地址在“HTTP_X_FORWARDED_FOR”属性上
		if (ip == null || ip.length()==0 || "unknow".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}

		// // IP地址从远程地址获取
		if (ip == null || ip.length()==0 || "unknow".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
}