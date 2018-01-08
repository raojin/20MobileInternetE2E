package com.eastcom_sw.poc.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * @author Sunk
 * @create 2017-12-21-11:05
 **/
public class DateTools {
    public DateTools() {
    }

    public static String getYearWeekFirstDay(int yearNum, int weekNum) throws Exception {
        if (yearNum >= 1900 && yearNum <= 9999) {
            Calendar cal = Calendar.getInstance();
            cal.setFirstDayOfWeek(2);
            cal.set(7, 2);
            cal.setMinimalDaysInFirstWeek(7);
            cal.set(1, yearNum);
            cal.set(3, weekNum);
            if (weekNum == 1) {
                String firstDateStr = yearNum + "-01-01";
                return firstDateStr;
            } else {
                cal.add(5, -7);
                return getDateFormat(cal.getTime());
            }
        } else {
            throw new NullPointerException("年度必须大于等于1900年小于等于9999年");
        }
    }

    public static String getLastDayOfWeek(int year, int week) {
        Calendar cal = Calendar.getInstance();
        cal.set(1, year);
        cal.set(3, week);
        cal.setFirstDayOfWeek(2);
        cal.set(7, cal.getFirstDayOfWeek() + 6);
        return getDateFormat(cal.getTime());
    }

    public static int getWeekOfYear(Date date) {
        Calendar c = new GregorianCalendar();
        c.setFirstDayOfWeek(2);
        c.setMinimalDaysInFirstWeek(1);
        c.setTime(date);
        return c.get(3);
    }

    public static String getWeekOfYearFmt(Date date) {
        Calendar c = new GregorianCalendar();
        c.setFirstDayOfWeek(2);
        c.setMinimalDaysInFirstWeek(1);
        c.setTime(date);
        int week = c.get(3);
        int year = c.get(1);
        String weekStr = year + "" + (week < 10 ? "0" + week : week);
        return weekStr;
    }

    public static int getMaxWeekNumOfYear(int year) {
        Calendar c = new GregorianCalendar();
        c.set(year, 11, 31, 23, 59, 59);
        return getWeekOfYear(c.getTime());
    }

    public static Date getFirstDayOfWeek(int year, int week) {
        Calendar c = new GregorianCalendar();
        c.set(1, year);
        c.set(2, 0);
        c.set(5, 1);
        int day = DateUtil.diffDate(c.getTime(), getFirstDayOfWeek(c.getTime()));
        if (day == 6) {
            day -= 7;
        }

        Calendar cal = (GregorianCalendar)c.clone();
        cal.add(5, (week - 1) * 7 - day);
        return getFirstDayOfWeek(cal.getTime());
    }

    public static Date getLastDayOfWeek(int year, int week, int _null) {
        Calendar c = new GregorianCalendar();
        c.set(1, year);
        c.set(2, 0);
        c.set(5, 1);
        int day = DateUtil.diffDate(c.getTime(), getFirstDayOfWeek(c.getTime()));
        if (day == 6) {
            day -= 7;
        }

        Calendar cal = (GregorianCalendar)c.clone();
        cal.add(5, (week - 1) * 7 - day);
        return getLastDayOfWeek(cal.getTime());
    }

    public static Date getFirstDayOfWeek(Date date) {
        Calendar c = new GregorianCalendar();
        c.setFirstDayOfWeek(2);
        c.setTime(date);
        c.set(7, c.getFirstDayOfWeek());
        return c.getTime();
    }

    public static Date getLastDayOfWeek(Date date) {
        Calendar c = new GregorianCalendar();
        c.setFirstDayOfWeek(2);
        c.setTime(date);
        c.set(7, c.getFirstDayOfWeek() + 6);
        return c.getTime();
    }

    public static Date getFirstDayOfWeek() {
        Calendar c = new GregorianCalendar();
        c.setFirstDayOfWeek(2);
        c.setTime(new Date());
        c.set(7, c.getFirstDayOfWeek());
        return c.getTime();
    }

    public static Date getLastDayOfWeek() {
        Calendar c = new GregorianCalendar();
        c.setFirstDayOfWeek(2);
        c.setTime(new Date());
        c.set(7, c.getFirstDayOfWeek() + 6);
        return c.getTime();
    }

    public static String getDateFormat(Date date) {
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = sd.format(date);
        return dateStr;
    }

    public static String getDateFormatSqlStr(String timeType, String columnName) {
        String sql = columnName;
        if (timeType.equals("day")) {
            sql = "to_char(to_date(" + columnName + ",'yyyymmddhh24mi'),'yyyy-mm-dd')";
        } else if (timeType.equals("month")) {
            sql = "to_char(to_date(" + columnName + ",'yyyymmddhh24mi'),'yyyy-mm')";
        }

        return sql;
    }
}
