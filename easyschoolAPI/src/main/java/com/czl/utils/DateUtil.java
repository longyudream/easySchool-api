package com.czl.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

	/**
	 * 获取当前日期是星期几<br>
	 * 
	 * @param date
	 * @return 当前日期是星期几
	 */
	public static String getWeekOfDate(Date date) {
		String[] weekDays = { "星期一", "星期二", "星期三", "星期四", "星期五", "星期六", "星期日" };
		int weekday = getWeekOfDay(date);
		return weekDays[weekday - 1];
	}

	/**
	 * 获取当前日期是星期几<br>
	 * 
	 * @param date
	 * @return int 1=周一；2=周二；...；6=周六；7=周日。
	 */
	public static int getWeekOfDay(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);

		int week = cal.get(Calendar.DAY_OF_WEEK);
		if (week == 1) {
			week = 7;
		} else {
			week += -1;
		}

		return week;
	}

	public static int getWeekOfDay(String date, String sFmt) {
		Date myDate = toDate(date, sFmt);
		return getWeekOfDay(myDate);
	}

	public static int getWeekOfDay(String date) {
		Date myDate = toDate(date, "yyyy-MM-dd");
		return getWeekOfDay(myDate);
	}

	public static Date dateAddOrReduce(Date date, int days) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);

		// 让日期加减
		calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + days);
		return calendar.getTime();
	}

	public static int getYear(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.YEAR);
	}

	public static int getMonth(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.MONTH) + 1;
	}

	public static int getMonthDay(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.DAY_OF_MONTH);
	}

	public static Date toDate(String dateString, String sFmt) {
		SimpleDateFormat sdf = new SimpleDateFormat(sFmt);
		Date rslt = null;
		try {
			rslt = sdf.parse(dateString);
		} catch (Exception e) {
			return null;
		}
		return rslt;
	}

	public static Date getSysDate() {
		return toDate(DateUtil.formatDatetimeNull(new Date()), "yyyy-MM-dd HH:mm:ss");
	}

	public static Date toDateYMD3S(String dateString) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date rslt = null;
		try {
			rslt = sdf.parse(dateString);
		} catch (Exception e) {
			return null;
		}
		return rslt;
	}

	public static Date toDateYMD(String dateString) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date rslt = null;
		try {
			rslt = sdf.parse(dateString);
		} catch (Exception e) {
			return null;
		}
		return rslt;
	}
	
	public static Date toDateT(String dateString) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		Date rslt = null;
		try {
			rslt = sdf.parse(dateString);
		} catch (Exception e) {
			return null;
		}
		return rslt;
	}
	
	public static Date toDateT3S(String dateString) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
		Date rslt = null;
		try {
			rslt = sdf.parse(dateString);
		} catch (Exception e) {
			return null;
		}
		return rslt;
	}

	public static String formatDate(java.util.Date date, String sPattern) {
		if (date == null)
			return "";
		String sDate;
		java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat(sPattern);
		sDate = formatter.format(date);
		return sDate;
	}

	public static String formatDateYMD6(java.util.Date date) {
		if (date == null)
			return "";
		String sDate;
		java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyMMdd");
		sDate = formatter.format(date);
		return sDate;
	}

	/**
	 * 日期格式化（yyyy-MM-dd）
	 * 
	 * @param date
	 * @return
	 */
	public static String formatDateYMD(java.util.Date date) {
		if (date == null)
			return "";
		String sDate;
		java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyy-MM-dd");
		sDate = formatter.format(date);
		return sDate;
	}

	public static String formatDateYMDCn(java.util.Date date) {
		if (date == null)
			return "";
		String sDate;
		java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyy年MM月dd日");
		sDate = formatter.format(date);
		return sDate;
	}

	public static String formatDateYMD3S(java.util.Date date) {
		if (date == null)
			return "";
		String sDate;
		java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		sDate = formatter.format(date);
		return sDate;
	}

	public static String formatDatesss(java.util.Date date) {
		if (date == null)
			return "";
		String sDate;
		java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyyMMddHHmmssSSS");
		sDate = formatter.format(date);
		return sDate;
	}

	public static String formatDate(String date, String fromPattern, String toPattern) {
		if (date == null || "".equals(date.trim()))
			return "";
		Date tempDate = toDate(date, fromPattern);
		String sDate = formatDate(tempDate, toPattern);
		return sDate;
	}

	@SuppressWarnings("deprecation")
	public static int calDiffMonths(Date startDate, Date endDate) {
		int startYearInt = startDate.getYear();
		int startMonthInt = startDate.getMonth();
		int endYearInt = endDate.getYear();
		int endMonthInt = endDate.getMonth();
		int length = (endYearInt - startYearInt) * 12 + (endMonthInt - startMonthInt);

		return length;
	}

	public static String formatDatetimeNull(java.util.Date date) {
		if (date == null)
			return null;
		String sDate;
		java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		sDate = formatter.format(date);
		return sDate;
	}

	/**
	 * 获取月末日期
	 * 
	 * @param date
	 * @return
	 */
	public static Date getMonthEndDate(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, 1);
		calendar.set(Calendar.DAY_OF_MONTH, 0);
		return calendar.getTime();
	}

}
