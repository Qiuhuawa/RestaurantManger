package org.zkpk.cs.common.utils;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

public class TimestampUtil {

	/**
	 * 得到当前时间
	 * 
	 * @return
	 */
	public static Timestamp crunttime() {
		return new Timestamp(System.currentTimeMillis());
	}

	// 得到当天的小时数
	public static int getCruntHour() {
		Calendar c = Calendar.getInstance();
		return c.get(Calendar.HOUR_OF_DAY);
	}

	public static String getCurrentDate() {
		Timestamp d = crunttime();
		return d.toString().substring(0, 10);
	}

	public static String getCurrentDateTime() {
		Timestamp d = crunttime();
		return d.toString().substring(0, 19);
	}

	public static String getStrDate(Timestamp t) {
		return t.toString().substring(0, 10);
	}

	public static String getStrDateTime(Timestamp t) {
		return t.toString().substring(0, 19);
	}

	public static String getStrIntervalDate(String days) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -Integer.parseInt(days));
		String strBeforeDays = sdf.format(cal.getTime());
		return strBeforeDays;
	}

	public static Date parseDateTime(String dt) {
		Date jDt = new Date();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			if (dt.length() >= 19) {
				jDt = sdf.parse(dt);
			} else if (dt.length() >= 16) {
				sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				jDt = sdf.parse(dt);
			} else if (dt.length() >= 10) {
				sdf = new SimpleDateFormat("yyyy-MM-dd");
				jDt = sdf.parse(dt);
			} else {
				jDt = null;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return jDt;
	}

	public static String parseDateTime(Date date) {
		String s = null;
		try {
			SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			s = f.format(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return s;
	}

	public static String parseShortDateTime(Date date) {
		String s = null;
		try {
			SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			s = f.format(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return s;
	}

	public static String parseShortDateMMDD(Date date) {
		String s = null;
		try {
			SimpleDateFormat f = new SimpleDateFormat("yyyy/MM/dd");
			s = f.format(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return s;
	}

	public static String parseDateHm(Date date) {
		String s = null;
		try {
			SimpleDateFormat f = new SimpleDateFormat("HH:mm");
			s = f.format(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return s;
	}

	public static String random() {
		String s = null;
		try {
			SimpleDateFormat f = new SimpleDateFormat("SSS");
			s = f.format(new Date());
			s += ((int) (Math.random() * 900) + 100);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return s;
	}

	public static String getLongDate(Date date) {
		String s = null;
		try {
			SimpleDateFormat f = new SimpleDateFormat("yyyyMMdd");
			s = f.format(date);
			s += ((int) (Math.random() * 900) + 100);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return s;
	}

	public static String parseShortDate(Date date) {
		String s = null;
		try {
			SimpleDateFormat f = new SimpleDateFormat("yy.MM");
			s = f.format(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return s;
	}

	public static Date parseDate(String dt) {
		Date jDt = new Date();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			if (dt.length() >= 8) {
				jDt = sdf.parse(dt);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return jDt;
	}

	public static String parseDate(Date date) {
		String s = null;
		try {
			SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
			s = f.format(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return s;
	}

	public static String getDateDirPath(String base) throws IOException {
		Date date = new Date();
		SimpleDateFormat y = new SimpleDateFormat("yyyy");
		SimpleDateFormat m = new SimpleDateFormat("MM");
		SimpleDateFormat d = new SimpleDateFormat("dd");
		String stry = y.format(date);
		String strm = m.format(date);
		String strd = d.format(date);
		File fy = new File(base + "/" + stry);
		File fm = new File(base + "/" + stry + "/" + strm);
		File fd = new File(base + "/" + stry + "/" + strm + "/" + strd);
		if (!fy.exists()) {
			fy.mkdir();
		}
		if (!fm.exists()) {
			fm.mkdir();
		}
		if (!fd.exists()) {
			fd.mkdir();
		}

		return "/" + stry + "/" + strm + "/" + strd + "/";
	}

	public static String getLongDateFromShortDate(String dt) {
		String strDT = dt;
		try {
			if (strDT != null && strDT.length() <= 10) {
				strDT = dt.trim() + " 00:00:00";
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return strDT;
	}

	@SuppressWarnings("deprecation")
	public static String getShortDateToHHMM(String dt) {
		String jDt = dt;
		try {
			if (jDt != null && jDt.length() <= 10) {
				jDt = jDt + " 00:00";
			}
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			jDt = sdf.parse(jDt).toLocaleString();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return jDt;
	}

	public static String formatDateToHHMM(String dateStr) {
		String resultDate = null;
		try {
			if (dateStr.length() > 10) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:ss");
				Date date = sdf.parse(dateStr);
				resultDate = sdf.format(date);
			} else
				resultDate = dateStr;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return resultDate;
	}

	@SuppressWarnings("deprecation")
	public static Timestamp date(String str) {
		Timestamp tp = null;
		if (str.length() <= 10) {
			String[] string = str.trim().split("-");
			int one = Integer.parseInt(string[0]) - 1900;
			int two = Integer.parseInt(string[1]) - 1;
			int three = Integer.parseInt(string[2]);
			tp = new Timestamp(one, two, three, 0, 0, 0, 0);
		}
		return tp;
	}

	@SuppressWarnings("deprecation")
	public static Timestamp datetime(String str) {
		Timestamp tp = null;
		if (str.length() >= 19) {
			String[] string = str.trim().split(" ");
			String[] date = string[0].split("-");
			String[] time = string[1].split(":");
			int date1 = Integer.parseInt(date[0]) - 1900;
			int date2 = Integer.parseInt(date[1]) - 1;
			int date3 = Integer.parseInt(date[2]);
			int time1 = Integer.parseInt(time[0]);
			int time2 = Integer.parseInt(time[1]);
			int time3 = Integer.parseInt(time[2]);
			tp = new Timestamp(date1, date2, date3, time1, time2, time3, 0);
		} else if (str.length() >= 16) {
			String[] string = str.trim().split(" ");
			String[] date = string[0].split("-");
			String[] time = string[1].split(":");
			int date1 = Integer.parseInt(date[0]) - 1900;
			int date2 = Integer.parseInt(date[1]) - 1;
			int date3 = Integer.parseInt(date[2]);
			int time1 = Integer.parseInt(time[0]);
			int time2 = Integer.parseInt(time[1]);
			tp = new Timestamp(date1, date2, date3, time1, time2, 0, 0);
		} else if (str.length() >= 10) {
			String[] date = str.split("-");
			int date1 = Integer.parseInt(date[0]) - 1900;
			int date2 = Integer.parseInt(date[1]) - 1;
			int date3 = Integer.parseInt(date[2]);
			tp = new Timestamp(date1, date2, date3, 0, 0, 0, 0);
		}
		return tp;
	}

	@SuppressWarnings("deprecation")
	public static Timestamp datetimeHm(String str) {
		Timestamp tp = null;
		if (str.length() > 10) {
			String[] string = str.trim().split(" ");
			String[] date = string[0].split("-");
			String[] time = string[1].split(":");
			int date1 = Integer.parseInt(date[0]) - 1900;
			int date2 = Integer.parseInt(date[1]) - 1;
			int date3 = Integer.parseInt(date[2]);
			int time1 = Integer.parseInt(time[0]);
			int time2 = Integer.parseInt(time[1]);
			tp = new Timestamp(date1, date2, date3, time1, time2, 0, 0);
		}
		return tp;
	}

	private static int getMondayPlus() {
		Calendar calendar = Calendar.getInstance();
		int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
		return (dayOfWeek == 1) ? -6 : 2 - dayOfWeek;
	}

	public static Date getMondayOfWeek(int week) {
		int mondayPlus = getMondayPlus(); // 相距周一的天数差
		GregorianCalendar current = new GregorianCalendar();
		current.add(GregorianCalendar.DATE, mondayPlus + 7 * week);
		return current.getTime();
	}

	public static Date getDay(Date date, int day) {
		GregorianCalendar c = new GregorianCalendar();
		c.setTime(date);
		c.add(GregorianCalendar.DATE, day);
		return c.getTime();
	}

	// 比较两个时间差
	public static long getDay(Timestamp nowDate, Timestamp lastDate) {
		long d = 0;
		long time1 = nowDate.getTime();
		long time2 = lastDate.getTime();
		if (time1 > time2) {
			d = (time1 - time2) / (1000 * 60 * 60 * 24);
		} else {
			d = (time2 - time1) / (1000 * 60 * 60 * 24);
		}
		return d;
	}

	// 比较两个时间差
	public static long getSecond(Timestamp nowDate, Timestamp lastDate) {
		long d = 0;
		long time1 = nowDate.getTime();
		long time2 = lastDate.getTime();
		if (time1 > time2) {
			d = (time1 - time2) / (1000);
		} else {
			d = (time2 - time1) / (1000);
		}
		return d;
	}

	public static String[] getDaysOfWeek(int week) {
		String[] days = new String[8];
		Date monday = getMondayOfWeek(week);
		days[0] = getStrDate(new Timestamp(getDay(monday, -1).getTime()));
		days[1] = getStrDate(new Timestamp(monday.getTime()));
		Timestamp t;
		for (int i = 1; i < 7; i++) {
			t = new Timestamp(getDay(monday, i).getTime());
			days[1 + i] = getStrDate(t);
		}
		return days;
	}

	public static Date mccUTC2Date(long utc) {
		Date d = new Date();
		d.setTime(utc * 1000);
		return d;
	}

	public static long mccDate2UTC(String str) {
		Date d = parseDateTime(str);
		return (long) d.getTime() / (long) 1000;
	}
	
	/**
     * 函数功能描述:UTC时间转本地时间格式
     * @param utcTime UTC时间
     * @param utcTimePatten UTC时间格式
     * @param localTimePatten   本地时间格式
     * @return 本地时间格式的时间
     * "2017-11-27T03:16:03.944Z", "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
     * eg:utc2Local("2017-06-14 09:37:50.788+08:00", "yyyy-MM-dd HH:mm:ss.SSSXXX", "yyyy-MM-dd HH:mm:ss.SSS")
     */
	public static String utc2Local(String utcTime, String utcTimePatten, String localTimePatten) {
		SimpleDateFormat utcFormater = new SimpleDateFormat(utcTimePatten);
		utcFormater.setTimeZone(TimeZone.getTimeZone("UTC"));// 时区定义并进行时间获取
		Date gpsUTCDate = null;
		try {
			gpsUTCDate = utcFormater.parse(utcTime);
		} catch (ParseException e) {
			return utcTime;
		}
		SimpleDateFormat localFormater = new SimpleDateFormat(localTimePatten);
		localFormater.setTimeZone(TimeZone.getDefault());
		String localTime = localFormater.format(gpsUTCDate.getTime());
		return localTime;
	}

    /**
     * 函数功能描述:UTC时间转本地时间格式
     * @param utcTime UTC时间
     * @param localTimePattern 本地时间格式(要转换的本地时间格式)
     * @return 本地时间格式的时间
     */
	public static String utc2Local(String utcTime, String localTimePattern) {
		String utcTimePattern = "yyyy-MM-dd";
		String subTime = utcTime.substring(10);// UTC时间格式以 yyyy-MM-dd 开头,将utc时间的前10位截取掉,之后是含有多时区时间格式信息的数据

		// 处理当后缀为:+8:00时,转换为:+08:00 或 -8:00转换为-08:00
		if (subTime.indexOf("+") != -1) {
			subTime = changeUtcSuffix(subTime, "+");
		}
		if (subTime.indexOf("-") != -1) {
			subTime = changeUtcSuffix(subTime, "-");
		}
		utcTime = utcTime.substring(0, 10) + subTime;

		// 依据传入函数的utc时间,得到对应的utc时间格式
		// 步骤一:处理 T
		if (utcTime.indexOf("T") != -1) {
			utcTimePattern = utcTimePattern + "'T'";
		}

		// 步骤二:处理毫秒SSS
		if (utcTime.indexOf(".") != -1) {
			utcTimePattern = utcTimePattern + "HH:mm:ss.SSS";
		} else {
			utcTimePattern = utcTimePattern + "HH:mm:ss";
		}

		// 步骤三:处理时区问题
		if (subTime.indexOf("+") != -1 || subTime.indexOf("-") != -1) {
			utcTimePattern = utcTimePattern + "XXX";
		} else if (subTime.indexOf("Z") != -1) {
			utcTimePattern = utcTimePattern + "'Z'";
		}

		if ("yyyy-MM-dd HH:mm:ss".equals(utcTimePattern) || "yyyy-MM-dd HH:mm:ss.SSS".equals(utcTimePattern)) {
			return utcTime;
		}

		SimpleDateFormat utcFormater = new SimpleDateFormat(utcTimePattern);
		utcFormater.setTimeZone(TimeZone.getTimeZone("UTC"));
		Date gpsUtcDate = null;
		try {
			gpsUtcDate = utcFormater.parse(utcTime);
		} catch (Exception e) {
			return utcTime;
		}
		SimpleDateFormat localFormater = new SimpleDateFormat(localTimePattern);
		localFormater.setTimeZone(TimeZone.getDefault());
		String localTime = localFormater.format(gpsUtcDate.getTime());
		return localTime;
	}

    /**
     * 函数功能描述:修改时间格式后缀
     * 函数使用场景:处理当后缀为:+8:00时,转换为:+08:00 或 -8:00转换为-08:00
     * @param subTime
     * @param sign
     * @return
     */
    private static String changeUtcSuffix(String subTime, String sign){
        String timeSuffix = null;
        String[] splitTimeArrayOne = subTime.split("\\" + sign);
        String[] splitTimeArrayTwo = splitTimeArrayOne[1].split(":");
        if(splitTimeArrayTwo[0].length() < 2){
            timeSuffix = "+" + "0" + splitTimeArrayTwo[0] + ":" + splitTimeArrayTwo[1];
            subTime = splitTimeArrayOne[0] + timeSuffix;
            return subTime;
        }
        return subTime;
    }

    /**
     * 函数功能描述:获取本地时区的表示(比如:第八区-->+08:00)
     * @return
     */
    public static String getTimeZoneByNumExpress(){
        Calendar cal = Calendar.getInstance();
        TimeZone timeZone = cal.getTimeZone();
        int rawOffset = timeZone.getRawOffset();
        int timeZoneByNumExpress = rawOffset/3600/1000;
        String timeZoneByNumExpressStr = "";
        if(timeZoneByNumExpress > 0 && timeZoneByNumExpress < 10){
            timeZoneByNumExpressStr = "+" + "0" + timeZoneByNumExpress + ":" + "00";
        }
        else if(timeZoneByNumExpress >= 10){
            timeZoneByNumExpressStr = "+" + timeZoneByNumExpress + ":" + "00";
        }
        else if(timeZoneByNumExpress > -10 && timeZoneByNumExpress < 0){
            timeZoneByNumExpress = Math.abs(timeZoneByNumExpress);
            timeZoneByNumExpressStr = "-" + "0" + timeZoneByNumExpress + ":" + "00";
        }else if(timeZoneByNumExpress <= -10){
            timeZoneByNumExpress = Math.abs(timeZoneByNumExpress);
            timeZoneByNumExpressStr = "-" + timeZoneByNumExpress + ":" + "00";
        }else{
            timeZoneByNumExpressStr = "Z";
        }
        return timeZoneByNumExpressStr;
    } 

	public static String getPreviousMonth(int month) {
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(new Date());
		cal1.add(Calendar.MONTH, -month);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		return formatter.format(cal1.getTime());
	}

	public static boolean getDifferMinute(long utc, int minute) {
		Date newDate = new Date();
		GregorianCalendar c = new GregorianCalendar();
		c.setTime(newDate);
		c.add(GregorianCalendar.MINUTE, -minute);
		return (c.getTime().getTime() - utc * 1000) > 0 ? true : false;
	}

	public static String compareDate(Date nowDate, Date compareD1, Date compareD2) {
		if (null != compareD1 && null != compareD2) {
			if ((nowDate.getTime() > compareD1.getTime()) && (nowDate.getTime() < compareD2.getTime())) {
				return "1";
			}
		}
		return "0";
	}

	public static Date getCurNextMin(Date tdate, int min) {
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(tdate);
		cal1.add(Calendar.MINUTE, min);
		return cal1.getTime();
	}

	// 得到两个时间的中间
	public static Date getCenterDate(Date beginDate, Date endDate) {
		int min = (int) ((endDate.getTime() - beginDate.getTime()) / (1000 * 2 * 60));
		return getCurNextMin(beginDate, min);
	}

	/**
	 * 某个时间点前后几天的时间(不含当天)
	 * 
	 * @return
	 */
	public static List<Date> getCountDay(int day, Date date) {
		List<Date> dateList = new ArrayList<Date>();
		if (day >= 0) {
			for (int i = 1; i <= day; i++) {
				dateList.add(getDay(date, i));
			}
		} else {
			for (int i = day; i < 0; i++) {
				dateList.add(getDay(date, i));
			}
		}
		return dateList;
	}

	/**
	 * 某个时间点前后几天的日期时间字符不含当天
	 * @return
	 */
	public static List<String> getCountStrDay(int day, Date date) {
		List<String> dateList = new ArrayList<String>();
		if (day >= 0) {
			for (int i = 0; i < day; i++) {
				dateList.add(parseDate(getDay(date, i)));
			}
		} else {
			for (int i = day; i < 0; i++) {
				dateList.add(parseDate(getDay(date, i)));
			}
		}
		return dateList;
	}

	/**
	 * 根据日期获得所在周的日期
	 * 
	 * @param mdate
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static List<Date> dateToWeek(Date mdate) {
		int b = mdate.getDay();
		if (b == 0)
			b = 7;
		Date fdate;
		List<Date> list = new ArrayList<Date>();
		Long fTime = mdate.getTime() - b * 24 * 3600000;
		for (int a = 1; a <= 7; a++) {
			fdate = new Date();
			fdate.setTime(fTime + (a * 24 * 3600000));
			// list.add(a-1, fdate);
			list.add(a - 1, parseDate(parseDate(fdate)));
		}
		return list;
	}

	/**
	 * 转化为汉字版 星期几
	 * 
	 * @param mdate
	 * @return
	 */
	public static String parseWeek(Date mdate) {
		// 定义输出日期格式
		SimpleDateFormat sdf = new SimpleDateFormat("EEE");
		return sdf.format(mdate);
	}

	/**
	 * @param begin
	 *            Date类型
	 * @param end
	 *            Date类型
	 * @return 获取两个日期间的所有日期
	 */
	public static List<Date> getBetweenDates(Date begin, Date end) {
		List<Date> result = new ArrayList<Date>();
		Calendar tempStart = Calendar.getInstance();
		tempStart.setTime(begin);

		while (begin.getTime() <= end.getTime()) {
			result.add(tempStart.getTime());
			tempStart.add(Calendar.DAY_OF_YEAR, 1);
			begin = tempStart.getTime();
		}

		return result;
	}

	/**
	 * @param begin
	 *            字符串类型
	 * @param end
	 *            字符串类型
	 * @return 获取两个日期间的所有日期
	 */
	public static List<Date> getBetweenDates(String begin, String end) {
		Date begin2 = parseDate(begin);
		Date end2 = parseDate(end);

		List<Date> result = new ArrayList<Date>();
		Calendar tempStart = Calendar.getInstance();
		tempStart.setTime(begin2);

		while (begin2.getTime() <= end2.getTime()) {
			result.add(tempStart.getTime());
			tempStart.add(Calendar.DAY_OF_YEAR, 1);
			begin2 = tempStart.getTime();
		}

		return result;
	}

	public static String getDateBefore(Date d, int day) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar now = Calendar.getInstance();
		now.setTime(d);
		now.set(Calendar.DATE, now.get(Calendar.DATE) - day);
		Date date = now.getTime();
		return sdf.format(date);
	}

	 /** 
     * 两个时间相差距离多少天多少小时多少分多少秒 
     * @param str1 时间参数 1 格式：1990-01-01 12:00:00 
     * @param str2 时间参数 2 格式：2009-01-01 12:00:00 
     * @return String 返回值为：xx天xx小时xx分xx秒 
     */  
	public static String getDistanceTime(Date one, Date two) {
		long day = 0;
		long hour = 0;
		long min = 0;
		long sec = 0;
		long time1 = one.getTime();
		long time2 = two.getTime();
		long diff;
		if (time1 < time2) {
			diff = time2 - time1;
		} else {
			diff = time1 - time2;
		}
		day = diff / (24 * 60 * 60 * 1000);
		hour = (diff / (60 * 60 * 1000) - day * 24);
		min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);
		sec = (diff / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
		return day + "," + hour + "," + min + "," + sec + ",";
	}

}
