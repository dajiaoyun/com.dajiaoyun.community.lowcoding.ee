package com.dajiaoyun.community.lowcoding.ee.util.date;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import com.dajiaoyun.community.lowcoding.model.TableObject;

public class FlexDate {
	
	/**
	 * 输入格式 2014/1/6 22:54:00
	 * @param trildate
	 * @return返回格式 2014-01-06
	 */
	public static String getFullDate(String trildate) {
		String ret=trildate;
		if(trildate!=null&&!trildate.equals("")){
			trildate=trildate.trim();
			trildate=trildate.split(" ")[0];
			trildate=trildate.trim();
			trildate=trildate.replaceAll("/", "-");
			 //2014-4-16  17:55:20 2014-1-6  22:54:00
			String[] t=trildate.split("-");
			String year=t[0];
			if(year.length()==2){
				year="20"+year; 
			}
			String month=t[1];
			if(month.length()<2){
				month="0"+month;
			}
			String day=t[2];
			if(day.length()<2){
				day="0"+day;
			}
			ret=year+"-"+month+"-"+day;
		}
		
		return ret;
	}
	/**
	 * YYYY-MM-DD HH:MM:SS
	 * 
	 * @return
	 */
	public static String getFullDate() {
		String ret = "";
		Date trialTime = new Date(System.currentTimeMillis());
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(trialTime);

		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		int day_of_month = calendar.get(Calendar.DAY_OF_MONTH);
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		int minute = calendar.get(Calendar.MINUTE);
		int second = calendar.get(Calendar.SECOND);
		ret = year + "-";
		if (month < 10) {
			ret = ret + "0" + month + "-";
		} else {
			ret = ret + month + "-";
		}
		if (day_of_month < 10) {
			ret = ret + "0" + day_of_month;
		} else {
			ret = ret + day_of_month;
		}
		ret = ret + " ";
		if (hour < 10) {
			ret = ret + "0" + hour + ":";
		} else {
			ret = ret + hour + ":";
		}
		if (minute < 10) {
			ret = ret + "0" + minute + ":";
		} else {
			ret = ret + minute + ":";
		}
		if (second < 10) {
			ret = ret + "0" + second;
		} else {
			ret = ret + second;
		}
		return ret;
	}

	/**
	 * 在当前时间之后，再加多少时间
	 * 
	 * @param currentdatetime
	 * @param hour
	 * @return
	 */
	public static String getafterdatetime(String currentdatetime, int time, int type) {
		// type =0 灏忔椂 =1 鍒嗛挓 2绉�
		java.util.Calendar cal = getFullCalendarDate(currentdatetime);
		if (type == 0) {
			cal.add(java.util.Calendar.HOUR_OF_DAY, time);
		} else if (type == 1) {
			cal = getCalendarDate(new Date((cal.getTimeInMillis() + time * 60 * 1000)));
		}else{
			cal = getCalendarDate(new Date((cal.getTimeInMillis() + time *  1000)));
		}
		return getFullDate(cal);
	}
	
	/**
	 * 获得当前月对应的下一个月的时间
	 */
	public static String getnextmonth(String currentdatetime) {
		java.util.Calendar cal = getCalendarDate(currentdatetime);
		cal.add(java.util.Calendar.MONTH, 1);
		return getDate(cal);
	}
	
	/**
	 * YYYY-MM-DD HH:MM:SS
	 * 
	 * @return
	 */
	public static String getFullDate(Calendar calendar) {
		String ret = "";
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		int day_of_month = calendar.get(Calendar.DAY_OF_MONTH);
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		int minute = calendar.get(Calendar.MINUTE);
		int second = calendar.get(Calendar.SECOND);
		ret = year + "-";
		if (month < 10) {
			ret = ret + "0" + month + "-";
		} else {
			ret = ret + month + "-";
		}
		if (day_of_month < 10) {
			ret = ret + "0" + day_of_month;
		} else {
			ret = ret + day_of_month;
		}
		ret = ret + " ";
		if (hour < 10) {
			ret = ret + "0" + hour + ":";
		} else {
			ret = ret + hour + ":";
		}
		if (minute < 10) {
			ret = ret + "0" + minute + ":";
		} else {
			ret = ret + minute + ":";
		}
		if (second < 10) {
			ret = ret + "0" + second;
		} else {
			ret = ret + second;
		}
		return ret;
	}

