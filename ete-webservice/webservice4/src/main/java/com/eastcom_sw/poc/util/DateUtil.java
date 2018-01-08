package com.eastcom_sw.poc.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 * @author Sunk
 * @create 2017-12-21-11:07
 **/
public class DateUtil {
    private static Log log = LogFactory.getLog(DateUtil.class);
    private static String datePattern = "yyyy-MM-dd";
    private static String timePattern = "HH:mm";
    public static String fullPattern = "yyyy-MM-dd HH:mm:ss";

    public DateUtil() {
    }

    public static String getDatePattern() {
        return datePattern;
    }

    public static final String getDate(Date aDate) {
        SimpleDateFormat df = null;
        String returnValue = "";
        if (aDate != null) {
            df = new SimpleDateFormat(datePattern);
            returnValue = df.format(aDate);
        }

        return returnValue;
    }

    public static final String getDate(String pattern) {
        Date date = new Date();
        return getDate(date, pattern);
    }

    public static final String getDate(Date date, String pattern) {
        SimpleDateFormat df = null;
        String returnValue = "";
        if (date != null) {
            df = new SimpleDateFormat(pattern);
            returnValue = df.format(date);
        }

        return returnValue;
    }

    public static Date getDate(String dateString, String pattern) {
        SimpleDateFormat df = null;
        Date date = new Date();
        if (dateString != null) {
            try {
                df = new SimpleDateFormat(pattern);
                date = df.parse(dateString);
            } catch (Exception var5) {
                ;
            }
        }

        return date;
    }

    public static final Date convertStringToDate(String aMask, String strDate) {
        SimpleDateFormat df = null;
        Date date = null;
        df = new SimpleDateFormat(aMask);
        if (log.isDebugEnabled()) {
            log.debug("converting '" + strDate + "' to date with mask '" + aMask + "'");
        }

        try {
            date = df.parse(strDate);
        } catch (ParseException var5) {
            ;
        }

        return date;
    }

    public static String getTimeNow(Date theTime) {
        return getDateTime(timePattern, theTime);
    }

    public static String getCurrentDatetime() {
        return getDateTime("yyyy-MM-dd HH:mm:ss", new Date());
    }

    public static String dateStringParse(String dateStr, String inPattern, String outPattern) {
        SimpleDateFormat sdf1 = new SimpleDateFormat(inPattern);
        SimpleDateFormat sdf2 = new SimpleDateFormat(outPattern);
        Date d = null;

        try {
            d = sdf1.parse(dateStr);
            dateStr = sdf2.format(d);
        } catch (ParseException var7) {
            var7.printStackTrace();
        }

        return dateStr;
    }

    public static Calendar getToday() throws ParseException {
        Date today = new Date();
        SimpleDateFormat df = new SimpleDateFormat(datePattern);
        String todayAsString = df.format(today);
        Calendar cal = new GregorianCalendar();
        cal.setTime(convertStringToDate(todayAsString));
        return cal;
    }

    public static final String getDateTime(String aMask, Date aDate) {
        SimpleDateFormat df = null;
        String returnValue = "";
        if (aDate == null) {
            log.error("aDate is null!");
        } else {
            df = new SimpleDateFormat(aMask);
            df.setTimeZone(TimeZone.getTimeZone("GMT+8"));
            returnValue = df.format(aDate);
        }

        return returnValue;
    }

    public static final String convertDateToString(Date aDate) {
        return getDateTime(datePattern, aDate);
    }

    public static Date convertStringToDate(String strDate) {
        Date aDate = null;
        if (log.isDebugEnabled()) {
            log.debug("converting date with pattern: " + datePattern);
        }

        aDate = convertStringToDate(datePattern, strDate);
        return aDate;
    }

    public static String getYear() {
        Date date = new Date();
        return getDate(date, "yyyy");
    }

    public static String getMonth() {
        Date date = new Date();
        return getDate(date, "MM");
    }

