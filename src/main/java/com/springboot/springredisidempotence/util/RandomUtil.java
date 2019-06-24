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

import java.util.Random;
import java.util.UUID;

/**
 * <p> Title: </p>
 *
 * <p> Description: </p>
 *
 * @author: Guo.Weifeng
 * @version: 1.0
 * @create: 2019/6/24 10:44
 */
public class RandomUtil {
	/**所有字符*/
	public static final String allChar = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

	/**单词字符*/
	public static final String letterChar = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

	/**数字字符*/
	public static final String numberChar = "0123456789";

	/**
	 * 随机生成UUID32
	 * */
	public static String UUID32() {
		String str = UUID.randomUUID().toString();
		return str.replace("-", "");
	}

	public static String UUID36() {
		return UUID.randomUUID().toString();
	}

	/**
	 * 生成包含大、小写字符、数字的字符串
	 * @param length length
	 * @return for example: zsK8rCCi
	 * */
	public static String generateStr(int length) {
		StringBuffer sb = new StringBuffer(length);
		Random random = new Random();

		for (int i = 0; i < length; i++) {
			sb.append(allChar.charAt(random.nextInt(allChar.length())));
		}

		return sb.toString();
	}

	/**
	 * 生成纯数字字符串
	 * @param length length
	 * @return all digest
	 * */
	public static String generateDigitalStr(int length) {
		StringBuffer sb = new StringBuffer(length);
		Random random = new Random();

		for (int i = 0; i < length; i++) {
			sb.append(numberChar.charAt(random.nextInt(numberChar.length())));
		}
		return sb.toString();
	}

	/**
	 * 生成只包含大小写的字符串
	 * @param length length
	 * @return all character
	 * */
	public static String generateLetterStr(int length) {
		StringBuffer sb = new StringBuffer(length);
		Random random = new Random();

		for (int i = 0; i < length; i++) {
			sb.append(letterChar.charAt(random.nextInt(letterChar.length())));
		}
		return sb.toString();
	}

	/**
	 * 生成只包含小写字母的字符串
	 * @param length length
	 * @return all lower String
	 * */
	public static String generateLowerStr(int length) {
		return generateLetterStr(length).toLowerCase();
	}

	/**
	 * 生成只包含大写字母的字符串
	 * @param length length
	 * @return all lower String
	 * */
	public static String generateUpperStr(int length) {
		return generateLetterStr(length).toUpperCase();
	}

	/**
	 * 生成纯0字符串
	 * @param length length
	 * @return 00000000
	 * */
	public static String generateZeroStr(int length) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			sb.append('0');
		}
		return sb.toString();
	}

	/**
	 * 根据数字生成字符串，长度不够前面补0
	 *
	 * @param num       数字
	 * @param strLength 字符串长度
	 * @return 如: 00000099
	 */
	public static String generateStrWithZero(int num, int strLength) {
		StringBuffer sb = new StringBuffer();
		String strNum = String.valueOf(num);
		if (strLength - strNum.length() >= 0) {
			sb.append(generateZeroStr(strLength - strNum.length()));
		} else {
			throw new RuntimeException("将数字" + num + "转化为长度为" + strLength + "的字符串异常!");
		}
		sb.append(strNum);
		return sb.toString();
	}
}