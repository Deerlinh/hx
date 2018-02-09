/**
 *
 */
package com.jianq.manager.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.TimeZone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Yuan Song
 * @date 2013-2-26
 */
public class DateUtil {

    private static final Logger log = LoggerFactory.getLogger(DateUtil.class);
    public static final String BUNDLE_KEY = "ApplicationResources";
    private static final String TIME_PATTERN = "HH:mm";
    private int lunarYear;
    private int lunarMonth;
    private int lunarDay;
    private boolean lunarLeap;
    public final static String FORMAT_DATE = "yyyy-MM-dd";
    public final static String FORMAT_YEAR_MONTH = "yyyy-MM";
    public final static String FORMAT_HOUR_MIN_SECOND = "HHmmss";
    public final static String FORMAT_DATE_NO_SPRIT = "yyyyMMdd";
    public final static String FORMAT_DATETIME = "yyyy-MM-dd HH:mm:ss";

    public final static String FORMAT_DATE_ZH = "yyyy年MM月dd日";
    public final static String FORMAT_DATETIME_ZH = "yyyy年MM月dd日 HH时mm分ss秒";

    public final static String TYPE_DATE = "date";
    public final static String TYPE_DATETIME = "datetime";
    private static int[] lunarInfo = {0x04bd8, 0x04ae0, 0x0a570, 0x054d5,
            0x0d260, 0x0d950, 0x16554, 0x056a0, 0x09ad0, 0x055d2, 0x04ae0,
            0x0a5b6, 0x0a4d0, 0x0d250, 0x1d255, 0x0b540, 0x0d6a0, 0x0ada2,
            0x095b0, 0x14977, 0x04970, 0x0a4b0, 0x0b4b5, 0x06a50, 0x06d40,
            0x1ab54, 0x02b60, 0x09570, 0x052f2, 0x04970, 0x06566, 0x0d4a0,
            0x0ea50, 0x06e95, 0x05ad0, 0x02b60, 0x186e3, 0x092e0, 0x1c8d7,
            0x0c950, 0x0d4a0, 0x1d8a6, 0x0b550, 0x056a0, 0x1a5b4, 0x025d0,
            0x092d0, 0x0d2b2, 0x0a950, 0x0b557, 0x06ca0, 0x0b550, 0x15355,
            0x04da0, 0x0a5d0, 0x14573, 0x052d0, 0x0a9a8, 0x0e950, 0x06aa0,
            0x0aea6, 0x0ab50, 0x04b60, 0x0aae4, 0x0a570, 0x05260, 0x0f263,
            0x0d950, 0x05b57, 0x056a0, 0x096d0, 0x04dd5, 0x04ad0, 0x0a4d0,
            0x0d4d4, 0x0d250, 0x0d558, 0x0b540, 0x0b5a0, 0x195a6, 0x095b0,
            0x049b0, 0x0a974, 0x0a4b0, 0x0b27a, 0x06a50, 0x06d40, 0x0af46,
            0x0ab60, 0x09570, 0x04af5, 0x04970, 0x064b0, 0x074a3, 0x0ea50,
            0x06b58, 0x055c0, 0x0ab60, 0x096d5, 0x092e0, 0x0c960, 0x0d954,
            0x0d4a0, 0x0da50, 0x07552, 0x056a0, 0x0abb7, 0x025d0, 0x092d0,
            0x0cab5, 0x0a950, 0x0b4a0, 0x0baa4, 0x0ad50, 0x055d9, 0x04ba0,
            0x0a5b0, 0x15176, 0x052b0, 0x0a930, 0x07954, 0x06aa0, 0x0ad50,
            0x05b52, 0x04b60, 0x0a6e6, 0x0a4e0, 0x0d260, 0x0ea65, 0x0d530,
            0x05aa0, 0x076a3, 0x096d0, 0x04bd7, 0x04ad0, 0x0a4d0, 0x1d0b6,
            0x0d250, 0x0d520, 0x0dd45, 0x0b5a0, 0x056d0, 0x055b2, 0x049b0,
            0x0a577, 0x0a4b0, 0x0aa50, 0x1b255, 0x06d20, 0x0ada0, 0x14b63};

    public DateUtil() {
    }