	/**
	 * 杩斿洖 YYYY-MM-DD HH:MM:SS
	 * 
	 * @param epoch
	 * @return
	 */
	public static String getFullDate(long epoch) {
		Date nowTime = new Date(epoch);
		SimpleDateFormat sdFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String retStrFormatNowDate = sdFormatter.format(nowTime);
		return retStrFormatNowDate;
	}

	/**
	 * YYYY-MM-DD HH:MM:SS
	 * 
	 * @return
	 */
	public static String getFullDate(Date trialTime) {
		String ret = "";
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(trialTime);

		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		int day_of_month = calendar.get(Calendar.DAY_OF_MONTH);
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		int minute = calendar.get(Calendar.MINUTE);
		int second = calendar.get(Calendar.SECOND);
		ret = year + "-";
		if (month < 10) {
			ret = ret + "0" + month + "-";
		} else {
			ret = ret + month + "-";
		}
		if (day_of_month < 10) {
			ret = ret + "0" + day_of_month;
		} else {
			ret = ret + day_of_month;
		}
		ret = ret + " ";
		if (hour < 10) {
			ret = ret + "0" + hour + ":";
		} else {
			ret = ret + hour + ":";
		}
		if (minute < 10) {
			ret = ret + "0" + minute + ":";
		} else {
			ret = ret + minute + ":";
		}
		if (second < 10) {
			ret = ret + "0" + second;
		} else {
			ret = ret + second;
		}
		return ret;
	}

	/**
	 * YYYYMMDDHHMMSSMILL
	 * 
	 * @return
	 */
	public static String getFullDatetime() {
		String ret = "";
		Date trialTime = new Date(System.currentTimeMillis());
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(trialTime);

		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		int day_of_month = calendar.get(Calendar.DAY_OF_MONTH);
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		int minute = calendar.get(Calendar.MINUTE);
		int second = calendar.get(Calendar.SECOND);
		int millsecond = calendar.get(Calendar.MILLISECOND);
		ret = String.valueOf(year);
		if (month < 10) {
			ret = ret + "0" + month;
		} else {
			ret = ret + month;
		}
		if (day_of_month < 10) {
			ret = ret + "0" + day_of_month;
		} else {
			ret = ret + day_of_month;
		}
		if (hour < 10) {
			ret = ret + "0" + hour;
		} else {
			ret = ret + hour;
		}
		if (minute < 10) {
			ret = ret + "0" + minute;
		} else {
			ret = ret + minute;
		}
		if (second < 10) {
			ret = ret + "0" + second;
		} else {
			ret = ret + second;
		}
		ret = ret + millsecond;
		return ret;
	}

	/**
	 * 
	 * @return YYYYMMDD
	 */
	public static String getShortCurrentDate() {
		String ret = "";
		Date trialTime = new Date(System.currentTimeMillis());
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(trialTime);

		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		int day_of_month = calendar.get(Calendar.DAY_OF_MONTH);
		ret = String.valueOf(year);
		if (month < 10) {
			ret = ret + "0" + month;
		} else {
			ret = ret + month;
		}
		if (day_of_month < 10) {
			ret = ret + "0" + day_of_month;
		} else {
			ret = ret + day_of_month;
		}
		return ret;
	}

	/**
	 * 
	 * @return YYMMDD
	 */
	public static String getAbsShortCurrentDate() {
		String ret = "";
		Date trialTime = new Date(System.currentTimeMillis());
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(trialTime);

		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		int day_of_month = calendar.get(Calendar.DAY_OF_MONTH);
		ret = String.valueOf(year);
		ret = ret.substring(2);
		if (month < 10) {
			ret = ret + "0" + month;
		} else {
			ret = ret + month;
		}
		if (day_of_month < 10) {
			ret = ret + "0" + day_of_month;
		} else {
			ret = ret + day_of_month;
		}
		return ret;
	}

