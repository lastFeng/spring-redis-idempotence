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
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Date;

/**
 * <p> Title: </p>
 *
 * <p> Description: </p>
 *
 * @author: Guo.Weifeng
 * @version: 1.0
 * @create: 2019/6/24 9:46
 */
@Slf4j
public class JodaTimeUtil {
	/**标准的日期格式*/
	private static final String STANDARD_FORMAT = "yyyy-MM-dd HH:mm:ss";

	/**
	 * Date --> String
	 * @param date need trans date
	 * @return trans string
	 * */
	public static String dateToStr(Date date) {
		return dateToStr(date);
	}

	/**
	 * Date --> String use the custom trans format
	 * @param date need trans date
	 * @param format custom trans format
	 * @return trans string if valid, null if invalid
	 * */
	public static String dateToStr(Date date, String format) {
		if (date == null) {
			return null;
		}

		format = StringUtils.isBlank(format) ? STANDARD_FORMAT : format;
		DateTime dateTime = new DateTime(date);
		return dateTime.toString(format);
	}

	/**
	 * String --> Date
	 * @param timeStr need trans string
	 * @param format trans format
	 * @return Date
	 * */
	public static Date strToDate(String timeStr, String format) {
		if (StringUtils.isBlank(timeStr)) {
			return null;
		}

		format = StringUtils.isBlank(format) ? STANDARD_FORMAT : format;

		DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern(format);

		DateTime dateTime;

		try {
			dateTime = dateTimeFormatter.parseDateTime(timeStr);
		} catch (Exception e) {
			log.error("strToDate error: timeStr:{}", timeStr, e);
			return null;
		}

		return dateTime.toDate();
	}

	/**
	 * 判断date日期是否过期（与当前时间相比）
	 * @param date date
	 * @return true is expired and false is not.
	 * */
	public static Boolean isTimeExpired(Date date) {
		String timeStr = dateToStr(date);
		return isBeforeNow(timeStr);
	}

	/**
	 * 判断字符型日期是否过期（与当前时间相比）
	 * @param timeStr 日期字符串
	 * @return  true is expired or str is invalid; false is not.
	 * */
	public static Boolean isTimeExpired(String timeStr) {
		if (StringUtils.isBlank(timeStr)) {
			return true;
		}
		return isBeforeNow(timeStr);
	}

	/**
	 * 判断日期字符串是否在当前时刻之前
	 * @param timeStr 日期字符串
	 * @return true is expired or str is invalid, and false is not.
	 * */
	private static Boolean isBeforeNow(String timeStr) {
		DateTimeFormatter formatter = DateTimeFormat.forPattern(STANDARD_FORMAT);
		DateTime dateTime;

		try {
			dateTime = DateTime.parse(timeStr, formatter);
		} catch (Exception e) {
			log.error("isBeforeNow error: timeStr: {}", timeStr, e);
			return null;
		}

		return dateTime.isBeforeNow();
	}

	/**
	 * 增加日期天数
	 * @param date date
	 * @param days 增加天数大小
	 * @return new date
	 * */
	public static Date plusDays(Date date, int days) {
		return plusOrMinusDays(date, days, 0);
	}

	/**
	 * 减少日期天数
	 * @param date date
	 * @param days 减少的天数大小
	 * @return new date
	 * */
	public static Date minusDays(Date date, int days) {
		return plusOrMinusDays(date, days, 1);
	}

	/**
	 * 加减天数
	 * @param date date
	 * @param days 天数大小
	 * @param type 操作类型 0 -- plus  1 -- minus
	 * @return new date
	 * */
	private static Date plusOrMinusDays(Date date, int days, int type) {
		if (null == date) {
			return null;
		}

		DateTime dateTime = new DateTime(date);

		if (type == 1){
			dateTime = dateTime.minusDays(days);
		}
		if (type == 0) {
			dateTime = dateTime.plusDays(days);
		}

		return dateTime.toDate();
	}

	/**
	 * 日期增加分钟
	 * @param date date
	 * @param minutes minutes
	 * @return new date
	 * */
	public static Date plusMinutes(Date date, int minutes) {
		return plusOrMinusMinutes(date, minutes, 0);
	}

	/**
	 * 日期减少分钟
	 * @param date date
	 * @param minutes minutes
	 * @return new date
	 * */
	public static Date minusMinutes(Date date, int minutes) {
		return plusOrMinusMinutes(date, minutes, 1);
	}

	/**
	 * 日期分钟操作
	 * @param date date
	 * @param minutes minutes
	 * @param type operation type 0-plus 1-minus
	 * @return new date
	 * */
	private static Date plusOrMinusMinutes(Date date, int minutes, int type) {
		if (date == null) {
			return null;
		}

		DateTime dateTime = new DateTime(date);

		if (type == 0) {
			dateTime = dateTime.plusMinutes(minutes);
		}
		if (type == 1){
			dateTime = dateTime.minusMinutes(minutes);
		}

		return dateTime.toDate();
	}

	/**
	 * 日期+月份操作
	 * @param date date
	 * @param months months
	 * @return new date
	 * */
	public static Date plusMonths(Date date, int months) {
		return plusOrMinusMonths(date, months, 1);
	}

	/**
	 * 日期-月份操作
	 * @param date date
	 * @param months months
	 * @return new date
	 * */
	public static Date minusMonths(Date date, int months) {
		return plusOrMinusMinutes(date, months, 1);
	}

	/**
	 * 日期月份操作
	 * @param date date
	 * @param months months
	 * @param type operation type 0-plus 1-minus
	 * @return new date
	 * */
	private static Date plusOrMinusMonths(Date date, int months, int type) {
		if (date == null) {
			return null;
		}

		DateTime dateTime = new DateTime(date);

		if (type == 0) {
			dateTime = dateTime.plusMonths(months);
		}
		if (type == 1){
			dateTime = dateTime.minusMonths(months);
		}

		return dateTime.toDate();
	}

	/**
	 * 判断target是否在开始和结束时间之内
	 * @param target target date
	 * @param startTime start time
	 * @param endTime end time
	 * @return true is yes and false is no or invalid
	 * */
	public static Boolean isBetweenStartAndEndTime(Date target, Date startTime, Date endTime) {
		if (null == target || null == startTime || null == endTime) {
			return false;
		}
		DateTime dateTime = new DateTime(target);
		return dateTime.isAfter(startTime.getTime()) && dateTime.isBefore(endTime.getTime());
	}
}