    public static Date parseDate(String str, String formatStr) {
        SimpleDateFormat format = new SimpleDateFormat(formatStr);
        try {
            return format.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Return default datePattern (MM/dd/yyyy);可以从系统中读取默认的pattern
     */
    public static String getDatePattern() {
        String defaultDatePattern;
        try {
            defaultDatePattern = ResourceBundle.getBundle(BUNDLE_KEY)
                    .getString("date.format"); // 从资源文件中读取默认的日期格式
        } catch (MissingResourceException mse) {
            defaultDatePattern = "MM/dd/yyyy";
        }
        return defaultDatePattern;
    }

    /**
     * 返回系统中默认的时间格式 (MM/dd/yyyyHH:mm:ss.S);
     */
    public static String getDateTimePattern() {
        return DateUtil.getDatePattern() + " HH:mm:ss.S";
    }

    /**
     * 获取格林时间
     */
    public static Date getGreenwichDate() {
        Date date = new Date();
        try {
            TimeZone tz = TimeZone.getTimeZone("Etc/Greenwich");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            sdf.setTimeZone(tz);
            String date_tz = sdf.format(date);
            sdf.setTimeZone(TimeZone.getDefault());
            return sdf.parse(date_tz);
        } catch (ParseException e) {
            return date;
        }
    }

    /**
     * 获取系统时间
     */
    public static Date getSystemTime() {
        Calendar cldCurrent = Calendar.getInstance();
        Date currentDate = cldCurrent.getTime();
        return currentDate;
    }

    /**
     * 根据日期格式将字符串日期转换为日期
     */
    public static Date convertStringToDate(String aMask, String strDate)
            throws ParseException {
        SimpleDateFormat df;
        Date date;
        df = new SimpleDateFormat(aMask);
        if (log.isDebugEnabled()) {
            log.info("converting '" + strDate + "' to date with mask '" + aMask
                    + "'");
        }
        try {
            date = df.parse(strDate);
        } catch (ParseException pe) {
            throw new ParseException(pe.getMessage(), pe.getErrorOffset());
        }
        return (date);
    }

    /**
     * 根据日期格式将字符串日期转换为日期
     */
    public static Date convertStringToDate(String aMask, String strDate,
                                           Locale locale) throws ParseException {
        SimpleDateFormat df;
        Date date;
        df = new SimpleDateFormat(aMask, locale);
        if (log.isDebugEnabled()) {
            log.info("converting '" + strDate + "' to date with mask '" + aMask
                    + "'");
        }
        try {
            date = df.parse(strDate);
        } catch (ParseException pe) {
            throw new ParseException(pe.getMessage(), pe.getErrorOffset());
        }
        return (date);
    }

    /**
     * 根据日期格式将日期转换为字符串日期
     */
    public static String convertDateToString(String aMask, Date aDate) {
        return getDateTime(aMask, aDate);
    }

    /**
     * 根据日期格式将日期转换为字符串日期
     */
    public static String convertDateToString(String aMask, Date aDate,
                                             Locale locale) {
        return getDateTime(aMask, aDate, locale);
    }

    /**
     * 根据日期时间返回时间，日期格式：MM/dd/yyyy HH:MM
     */
    public static String getTimeNow(Date theTime) {
        SimpleDateFormat sdf = new SimpleDateFormat(TIME_PATTERN);
        String date_tz = sdf.format(theTime);
        return date_tz;
    }

    /**
     * This method attempts to convert an Oracle-formatted date in the form
     * dd-MMM-yyyy to mm/dd/yyyy.
     *
     * @param aDate date from database as a string
     * @return formatted string for the ui
     */
    public static String getDate(Date aDate) {
        SimpleDateFormat df;
        String returnValue = "";
        if (aDate != null) {
            df = new SimpleDateFormat(getDatePattern());
            returnValue = df.format(aDate);
        }
        return (returnValue);
    }

    /**
     * This method returns the current date in the format: MM/dd/yyyy
     *
     * @return the current date
     * @throws ParseException when String doesn't match the expected format
     */
    public static Calendar getToday() throws ParseException {
        Date today = new Date();
        SimpleDateFormat df = new SimpleDateFormat(getDatePattern());

        // This seems like quite a hack (date -> string -> date),
        // but it works ;-)
        String todayAsString = df.format(today);
        Calendar cal = new GregorianCalendar();
        cal.setTime(convertStringToDate(todayAsString));
        return cal;
    }

    /**
     * This method generates a string representation of a date's date/time in
     * the format you specify on input
     *
     * @param aMask the date pattern the string is in
     * @param aDate a date object
     * @return a formatted string representation of the date
     * @see java.text.SimpleDateFormat
     */
    public static String getDateTime(String aMask, Date aDate) {
        SimpleDateFormat df = null;
        String returnValue = "";
        if (aDate == null) {
            log.error("aDate is null!");
        } else {
            df = new SimpleDateFormat(aMask);
            returnValue = df.format(aDate);
        }
        return (returnValue);
    }

    /**
     * This method generates a string representation of a date's date/time in
     * the format you specify on input
     *
     * @param aMask the date pattern the string is in
     * @param aDate a date object
     * @return a formatted string representation of the date
     * @see java.text.SimpleDateFormat
     */
    public static String getDateTime(String aMask, Date aDate, Locale locale) {
        SimpleDateFormat df = null;
        String returnValue = "";
        if (aDate == null) {
            log.error("aDate is null!");
        } else {
            df = new SimpleDateFormat(aMask, locale);
            returnValue = df.format(aDate);
        }
        return (returnValue);
    }

    /**
     * This method generates a string representation of a date based on the
     * System Property 'dateFormat' in the format you specify on input
     *
     * @param aDate A date to convert
     * @return a string representation of the date
     */
    public static String convertDateToString(Date aDate) {
        return getDateTime(getDatePattern(), aDate);
    }

    /**
     * This method converts a String to a date using the datePattern
     *
     * @param strDate the date to convert (in format MM/dd/yyyy)
     * @return a date object
     * @throws ParseException when String doesn't match the expected format
     */
    public static Date convertStringToDate(String strDate)
            throws ParseException {
        Date aDate = null;
        try {
            if (log.isDebugEnabled()) {
                log.info("converting date with pattern: " + getDatePattern());
            }
            aDate = convertStringToDate(getDatePattern(), strDate);
        } catch (ParseException pe) {
            log.error("Could not convert '" + strDate
                    + "' to a date, throwing exception");
            pe.printStackTrace();
            throw new ParseException(pe.getMessage(), pe.getErrorOffset());
        }
        return aDate;
    }

    /**
     * 根据字符串格式日期获取相隔天数
     */
    public static long getDistinceDay(String beforedate, String afterdate) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        long dayCount = 0;
        try {
            java.util.Date d1 = df.parse(beforedate);
            java.util.Date d2 = df.parse(afterdate);
            dayCount = (d2.getTime() - d1.getTime()) / (24 * 60 * 60 * 1000);
        } catch (ParseException e) {
            log.info("Date parse error!");
        }
        return dayCount;
    }

    /**
     * 获取相隔天数
     */
    public static long getDistinceDay(Date beforedate, Date afterdate) {
        long dayCount = 0;
        try {
            dayCount = (afterdate.getTime() - beforedate.getTime())
                    / (24 * 60 * 60 * 1000);
        } catch (Exception e) {
            log.info("Date parse error!");
        }
        return dayCount;
    }

    /**
     * 获取相隔时间数
     */
    public static long getDistinceTime(String beforeDateTime,
                                       String afterDateTime) throws Exception {
        SimpleDateFormat d = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long timeCount = 0;
        try {
            java.util.Date d1 = d.parse(beforeDateTime);
            java.util.Date d2 = d.parse(afterDateTime);
            timeCount = (d2.getTime() - d1.getTime()) / (60 * 60 * 1000);
        } catch (ParseException e) {
            log.info("Date parse error!");
            throw e;
        }
        return timeCount;
    }

    /**
     * 获取相隔时间数
     */

    public static long getDistinceTime(Date beforeDateTime, Date afterDateTime)
            throws Exception {
        long timeCount = 0;
        try {
            timeCount = (afterDateTime.getTime() - beforeDateTime.getTime())
                    / (60 * 60 * 1000);
        } catch (Exception e) {
            log.info("Date parse error!");
            throw e;
        }
        return timeCount;
    }

    /**
     * 获取相隔时间数
     */
    public static long getDistinceTime(String beforeDateTime) throws Exception {
        return getDistinceTime(beforeDateTime,
                convertDateToString(getGreenwichDate()));
    }

    /**
     * 获取相隔时间数
     */
    public static long getDistinceTime(Date beforeDateTime) throws Exception {
        return getDistinceTime(beforeDateTime, getGreenwichDate());
    }

    /**
     * 获取相隔分钟数
     */
    public static long getDistinceMinute(String beforeDateTime,
                                         String afterDateTime) throws Exception {
        long minutesCount = 0;
        SimpleDateFormat d = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            java.util.Date d1 = d.parse(beforeDateTime);
            java.util.Date d2 = d.parse(afterDateTime);
            minutesCount = (d2.getTime() - d1.getTime()) / (60 * 1000);
        } catch (ParseException e) {
            log.info("Date parse error!");
            throw e;
        }
        return minutesCount;
    }