    public static String getDay() {
        Date date = new Date();
        return getDate(date, "dd");
    }

    public static int getHour(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(11);
    }

    public static int getMinute(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(12);
    }

    public static int getSecond(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(13);
    }

    public static long getMillis(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.getTimeInMillis();
    }

    public static Date addDate(Date date, int day) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(getMillis(date) + (long)day * 24L * 3600L * 1000L);
        return c.getTime();
    }

    public static int diffDate(Date date, Date date1) {
        return (int)((getMillis(date) - getMillis(date1)) / 86400000L);
    }

    public static int diffDateToHour(Date date, Date date1) {
        return (int)((getMillis(date) - getMillis(date1)) / 3600000L);
    }

    public static String getDateTimePattern() {
        return getDatePattern() + " HH:mm:ss";
    }

    public static long calcHours(long timeInSeconds) {
        long hours = timeInSeconds / 3600L;
        return hours;
    }

    public static long getCalueteSeconde(String smallString, String bigString) {
        Date bigdate = convertStringToDate("yyyy-MM-dd HH:mm:ss", bigString);
        Date smalldate = convertStringToDate("yyyy-MM-dd HH:mm:ss", smallString);
        long big = bigdate.getTime();
        long mall = smalldate.getTime();
        long difference = big - mall;
        return difference / 1000L;
    }

    public static long getCalueteMillisecond(String dataString) {
        Date malldate = convertStringToDate("yyyy-MM-dd HH:mm:ss", getCurrentDatetime());
        Date bigdate = convertStringToDate("yyyy-MM-dd HH:mm:ss", dataString);
        long big = bigdate.getTime();
        long mall = malldate.getTime();
        long difference = big - mall;
        return difference;
    }

    public static String getDistanceTime(String dataString) {
        long day = 0L;
        long hour = 0L;
        long min = 0L;
        long sec = 0L;
        long diff = Math.abs(getCalueteMillisecond(dataString));
        day = diff / 86400000L;
        hour = diff / 3600000L - day * 24L;
        min = diff / 60000L - day * 24L * 60L - hour * 60L;
        sec = diff / 1000L - day * 24L * 60L * 60L - hour * 60L * 60L - min * 60L;
        return (day > 0L ? day + "天" : "") + (hour > 0L ? hour + "小时" : "") + (min > 0L ? min + "分" : sec + "秒");
    }

    public static String formatTime(long s) {
        long day = 0L;
        long hour = 0L;
        long min = 0L;
        long sec = 0L;
        long mis = 0L;
        day = s / 86400000L;
        hour = s / 3600000L - day * 24L;
        min = s / 60000L - day * 24L * 60L - hour * 60L;
        sec = s / 1000L - day * 24L * 60L * 60L - hour * 60L * 60L - min * 60L;
        mis = s - day * 24L * 60L * 60L * 1000L - hour * 60L * 60L * 1000L - min * 60L * 1000L - sec * 1000L;
        return (day >= 0L ? day + "天" : "") + (hour >= 0L ? hour + "小时" : "") + (min >= 0L ? min + "分" : "") + (sec >= 0L ? sec + "秒" : "") + (mis >= 0L ? mis + "毫秒" : "");
    }

    public static long calcuteHourTetweenTwo(String smallString, String bigString) {
        return calcHours(getCalueteSeconde(smallString, bigString));
    }

    public static String getDateAndHours(String datatime, int hours) throws Exception {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date1 = format.parse(datatime);
        long Time = date1.getTime() / 1000L - (long)(3600 * hours);
        date1.setTime(Time * 1000L);
        String mydate1 = format.format(date1);
        return mydate1;
    }

    public static String getDateAddHours(String datatime, int hours) throws Exception {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date1 = format.parse(datatime);
        long Time = date1.getTime() / 1000L + (long)(3600 * hours);
        date1.setTime(Time * 1000L);
        String mydate1 = format.format(date1);
        return mydate1;
    }
}
