package com.stock.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DateUtils {
    private static Logger      log                         = LoggerFactory.getLogger(DateUtils.class);
    /**
     * 默认时间字符串的格式
     */
    public static final String LONG_DATE_PATTERN           = "yyyy-MM-dd HH:mm:ss";
    public static final String DEFAULT_FORMAT_STR          = "yyyy-MM-dd";
    public static final String SHORT_DATE_PATTERN_4_SEARCH = "yyyyMMdd";
    public static final String DEFAULT_X_FORMAT_STR        = "yyyy/MM/dd";

    public static final String YYYY_MM_DD_HH_MM_SS_SSS     = "yyyy-MM-dd HH:mm:ss.SSS";
    public static final String YYYY_MM_DD_HH_MM_SS         = "yyyy-MM-dd HH:mm:ss";
    public static final String ZH_TO_DAY                   = "yyyy年MM月dd日";
    public static final String ZH_TO_MINUTE                = "yyyy年MM月dd日 HH时mm分";
    public static final String ZH_TO_SECOND                = "yyyy年MM月dd日 HH时mm分ss秒";
    public static final String SHORT_SECOND                = "yyyyMMddHHmmss";

    /**
     * 把日期对象转成字符串，按yyyy年MM月dd日
     * 
     * @param date
     * @return
     */
    public static String zhToDay(Date date) {
        SimpleDateFormat sfZhToDay = new SimpleDateFormat(ZH_TO_DAY);
        return date == null ? "" : sfZhToDay.format(date);
    }

    /**
     * 把格式为 yyyy-MM-dd的日期字符串 解析成日期类型
     * 
     * @param date
     * @return
     */
    public static Date dateToDay(String date) {
        Date result = null;
        SimpleDateFormat dateZhToDay = new SimpleDateFormat(DEFAULT_FORMAT_STR);
        try {
            if (StringUtils.isNotBlank(date)) {
                result = dateZhToDay.parse(date);
            }
        } catch (ParseException e) {
            log.error(e.getMessage(), e);
        }
        return result;
    }

    /**
     * 把日期对象转成字符串，按yyyy年MM月dd日 HH时mm分
     * 
     * @param date
     * @return
     */
    public static String zhToMinute(Date date) {
        SimpleDateFormat sfZhToMinute = new SimpleDateFormat(ZH_TO_MINUTE);
        return date == null ? "" : sfZhToMinute.format(date);
    }

    /**
     * 计算当前时间离当天结束还有多少秒
     * 
     * @return
     */
    public static int calcTodayEndSecond() {
        Calendar todayEnd = Calendar.getInstance();
        todayEnd.set(Calendar.HOUR_OF_DAY, 23);
        todayEnd.set(Calendar.MINUTE, 59);
        todayEnd.set(Calendar.SECOND, 59);
        return (int) ((todayEnd.getTime().getTime() - (new Date().getTime())) / 1000);
    }

    public static String longFormat(Date date) {
        return format(date, LONG_DATE_PATTERN);
    }

    public static String shortFormat(Date date) {
        return format(date, DEFAULT_FORMAT_STR);
    }

    public static String shortFormat4Search(Date date) {
        return format(date, SHORT_DATE_PATTERN_4_SEARCH);
    }

    public static String format(Date date, String patern) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat sDateFormat = new SimpleDateFormat(patern);
        return sDateFormat.format(date);
    }

    /**
     * 获取当前时间
     * 
     * @return
     */
    public static String getCurrentDate(String formatStr) {
        if (null == formatStr) {
            formatStr = DEFAULT_FORMAT_STR;
        }
        return date2String(new Date(), formatStr);
    }

    /**
     * 将Date日期转换为String
     * 
     * @param date
     * @param formatStr
     * @return
     */
    public static String date2String(Date date, String formatStr) {
        if (null == date)
            return "";
        if (formatStr == null)
            formatStr = DEFAULT_FORMAT_STR;
        DateFormat df = createFormatter(formatStr);
        return df.format(date);
    }
    public static Date String2Date(String date, String format) throws ParseException {
    	SimpleDateFormat sdf = new SimpleDateFormat(format);
    	return sdf.parse(date);
    }

    public static Date String2Date(String date) throws ParseException {
        DateFormat df = createFormatter(DEFAULT_FORMAT_STR);
        return df.parse(date);
    }

    public static DateFormat createFormatter(String pattern) {
        return new SimpleDateFormat(pattern);
    }

    public static String ymdFormat(Date date) {
        if (date == null) {
            return "";
        }
        DateFormat df = createFormatter(DEFAULT_FORMAT_STR);
        return df.format(date);
    }

    public static String ymdXFormat(Date date) {
        if (date == null) {
            return "";
        }
        DateFormat df = createFormatter(DEFAULT_X_FORMAT_STR);
        return df.format(date);
    }

    /**
     * 将String型格式化,比如想要将2011-11-11格式化成2011年11月11日,就StringPattern("2011-11-11",
     * "yyyy-MM-dd","yyyy年MM月dd日").
     * 
     * @param date String 想要格式化的日期
     * @param oldPattern String 想要格式化的日期的现有格式
     * @param newPattern String 想要格式化成什么格式
     * @return String
     */
    public static final String StringPattern(String date, String oldPattern, String newPattern) {
        if (date == null || oldPattern == null || newPattern == null)
            return "";
        SimpleDateFormat sdf1 = new SimpleDateFormat(oldPattern); // 实例化模板对象    
        SimpleDateFormat sdf2 = new SimpleDateFormat(newPattern); // 实例化模板对象    
        Date d = null;
        try {
            d = sdf1.parse(date); // 将给定的字符串中的日期提取出来    
        } catch (Exception e) { // 如果提供的字符串格式有错误，则进行异常处理    
            log.error(e.getMessage(), e); // 打印异常信息    
        }
        return sdf2.format(d);
    }

    /**
     * 把日期对象转成字符串，按yyyy年MM月dd日 HH时mm分ss秒
     * 
     * @param date
     * @return
     */
    public static String zhToSecond(Date date) {
        SimpleDateFormat shZhToSecond = new SimpleDateFormat(ZH_TO_SECOND);
        return date == null ? "" : shZhToSecond.format(date);
    }

    /**
     * 计算oneDate和otherDate 之间的分钟数
     * 
     * @return
     */
    public static Long calculateSeconds(Date oneDate, Date otherDate) {
        if (null == oneDate || null == otherDate) {
            return null;
        }
        try {
            return (oneDate.getTime() - otherDate.getTime()) / (1000);
        } catch (Exception e) {
            // TODO: handle exception
            log.error(e.getMessage(), e);
            return null;
        }
    }
    
    /**
     * 计算oneDate和otherDate 之间的天数
     * 
     * @return
     * @throws ParseException 
     */
    public static int calculateDaysBetween(String oneDateS, String otherDateS) throws ParseException {

        if (null == oneDateS || null == otherDateS) {
            return 0;
        }
        
    	String format= "yyyyMMdd";
    	Date oneDate = DateUtils.String2Date(oneDateS, format);
    	Date otherDate = DateUtils.String2Date(otherDateS, format);

        try {
            return  Integer.valueOf((oneDate.getTime() - otherDate.getTime()) / (1000 * 3600 * 24) + "");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return 0;
        }
    }

    /**
     * 计算参数距离当前时间相差秒数
     * 
     * @param param 日期格式为yyyyMMddHHmmss
     * @return
     * @throws Exception
     */
    public static int calcParamCurSecond(String param) throws Exception {
        Date date = new SimpleDateFormat(SHORT_SECOND).parse(param);
        return (int) (new Date().getTime() - date.getTime()) / 1000;
    }
    
    public static Long calculateMiniute(Date oneDate, Date otherDate) {
        if (null == oneDate || null == otherDate) {
            return null;
        }
        return Math.abs(((oneDate.getTime() - otherDate.getTime()) / 1000 /60));
    }


    /**
     * 获得日期字符串
     * 
     * @return yyyyMMddHHmmss
     */
    public static String shortSecondStr() {
        return new SimpleDateFormat(SHORT_SECOND).format(new Date());
    }

    /**
     * 获取在日期上增加一段时间后的日期
     * 
     * @author Mac
     * @param date1
     * @param calendarEx like:Calendar.DATE
     * @param time
     * @return
     */
    public static Date getDateAdd(Date date1, int calendarEx, int time) {
        Date endday = date1;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(endday);
        calendar.add(calendarEx, time);
        return calendar.getTime();

    }

}