    /**
     * 获取相隔分钟数
     */
    public static long getDistinceMinute(Date beforeDateTime, Date afterDateTime)
            throws Exception {
        long minutesCount = 0;
        try {
            minutesCount = (afterDateTime.getTime() - beforeDateTime.getTime())
                    / (60 * 1000);
        } catch (Exception e) {
            log.info("Date parse error!");
            throw e;
        }
        return minutesCount;
    }

    /**
     * 将Date日期转换为Calendar日期
     */
    public static Calendar convertDateToCalendar(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    /**
     * 得到年
     *
     * @param c
     * @return int
     */
    public static int getYear(Calendar c) {
        if (c != null) {
            return c.get(Calendar.YEAR);
        } else {
            return Calendar.getInstance().get(Calendar.YEAR);
        }
    }

    /**
     * 得到月份 注意，这里的月份依然是从0开始的
     *
     * @param c
     * @return int
     */
    public static int getMonth(Calendar c) {
        if (c != null) {
            return c.get(Calendar.MONTH);
        } else {
            return Calendar.getInstance().get(Calendar.MONTH);
        }
    }

    /**
     * 得到日期
     *
     * @param c
     * @return int
     */
    public static int getDate(Calendar c) {
        if (c != null) {
            return c.get(Calendar.DATE);
        } else {
            return Calendar.getInstance().get(Calendar.DATE);
        }
    }

    /**
     * 得到星期
     *
     * @param c
     * @return int
     */
    public static int getDay(Calendar c) {
        if (c != null) {
            return c.get(Calendar.DAY_OF_WEEK);
        } else {
            return Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
        }
    }

    /**
     * 得到小时
     *
     * @param c
     * @return int
     */
    public static int getHour(Calendar c) {
        if (c != null) {
            return c.get(Calendar.HOUR);
        } else {
            return Calendar.getInstance().get(Calendar.HOUR);
        }
    }

    /**
     * 得到分钟
     *
     * @param c
     * @return int
     */
    public static int getMinute(Calendar c) {
        if (c != null) {
            return c.get(Calendar.MINUTE);
        } else {
            return Calendar.getInstance().get(Calendar.MINUTE);
        }
    }

    /**
     * 传回农历 year年的总天数
     *
     * @param year int
     * @return int
     */
    public int getYearDays(int year) {
        int i, sum = 348;
        for (i = 0x8000; i > 0x8; i >>= 1) {
            sum += (lunarInfo[year - 1900] & i) > 0 ? 1 : 0;
        }
        return (sum + getLeapDays(year));
    }

    /**
     * 传回农历 year年闰月的天数
     *
     * @param year int
     * @return int
     */
    public int getLeapDays(int year) {
        if (getLeapMonth(year) > 0) {
            return ((lunarInfo[year - 1900] & 0x10000) > 0 ? 30 : 29);
        } else {
            return (0);
        }
    }

    /**
     * 取得农历year年闰哪个月
     *
     * @param year int
     * @return int 闰月的月份,不闰月返回0
     */
    public int getLeapMonth(int year) {
        return (lunarInfo[year - 1900] & 0xf);
    }

    /**
     * 取农历year年,month月的总天数
     *
     * @param year  int
     * @param month int
     * @return int
     */
    public int getMonthDays(int year, int month) {
        return ((lunarInfo[year - 1900] & (0x10000 >> month)) > 0 ? 30 : 29);
    }

    /**
     * 取得两个农历日期之间相差的天数
     *
     * @param minYear  int
     * @param minMonth int
     * @param minDay   int
     * @param isLeap   boolean
     * @param maxYear  int
     * @param maxMonth int
     * @param maxDay   int
     * @return int scan 两个日期之间相差的天数
     */
    public int getScan(int minYear, int minMonth, int minDay, boolean isLeap,
                       int maxYear, int maxMonth, int maxDay) {
        int offset = 0;
        int yearScan = maxYear - minYear;
        int i;
        if (yearScan > 0) {
            // 不是同一年
            // 取得当年还剩的天数
            for (i = minMonth; i <= 12; i++) {
                offset += getMonthDays(minYear, i);
            }
            offset -= minDay;
            // 如果有闰月并且当前月不是闰月,应包含闰月的天数
            if (!isLeap && getLeapMonth(minYear) >= minMonth) {
                offset += getLeapDays(minYear);
            }
            // 取得相隔整年的天数
            for (i = 1; i < yearScan; i++) {
                offset += getYearDays(minYear + i);
            }
            // 取得最后一年的天数
            for (i = 1; i < maxMonth; i++) {
                offset += getMonthDays(maxYear, i);
            }
            // 如果有闰月在应包含闰月的天数
            if (getLeapMonth(maxYear) < maxMonth) {
                offset += getLeapDays(maxYear);
            }
            offset += maxDay;
        } else {
            // 同一年
            for (i = minMonth; i < maxMonth; i++) {
                offset += getMonthDays(minYear, i);
            }
            int leap = getLeapMonth(minYear);
            if (!isLeap && leap >= minMonth && leap < maxMonth) {
                offset += getLeapDays(maxYear);
            }
            offset -= minDay;
            offset += maxDay;
        }
        return offset;
    }

    public int getLunarYear() {
        return this.lunarYear;
    }

    public int getLunarMonth() {
        return this.lunarMonth;
    }

    public int getLunarDay() {
        return this.lunarDay;
    }

    public boolean getLunarLeap() {
        return this.lunarLeap;
    }

    /**
     * 取得指定日期所在周的第一天
     *
     * @param date
     * @return
     */
    public static Date getFirstDayOfWeek(Date date) {
        Calendar c = new GregorianCalendar();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.setTime(date);
        c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek()); // Monday
        return c.getTime();
    }

