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

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;

/**
 * <p> Title: </p>
 *
 * <p> Description: </p>
 *
 * @author: Guo.Weifeng
 * @version: 1.0
 * @create: 2019/6/24 10:21
 * Json 工具类
 */
@Slf4j
public class JsonUtil {
	/**ObjectMapper对象*/
	private static ObjectMapper objectMapper = new ObjectMapper();
	/**日期格式*/
	private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

	/**
	 * 设置ObjectMapper对象的属性
	 * */
	static {
		// 对象的所有字段全部列入
		objectMapper.setSerializationInclusion(JsonInclude.Include.ALWAYS);
		// 取消迷人转换timestamps形式
		objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		// 忽略空bean转json的错误
		objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		// 统一日期格式
		objectMapper.setDateFormat(new SimpleDateFormat(DATE_FORMAT));
		// 忽略在json字符串中存在，但在java对象中不存在对应属性的情况，防止错误
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}

	/**
	 * Object --> String
	 * @param obj 泛型对象
	 * @return string if success , null if failed
	 * */
	public static <T> String objToStr(T obj) {
		if (null == obj) {
			return null;
		}

		try {
			return obj instanceof String ? (String) obj : objectMapper.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			log.warn("objToStr error: ", e);
			return null;
		}
	}

	/**
	 * String --> Object
	 * @param str string
	 * @param clazz trans class name
	 * @return clazz object
	 * */
	public static <T> T strToObj(String str, Class<T> clazz) {
		if (StringUtils.isBlank(str) || clazz == null) {
			return null;
		}

		try {
			return clazz.equals(String.class) ? (T) str : objectMapper.readValue(str, clazz);
		} catch (Exception e) {
			log.warn("strToObj error: ", e);
			return null;
		}
	}

	/**
	 * String --> Object
	 * @param str string
	 * @param typeReference trans type reference
	 * @return clazz object
	 * */
	public static <T> T strToObj(String str, TypeReference<T> typeReference) {
		if (StringUtils.isBlank(str) || typeReference == null) {
			return null;
		}

		try {
			return (T) (typeReference.getType().equals(String.class) ? str : objectMapper.readValue(str, typeReference));
		} catch (Exception e) {
			log.warn("strToObj error: ", e);
			return null;
		}
	}
}