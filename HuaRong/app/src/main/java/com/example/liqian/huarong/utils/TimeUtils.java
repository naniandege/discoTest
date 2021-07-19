package com.example.liqian.huarong.utils;

import android.annotation.SuppressLint;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeUtils {
    /**
     * 获取年
     *
     * @return
     */
    public static int getYear() {
        Calendar cd = Calendar.getInstance();
        return cd.get(Calendar.YEAR);
    }

    /**
     * 获取月
     *
     * @return
     */
    public static int getMonth() {
        Calendar cd = Calendar.getInstance();
        return cd.get(Calendar.MONTH) + 1;
    }

    /**
     * 获取日
     *
     * @return
     */
    public static int getDay() {
        Calendar cd = Calendar.getInstance();
        return cd.get(Calendar.DATE);
    }

    /**
     * 获取时
     *
     * @return
     */
    public static int getHour() {
        Calendar cd = Calendar.getInstance();
        return cd.get(Calendar.HOUR);
    }

    /**
     * 获取分
     *
     * @return
     */
    public static int getMinute() {
        Calendar cd = Calendar.getInstance();
        return cd.get(Calendar.MINUTE);
    }

    /**
     * 获取当前时间的时间戳
     *
     * @return
     */
    public static long getCurrentTimeMillis() {
        return System.currentTimeMillis();
    }

    /**
     * 获取当前时间
     */

    public static String getCurrentTime() {
        return getFormatedDateTime(System.currentTimeMillis());
    }

    /**
     * 将long转换为日期（yyyy-MM-dd HH:mm）
     *
     * @param dateTime
     * @return 到分
     */
    @SuppressLint("SimpleDateFormat")
    public static String getFormatedDateTime(long dateTime) {
        String time = "";
        try {
            SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            time = sDateFormat.format(new Date(dateTime + 0));
        } catch (Exception e) {

        }
        return time;
    }


    public static String getFormatedDateTime1(long dateTime) {
        String time = "";
        try {
            SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            time = sDateFormat.format(new Date(dateTime + 0));
        } catch (Exception e) {

        }
        return time;
    }


    /**
     * 将long转换为日期（yyyy-MM-dd HH:mm）
     *
     * @param dateTime
     * @return 到分
     */
    @SuppressLint("SimpleDateFormat")
    public static String getFormatedDateTime2(long dateTime) {
        String time = "";
        try {
            SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            time = sDateFormat.format(new Date(dateTime + 0));
        } catch (Exception e) {

        }
        return time;
    }


    /**
     * 根据提供的年月日获取该月份的第一天
     *
     * @param year
     * @param monthOfYear
     * @return
     */
    public static String getSupportBeginDayofMonth(int year, int monthOfYear) {
        Calendar cal = Calendar.getInstance();
        // 不加下面2行，就是取当前时间前一个月的第一天及最后一天
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, monthOfYear);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);

        cal.add(Calendar.DAY_OF_MONTH, -1);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        Date firstDate = cal.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        String t1 = format.format(firstDate);

        return t1;
    }

    /**
     * 根据提供的年月获取该月份的最后一天
     *
     * @param year
     * @param monthOfYear
     * @return
     */
    public static String getSupportEndDayofMonth(int year, int monthOfYear) {
        Calendar cal = Calendar.getInstance();
        // 不加下面2行，就是取当前时间前一个月的第一天及最后一天
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, monthOfYear);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);

        cal.add(Calendar.DAY_OF_MONTH, -1);
        Date lastDate = cal.getTime();

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String t1 = format.format(lastDate);
        return t1;
    }


    public static String getSupportEndDayofMonth1(int year, int monthOfYear) {
        Calendar cal = Calendar.getInstance();
        // 不加下面2行，就是取当前时间前一个月的第一天及最后一天
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, monthOfYear);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);

        cal.add(Calendar.DAY_OF_MONTH, -1);
        Date lastDate = cal.getTime();

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
        String t1 = format.format(lastDate);
        return t1;
    }


    /**
     * 将字符串型日期转换成日期
     *
     * @param dateStr    字符串型日期
     * @param dateFormat 日期格式
     * @return
     */
    public static Date stringToDate(String dateStr, String dateFormat) {
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
        try {
            return formatter.parse(dateStr);
        } catch (ParseException e) {
            return null;
        }
    }


    /**
     * //时间提前5分钟
     *
     * @param dateStr    Date
     * @param dateFormat 日期格式
     * @return
     */
    public static String dateAdvance(Date dateStr, String dateFormat) {
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
        Date date = new Date(dateStr.getTime() - 300000);
        return formatter.format(date);
    }

    /**
     * //提前一天
     *
     * @param dateFormat 日期格式
     * @return
     */
    public static String dayAdvance(String dateFormat) {
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);
        String format = formatter.format(calendar.getTime());
        return format;
    }


    public static String yearAdvance() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, -1);
        String format = formatter.format(calendar.getTime());
        return format;
    }

    public static String addAdvance() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, +1);
        String format = formatter.format(calendar.getTime());
        return format;
    }


    /*
计算天数差
因为计算出来的时间有-符号 所以要把符号替换成" "；
* */
    public static String NumberDays(String startData, String endData) {
        String dateStr = "";
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date d1 = df.parse(startData);
            Date d2 = df.parse(endData);
            long d3 = d1.getTime() - d2.getTime();
            int i = 60 * 60 * 1000 * 24;
            long l = d3 / i;
            dateStr = String.valueOf(l);
            dateStr = dateStr.replaceAll("-", "");//把-这个字符替换成空，
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateStr;
    }
}
