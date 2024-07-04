package org.zkpk.cs.common.utils;

import java.text.DateFormat;
import java.text.ParseException;  
import java.text.SimpleDateFormat;  
import java.util.Calendar;  
import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;  
import javax.xml.datatype.DatatypeFactory;  
import javax.xml.datatype.XMLGregorianCalendar;

public class DateUtil {

	/**  
     * 计算两个日期之间相差的天数  
     * @param start 开始时间 
     * @param end   结束时间 
     * @return 相差天数 
     * @throws ParseException  
     */    
	public static int daysBetween(Date start, Date end) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(start);
		long time1 = cal.getTimeInMillis();
		cal.setTime(end);
		long time2 = cal.getTimeInMillis();
		long between_days = (time2 - time1) / (1000 * 3600 * 24);
		return Integer.parseInt(String.valueOf(between_days));
	}
	
	/**
     * 字符串日期格式的计算
     * @param smdate
     * @param bdate
     * @return
     * @throws ParseException
     */
    public static int daysBetween(String smdate, String bdate) throws ParseException {  
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.setTime(sdf.parse(smdate));
		long time1 = cal.getTimeInMillis();
		cal.setTime(sdf.parse(bdate));
		long time2 = cal.getTimeInMillis();
		long between_days = (time2 - time1) / (1000 * 3600 * 24);
		return Integer.parseInt(String.valueOf(between_days));
    }  

    
    /** 
     * 将指定字符串转换成日期 
     *  
     * @param date 
     *            String 日期字符串 
     * @param datePattern 
     *            String 日期格式 
     * @return Date 
     */  
    public static java.util.Date getFormatDate(String date, String datePattern) {  
        SimpleDateFormat sd = new SimpleDateFormat(datePattern);  
        return sd.parse(date, new java.text.ParsePosition(0));  
    }  
      
  
    /** 
     * 将指定日期对象转换成格式化字符串 
     *  
     * @param date 
     *            Date XML日期对象 
     * @param datePattern 
     *            String 日期格式 
     * @return String 
     */  
    public static String getFormattedString(Date date, String datePattern) {  
        SimpleDateFormat sd = new SimpleDateFormat(datePattern);  
  
        return sd.format(date);  
    }  
  
    /** 
     * 将指定XML日期对象转换成格式化字符串 
     *  
     * @param xmlDate 
     *            Date XML日期对象 
     * @param datePattern 
     *            String 日期格式 
     * @return String 
     */  
    public static String getFormattedString(XMLGregorianCalendar xmlDate, String datePattern) {  
        SimpleDateFormat sd = new SimpleDateFormat(datePattern);  
        Calendar calendar = xmlDate.toGregorianCalendar();  
        return sd.format(calendar.getTime());  
    }  
  
    /** 
     * 将指定XML日期对象转换成日期对象 
     *  
     * @param xmlDate 
     *            Date XML日期对象 
     * @param datePattern 
     *            String 日期格式 
     * @return Date 
     */  
    public static Date xmlGregorianCalendar2Date(XMLGregorianCalendar xmlDate) {  
        return xmlDate.toGregorianCalendar().getTime();  
    }  
    
    /**
     * @Title: getDateNow  
     * @Description: 获取string类型的日期及时间
     * @param @return    参数  
     * @return String    返回类型  
     * @throws
     */
    public static String getDateNow(){
    	Date d = new Date();  
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
	    return sdf.format(d); 
    }
    
    /**
     * @Title: getThisdate  
     * @Description: 获取string类型的日期
     * @param @return    参数  
     * @return String    返回类型  
     * @throws
     */
    public static String getThisdate() {  
		// 获得当前日期
		Calendar cldCurrent = Calendar.getInstance();
		// 获得年月日
		String strYear = String.valueOf(cldCurrent.get(Calendar.YEAR));
		String strMon = String.valueOf((cldCurrent.get(Calendar.MONTH) + 1));
		String strDay = String.valueOf(cldCurrent.get(Calendar.DAY_OF_MONTH));
		return strYear + "-" + strMon + "-" + strDay;
    }  
  
    /**
     * 
     * @Description: TODO
     * @author HUCHAO
     * @date 2018-09-10 13:55:14
     * @param calendar
     * @return
     */
    public static XMLGregorianCalendar convert2XMLCalendar(Calendar calendar) {  
        try {  
            DatatypeFactory dtf = DatatypeFactory.newInstance();              
            return dtf.newXMLGregorianCalendar(  
                    calendar.get(Calendar.YEAR),  
                    calendar.get(Calendar.MONTH)+1,  
                    calendar.get(Calendar.DAY_OF_MONTH),  
                    calendar.get(Calendar.HOUR),  
                    calendar.get(Calendar.MINUTE),  
                    calendar.get(Calendar.SECOND),  
                    calendar.get(Calendar.MILLISECOND),  
                    calendar.get(Calendar.ZONE_OFFSET)/(1000*60));  
                  
        } catch (DatatypeConfigurationException e) {  
            e.printStackTrace();  
            return null;  
        }  
    }  
  
    // 获取当天时间  
    public static java.sql.Timestamp getNowTime(String dateformat) {  
        Date now = new Date();  
        SimpleDateFormat dateFormat = new SimpleDateFormat(dateformat);// 可以方便地修改日期格式  
        String dateString = dateFormat.format(now);  
        SimpleDateFormat sd = new SimpleDateFormat(dateformat);  
        Date dateFormt = sd.parse(dateString, new java.text.ParsePosition(0));  
        java.sql.Timestamp dateTime = new java.sql.Timestamp(dateFormt.getTime());  
        return dateTime;  
    }  
  
    // 获取指定时间  
	public static java.sql.Timestamp getNowNewTime(String date, String dateformat) {
		// Date now = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat(dateformat);// 可以方便地修改日期格式
		dateFormat.parse(date, new java.text.ParsePosition(0));

		// String dateString= dateFormat.format(date);
		Date dateFormt = dateFormat.parse(date, new java.text.ParsePosition(0));
		java.sql.Timestamp dateTime = new java.sql.Timestamp(dateFormt.getTime());
		return dateTime;
	}  
    
    /** 
     * @param 含有yyyy-MM-dd'T'hh:mm:ss.SSS格式的时间转换. 
     * @return 
     */  
	public static String getTFormatString(String tdate) {
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
		String str = "";
		try {
			java.util.Date date = format1.parse(tdate);
			SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			str = format2.format(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return str;
	}  
      
    //获取当前时间前2个小时的时间。  
    public static String getBefore2HourDate(){  
        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");    
         Calendar c = Calendar.getInstance();      
         c.add(Calendar.HOUR_OF_DAY, -2); // 目前時間加3小時      
        return df.format(c.getTime());  
    }  
    
    /** 
     * @param time1   当前时间   
     * @param time2  比较时间  
     * @return  如果time1比time2大，则返回true; 
     */  
    public static boolean compareDateTime(Date time1, Date time2) {  
        return time1.getTime() > time2.getTime();  
    }  
      
    /** 
     * @param time1   当前时间   
     * @param time2  比较时间  
     * @return  如果time1比time2大gap分钟，则返回true; 
     */  
    public static boolean compareDateTime(Date time1, Date time2, int gap) {  
        return time1.getTime() - time2.getTime() > gap * 60 * 1000;  
    }  
    
    /**
     * 
     * @Description: 添加时间
     * @author HUCHAO
     * @date 2018-09-03 21:13:06
     * @param date
     * @param addnumber
     * @return
     */
    public static String addTime(Date date, int addnumber){
		String str = null;
		try {
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			// 时间累计
			Calendar gc = new GregorianCalendar();
			gc.setTime(date);
			gc.add(GregorianCalendar.MINUTE, addnumber);
			str = df.format(gc.getTime());
		} catch (Exception e) {

		}
		return str;
    }
    
    /**
     * 
     * @Description: 添加月份
     * @author HUCHAO
     * @date 2018-09-10 16:16:54
     * @param date
     * @param addnumber
     * @return
     */
    public static String addMonth(Date date, int addnumber){
		String str = null;
		try {
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			// 时间累计
			Calendar gc = new GregorianCalendar();
			gc.setTime(date);
			gc.add(GregorianCalendar.MONTH, addnumber);
			str = df.format(gc.getTime());
		} catch (Exception e) {

		}
		return str;
    }
	
}
