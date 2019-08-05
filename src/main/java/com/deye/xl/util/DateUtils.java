package com.deye.demo.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtils {

    /**
     * 获取当前的日期yyyyMMdd
     */
    public static String getCurrentDate() {
        return new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    }

    public static String getDate(int days) {
        Date date = new Date();//取时间
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(calendar.DATE, 1);//把日期往前减少一天，若想把日期向后推一天则将负数改为正数
        date = calendar.getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(date);

        return dateString;

    }

    /**
     * 获取当前的时间yyyy-MM-dd HH:mm:ss 2019-06-07 18:33:21
     */
    public static String getCurrentTime() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }

    public static String getCurrentTime2(Date date) {
        return new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(date);
    }
    public static String getCurrentTime1() {
        return new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date());
    }

    public static String getYearStr() {
        String dateStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

        return dateStr.substring(2, 4);
    }

    public static String getMonthStr() {
        String dateStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

        return dateStr.substring(5, 7);
    }

    public static String getDayStr() {
        String dateStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

        return dateStr.substring(8, 10);
    }

    public static String getHHStr() {
        String dateStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

        return dateStr.substring(11, 13);
    }

    public static String getMMStr() {
        String dateStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

        return dateStr.substring(14, 16);
    }

    public static String getSSStr() {
        String dateStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

        return dateStr.substring(17, 19);
    }
    /**
     * 通过时间秒毫秒数判断两个时间的间隔
     */
    public static int differentDaysByMillisecond(Date date1, Date date2) {
        int days = (int) ((date2.getTime() - date1.getTime()) / (1000 * 3600 * 24));
        return days;
    }

    /**
     * 时间差秒
     */
    public static long differenttimeByMillisecond(Date date1, Date date2) {
        long time = (date2.getTime() - date1.getTime()) / 1000;
        return time;
    }

    /**
     * 时间差分钟
     */
    public static long differentMInByMillisecond(Date date1, Date date2) {
        long time = (date2.getTime() - date1.getTime()) / (1000 * 60);
        return time;
    }

    public static Date StrToDate2(String str) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = format.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static Date StrToDate(String str) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = format.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static String getYear(Date date) {
        Calendar now = Calendar.getInstance();

        now.setTime(date);
        return String.valueOf(now.get(Calendar.YEAR));
    }

    public static String getMonth(Date date) {
        Calendar now = Calendar.getInstance();
        now.setTime(date);
        return String.valueOf(now.get(Calendar.MONTH) + 1);
    }

    public static String getDay(Date date) {
        Calendar now = Calendar.getInstance();
        now.setTime(date);
        return String.valueOf(now.get(Calendar.DAY_OF_MONTH));
    }

    public static String getHOUR(Date date) {
        Calendar now = Calendar.getInstance();
        now.setTime(date);
        return String.valueOf(now.get(Calendar.HOUR_OF_DAY));
    }

    public static String getMINUTE(Date date) {
        Calendar now = Calendar.getInstance();
        now.setTime(date);
        return String.valueOf(now.get(Calendar.MINUTE));
    }

    public static String getSECOND(Date date) {
        Calendar now = Calendar.getInstance();
        now.setTime(date);
        return String.valueOf(now.get(Calendar.SECOND));
    }

    public static String getTS() {
        return new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());

    }

}
