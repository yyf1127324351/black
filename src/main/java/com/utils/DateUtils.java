package com.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
    private static Logger log = LoggerFactory.getLogger(DateUtils.class);

    private static final String pattern = "yyyy-MM-dd HH:mm:ss";
    private static final String pattern2 = "yyyy-MM-dd";
    private static final String pattern3 = "yyyyMMddHHmmssSSS";


    /**
     * 将date类型转化为string yyyy-MM-dd HH:mm:ss
     */
    public static String formatDateToString(Date date) {
        return formatDate(date, pattern);
    }

    /**
     * 将date类型转化为string yyyy-MM-dd
     */
    public static String formatDateToString2(Date date) {
        return formatDate(date, pattern2);
    }

    /**
     * 将date类型转化为string yyyyMMddHHmmssSSS
     */
    public static String formatDateToString3(Date date) {
        return formatDate(date, pattern3);
    }

    /**
     * 根据日期根据将date类型的日期转化为string
     *
     * @param date    当前日期
     * @param pattern 日期格式
     * @return 日期
     */
    public static String formatDate(Date date, String pattern) {
        try {
            DateFormat dateFormat = new SimpleDateFormat(pattern);
            return dateFormat.format(date);
        } catch (Exception e) {
            log.error("日期转换字符串异常：", e);
            return null;
        }
    }

    /**
     * 获取该日期的毫秒数
     *
     * @param dateString 日期字符串 yyyy-MM-dd HH:mm:ss
     * @return 毫秒
     */
    public static Long formatDateTimes(String dateString) {
        return getDateTimes(dateString, pattern);

    }

    /**
     * 获取该日期的毫秒数
     *
     * @param dateString 日期字符串 yyyy-MM-dd
     * @return 毫秒
     */
    public static Long formatDateTimes2(String dateString) {
        return getDateTimes(dateString, pattern2);
    }


    /**
     * 获取该日期的毫秒数
     *
     * @param dateString 日期字符串
     * @param pattern    日期格式
     * @return 毫秒
     */
    public static Long getDateTimes(String dateString, String pattern) {
        try {
            DateFormat dateFormat = new SimpleDateFormat(pattern);
            Date date = dateFormat.parse(dateString);
            return date.getTime();
        } catch (ParseException e) {
            log.error("获取时间毫秒数异常：", e);
            return 0L;
        }
    }


    /**
     * 获取今日剩余时间（秒）
     */
    public static int getTodayRemainTimes() {
        String nowDay = formatDateToString2(new Date());
        String nowDayEndString = nowDay + " " + "23:59:59";
        Long nowDayEndTimes = formatDateTimes(nowDayEndString);
        String nowDayString = formatDateToString(new Date());
        Long nowDayTimes = formatDateTimes(nowDayString);
        int remainTimes = (int) ((nowDayEndTimes - nowDayTimes) / 1000);
        if (remainTimes < 0) {
            return 0;
        } else {
            return remainTimes;
        }
    }

    public static void main(String[] args) {
//        int times = getTodayRemainTimes();
//        System.out.println(times);
    }

}