    /**
     * 取得指定日期所在周的最后一天
     *
     * @param date
     * @return
     */
    public static Date getLastDayOfWeek(Date date) {
        Calendar c = new GregorianCalendar();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.setTime(date);
        c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek() + 6); // Sunday
        return c.getTime();
    }

    /**
     * 取得本月的第一天
     *
     * @param date
     * @return
     */
    public static Date getFirstDayOfMonth(Date date) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.set(Calendar.DATE, 1);
        return calendar.getTime();
    }

    /**
     * 取得本月的最后一天
     *
     * @param date
     * @return
     */
    public static Date getLastDayOfMonth(Date date) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.set(Calendar.DATE, 1);
        calendar.roll(Calendar.DATE, -1);
        return calendar.getTime();
    }

    /**
     * 取得上个月的第一天
     *
     * @param date
     * @return
     */
    public static Date getFirstDayOfLastMonth(Date date) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.set(Calendar.DATE, 1);
        calendar.add(Calendar.MONTH, -1);
        return calendar.getTime();
    }

    /**
     * 取得上个月的最后一天
     *
     * @param date
     * @return
     */
    public static Date getLastDayOfLastMonth(Date date) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.set(Calendar.DATE, 1);
        calendar.add(Calendar.MONTH, -1);
        calendar.roll(Calendar.DATE, -1);
        return calendar.getTime();
    }

    /**
     * 取得去年的今天
     *
     * @param date
     * @return
     */
    public static Date getDayOfLastYear(Date date) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.YEAR, -1);
        return calendar.getTime();
    }

    /**
     * 取得上个月的最后一天
     *
     * @param date
     * @return
     */
    public static Date getDayOfAddDays(Date date, int days) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DAY_OF_YEAR, days);
        return c.getTime();
    }

    /**
     * 得到某天是周几
     *
     * @param strDay
     * @return 周几
     */
    public static int getWeekDay(String strDay) {
        Date day = DateUtil.dateAdd(strDay, -1);
        Calendar strDate = Calendar.getInstance();
        strDate.setTime(day);
        int meStrDate = strDate.get(Calendar.DAY_OF_WEEK);
        return meStrDate;
    }

    /**
     * 得到某天是周几
     *
     * @param strDay
     * @return 周几
     */
    public static int getWeekDay(Date date) {
        Date day = DateUtil.dateAdd(format(date, "date"), -1);
        Calendar strDate = Calendar.getInstance();
        strDate.setTime(day);
        int meStrDate = strDate.get(Calendar.DAY_OF_WEEK);
        return meStrDate;
    }

    /**
     * 取得两个日期段的日期间隔
     *
     * @param t1 时间1
     * @param t2 时间2
     * @return t2 与t1的间隔天数
     * @throws ParseException 如果输入的日期格式不是0000-00-00 格式抛出异常
     * @author color
     */
    public static int getBetweenDays(String t1, String t2)
            throws ParseException {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        int betweenDays = 0;
        Date d1 = format.parse(t1);
        Date d2 = format.parse(t2);
        betweenDays = getBetweenDays(d1, d2);
        return betweenDays;
    }

    /**
     * 取得两个日期段的日期间隔
     *
     * @param d1 日期1
     * @param d2 日期2
     * @return t2 与t1的间隔天数
     */
    private static int getBetweenDays(Date d1, Date d2) {
        if (d1 == null || d2 == null) {
            return -1;
        }
        int betweenDays;
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.setTime(d1);
        c2.setTime(d2);
        // 保证第二个时间一定大于第一个时间
        if (c1.after(c2)) {
            c2.setTime(d1);
            c1.setTime(d2);
        }
        int betweenYears = c2.get(Calendar.YEAR) - c1.get(Calendar.YEAR);
        betweenDays = c2.get(Calendar.DAY_OF_YEAR)
                - c1.get(Calendar.DAY_OF_YEAR);
        for (int i = 0; i < betweenYears; i++) {
            c1.set(Calendar.YEAR, (c1.get(Calendar.YEAR) + 1));
            betweenDays += c1.getMaximum(Calendar.DAY_OF_YEAR);
        }
        return betweenDays;
    }

    /**
     * 判断指定日期是否在一个日期范围内
     *
     * @param fromDate 范围开始日期
     * @param toDate   范围结束日期
     * @param testDate 测试日期
     * @return 在范围内true, 否则false
     */
    public static boolean betweenDays(java.sql.Date fromDate,
                                      java.sql.Date toDate, java.sql.Date testDate) {
        if (fromDate == null || toDate == null || testDate == null) {
            return false;
        }

        // 1、 交换开始和结束日期
        if (fromDate.getTime() > toDate.getTime()) {
            java.sql.Date tempDate = fromDate;
            fromDate = toDate;
            toDate = tempDate;
        }

        // 2、缩小范围
        long testDateTime = testDate.getTime();
        if ((testDateTime > fromDate.getTime() && testDateTime > toDate
                .getTime())
                || testDateTime < fromDate.getTime()
                && testDateTime < toDate.getTime()) {
            return false;
        }

        return true;
    }

    /**
     * 得到指定年、月的最后一天
     *
     * @param year  年
     * @param month 月
     * @return 本年月的最后一天，如果2009,10，返回结果：2009-10-31
     */
    public static String getLastDateDayOfMonth(int year, int month) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        // 某年某月的最后一天
        int lastDate = cal.getActualMaximum(Calendar.DATE);
        return year + "-" + (month + 1) + "-" + lastDate;
    }

    /**
     * 判断两个日期是否为同一天
     *
     * @param d1 日期一
     * @param d2 日期二
     * @return 同一天true，不是同一天false
     */
    public static boolean isSameDate(Date d1, Date d2) {
        boolean result = false;
        Calendar c1 = Calendar.getInstance();
        c1.setTime(d1);

        Calendar c2 = Calendar.getInstance();
        c2.setTime(d2);

        if (c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR)
                && c1.get(Calendar.MONTH) == c2.get(Calendar.MONTH)
                && c1.get(Calendar.DAY_OF_MONTH) == c2
                .get(Calendar.DAY_OF_MONTH)) {
            result = true;
        }
        return result;
    }

    /**
     * 是否为周末
     *
     * @param strDate
     * @return true|false
     */
    public static boolean isWeekend(String strDate) {
        int weekDay = getWeekDay(strDate);
        if (weekDay == 6 || weekDay == 7) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 是否为周末
     *
     * @param strDate
     * @return true|false
     */
    public static boolean isWeekend(Date date) {
        int weekDay = getWeekDay(format(date, "date"));
        if (weekDay == 6 || weekDay == 7) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * @dateValue 日期对象，可以是java.util.Date和java.sql.Date
     * @dateType 格式化的类型, date和datetime
     */
    public static String format(Object dateValue, String dateType) {
        if (dateValue == null)
            return "";
        if (dateValue instanceof java.sql.Date) {
            return dateValue.toString();
        } else if (dateValue instanceof java.util.Date) {
            if (dateType.equals(TYPE_DATE)) {
                java.text.SimpleDateFormat sfdate = new java.text.SimpleDateFormat(
                        FORMAT_DATE);
                return sfdate.format(dateValue);
            } else if (dateType.equals(TYPE_DATETIME)) {
                java.text.SimpleDateFormat sftime = new java.text.SimpleDateFormat(
                        FORMAT_DATETIME);
                return sftime.format(dateValue);
            } else {
                return "非法日期格式[" + dateType + "]";
            }
        } else {
            return "非日期类型";
        }
    }

    /**
     * 将日期加上某些天或减去天数)返回字符串
     *
     * @param date 待处理日期
     * @param to   加减的天数
     * @return 日期
     */
    public static Date dateAdd(String date, int to) {
        java.util.Date d = null;
        try {
            d = java.sql.Date.valueOf(date);
        } catch (Exception e) {
            e.printStackTrace();
            d = new java.util.Date();
        }
        Calendar strDate = Calendar.getInstance();
        strDate.setTime(d);
        strDate.add(Calendar.DATE, to); // 日期减 如果不够减会将月变动
        return strDate.getTime();
    }

    /**
     * 将日期加上某些天或减去天数)返回字符串
     *
     * @param date 待处理日期
     * @param to   加减的天数
     * @return 日期
     */
    public static Date dateAdd(java.sql.Date date, int to) {
        Calendar strDate = Calendar.getInstance();
        strDate.setTime(date);
        strDate.add(Calendar.DATE, to); // 日期减 如果不够减会将月变动
        return new java.sql.Date(strDate.getTime().getTime());
    }

    /**
     * 两个日期大小比较
     *
     * @param date1
     * @param date2
     * @return
     */
    public static int compare_date(String date1, String date2) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date dt1 = df.parse(date1);
            Date dt2 = df.parse(date2);
            if (dt1.getTime() > dt2.getTime()) {
                return 1;
            } else if (dt1.getTime() < dt2.getTime()) {
                return -1;
            } else {
                return 1;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 取得当前天前to天的日期
     *
     * @param date
     * @return
     */
    public static Date addDay(Date date, int to) {
        Calendar cldCurrent = Calendar.getInstance();
        cldCurrent.setTime(date);
        cldCurrent.add(Calendar.DATE, to);
        return cldCurrent.getTime();
    }

    /**
     * 获得一周之内的日期列表
     *
     * @param date
     * @return
     */
    public static List<Date> getDatesOfWeek(Date date) {
        if (date == null) {
            date = DateUtil.getSystemTime();
        }
        List<Date> list = new ArrayList<Date>();
        Date firstDay = DateUtil.getFirstDayOfWeek(date);
        for (int i = 0; i < 7; i++) {
            list.add(DateUtil.addDay(firstDay, i));
        }
        return list;
    }

    /**
     * 取得昨天的日期
     *
     * @param date
     * @return
     */
    public static Date getYesterday() {
        Calendar cldCurrent = Calendar.getInstance();
        Date currentDate = cldCurrent.getTime();
        cldCurrent.setTime(currentDate);
        cldCurrent.add(Calendar.DATE, -1);
        return cldCurrent.getTime();
    }

    /**
     * 取得前天的日期
     *
     * @param date
     * @return
     */
    public static Date getBeforeYesterday() {
        Calendar cldCurrent = Calendar.getInstance();
        Date currentDate = cldCurrent.getTime();
        cldCurrent.setTime(currentDate);
        cldCurrent.add(Calendar.DATE, -2);
        return cldCurrent.getTime();
    }

    /**
     * 获取给定时间，多少分钟之前或之后的时间
     *
     * @param date   ：给定时间
     * @param minute ： 分钟数 向前即为负数
     * @return
     */
    public static Date addMinute(Date date, int minute) {
        Calendar current = Calendar.getInstance();
        current.setTime(date);
        current.add(Calendar.MINUTE, minute);
        return current.getTime();
    }
}
