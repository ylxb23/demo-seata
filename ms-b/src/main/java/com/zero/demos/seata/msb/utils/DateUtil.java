package com.zero.demos.seata.msb.utils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * 使用jdk1.8提供的api创建的时间工具类
 *
 * @author zero
 */
public class DateUtil {
	private DateUtil() {}
	
	public static final String DATE_TIME_PATTERN_yyyy_MM_dd_HH_mm_ss = "yyyy-MM-dd HH:mm:ss";
	
	public static final String DATE_TIME_PATTERN_yyyy_MM_dd = "yyyy-MM-dd";
	
	/**
	 * 将时间格式化为 "yyyy-MM-dd HH:mm:ss"
	 * @param date
	 * @return
	 */
	public static String format(Date date) {
		return format(date, DATE_TIME_PATTERN_yyyy_MM_dd_HH_mm_ss);
	}
	
	/**
	 * 将时间格式化为字符串
	 * @param date
	 * @param formatter
	 * @return
	 */
	public static String format(Date date, DateTimeFormatter formatter) {
		LocalDateTime ldt = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
		return ldt.format(formatter);
	}
	
	/**
	 * 将时间格式化为字符串
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String format(Date date, String pattern) {
		return format(date, DateTimeFormatter.ofPattern(pattern));
	}

	/**
	 * 解析时间字符串
	 * @param date
	 * @param formatter
	 * @return
	 */
	public static Date parse(String date, DateTimeFormatter formatter) {
		return localDateTime2Date(LocalDateTime.parse(date, formatter));
	}
	
	/**
	 * 解析时间字符串
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static Date parse(String date, String pattern) {
		return parse(date, DateTimeFormatter.ofPattern(pattern));
	}
	
	/**
	 * 将 {@linkplain #java.time.LocalDateTime} 转化为 {@linkplain #java.util.Date}
	 * @param localDateTime
	 * @return
	 */
	public static Date localDateTime2Date(LocalDateTime localDateTime) {
		return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
	}
	
	/**
	 * 将 {@linkplain #java.util.Date}转化为 {@linkplain #java.time.LocalDateTime}
	 * @param date
	 * @return
	 */
	public static LocalDateTime date2LocalDateTime(Date date) {
		return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
	}
}
