/**
 * 优分享VR
 * copy right: youkes.com
 * author:xuming
 * licence:GPL2
 */
package com.youkes.vr.utils;

import java.text.Format;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DateUtil {

	public static final TimeZone tz = TimeZone.getTimeZone("GMT+8:00");
	public static final SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy-MM-dd");

	private static final long ONEDAY = 86400000;
	public static final int SHOW_TYPE_SIMPLE = 0;
	public static final int SHOW_TYPE_COMPLEX = 1;
	public static final int SHOW_TYPE_ALL = 2;
	public static final int SHOW_TYPE_CALL_LOG = 3;
	public static final int SHOW_TYPE_CALL_DETAIL = 4;

	/**
	 * 获取当前当天日期的毫秒数 2012-03-21的毫秒数
	 *
	 * @return
	 */
	public static long getCurrentDayTime() {
		Date d = new Date(System.currentTimeMillis());
		String formatDate = yearFormat.format(d);
		try {
			return (yearFormat.parse(formatDate)).getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public static String formatDate(int year, int month, int day) {
		Date d = new Date(year - 1900, month, day);
		return yearFormat.format(d);
	}

	public static long getDateMills(int year, int month, int day) {
		//Date d = new Date(year, month, day);
		// 1960 4 22
		Calendar calendar = Calendar.getInstance(Locale.CHINA);
		calendar.set(year, month, day);
		TimeZone tz = TimeZone.getDefault();
		calendar.setTimeZone(tz);
		return calendar.getTimeInMillis();
	}

	public static String getDateString(long time, int type) {
		Calendar c = Calendar.getInstance();
		c = Calendar.getInstance(tz);
		c.setTimeInMillis(time);
		long currentTime = System.currentTimeMillis();
		Calendar current_c = Calendar.getInstance();
		current_c = Calendar.getInstance(tz);
		current_c.setTimeInMillis(currentTime);

		int currentYear = current_c.get(Calendar.YEAR);
		int y = c.get(Calendar.YEAR);
		int m = c.get(Calendar.MONTH) + 1;
		int d = c.get(Calendar.DAY_OF_MONTH);
		int hour = c.get(Calendar.HOUR_OF_DAY);
		int minute = c.get(Calendar.MINUTE);
		int second = c.get(Calendar.SECOND);
		long t = currentTime - time;
		long t2 = currentTime - getCurrentDayTime();
		String dateStr = "";
		if (t < t2 && t > 0) {
			if (type == SHOW_TYPE_SIMPLE) {
				dateStr = (hour < 10 ? "0" + hour : hour) + ":"
						+ (minute < 10 ? "0" + minute : minute);
			} else if (type == SHOW_TYPE_COMPLEX) {
				dateStr = "今天  " + (hour < 10 ? "0" + hour : hour) + ":"
						+ (minute < 10 ? "0" + minute : minute);
			} else if (type == SHOW_TYPE_CALL_LOG) {
				dateStr = "今天  " + (hour < 10 ? "0" + hour : hour) + ":"
						+ (minute < 10 ? "0" + minute : minute);
			} else if (type == SHOW_TYPE_CALL_DETAIL) {
				dateStr = "今天  ";
			}else {
				dateStr = (hour < 10 ? "0" + hour : hour) + ":"
						+ (minute < 10 ? "0" + minute : minute) + ":"
						+ (second < 10 ? "0" + second : second);
			}
		} else if (t < (t2 + ONEDAY) && t > 0) {
			if (type == SHOW_TYPE_SIMPLE || type == SHOW_TYPE_CALL_DETAIL) {
				dateStr = "昨天  ";
			} else if (type == SHOW_TYPE_COMPLEX ) {
				dateStr = "昨天  " + (hour < 10 ? "0" + hour : hour) + ":"
						+ (minute < 10 ? "0" + minute : minute);
			} else if (type == SHOW_TYPE_CALL_LOG) {
				dateStr = "昨天  " + (hour < 10 ? "0" + hour : hour) + ":"
						+ (minute < 10 ? "0" + minute : minute);
			} else {
				dateStr = "昨天  " + (hour < 10 ? "0" + hour : hour) + ":"
						+ (minute < 10 ? "0" + minute : minute) + ":"
						+ (second < 10 ? "0" + second : second);
			}
		} else if (y == currentYear) {
			if (type == SHOW_TYPE_SIMPLE) {
				dateStr = (m < 10 ? "0" + m : m) + "/" + (d < 10 ? "0" + d : d);
			} else if (type == SHOW_TYPE_COMPLEX) {
				dateStr = (m < 10 ? "0" + m : m) + "月" + (d < 10 ? "0" + d : d)
						+ "日";
			} else if (type == SHOW_TYPE_CALL_LOG || type == SHOW_TYPE_COMPLEX) {
				dateStr = (m < 10 ? "0" + m : m) + /* 月 */"/"
						+ (d < 10 ? "0" + d : d) + /* 日 */" "
						+ (hour < 10 ? "0" + hour : hour) + ":"
						+ (minute < 10 ? "0" + minute : minute);
			} else if (type == SHOW_TYPE_CALL_DETAIL) {
				dateStr = y + "/" + (m < 10 ? "0" + m : m) + "/"
						+ (d < 10 ? "0" + d : d);
			} else {
				dateStr = (m < 10 ? "0" + m : m) + "月" + (d < 10 ? "0" + d : d)
						+ "日 " + (hour < 10 ? "0" + hour : hour) + ":"
						+ (minute < 10 ? "0" + minute : minute) + ":"
						+ (second < 10 ? "0" + second : second);
			}
		} else {
			if (type == SHOW_TYPE_SIMPLE) {
				dateStr = y + "/" + (m < 10 ? "0" + m : m) + "/"
						+ (d < 10 ? "0" + d : d);
			} else if (type == SHOW_TYPE_COMPLEX ) {
				dateStr = y + "年" + (m < 10 ? "0" + m : m) + "月"
						+ (d < 10 ? "0" + d : d) + "日";
			} else if (type == SHOW_TYPE_CALL_LOG || type == SHOW_TYPE_COMPLEX) {
				dateStr = y + /* 年 */"/" + (m < 10 ? "0" + m : m) + /* 月 */"/"
						+ (d < 10 ? "0" + d : d) + /* 日 */"  "/*
																 * + (hour < 10
																 * ? "0" + hour
																 * : hour) + ":"
																 * + (minute <
																 * 10 ? "0" +
																 * minute :
																 * minute)
																 */;
			} else if (type == SHOW_TYPE_CALL_DETAIL) {
				dateStr = y + "/" + (m < 10 ? "0" + m : m) + "/"
						+ (d < 10 ? "0" + d : d);
			} else {
				dateStr = y + "年" + (m < 10 ? "0" + m : m) + "月"
						+ (d < 10 ? "0" + d : d) + "日 "
						+ (hour < 10 ? "0" + hour : hour) + ":"
						+ (minute < 10 ? "0" + minute : minute) + ":"
						+ (second < 10 ? "0" + second : second);
			}
		}
		return dateStr;
	}

	/**
	 *
	 * @return
	 */
	public static long getCurrentTime() {
		return System.currentTimeMillis() / 1000;
	}

	public static long getActiveTimelong(String result) {
		try {
			Date parse = yearFormat.parse(result);
			return parse.getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return System.currentTimeMillis();
	}




	public static String toHumanReadable(Date myDate) {

		if(myDate==null){
			return "";
		}
		Date date = new Date();
		long nowTime = date.getTime();
		long lastTime = myDate.getTime();

		long dif = nowTime - lastTime;
		if(dif<0){
			dif=0;
		}
		if (dif / 1000 < 60) {
			return dif / 1000 + "秒前";
		}

		if (dif / 1000 / 60 < 60) {
			return dif / 1000 / 60 + "分钟前";
		}
		if (dif / 1000 / 60 / 60 < 24) {
			return dif / 1000 / 60 / 60 + "小时前";
		}

		String datestr = fmtDate(myDate, "-") + " " + fmtTime(myDate, ":");
		if(myDate.getYear()==myDate.getYear()){
			datestr=datestr.substring(5);//fmtTime(myDate, ":");
		}else{
			datestr=datestr.substring(2);
		}
		//datestr.substring(start)
		return datestr;
		
	}

	public static String toHumanReadableSimple(Date myDate) {

		if(myDate==null){
			return "";
		}
		Date date = new Date();
		long nowTime = date.getTime();
		long lastTime = myDate.getTime();

		long dif = nowTime - lastTime;
		if(dif<0){
			dif=0;
		}
		if (dif / 1000 < 60) {
			return dif / 1000 + "秒前";
		}

		if (dif / 1000 / 60 < 60) {
			return dif / 1000 / 60 + "分钟前";
		}
		if (dif / 1000 / 60 / 60 < 24) {
			return dif / 1000 / 60 / 60 + "小时前";
		}
		
		return dif / 1000 / 60 / 60/24 + "天前";
		
		
	}
	
	public static String toHumanReadable2(Date myDate) {

		Date date = new Date();
		long nowTime = date.getTime();
		long lastTime = myDate.getTime();

		int year1=date.getYear();
		int month1=date.getMonth();
		int date1=date.getDate();
		
		int year2=myDate.getYear();
		int month2=myDate.getMonth();
		int date2=myDate.getDate();
		
		if(year1==year2&&month1==month2&&date1==date2){
			if(myDate.getHours()<=12){
				return "上午 "+fmtTime(myDate, ":");
			}else{
				return "下午 "+fmtTime(myDate, ":");
			}
			
		}
		
		long dif = nowTime - lastTime;
		if(dif<0){
			dif=0;
		}
		//24小时内
		if (dif / 1000 / 60 / 60 < 24) {
			return dif / 1000 / 60 / 60 + "小时前";
		}

		String datestr = fmtDate(myDate, "-") + " " + fmtTime(myDate, ":");
		return datestr;
	}

	public static String fmtDate(Date myDate, String seperator) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy" + seperator
				+ "MM" + seperator + "dd");
		formatter.setTimeZone(TimeZone.getTimeZone("GMT+8"));
		String strDate = formatter.format(myDate);
		return strDate;
	}

	public static String fmtTime(Date myDate, String seperator) {
		SimpleDateFormat formatter = new SimpleDateFormat("HH" + seperator
				+ "mm");// + seperator + "ss");
		formatter.setTimeZone(TimeZone.getTimeZone("GMT+8"));
		String strDate = formatter.format(myDate);
		return strDate;

		// HH:mm:ss
	}

	public static String getDateTimeStr(Date dt) {
		int h = dt.getHours();
		int m = dt.getMinutes();
		int s = dt.getSeconds();
		return h + ":" + m + ":" + s;
	}

	public static String getDateDateStr(Date dt) {
		// dt.getY
		int y = dt.getYear() + 1900;
		int m = dt.getMonth() + 1;
		int s = dt.getDate();
		return y + "-" + m + "-" + s;
	}

	public static Date addNowDate(int day) {

		Date date = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, day);
		Date dateBeforeDate = cal.getTime();
		// dateBeforeDate.toString();

		return dateBeforeDate;

	}

	public static Date addDateOffset(Date date, int day) {

		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, day);
		Date dateBeforeDate = cal.getTime();
		dateBeforeDate.toString();

		return dateBeforeDate;

	}

	public static String addNowDateString(int day) {
		Date date = addNowDate(day);
		Format formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
		String s = formatter.format(date);
		return s;
	}

	/**
	 * 字符串转换为java.util.Date<br>
	 * 支持格式为 yyyy.MM.dd G 'at' hh:mm:ss z 如 '2002-1-1 AD at 22:10:59 PSD'<br>
	 * yy/MM/dd HH:mm:ss 如 '2002/1/1 17:55:00'<br>
	 * yy/MM/dd HH:mm:ss pm 如 '2002/1/1 17:55:00 pm'<br>
	 * yy-MM-dd HH:mm:ss 如 '2002-1-1 17:55:00' <br>
	 * yy-MM-dd HH:mm:ss am 如 '2002-1-1 17:55:00 am' <br>
	 * 
	 * @param time
	 *            String 字符串<br>
	 * @return Date 日期<br>
	 */
	public static Date stringToDate(String time) {
		SimpleDateFormat formatter;
		int tempPos = time.indexOf("AD");
		time = time.trim();
		formatter = new SimpleDateFormat("yyyy.MM.dd G 'at' hh:mm:ss z");
		if (tempPos > -1) {
			time = time.substring(0, tempPos) + "公元"
					+ time.substring(tempPos + "AD".length());// china
			formatter = new SimpleDateFormat("yyyy.MM.dd G 'at' hh:mm:ss z");
		}
		tempPos = time.indexOf("-");
		if (tempPos > -1 && (time.indexOf(" ") < 0)) {
			formatter = new SimpleDateFormat("yyyyMMddHHmmssZ");
		} else if ((time.indexOf("/") > -1) && (time.indexOf(" ") > -1)) {
			formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		} else if ((time.indexOf("-") > -1) && (time.indexOf(" ") > -1)) {
			formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		} else if ((time.indexOf("/") > -1) && (time.indexOf("am") > -1)
				|| (time.indexOf("pm") > -1)) {
			formatter = new SimpleDateFormat("yyyy-MM-dd KK:mm:ss a");
		} else if ((time.indexOf("-") > -1) && (time.indexOf("am") > -1)
				|| (time.indexOf("pm") > -1)) {
			formatter = new SimpleDateFormat("yyyy-MM-dd KK:mm:ss a");
		}
		ParsePosition pos = new ParsePosition(0);
		Date ctime = formatter.parse(time, pos);

		return ctime;
	}

	/**
	 * 将java.util.Date 格式转换为字符串格式'yyyy-MM-dd HH:mm:ss'(24小时制)<br>
	 * 如Sat May 11 17:24:21 CST 2002 to '2002-05-11 17:24:21'<br>
	 * 
	 * @param time
	 *            Date 日期<br>
	 * @return String 字符串<br>
	 */

	public static String dateToString(Date time) {
		SimpleDateFormat formatter;
		formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String ctime = formatter.format(time);

		return ctime;
	}

	public static String dateToStringShort(Date time) {
		SimpleDateFormat formatter;
		formatter = new SimpleDateFormat("MM-dd HH:mm");
		String ctime = formatter.format(time);

		return ctime;
	}

	public static String dateToTimeOffStr(Date time) {
		SimpleDateFormat formatter;
		formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String ctime = formatter.format(time);

		return ctime;
	}

	/**
	 * 将java.util.Date 格式转换为字符串格式'yyyy-MM-dd HH:mm:ss a'(12小时制)<br>
	 * 如Sat May 11 17:23:22 CST 2002 to '2002-05-11 05:23:22 下午'<br>
	 * 
	 * @param time
	 *            Date 日期<br>
	 * @param x
	 *            int 任意整数如：1<br>
	 * @return String 字符串<br>
	 */
	public static String dateToString(Date time, int x) {
		SimpleDateFormat formatter;
		formatter = new SimpleDateFormat("yyyy-MM-dd KK:mm:ss a");
		String ctime = formatter.format(time);

		return ctime;
	}

	/**
	 * 取系统当前时间:返回只值为如下形式 2002-10-30 20:24:39
	 * 
	 * @return String
	 */
	public static String Now() {
		return dateToString(new Date());
	}

	/**
	 * 取系统当前时间:返回只值为如下形式 2002-10-30 08:28:56 下午
	 * 
	 * @param hour
	 *            为任意整数
	 * @return String
	 */
	public static String Now(int hour) {
		return dateToString(new Date(), hour);
	}

	/**
	 * 取系统当前时间:返回值为如下形式 2002-10-30
	 * 
	 * @return String
	 */
	public static String getYYYY_MM_DD() {
		return dateToString(new Date()).substring(0, 10);

	}

	/**
	 * 取系统给定时间:返回值为如下形式 2002-10-30
	 * 
	 * @return String
	 */
	public static String getYYYY_MM_DD(String date) {
		return date.substring(0, 10);

	}

	public static String getHour() {
		SimpleDateFormat formatter;
		formatter = new SimpleDateFormat("H");
		String ctime = formatter.format(new Date());
		return ctime;
	}

	public static String getDay() {
		SimpleDateFormat formatter;
		formatter = new SimpleDateFormat("d");
		String ctime = formatter.format(new Date());
		return ctime;
	}

	public static String getMonth() {
		SimpleDateFormat formatter;
		formatter = new SimpleDateFormat("M");
		String ctime = formatter.format(new Date());
		return ctime;
	}

	public static String getYear() {
		SimpleDateFormat formatter;
		formatter = new SimpleDateFormat("yyyy");
		String ctime = formatter.format(new Date());
		return ctime;
	}

	public static String getWeek() {
		SimpleDateFormat formatter;
		formatter = new SimpleDateFormat("E");
		String ctime = formatter.format(new Date());
		return ctime;
	}

	public static String fmtShortEnu(Date myDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
		String strDate = formatter.format(myDate);
		return strDate;
	}

}