	/**
	 * 
	 * @param trialTime
	 * @return YYYY-MM-DD
	 */
	public static String getDate(Date trialTime) {
		String ret = "";
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(trialTime);

		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		int day_of_month = calendar.get(Calendar.DAY_OF_MONTH);
		ret = year + "-";
		if (month < 10) {
			ret = ret + "0" + month + "-";
		} else {
			ret = ret + month + "-";
		}
		if (day_of_month < 10) {
			ret = ret + "0" + day_of_month;
		} else {
			ret = ret + day_of_month;
		}
		return ret;
	}

	/**
	 * 
	 * @return YYYY-MM-DD
	 */
	public static String getCurrentDate() {
		String ret = "";
		Date trialTime = new Date(System.currentTimeMillis());
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(trialTime);

		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		int day_of_month = calendar.get(Calendar.DAY_OF_MONTH);
		ret = year + "-";
		if (month < 10) {
			ret = ret + "0" + month + "-";
		} else {
			ret = ret + month + "-";
		}
		if (day_of_month < 10) {
			ret = ret + "0" + day_of_month;
		} else {
			ret = ret + day_of_month;
		}
		return ret;
	}

	public static String convertCalendarToDate(Calendar date) {
		return getDate(date);
	}

	public static Calendar getCalendarDate(String dateV) {
		Calendar argument = Calendar.getInstance();
		Date trialTime;
		try {
			trialTime = (Date) new SimpleDateFormat("yyyy-MM-dd").parse(dateV);
			argument.setTime(trialTime);
			return argument;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 杞崲 yyyy-MM-dd HH:mm:ss
	 * 
	 * @param dateV
	 * @return
	 */
	public static Calendar getFullCalendarDate(String dateV) {
		Calendar argument = Calendar.getInstance();
		Date trialTime;
		try {
			if(dateV.length()==10){
				dateV=dateV+" 00:00:01";
			}
			trialTime = (Date) new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateV);
			argument.setTime(trialTime);
			return argument;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Calendar getCalendarDate(Date trialTime) {
		Calendar argument = Calendar.getInstance();
		try {
			argument.setTime(trialTime);
			return argument;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Calendar getCalendarDate() {
		Calendar currentdate = Calendar.getInstance();
		return currentdate;
	}

	public static java.util.Date convertCalendarToDate2(Calendar date) {
		return date.getTime();
	}

	public static int getYear(Calendar date) {
		return date.get(Calendar.YEAR) + 1900;
	}
	
	public static int getYear() {
		Calendar currentdate = Calendar.getInstance();
		return currentdate.get(Calendar.YEAR)+ 1900;
	}

	public static int getMonth(Calendar date) {
		return date.get(Calendar.MONTH) + 1;
	}
	
	public static int getMonth() {
		Calendar date = Calendar.getInstance();
		return date.get(Calendar.MONTH)+ 1;
	}
	/**
	 * 上月同期时间
	 * @return
	 */
	public static String getPreviousDate() {
		Calendar cal = getCalendarDate();
		cal.add(java.util.Calendar.MONTH, -1);
		return getDate(cal);
	}
	public static String getPreviousDate(String dat) {
		Calendar cal = getCalendarDate(dat);
		cal.add(java.util.Calendar.MONTH, -1);
		return getDate(cal);
	}
	/**
	 * 
	 * @return 201209
	 */
	public static String getYearMonth() {
		String ret = "";
		Date trialTime = new Date(System.currentTimeMillis());
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(trialTime);

		String year = String.valueOf(calendar.get(Calendar.YEAR));
		int month = calendar.get(Calendar.MONTH) + 1;
		ret = year;
		if (month < 10) {
			ret = ret + "0" + month;
		} else {
			ret = ret + month;
		}
		return ret;
	}

	public static int getYear(String dat) {
		if (dat == null || dat.length() < 10) {
			return 0;
		}
		String ret = dat.substring(0, 4);
		return Integer.parseInt(ret);
	}

	public static int getMonth(String dat) {
		if (dat == null || dat.length() < 10) {
			return 0;
		}
		String ret = dat.substring(5, 7);
		return Integer.parseInt(ret);
	}

	public static int getWeek() {
		Calendar applydate = getCalendarDate();
		return applydate.get(Calendar.DAY_OF_WEEK); // SUNDAY =
													// 1;MONDAY=2;SATURDAY = 7;
	}

	public static int getWeek(Date date) {
		Calendar applydate = getCalendarDate(date);
		return applydate.get(Calendar.DAY_OF_WEEK); // SUNDAY =
													// 1;MONDAY=2;SATURDAY = 7;
	}
	
	/**
	 * 返回 星期 一/星期 二/星期 三 ...
	 * @param date
	 * @return
	 */
	public static String getTitleWeek(Date date) {
		Calendar applydate = getCalendarDate(date);
		int week= applydate.get(Calendar.DAY_OF_WEEK); // SUNDAY =// 1;MONDAY=2;SATURDAY = 7;
		String ret="";
		switch(week){
			case 1:
				ret="星期日";
				break;
			case 2:
				ret="星期一";
				break;
			case 3:
				ret="星期二";
				break;
			case 4:
				ret="星期三";
				break;
			case 5:
				ret="星期四";
				break;
			case 6:
				ret="星期五";
				break;
			case 7:
				ret="星期六";
				break;
		}
		return ret;	
	}
	public static int getWeek(String date) {
		Calendar applydate = getCalendarDate(date);
		return applydate.get(Calendar.DAY_OF_WEEK); // SUNDAY =
													// 1;MONDAY=2;SATURDAY = 7;
	}

	/**
	 * format:YYYY-MM-DD
	 * 
	 * @param applydate
	 * @return
	 */
	public static String getDate(Calendar applydate) {
		if (applydate == null)
			return "";
		String ret = "";
		int year = applydate.get(Calendar.YEAR);
		int month =applydate.get(Calendar.MONTH) + 1;
		int date = applydate.get(Calendar.DAY_OF_MONTH);
		ret = String.valueOf(year) + "-";
		if (month < 10) {
			ret = ret + "0" + String.valueOf(month) + "-";
		} else {
			ret = ret + String.valueOf(month) + "-";
		}
		if (date < 10) {
			ret = ret + "0" + String.valueOf(date);
		} else {
			ret = ret + String.valueOf(date);
		}
		return ret;
	}

	public static int getDay(String fullDate) {
		return Integer.parseInt(fullDate.substring(8, 10));
	}

	public static int getHour(String fullDate) {
		return Integer.parseInt(fullDate.substring(11, 13));
	}

	public static int getMin(String fullDate) {
		return Integer.parseInt(fullDate.substring(14, 16));
	}

	public static int getSec(String fullDate) {
		return Integer.parseInt(fullDate.substring(17));
	}

	public static java.sql.Date getDate() {
		java.sql.Date trialTime = new java.sql.Date(System.currentTimeMillis());
		return trialTime;
	}

	/**
	 * YYYY-MM-DD
	 * 
	 * @param currentdate
	 * @param days
	 * @return
	 */
	public static String getnextdate(String currentdate, int amount) {
		Calendar cdate = getCalendarDate(currentdate);
		cdate.add(Calendar.DATE, amount);
		return getDate(cdate);
	}

	/**
	 * date1是否在 date2之前 即date1日期<date2日期
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static boolean before(String date1, String date2) {
		Calendar cdate1 = getFullCalendarDate(date1);
		Calendar cdate2 = getFullCalendarDate(date2);
		return cdate1.before(cdate2);
	}

	public static Date getbeforedate(int amount) {
		Calendar cdate = getCalendarDate();
//		long d = 24 * amount;
//		long l = cdate.getTimeInMillis() / (3600 * 1000) - d;
//		cdate.setTimeInMillis(l * 1000 * 3600);
//		return cdate.getTime();
		cdate.set(Calendar.DATE,cdate.get(Calendar.DATE)-amount);
		return cdate.getTime();
	}
	public static Date getbeforedate(String currentdate, int amount) {
		Calendar cdate = getCalendarDate(currentdate);
		cdate.set(Calendar.DATE,cdate.get(Calendar.DATE)-amount);
		return cdate.getTime();
	}


	
	/**
	 * currentdate之后多少天
	 * @param currentdate
	 * @param amount
	 * @return
	 */
	public static Date getafterdate(String currentdate, int amount) {
		Calendar cdate = getCalendarDate(currentdate);
		long intenl=24 * 60 * 60 * amount * 1000l;
		long l = cdate.getTimeInMillis() + intenl;		
		cdate.setTimeInMillis(l);
		return cdate.getTime();
	}

	/**
	 * 两个日期相差的天数
	 * 
	 * @param d1
	 *            后一个日期
	 * @param d2
	 *            前一个日期
	 * @return
	 */
	public static long getDaysBetween(String d1, String d2) {
		Long l_d1 = FlexDate.getUnixDate(d1);
		Long l_d2 = FlexDate.getUnixDate(d2);

		long days = (l_d1 - l_d2) / (3600 * 24);
		return days;
	}
	
	/**
	 * 两个日期相差多少秒
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static long getSecondsBetween(String d1, String d2) {
		Long l_d1 = FlexDate.getUnixDate(d1);
		Long l_d2 = FlexDate.getUnixDate(d2);

		long seconds = (l_d1 - l_d2);
		return seconds;
	}
	
	public static java.util.Date getDate(String trilDate) {
		if (trilDate == null) {
			return null;
		}
		int year = getYear(trilDate);
		int month = getMonth(trilDate);
		int day = getDay(trilDate);
		int hour = 0;
		int min = 0;
		int sec = 0;
		if (trilDate.length() > 10) {
			hour = getHour(trilDate);
			min = getMin(trilDate);
			sec = getSec(trilDate);
		}
		return new java.util.Date(year - 1900, month - 1, day, hour, min, sec);
	}

	/**
	 * YYYY-MM-DD HH:MM:SS
	 * 
	 * @param epoch Unix时间
	 *            
	 * @return
	 */
	public static String getFullDateFromUnixTime(long epoch) {
		String date = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date(epoch * 1000));
		return date;
	}
	
	/**
	 * 获取每月最后一天
	 * @param sDate1
	 * @return
	 */
	public static int getLastDayOfMonth(Date sDate1) {
		Calendar cDay1 = Calendar.getInstance();
		cDay1.setTime(sDate1);
		int lastDay = cDay1.getActualMaximum(Calendar.DAY_OF_MONTH);

		return lastDay;
	}

	/**
	 * 获取每月最后一天
	 * @param sDate1
	 * @return
	 */
	public static int getLastDayOfMonth(String trilDate) {
		Date date=FlexDate.getDate(trilDate);
		return getLastDayOfMonth(date);
	}

	/**
	 * 获取每月最后一天
	 * @param sDate1
	 * @return
	 */
	public static int getLastDayOfMonth() {
		Date date=FlexDate.getDate();
		return getLastDayOfMonth(date);
	}
	
	/**
	 * day of month
	 * @param date
	 * @return
	 */
	public static int getDayofMonth(String date) {
		Date trialTime=FlexDate.getDate(date);
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(trialTime);
		int day_of_month = calendar.get(Calendar.DAY_OF_MONTH);
		return day_of_month;
	}
	
	/**
	 * 获取指定日期的最后一天
	 * @param date
	 * @return
	 */
	public static String getLastdate(String date){
		String lastdate="";
		int lastday=FlexDate.getLastDayOfMonth(date);
		int year=FlexDate.getYear(date);
		int month=FlexDate.getMonth(date);
		lastdate=String.valueOf(year);
		if(month<10){
			lastdate=lastdate+"-"+"0"+month;
		}else{
			lastdate=lastdate+"-"+month;
		}
		lastdate=lastdate+"-"+lastday;
		return lastdate;
	}
	
	public static String getPrevoiosMonthFirstDate() {
		String date1=getPreviousDate();
		Calendar calendar=FlexDate.getCalendarDate(date1);
		int year=calendar.get(Calendar.YEAR);
		int month=calendar.get(Calendar.MONTH)+1;
		String firstdate=String.valueOf(year);
		if(month<10){
			firstdate=firstdate+"-"+"0"+month;
		}else{
			firstdate=firstdate+"-"+month;
		}
		firstdate=firstdate+"-"+"01";
		
		return firstdate;
		
	}
	public static String getPreviousMonthLastDate() {
		String firstdate=getPrevoiosMonthFirstDate();
		String lastdate=FlexDate.getLastdate(firstdate);
		return lastdate;
		
	}
	/**
	 * 获取指定日期的第一天
	 * @param date
	 * @return
	 */
	public static String getFirstdate(String date){
		String firstdate="";
		int year=FlexDate.getYear(date);
		int month=FlexDate.getMonth(date);
		firstdate=String.valueOf(year);
		if(month<10){
			firstdate=firstdate+"-"+"0"+month;
		}else{
			firstdate=firstdate+"-"+month;
		}
		firstdate=firstdate+"-"+"01";
		return firstdate;
	}
	
	public static String getNextMonthoffirstday(){
		String date=FlexDate.getnextmonth(FlexDate.getCurrentDate());
		String ret="";
		int year=FlexDate.getYear(date);
		int month=FlexDate.getMonth(date);
		if(month<10){
			ret=year+"-"+"0"+month+"-"+"01";
		}else{
			ret=year+"-"+month+"-"+"01";
		}
		return ret;
	}
	public static String getNextMonthoffirstday(String date1){
		String date=FlexDate.getnextmonth(date1);
		String ret="";
		int year=FlexDate.getYear(date);
		int month=FlexDate.getMonth(date);
		if(month<10){
			ret=year+"-"+"0"+month+"-"+"01";
		}else{
			ret=year+"-"+month+"-"+"01";
		}
		return ret;
	}
	public static String getNextMonthoflastday(){
		String date=getNextMonthoffirstday();
		return FlexDate.getLastdate(date);
	}
	public static String getNextMonthoflastday(String date1){
		String date=getNextMonthoffirstday(date1);
		return FlexDate.getLastdate(date);
	}
	
	/**
	 * 剩余天数
	 * @param seconds
	 * @return
	 */
	public static TableObject getremaintimes(long seconds){
		TableObject ret=new TableObject();
		long t=3600*24;
		long days=seconds/t; //澶氬皯澶�
		ret.setAttribute("days",String.valueOf(days));
		long hours=(seconds-t*days)/3600; //灏忔椂
		ret.setAttribute("hours",String.valueOf(hours));
		long minutes=(seconds-t*days-hours*3600)/60;
		ret.setAttribute("minutes",String.valueOf(minutes));
		long second=seconds-t*days-hours*3600-minutes*60;
		ret.setAttribute("seconds",String.valueOf(second));
		return ret;
	}
	/**
	 * 
	 * @param trilDate
	 * @return
	 */
	public static long getUnixDate(String trilDate) {
		Date date = getDate(trilDate);
		return date.getTime() / 1000;
	}

	public static void main(String[] args) {
		// System.out.print(getafterdatetime("2012-05-16 15:35:00",30,1));
		// String startdatetime = getFullDate();
		// startdatetime = FlexDate.getDate(getbeforedate(3));
		// startdatetime = startdatetime.substring(0, startdatetime.length() -
		// 2) + "00";
		// System.out.println(startdatetime);
		// System.out.print(getafterdatetime(startdatetime,30,1));
		// System.out.print(before("2012-10-01 01:01:01", startdatetime));
		//long date = FlexDate.getDaysBetween("2013-08-02", "2013-08-04");
//		int s=getDayofMonth("2013-08-12");
//		System.out.println(getUnixDate("2014-01-12 10:08:40")); //1389056978   2012-03-31 11:53:19
//		System.out.println(getFullDateFromUnixTime(1397244722));
//		System.out.println(s);
//		getDaysBetween("2015-02-16","2014-03-24");
	//	String cashdate="20140808143715";
		//cashdate=cashdate.substring(0, 4)+"-"+cashdate.substring(4, 6)+"-"+cashdate.substring(6,8)+" "+cashdate.substring(8, 10)+":"+cashdate.substring(10, 12)+":"+cashdate.substring(12);
//		long d=getUnixDate("2016-06-02 09:02:11");
		//1432088702
//		String t=getFullDateFromUnixTime(1517673600l);//
//		
//		String date1=FlexDate.getFullDate();
//		String date2="2016-12-05 12:12:12";
//		boolean isd=FlexDate.before(date1, date2);
		
		long date1=FlexDate.getUnixDate("2018-11-22 00:00:00");
		System.out.println("");
	}
}
