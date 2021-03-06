
package com.example.liqian.huarong.utils;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.AppOpsManager;
import android.app.PendingIntent;
import android.app.PendingIntent.CanceledException;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.Intent.ShortcutIconResource;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.media.ExifInterface;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Build;
import android.os.Debug;
import android.os.Environment;
import android.os.StatFs;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.Thumbnails;
import android.provider.Settings;
import android.provider.Settings.SettingNotFoundException;
import android.support.annotation.RequiresApi;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;


import com.example.liqian.huarong.R;
import com.example.liqian.huarong.base.BaseApp;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.net.URLConnection;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.TimeZone;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPOutputStream;


/**
 * Created by xts on 2016-3-16.
 *
 * @?????? ????????????????????????
 */
public class Tools {

    /**
     * ??????versionCode???ANDROID????????????
     */
    public static int getVersionCode() {
        int versioncode = 0;
        try {
            PackageInfo pinfo = BaseApp.getInstance().getPackageManager().getPackageInfo(UIUtils.getPackageName(), 0);
            versioncode = pinfo.versionCode;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return versioncode;
    }

    /**
     * ??????VersionName
     *
     * @return
     */
    public static String getVersionName() {
        try {
            PackageInfo pinfo = BaseApp.getInstance().getPackageManager()
                    .getPackageInfo(UIUtils.getPackageName(), 0);
            String versionName = pinfo.versionName;
            if (null != versionName) {
                return versionName.toLowerCase();
                //return "2.2.1";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * ??????IP
     *
     * @return
     */
    public static String getLocalIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress() && (inetAddress instanceof Inet4Address)) {
                        String ip = inetAddress.getHostAddress().toString();
                        if (ip.startsWith("10.")) {
                            return "";
                        } else if (ip.startsWith("192.168.")) {
                            return "";
                        } else if (ip.startsWith("176") && (Integer.valueOf(ip.split(".")[1]) >= 16)
                                && (Integer.valueOf(ip.split(".")[1]) <= 31)) {
                            return "";
                        } else {
                            return ip;
                        }

                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }

    /**
     * ??????wifi???mac??????
     *
     * @return
     */
//    public static String getMacAddress() {
//        try {
//            WifiManager wifi = (WifiManager) BaseApplication.BaseApp.getInstance().getSystemService(Context.WIFI_SERVICE);
//            WifiInfo info = wifi.getConnectionInfo();
//            String mac = info.getMacAddress();
//            if (null != mac) {
//                return mac;
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return "";
//    }

    /**
     * ??????????????????????????? ?????? yyyy-MM-dd HH:mm
     */
    public static String getCurrentDateTimeNoSS() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",
                Locale.getDefault());
        String current_time = sdf.format(date);
        return current_time;
    }

    /**
     * * String???????????????Long
     *
     * @param ("MM/dd/yyyy HH:mm:ss")
     * @param date("12/31/2013 21:08:00")
     * @return * @throws ParseException
     */
    public static Long getLongTime(String date) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dt = sdf.parse(date);
        return dt.getTime();
    }

    /**
     * * String???????????????Long
     *
     * @param ("MM/dd/yyyy HH:mm")
     * @param date("12/31/2013 21:08:00")
     * @return * @throws ParseException
     */
    public static Long getLongTime1(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date dt = null;
        try {
            dt = sdf.parse(date);
            return dt.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0L;
    }

    /**
     * * String???????????????Long
     *
     * @param ("HH:mm")
     * @param date(21:08:00")
     * @return * @throws ParseException
     */
    public static Long getLongTime3(String date) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        Date dt = sdf.parse(date);
        return dt.getTime();
    }

    /**
     * ??????????????????????????? ?????? yyyy-MM-dd HH:mm:ss
     */
    public static String getCurrentDateTime() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        String current_time = sdf.format(date);
        return current_time;
    }

    /**
     * ??????????????????
     */
    public static int getYear() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.YEAR);
    }

    /**
     * ?????????,??????
     *
     * @return
     */
    public static String getMonth() {
        int month = Calendar.getInstance().get(Calendar.MONTH);
        String monthStr = "01";
        switch (month) {
            case 0:
                monthStr = "January";
                break;
            case 1:
                monthStr = "February";
                break;
            case 2:
                monthStr = "March";
                break;
            case 3:
                monthStr = "April";
                break;
            case 4:
                monthStr = "May";
                break;
            case 5:
                monthStr = "June";
                break;
            case 6:
                monthStr = "July";
                break;
            case 7:
                monthStr = "August";
                break;
            case 8:
                monthStr = "September";
                break;
            case 9:
                monthStr = "October";
                break;
            case 10:
                monthStr = "November";
                break;
            case 11:
                monthStr = "December";
                break;
        }
        return monthStr;
    }

    public static String getDay() {
        Calendar instance = Calendar.getInstance();
        int i = instance.get(Calendar.DAY_OF_MONTH);
        if (i > 9) {
            return i + "";
        } else {
            return "0" + i;
        }
    }

    /**
     * ??????????????????????????? ??????yyyy-MM-dd HH:mm:ss:SS
     */
    public static String getCurrentDateTimeWithSS() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        String current_time = sdf.format(date);
        return current_time;
    }

    /**
     * ??????????????????????????? ??????yyyy-MM-dd HH:mm:ss:
     */
    public static String getCurrentDateTimeWithSSS() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-ddHH:mm:ss", Locale.getDefault());
        String current_time = sdf.format(date);
        return current_time;
    }

    /**
     * ??????????????????????????????
     */
    public static String getymd() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy - MM - dd", Locale.getDefault());
        String current_time = sdf.format(date);
        return current_time;
    }

    /**
     * ??????????????????
     */
    public static String getCurrentTime() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
        String current_time = sdf.format(date);
        return current_time;
    }

    /**
     * ??????????????????
     */
    public static String getCurrentTimeMM() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("mm", Locale.getDefault());
        String current_time = sdf.format(date);
        return current_time;
    }

    /**
     * ?????????????????????????????????
     *
     * @return
     */
    public static long getCurrentTimeMillis() {
        return System.currentTimeMillis();
    }

    /**
     * ???????????? string 2 long
     *
     * @param date
     * @return
     */
    public static long getDateStringToLong(String date) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date dt2 = sdf.parse(date);
            //???????????????????????????long???
            long time = dt2.getTime();
            return time;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }


    /**
     * ???????????? string 2 long
     *
     * @param date
     * @return
     */
    public static long getDateToLong(String date) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            Date dt2 = sdf.parse(date);
            //???????????????????????????long???
            long time = dt2.getTime();
            return time;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * ?????????????????????
     *
     * @param date
     * @return
     */
    public static long getDateTimeStringToLong(String date) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date dt2 = sdf.parse(date);
            //???????????????????????????long???
            long time = dt2.getTime();
            return time;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * ??????????????????
     */
    public static String getCurrentDate() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String current_time = sdf.format(date);
        return current_time;
    }

    /**
     * ??????????????????
     */
    public static String getTodayate() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy???MM???dd???", Locale.getDefault());
        String current_time = sdf.format(date);
        return current_time;
    }

    public static boolean isAfterToday(String curDate) {
        long curLong = getDateTimeStringToLong(curDate);
        long todayLong = System.currentTimeMillis();

        return curLong > todayLong;

    }

    /**
     * ???????????????????????????
     *
     * @param time
     * @return
     */
    public static String getDateToSS(long time) {
        Date date = new Date(time);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        String current_time = sdf.format(date);
        return current_time;
    }

    /**
     * ???????????????????????????
     *
     * @param time
     * @return
     */
    public static String getDateToMM(long time) {
        Date date = new Date(time);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
        String current_time = sdf.format(date);
        return current_time;
    }

    /**
     * ???????????????????????????
     *
     * @param time
     * @return
     */
    public static String getDate(long time) {
        Date date = new Date(time);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String current_time = sdf.format(date);
        return current_time;
    }

    /**
     * ?????????????????????
     *
     * @param time
     * @return
     */
    public static String timeLongToString(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
        Date dt = new Date(time * 1000l);
        String sDateTime = sdf.format(dt); // ??????????????????????????????08/31/2006 21:08:00
        return sDateTime;
    }

    /**
     * 2015-12-7T16:00:00.000Z ??????????????????
     *
     * @param date
     * @return
     */
    public static String parseData(String date) {
        if (TextUtils.isEmpty(date)) {
            return "";
        }
        date = date.replace("Z", " UTC");//???????????????+UTC
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS Z");//???????????????????????????
        try {
            Date d = format.parse(date);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            String format1 = sdf.format(d);
            return format1;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * ????????????
     *
     * @param c
     * @return
     */
    public static String pad(int c) {
        if (c >= 10)
            return String.valueOf(c);
        else
            return "0" + String.valueOf(c);
    }

    /**
     * ????????????
     */
    public static int getDayNum(long millisTime) {
        int day = (int) (millisTime / (1000 * 60 * 60 * 24));
        if (millisTime % (1000 * 60 * 60 * 24) != 0) {
            return day + 1;
        }
        return day;
    }

    public static int getHourNum(long millisTime) {

        int day = (int) (millisTime / (1000 * 60 * 60));
        if (millisTime % (1000 * 60 * 60) != 0) {
            return day + 1;
        }
        return day;

    }

    /**
     * @param startTime
     * @param endTime
     * @return
     * @Description: ??????????????????????????????
     */
    public static int getDayBetweenDay(long startTime, long endTime) {
        int startDay = getDayNum(startTime);
        int endDay = getDayNum(endTime);
        return Math.abs(startDay - endDay);
    }

    /**
     * @param startTime
     * @param endTime
     * @return
     * @Description: ????????????????????????????????????
     */
    public static int getHourBetweenDay(long startTime, long endTime) {
        int startDay = getHourNum(startTime);
        int endDay = getHourNum(endTime);
        return Math.abs(startDay - endDay);
    }

    /**
     * @param startTime
     * @param endTime
     * @return
     * @Description: ????????????????????????????????????
     */
    public static int getHourBetweenDay(Date startTime, Date endTime) {
        int startDay = getHourNum(startTime.getTime());
        int endDay = getHourNum(endTime.getTime());
        return Math.abs(startDay - endDay);
    }

    /**
     * ???????????????????????????????????????
     *
     * @param startTime
     * @param nowTime
     * @return
     */
    public static String showRuleTime(long startTime, long nowTime) {
        String re = "";

        long difftime = nowTime - startTime;
        if (difftime < 0) {
            re = "1??????";
        } else if (difftime < 60 * 1000) {
            // ??????60s
            re = difftime / 1000 + "??????";
        } else if (difftime < 60 * 60 * 1000) {
            // ??????60min
            re = (difftime / 1000) / 60 + "?????????";
        } else {
            Date date_start = new Date(startTime);
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            String nowDay = formatter.format(new Date(nowTime));
            String yesterDay = formatter.format(new Date(nowTime - 24 * 60 * 60 * 1000));
            String startDay = formatter.format(date_start);
            if (startDay.equals(nowDay)) {
                SimpleDateFormat myFormatter = new SimpleDateFormat("HH:mm", Locale.getDefault());
                re = "??????  " + myFormatter.format(date_start);
            } else if (startDay.equals(yesterDay)) {
                SimpleDateFormat myFormatter = new SimpleDateFormat("HH:mm", Locale.getDefault());
                re = "??????  " + myFormatter.format(date_start);
            } else {
                SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
                re = myFormatter.format(date_start);
            }
        }
        return re;
    }

    /**
     * ???????????????????????????
     *
     * @param beforeTime  ??????????????????
     * @param nowTime     ???????????????
     * @param defaultDiff ???????????????
     * @return
     */
    public static boolean dateDiff(String beforeTime, String nowTime, long defaultDiff) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
            Date date_before = formatter.parse(beforeTime);
            Date date_after = formatter.parse(nowTime);
            long now_time = date_after.getTime();
            long before_time = date_before.getTime();
            long diff = now_time - before_time;
            if (diff - defaultDiff > 0) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * ??????????????????????????????
     *
     * @param second        ?????????
     * @param isKeepSeconds ???????????????
     * @return
     */
    public static String secondToMin(long second, boolean isKeepSeconds) {
        String timeStr = 0 + "???";
        try {
            if (second > 0) {
                int minute = (int) (second / 60);
                int seconds = (int) (second % 60);
                if (minute > 0) {
                    if (seconds > 0) {
                        if (isKeepSeconds) {
                            if (seconds < 10) {
                                timeStr = minute + "???0" + seconds + "???";
                            } else {
                                timeStr = minute + "???" + seconds + "???";
                            }
                        } else {
                            if (seconds >= 30) {// ??????30???+1??????
                                timeStr = (minute + 1) + "???";
                            } else {// ??????30????????????
                                timeStr = minute + "???";
                            }
                        }
                    } else {
                        timeStr = minute + "???" + "00???";
                    }
                } else {
                    timeStr = seconds + "???";
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return timeStr;
    }

    /**
     * ??????????????????????????????,??????01:23:45
     *
     * @param second        ?????????
     * @param isKeepSeconds ???????????????
     * @return
     */
    public static String secondToMin2(long second, boolean isKeepSeconds) {
        String timeStr = "00:00";
        try {
            if (second > 0) {
                int hour = (int) (second / 3600);
                int minute = (int) (second % 3600 / 60);
                int seconds = (int) (second % 3600 % 60);
                if (hour > 0) {
                    if (hour >= 10) {
                        timeStr = hour + ":";
                    } else {
                        timeStr = "0" + hour + ":";
                    }

                    if (minute >= 10) {
                        timeStr += minute + ":";
                        if (seconds >= 10) {
                            timeStr += seconds;
                        } else if (seconds > 0) {
                            timeStr += "0" + seconds;
                        } else {
                            timeStr += "00";
                        }

                    } else if (minute > 0) {
                        timeStr += "0" + minute + ":";
                        if (seconds >= 10) {
                            timeStr += seconds;
                        } else if (seconds > 0) {
                            timeStr += "0" + seconds;
                        } else {
                            timeStr += "00";
                        }
                    } else {
                        timeStr += "00:";
                        if (seconds >= 10) {
                            timeStr += seconds;
                        } else if (seconds > 0) {
                            timeStr += "0" + seconds;
                        } else {
                            timeStr += "00";
                        }
                    }
                } else {
                    if (minute >= 10) {
                        timeStr = minute + ":";
                        if (seconds >= 10) {
                            timeStr += seconds;
                        } else if (seconds > 0) {
                            timeStr += "0" + seconds;
                        } else {
                            timeStr += "00";
                        }

                    } else if (minute > 0) {
                        timeStr = "0" + minute + ":";
                        if (seconds >= 10) {
                            timeStr += seconds;
                        } else if (seconds > 0) {
                            timeStr += "0" + seconds;
                        } else {
                            timeStr += "00";
                        }
                    } else {
                        timeStr = "00:";
                        if (seconds >= 10) {
                            timeStr += seconds;
                        } else if (seconds > 0) {
                            timeStr += "0" + seconds;
                        } else {
                            timeStr += "00";
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return timeStr;
    }

    /**
     * ????????????????????????
     *
     * @param activity
     * @param icon     ??????????????????intent
     * @param icon     ??????icon
     */
    public static void addShortcut(Activity activity, int icon, Activity start) {
        Intent shortcut = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");

        // ?????????????????????
        shortcut.putExtra(Intent.EXTRA_SHORTCUT_NAME, activity.getString(R.string.app_name));
        shortcut.putExtra("duplicate", false); // ?????????????????????

        // ???????????????Activity??????????????????????????????: ??? com.everest.video.VideoPlayer
        // ??????: ComponentName????????????????????????????????????(.)?????????????????????????????????????????????
        // ComponentName comp = new ComponentName(activity.getPackageName(),
        // "."+activity.getLocalClassName());

        shortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT, new Intent(activity, start.getClass())
                .setAction(Intent.ACTION_MAIN).addCategory(Intent.CATEGORY_LAUNCHER));
        // shortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT, intent);

        // ?????????????????????
        ShortcutIconResource iconRes = ShortcutIconResource.fromContext(activity, icon);
        shortcut.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, iconRes);

        activity.sendBroadcast(shortcut);
    }

    /**
     * ?????????????????? ?????????????????????????????????
     */
    public static boolean limitSpecialCharacters(String str) {
        String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~???@#???%??????&*????????????+|{}???????????????????????????????????? ???]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return !m.replaceAll("").equalsIgnoreCase(str);
    }

    /**
     * ??????????????????
     *
     * @param resId ?????????
     * @param resId ??????id
     * @return
     */
    public static Bitmap readBitmap(int resId) {
        InputStream stream = null;
        try {
            BitmapFactory.Options opt = new BitmapFactory.Options();
            opt.inPreferredConfig = Config.ARGB_8888;
            opt.inPurgeable = true;
            opt.inInputShareable = true;
            stream = BaseApp.getInstance().getResources().openRawResource(resId);
            return BitmapFactory.decodeStream(stream, null, opt);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (stream != null) {
                    stream.close();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }

    /**
     * ????????????????????????
     *
     * @param src        ?????????
     * @param destWidth  ???????????????
     * @param destHeigth ???????????????
     * @return
     */
    public static Bitmap lessenBitmap(Bitmap src, int destWidth, int destHeigth) {
        try {
            if (src == null)
                return null;

            int w = src.getWidth();// ??????????????????
            int h = src.getHeight();
            float scaleWidth = ((float) destWidth) / w;// ??????????????????
            float scaleHeight = ((float) destHeigth) / h;// ??????????????????
            Matrix m = new Matrix();// ??????
            m.postScale(scaleWidth, scaleHeight);// ??????????????????
            Bitmap resizedBitmap = Bitmap.createBitmap(src, 0, 0, w, h, m, true);// ???????????????????????????????????????????????????
            return resizedBitmap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * ??????????????????
     *
     * @param src
     * @param targetWidth
     * @param targetHeight
     * @return
     */
    public static Bitmap isometricScaleBitmap(Bitmap src, int targetWidth, int targetHeight) {
        if (src != null) {
            int width = src.getWidth();
            int height = src.getHeight();
            if (width * targetHeight > targetWidth * height) {
                targetHeight = targetWidth * height / width;
            } else if (width * targetHeight < targetWidth * height) {
                targetWidth = targetHeight * width / height;
            }
            float scaleWidth = ((float) targetWidth) / width;// ??????????????????
            float scaleHeight = ((float) targetHeight) / height;// ??????????????????
            Matrix m = new Matrix();// ??????
            m.postScale(scaleWidth, scaleHeight);// ??????????????????
            Bitmap resizedBitmap = Bitmap.createBitmap(src, 0, 0, width, height, m, true);// ???????????????????????????????????????????????????
            return resizedBitmap;
        }
        return null;
    }

    /**
     * ?????????????????????????????????????????? ?????????????????????
     *
     * @param imagePath
     * @return
     */
    public static Bitmap readBitmapFormPath(String imagePath) {
        if (TextUtils.isEmpty(imagePath)) {
            return null;
        }
        try {
            Bitmap bitmap = null;
            File file = new File(imagePath);
            if (file.exists()) {
                bitmap = BitmapFactory.decodeFile(imagePath);
            }
            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * ???sdcard???data?????????????????????(?????????????????????????????????)
     *
     * @param imagePath
     * @return
     */
    public static Bitmap createBitmapFormSdcardOrData(String imagePath) {
        if (null == imagePath) {
            return null;
        }
        InputStream stream = null;
        try {
            File file = new File(imagePath);
            if (!file.exists())
                return null;
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(new FileInputStream(imagePath), null, o);

            final int REQUIRED_SIZE = 100;
            int width_tmp = o.outWidth, height_tmp = o.outHeight;
            int scale = 1;
            while (true) {
                if (width_tmp / 2 < REQUIRED_SIZE || height_tmp / 2 < REQUIRED_SIZE)
                    break;
                width_tmp /= 2;
                height_tmp /= 2;
                scale++;
            }

            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(imagePath), null, o2);
            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (stream != null) {
                    stream.close();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }

    /**
     * ???sdcard???data?????????????????????
     *
     * @param imagePath
     * @return
     */
    public static Bitmap createBitmapFormSdcardOrData(String imagePath, int height, int width) {
        if (null == imagePath) {
            return null;
        }
        InputStream stream = null;
        try {
            File file = new File(imagePath);
            if (!file.exists() && !file.canRead() && !file.isFile())
                return null;
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(new FileInputStream(imagePath), null, o);
            int width_tmp = o.outWidth, height_tmp = o.outHeight;
            int scale = 1;
            while (width_tmp / scale > width && height_tmp / scale > height) {
                width_tmp /= 2;
                height_tmp /= 2;
                scale++;
            }
            o.inJustDecodeBounds = false;
            o.inSampleSize = scale;
            Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(imagePath), null, o);
            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (stream != null) {
                    stream.close();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }

    /**
     * ??????????????????
     *
     * @param bitmap  ?????????
     * @param roundPx ????????????
     * @return
     */
    public static Bitmap getRoundBitmap(Bitmap bitmap, int roundPx) {
        return getRoundBitmap(bitmap, roundPx, 1.0f, 1.0f);
    }

    /**
     * ??????????????????
     *
     * @param bitmap      ?????????
     * @param roundPx     ????????????
     * @param widthScale  ??????????????????????????????????????? ??????????????????????????????1.0f
     * @param heightScale ????????????????????????????????????????????? ??????????????????????????????1.0f
     * @return
     */
    public static Bitmap getRoundBitmap(Bitmap bitmap, int roundPx, float widthScale, float heightScale) {
        Bitmap roundBitmap = null;
        try {
            roundBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);
            Canvas canvas = new Canvas(roundBitmap);
            final int color = 0xff424242;
            final Paint paint = new Paint();
            paint.setAntiAlias(true);
            int width = (int) (bitmap.getWidth() * widthScale);
            int height = (int) (bitmap.getHeight() * heightScale);
            final Rect rect = new Rect(0, 0, width, height);
            final RectF rectF = new RectF(rect);
            canvas.drawARGB(0, 0, 0, 0);
            paint.setColor(color);
            canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
            paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
            canvas.drawBitmap(bitmap, rect, rect, paint);
        } catch (Exception e) {
            e.printStackTrace();
            roundBitmap = bitmap;
        }
        return roundBitmap;
    }

    /**
     * ???assets?????????????????????
     *
     * @param imagePath
     * @return
     */
    public static Bitmap createBitmapFormAssets(String imagePath) {
        InputStream stream = null;
        try {
            if (imagePath != null) {
                stream = BaseApp.getInstance().getAssets().open(imagePath);
            }
            if (stream != null) {
                return Bitmap.createBitmap(BitmapFactory.decodeStream(stream));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (stream != null) {
                    stream.close();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }

    /**
     * ??????????????????
     *
     * @param location
     * @return
     */
    public static String getAddress(Location location) {
        try {
            if (location != null) {
                Geocoder geo = new Geocoder(BaseApp.getInstance(), Locale.getDefault());
                List<Address> addresses = geo.getFromLocation(location.getLatitude(), location.getLongitude(), 1);

                if (!addresses.isEmpty()) {
                    if (addresses.size() > 0) {
                        Address address = addresses.get(0);
                        String addressName = address.getAddressLine(0);
                        if (addressName == null || addressName.length() <= 3) {
                            addressName = address.getLocality();
                        }
                        return addressName;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "????????????";
    }

    /**
     * ???????????????
     *
     * @param location
     * @return
     */
    public static String getCurrentCity(Location location) {
        try {
            if (location != null) {
                Geocoder geo = new Geocoder(BaseApp.getInstance(), Locale.getDefault());
                List<Address> addresses = geo.getFromLocation(location.getLatitude(), location.getLongitude(), 1);

                if (!addresses.isEmpty()) {
                    if (addresses.size() > 0) {
                        Address address = addresses.get(0);
                        String addressName = address.getLocality();
                        return addressName;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "??????";
    }

    /**
     * ??????????????????
     *
     * @return
     */
    public static String getPhoneModel() {
        try {
            String phoneVersion = Build.MODEL;
            if (null != phoneVersion) {
                return phoneVersion;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * ??????????????????
     * <p/>
     * Activity??????
     *
     * @param body ????????????
     */
    public static void sendSms(Activity activity, String phoneNumber, String body) {
        try {
            Uri smsToUri = Uri.parse("smsto:" + phoneNumber);// ???????????????
            Intent intent = new Intent(Intent.ACTION_SENDTO, smsToUri);
            intent.putExtra("address", phoneNumber);
            intent.putExtra("sms_body", body);// ???????????????
            activity.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * ??????????????????
     *
     * @param activity
     * @param phoneNum
     */
    public static void openSystemPhone(Activity activity, String phoneNum) {
        try {
            Intent it = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNum));
            activity.startActivity(it);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * ?????????????????????
     *
     * @param activity
     * @param url
     */
    public static void openSystemBrowser(Activity activity, String url) {
        try {

            Intent it = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            activity.startActivity(it);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * ????????????????????????
     *
     * @param activity
     */
    public static void openSystemNetworkSetting(Activity activity) {
        try {
            Intent i = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            activity.startActivity(i);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * ??????????????????
     *
     * @param activity
     */
    public static void openSystemMap(Activity activity, String latitude, String longitude) {
        try {
            Uri mUri = Uri.parse("geo:" + latitude + "," + longitude);
            Intent mIntent = new Intent(Intent.ACTION_VIEW, mUri);
            activity.startActivity(mIntent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * ?????????????????????
     *
     * @return int
     */
    public static int getScreenWidth() {
        DisplayMetrics dm = new DisplayMetrics();
        dm = BaseApp.getInstance().getApplicationContext().getResources().getDisplayMetrics();
        return dm.widthPixels;
    }

    /**
     * ?????????????????????
     *
     * @return int
     */
    public static int getScreenHeight() {
        DisplayMetrics dm = new DisplayMetrics();
        dm = BaseApp.getInstance().getResources().getDisplayMetrics();
        return dm.heightPixels;
    }

    /**
     * ????????????????????????
     */
    public static int getMemory() {
        int pss = 0;
        ActivityManager myAM = (ActivityManager) BaseApp.getInstance()
                .getSystemService(Context.ACTIVITY_SERVICE);
        String packageName = BaseApp.getInstance().getPackageName();
        List<RunningAppProcessInfo> appProcesses = myAM.getRunningAppProcesses();
        for (RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.processName.equals(packageName)) {
                int pids[] = {appProcess.pid};
                Debug.MemoryInfo self_mi[] = myAM.getProcessMemoryInfo(pids);
                pss = self_mi[0].getTotalPss();
            }
        }
        return pss;
    }

    /**
     * ??????CPU?????????
     */
    public static int getCpuInfo() {
        int cpu = 0;
        try {
            RandomAccessFile reader = new RandomAccessFile("/proc/stat", "r");
            String load = reader.readLine();
            String[] toks = load.split(" ");
            long idle1 = Long.parseLong(toks[5]);
            long cpu1 = Long.parseLong(toks[2]) + Long.parseLong(toks[3]) + Long.parseLong(toks[4])
                    + Long.parseLong(toks[6]) + Long.parseLong(toks[7]) + Long.parseLong(toks[8]);
            try {
                Thread.sleep(500);
            } catch (Exception e) {
                e.printStackTrace();
            }
            reader.seek(0);
            load = reader.readLine();
            reader.close();
            toks = load.split(" ");
            long idle2 = Long.parseLong(toks[5]);
            long cpu2 = Long.parseLong(toks[2]) + Long.parseLong(toks[3]) + Long.parseLong(toks[4])
                    + Long.parseLong(toks[6]) + Long.parseLong(toks[7]) + Long.parseLong(toks[8]);
            cpu = (int) (100 * (cpu2 - cpu1) / ((cpu2 + idle2) - (cpu1 + idle1)));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return cpu;
    }

    /**
     * ????????????IMEI
     *
     * @return
     */
    public static String getIMEI() {
        try {
            TelephonyManager tm = (TelephonyManager) BaseApp.getInstance()
                    .getSystemService(Context.TELEPHONY_SERVICE);
            @SuppressLint("MissingPermission") String imei = tm.getDeviceId();
            if (null != imei) {
                return imei;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * ????????????IMSI
     *
     * @return
     */
    public static String getIMSI() {
        try {
            TelephonyManager tm = (TelephonyManager) BaseApp.getInstance()
                    .getSystemService(Context.TELEPHONY_SERVICE);
            @SuppressLint("MissingPermission") String imsi = tm.getSubscriberId();
            if (null != imsi) {
                return imsi;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * ??????????????????
     *
     * @return
     */
    public static int getSystemBrightness() {
        int brightness = 5;
        try {
            brightness = Settings.System.getInt(BaseApp.getInstance().getContentResolver(),
                    Settings.System.SCREEN_BRIGHTNESS);
            brightness = brightness * 100 / 255;
        } catch (SettingNotFoundException ex) {
            ex.printStackTrace();
        }
        return brightness >= 5 ? brightness : 5;
    }

    /**
     * ??????????????????
     *
     * @param value
     */
    public static void setBackLight(Activity activity, int value) {
        try {
            WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
            lp.screenBrightness = (float) (value * (0.01));
            activity.getWindow().setAttributes(lp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * ??????sdcard???data???????????????
     */
    public static long getSdcardFreeSize(String rootPath) {
        // ??????sdcard????????????
        StatFs statFs = new StatFs(rootPath);
        // ??????block???SIZE
        long blocSize = statFs.getBlockSize();
        // ????????????Block?????????
        long availaBlock = statFs.getAvailableBlocks();
        // ??????????????????
        long freeSize = availaBlock * blocSize;
        return freeSize;
    }

    /**
     * ?????????????????????
     *
     * @return
     */
    public static String getSDK() {
        try {
            String release = Build.VERSION.RELEASE;
            if (null != release) {
                return release;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * ???????????????launcher??????
     *
     * @param context
     * @return
     */
    private static List<String> getAllTheLauncher(Context context) {
        List<String> names = null;
        PackageManager pkgMgt = context.getPackageManager();
        Intent it = new Intent(Intent.ACTION_MAIN);
        it.addCategory(Intent.CATEGORY_HOME);
        List<ResolveInfo> ra = pkgMgt.queryIntentActivities(it, 0);
        if (ra.size() != 0) {
            names = new ArrayList<String>();
        }
        for (int i = 0; i < ra.size(); i++) {
            String packageName = ra.get(i).activityInfo.packageName;
            names.add(packageName);
        }
        return names;
    }

    /**
     * ??????????????????????????????(????????????float??????)
     *
     * @param str
     * @return
     */
    public static boolean isNumber(String str) {
        if (str.matches("\\d*")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * ??????????????????????????????(??????float??????)
     *
     * @param str
     */
    public static boolean isNumeric(String str) {
        if (str.matches("\\d+(.\\d+)?")) {
            return true;
        } else {// ????????????
            return false;
        }
    }

    /**
     * ??????Selector
     */
    public static StateListDrawable newSelector(Context context, int[] state) {
        StateListDrawable bg = new StateListDrawable();
        Drawable normal = state[0] == -1 ? null : context.getResources().getDrawable(state[0]);
        Drawable pressed = state[1] == -1 ? null : context.getResources().getDrawable(state[1]);
        Drawable focused = state[1] == -1 ? null : context.getResources().getDrawable(state[1]);
        Drawable unable = state[0] == -1 ? null : context.getResources().getDrawable(state[0]);
        // View.PRESSED_ENABLED_STATE_SET
        bg.addState(new int[]{android.R.attr.state_pressed, android.R.attr.state_enabled}, pressed);
        // View.ENABLED_FOCUSED_STATE_SET
        bg.addState(new int[]{android.R.attr.state_enabled, android.R.attr.state_focused}, focused);
        // View.ENABLED_STATE_SET
        bg.addState(new int[]{android.R.attr.state_enabled}, normal);
        // View.FOCUSED_STATE_SET
        bg.addState(new int[]{android.R.attr.state_focused}, focused);
        // View.WINDOW_FOCUSED_STATE_SET
        bg.addState(new int[]{android.R.attr.state_window_focused}, unable);
        // View.EMPTY_STATE_SET
        bg.addState(new int[]{}, normal);
        return bg;
    }

    /**
     * ???????????????
     */
    public static void switchKeyBoardCancle(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        // ??????InputMethodManager?????????
        if (imm.isActive()) {
            // ????????????
            imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_NOT_ALWAYS);
            // ????????????????????????????????????????????????????????????????????????????????????
        }
    }

    /**
     * ???????????????
     *
     * @param mEditText ?????????
     */
    public static void openKeybord(EditText mEditText) {
        InputMethodManager imm = (InputMethodManager) BaseApp.getInstance()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(mEditText, InputMethodManager.RESULT_SHOWN);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,
                InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    /**
     * ???????????????
     *
     * @param activity
     */
    public static void closeKeyBoard(Activity activity, View v) {
       /* InputMethodManager im = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        im.hideSoftInputFromWindow(activity.getWindow().getDecorView().getApplicationWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);*/
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    /**
     * ???????????????
     *
     * @param activity
     */
    public static void closeKeyBoard(Activity activity) {
        InputMethodManager im = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        im.hideSoftInputFromWindow(activity.getWindow().getDecorView().getApplicationWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);

    }


    /**
     * MD5??????
     */
    public static String getMD5Str(String str) {
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(str.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        byte[] byteArray = messageDigest.digest();
        StringBuffer md5StrBuff = new StringBuffer();
        for (int i = 0; i < byteArray.length; i++) {
            if (Integer.toHexString(0xFF & byteArray[i]).length() == 1) {
                md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));
            } else {
                md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
            }
        }
        return md5StrBuff.substring(0, md5StrBuff.length()).toString();
    }

    /**
     * ???????????????
     *
     * @return
     */
    public static String getRandomNumber() {
        return new DecimalFormat("0000000000").format(new Random().nextInt(1000000000));
    }

    /**
     * ?????????????????????????????????
     *
     * @return
     */
    public static String subAndCombinationString(String str, int subLength,
                                                 boolean isReduction) {
        if (isReduction) {
            String str1 = str.substring(0, subLength);
            String str2 = str.replace(str1, "");
            String result = str2 + str1;
            return result;
        } else {
            String temp = str.substring(0, str.length() - subLength);
            String str1 = temp.substring(0, subLength);
            String str2 = temp.replace(str1, "");
            String str3 = str.replace(temp, "");
            String result = str3 + str1 + str2;
            return result;
        }
    }

    /**
     * ?????????????????????sd???
     *
     * @param bitmapPath
     * @param bitmapName
     * @param mBitmap
     */
    public static void saveBitmapToSdcard(String bitmapPath, String bitmapName, Bitmap mBitmap) {
        FileOutputStream fOut = null;
        try {
            File f = new File(bitmapPath, bitmapName);
            File parent = f.getParentFile();
            if (parent != null && !parent.exists()) {
                parent.mkdirs();
            }

            if (!f.exists()) {
                f.createNewFile();
            }

            fOut = new FileOutputStream(f);

            mBitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fOut != null) {
                    fOut.flush();
                    fOut.close();
                    fOut = null;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * ?????????????????????sd???
     *
     * @param bitmapPath
     * @param mBitmap
     */
    public static void saveBitmapToSdcard(String bitmapPath, Bitmap mBitmap) {
        FileOutputStream fOut = null;
        try {
            File f = new File(bitmapPath);
            File parent = f.getParentFile();
            if (parent != null && !parent.exists()) {
                parent.mkdirs();
            }
            if (!f.exists()) {
                f.createNewFile();
            }

            fOut = new FileOutputStream(f);

            mBitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);

        } catch (final Exception e) {
            e.printStackTrace();

        } finally {
            try {
                if (fOut != null) {
                    fOut.flush();
                    fOut.close();
                    fOut = null;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * string???boolean
     *
     * @param s
     * @return
     */
    public static boolean stringToBoolean(String s) {
        if (s != null) {
            if (s.equals("1")) {
                return true;
            } else if (s.equals("0")) {
                return false;
            } else {
                return Boolean.parseBoolean(s);
            }
        }
        return false;
    }

    /**
     * ??????uuid
     *
     * @return
     */
    public static String getUuid() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * ????????????
     */
    public static void exitApp(boolean is) {

//		BaseApplication.BaseApp.getInstance().exit();
//		Context BaseApp.getInstance() = BaseApplication.BaseApp.getInstance();
//		NotificationManager notificationManager = (NotificationManager) BaseApp.getInstance()
//				.getSystemService(Context.NOTIFICATION_SERVICE);
//		notificationManager.cancelAll();
//		if(is)
//		{
//			ActivityManager activityManager = (ActivityManager) BaseApp.getInstance().getSystemService(Context.ACTIVITY_SERVICE);
//			activityManager.restartPackage(BaseApp.getInstance().getPackageName());
//		}


//		android.os.Process.killProcess(android.os.Process.myPid());


    }

    public static void restartApplication(Context context) {
        final Intent intent = context.getPackageManager().getLaunchIntentForPackage(UIUtils.getPackageName());
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
    }

    /**
     * ???????????????sd???
     *
     * @param content
     * @param fileName
     * @param filePath
     * @param isGzip   true????????????
     * @param isAppend true???????????????false???????????????
     * @return 0:?????????1???sd????????????2???????????????,3:???????????????
     * @throws
     */
    public synchronized static void writeDataToSdcard(byte[] content, String filePath, String fileName, boolean isGzip,
                                                      boolean isAppend) throws Exception {
        FileOutputStream fos = null;
        GZIPOutputStream gzin = null;
        try {
            // ??????????????????????????????????????????sdcard
            File testDir = new File(filePath);
            if (!testDir.exists()) {
                testDir.mkdirs();
            }

            File file = new File(filePath + fileName);
            if (isAppend) {
                if (!file.exists()) {
                    file.createNewFile();
                }
            } else {
                if (file.exists()) {
                    file.delete();
                }
                file.createNewFile();
            }

            if (file.exists() && file.canWrite()) {
                fos = new FileOutputStream(file, isAppend);
                if (isGzip) {
                    gzin = new GZIPOutputStream(fos);
                    gzin.write(content);
                    gzin.flush();
                } else {
                    fos.write(content);
                    fos.flush();
                }
            }
        } catch (Exception e) {
            throw e;
        } finally {
            try {
                if (gzin != null) {
                    gzin.close();
                }
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * ?????????????????????????????????
     *
     * @param paint
     * @return
     */
    public static float getFontHeight(Paint paint) {
        FontMetrics fm = paint.getFontMetrics();
        return fm.descent - fm.ascent;
    }

    /**
     * ?????????????????????_id?????????
     *
     * @return
     */
    public static LinkedHashMap<Long, String> getAllImages(Context context) {
        LinkedHashMap<Long, String> images = new LinkedHashMap<Long, String>();
        try {
            // ??????????????????????????????Cursor
            Intent intent = new Intent(Intent.ACTION_PICK,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            Uri uri = intent.getData();
            String[] proj = {MediaStore.Images.Media._ID, MediaStore.Images.Media.DATA};
            Cursor cursor = context.getContentResolver().query(uri, proj, null, null,
                    MediaStore.Images.ImageColumns.DATE_ADDED + " DESC");
            while (cursor.moveToNext()) {
                // ???????????????_id
                long id = cursor.getLong(cursor.getColumnIndex(MediaStore.Images.Media._ID));
                // ?????????????????????
                String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
                images.put(id, path);
            }
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return images;
    }

    /**
     * ????????????????????????
     *
     * @param context
     * @param imageId ?????????????????????_id
     * @return
     */
    public static Bitmap getImageThumbnail(Context context, long imageId) {
        Bitmap bitmap = null;
        // ??????ID???????????????
        bitmap = Thumbnails.getThumbnail(context.getContentResolver(), imageId, Thumbnails.MICRO_KIND, null);
        return bitmap;
    }

    /**
     * ????????????????????????????????????_id?????????
     *
     * @return
     */
    public static LinkedHashMap<Long, String> getAllImages(Context context, String folderPath) {
        LinkedHashMap<Long, String> images = new LinkedHashMap<Long, String>();
        try {
            // ??????????????????????????????Cursor
            Intent intent = new Intent(Intent.ACTION_PICK,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            Uri uri = intent.getData();
            String[] proj = {MediaStore.Images.Media._ID, MediaStore.Images.Media.DATA};
            Cursor cursor = context.getContentResolver().query(uri, proj, null, null,
                    MediaStore.Images.ImageColumns.DATE_ADDED + " DESC");
            while (cursor.moveToNext()) {
                // ???????????????_id
                long id = cursor.getLong(cursor.getColumnIndex(MediaStore.Images.Media._ID));
                // ?????????????????????
                String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
                int lastIndexOf = path.lastIndexOf(File.separator);
                String substring = path.substring(0, lastIndexOf);
                if (folderPath.equals(substring)) {
                    images.put(id, path);
                }
            }
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return images;
    }

    /**
     * ???????????????????????????
     *
     * @param videoPath ????????????
     * @return
     */
    public static Bitmap getVideoFirstFrame(String videoPath) {
        Bitmap bitmap = null;
        try {
            if (!TextUtils.isEmpty(videoPath)) {
                MediaMetadataRetriever media = new MediaMetadataRetriever();
                media.setDataSource(videoPath);
                bitmap = media.getFrameAtTime(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    /**
     * ????????????
     *
     * @param angle
     * @param bitmap
     * @return Bitmap
     */
    public static Bitmap rotaingImageView(int angle, Bitmap bitmap) {
        // ???????????? ??????
        Matrix matrix = new Matrix();
        ;
        matrix.postRotate(angle);
        // ??????????????????
        Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return resizedBitmap;
    }

    /**
     * ????????????????????????????????????
     *
     * @param path ??????????????????
     * @return degree???????????????
     */
    public static int readPictureDegree(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    /**
     * ????????????????????????1???11???????????????????????????
     *
     * @param no
     * @return
     */
    public static boolean matchesPhoneNo(String no) {
        String pattern = "^[1]\\d{10}$";
        return Pattern.compile(pattern).matcher(no).matches();
    }


    /**
     * ???????????????????????????
     *
     * @param no
     * @return
     */
    public static boolean isPsd(String no) {
        String pattern = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,20}$";
        return Pattern.compile(pattern).matcher(no).matches();
    }

    /**
     * ??????yyyy-MM-dd???????????????  ??????????????????
     *
     * @param s ??????2010-01-01???????????????
     * @param n ???????????????????????????
     * @return
     */
    public static String addDay(String s, int n) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            Calendar cd = Calendar.getInstance();
            cd.setTime(sdf.parse(s));
            cd.add(Calendar.DATE, n);//????????????
            //cd.add(Calendar.MONTH, n);//???????????????

            return sdf.format(cd.getTime());

        } catch (Exception e) {
            return null;
        }

    }

    /**
     * ??????yyyy-MM-dd???????????????  ???????????????
     *
     * @param s 2010-01-01???????????????
     * @param n ???????????????????????????
     * @return
     */
    public static String JianDay(String s, int n) {
        try {

            SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd");
            Calendar date = Calendar.getInstance();
            date.setTime(dft.parse(s));
            date.set(Calendar.DATE, date.get(Calendar.DATE) - 1);//?????????
//	    		date.set(Calendar.MONTH, date.get(Calendar.MONTH)-1);//?????????
            Date endDate = dft.parse(dft.format(date.getTime()));
            return dft.format(endDate);

        } catch (Exception e) {
            return null;
        }

    }


    /**
     * @param curTime
     * @param start   HH???mm ??????
     * @param end     HH???mm ??????
     * @return
     * @Description: ???????????????????????????????????????
     */
    public static boolean isTimeBetween(long curTime, String date, String start, String end) {

        long startLong = Tools.getDateStringToLong(date + " " + start + ":00");
        long endLong = Tools.getDateStringToLong(date + " " + end + ":00");

        return curTime > startLong && curTime < endLong;
    }

    /**
     * ??????????????????????????????bitmap url - ???????????????????????????????????????,??????:
     * <p/>
     * A.????????????: url="http://blog.foreverlove.us/girl2.png" ;
     * <p/>
     * B.????????????:url="file://mnt/sdcard/photo/image.png";
     * <p/>
     * C.????????????????????? ,png, jpg,bmp,gif??????
     *
     * @param path
     * @return
     */

    public static Bitmap convertToBitmap(String path) {
        int w = BaseApp.mWidthPixels ;
        int h = BaseApp.mHeightPixels;
        BitmapFactory.Options opts = new BitmapFactory.Options();
        // ?????????ture?????????????????????
        opts.inJustDecodeBounds = true;
        opts.inPreferredConfig = Config.ARGB_8888;
        // ????????????
        BitmapFactory.decodeFile(path, opts);
        int width = opts.outWidth;
        int height = opts.outHeight;
        float scaleWidth = 0.f, scaleHeight = 0.f;
        if (width > w || height > h) {
            // ??????
            scaleWidth = ((float) width) / w;
            scaleHeight = ((float) height) / h;
        }
        opts.inJustDecodeBounds = false;
        float scale = Math.max(scaleWidth, scaleHeight);
        opts.inSampleSize = (int) scale;
        WeakReference<Bitmap> weak = new WeakReference<Bitmap>(BitmapFactory.decodeFile(path, opts));
//        return Bitmap.createScaledBitmap(weak.get(), w, h, true);
        return Bitmap.createBitmap(weak.get());
    }


    public static boolean isAppOnForeground() {
        // Returns a list of application processes that are running on the
        // device

        ActivityManager activityManager = (ActivityManager) BaseApp.getInstance().getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
        String packageName = BaseApp.getInstance().getApplicationContext().getPackageName();

        List<RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
        if (appProcesses == null)
            return false;

        for (RunningAppProcessInfo appProcess : appProcesses) {
            // The name of the process that this object is associated with.
            if (appProcess.processName.equals(packageName)
                    && appProcess.importance == RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                return true;
            }
        }

        return false;
    }

    /**
     * ??????????????????????????????bitmap url - ???????????????????????????????????????,??????:
     * <p/>
     * A.????????????: url="http://blog.foreverlove.us/girl2.png" ;
     * <p/>
     * B.????????????:url="file://mnt/sdcard/photo/image.png";
     * <p/>
     * C.????????????????????? ,png, jpg,bmp,gif??????
     *
     * @param path
     * @return
     */

    public static Bitmap convertToBitmap(String path, int w, int h) {
        BitmapFactory.Options opts = new BitmapFactory.Options();
        // ?????????ture?????????????????????
        opts.inJustDecodeBounds = true;
        opts.inPreferredConfig = Config.ARGB_8888;
        // ????????????
        BitmapFactory.decodeFile(path, opts);
        int width = opts.outWidth;
        int height = opts.outHeight;
        float scaleWidth = 0.f, scaleHeight = 0.f;
        if (width > w || height > h) {
            // ??????
            scaleWidth = ((float) width) / w;
            scaleHeight = ((float) height) / h;
        }
        opts.inJustDecodeBounds = false;
        float scale = Math.max(scaleWidth, scaleHeight);
        opts.inSampleSize = (int) scale;
        WeakReference<Bitmap> weak = new WeakReference<Bitmap>(BitmapFactory.decodeFile(path, opts));
        return Bitmap.createScaledBitmap(weak.get(), w, h, true);
    }

    /**
     * ??????????????????
     *
     * @param bitmap
     * @return
     */
    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap) {

        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final Paint paint = new Paint();
        //????????????????????????????????????
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int w;
        int deltaX = 0;
        int deltaY = 0;
        if (width <= height) {
            w = width;
            deltaY = height - w;
        } else {
            w = height;
            deltaX = width - w;
        }
        final Rect rect = new Rect(deltaX, deltaY, w, w);
        final RectF rectF = new RectF(rect);

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        //???????????????????????????

        int radius = (int) (Math.sqrt(w * w * 2.0d) / 2);
        canvas.drawRoundRect(rectF, radius, radius, paint);

        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;
    }

    /**
     * ???????????????emoji??????
     *
     * @param source
     * @return
     */
    public static boolean containsEmoji(String source) {
        int len = source.length();
        for (int i = 0; i < len; i++) {
            char codePoint = source.charAt(i);
            if (!isEmojiCharacter(codePoint)) {
                //??????????????????,???????????????Emoji??????
                return true;
            }
        }
        return false;
    }

    /**
     * ???????????????Emoji
     *
     * @param codePoint ?????????????????????
     * @return
     */
    private static boolean isEmojiCharacter(char codePoint) {
        return (codePoint == 0x0) || (codePoint == 0x9) || (codePoint == 0xA) ||
                (codePoint == 0xD) || ((codePoint >= 0x20) && (codePoint <= 0xD7FF)) ||
                ((codePoint >= 0xE000) && (codePoint <= 0xFFFD)) || ((codePoint >= 0x10000)
                && (codePoint <= 0x10FFFF));
    }

    public static String getSelectTime(int y, int m, int d) {
        StringBuffer sb = new StringBuffer();
        sb.append(y);
        sb.append("-");
        if (m < 10)
            sb.append(0);
        sb.append(m);
        sb.append("-");
        if (d < 10)
            sb.append(0);
        sb.append(d);

        return sb.toString();
    }

    /**
     * ??????GPS???????????????GPS??????AGPS?????????????????????????????????
     *
     * @param context
     * @return true ????????????
     */
    public static final boolean isOPen(final Context context) {
        LocationManager locationManager
                = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        // ??????GPS??????????????????????????????????????????????????????24????????????????????????????????????????????????????????????????????????
        boolean gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (gps) {
            return true;
        }

        return false;
    }

    /**
     * ?????????????????????GPS
     *
     * @param context
     */
    public static final void openGPS(Context context) {
        Intent GPSIntent = new Intent();
        GPSIntent.setClassName("com.android.settings",
                "com.android.settings.widget.SettingsAppWidgetProvider");
        GPSIntent.addCategory("android.intent.category.ALTERNATIVE");
        GPSIntent.setData(Uri.parse("custom:3"));
        try {
            PendingIntent.getBroadcast(context, 0, GPSIntent, 0).send();
        } catch (CanceledException e) {
            e.printStackTrace();
        }
    }


    /**
     * ???list<String>??????????????????????????????
     */
    public static String getListString(List<String> list) {
        StringBuffer buffer = new StringBuffer();
        if (null != list && list.size() > 0) {
            for (String str : list) {
                buffer.append(str + ",");
            }
            return buffer.toString().substring(0, buffer.toString().length() - 1);
        }
        return "";
    }

    /**
     * ?????????????????????????????????list<String></>
     */
    public static List<String> getList(String str) {
        if (!TextUtils.isEmpty(str)) {
            String[] array = str.split(",");
            return Arrays.asList(array);
        }
        return null;
    }

    public static Bitmap createColorBitmap(int color, int width, int height) {
        Bitmap bmp = Bitmap.createBitmap(width, height, Config.ARGB_8888);
        bmp.eraseColor(color);
        return bmp;
    }


    /**
     * ?????????????????????????????????
     */
    public static double getDistanceFromXtoY(double lat_a, double lng_a,
                                             double lat_b, double lng_b) {
        double pk = (double) (180 / 3.14169);

        double a1 = lat_a / pk;
        double a2 = lng_a / pk;
        double b1 = lat_b / pk;
        double b2 = lng_b / pk;

        double t1 = Math.cos(a1) * Math.cos(a2) * Math.cos(b1) * Math.cos(b2);
        double t2 = Math.cos(a1) * Math.sin(a2) * Math.cos(b1) * Math.sin(b2);
        double t3 = Math.sin(a1) * Math.sin(b1);
        double tt = Math.acos(t1 + t2 + t3);

        return 6366000 * tt;
    }

    //???????????????????????????????????????
    public static Bitmap rotateBitmapByDegree(Bitmap bm, int degree) {
        Bitmap returnBm = null;

        // ???????????????????????????????????????
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        try {
            // ?????????????????????????????????????????????????????????????????????
            returnBm = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), matrix, true);
        } catch (OutOfMemoryError e) {
        }
        if (returnBm == null) {
            returnBm = bm;
        }
        if (bm != returnBm) {
            bm.recycle();
        }
        return returnBm;
    }

    //bitmap?????????drawable
    public static Bitmap setDrawable1(Bitmap backGroundMap) {
        int widthDrawable = backGroundMap.getWidth();
        int heightDrawable = backGroundMap.getHeight();//??????????????????????????????

        int center_X = widthDrawable / 2;
        int center_Y = heightDrawable / 2;

        int WH = 100;
        if (widthDrawable > WH && heightDrawable > WH) {
            WH = 100;
        } else {
            if (widthDrawable > heightDrawable) {
                WH = heightDrawable;
            } else {
                WH = widthDrawable;
            }
        }
        int start_x = center_X - WH / 2;
        int start_y = center_Y - WH / 2;
        return Bitmap.createBitmap(backGroundMap, start_x, start_y, WH,
                WH, null, true);

    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    public static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};
        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    // ??????Android4.4????????????Uri??????????????????????????????????????????????????????
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static String getPathByUri4kitkat(final Context context, final Uri uri) {
        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            if (isExternalStorageDocument(uri)) {// ExternalStorageProvider
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];
                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            } else if (isDownloadsDocument(uri)) {// DownloadsProvider
                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"),
                        Long.valueOf(id));
                return getDataColumn(context, contentUri, null, null);
            } else if (isMediaDocument(uri)) {// MediaProvider
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];
                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }
                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{split[1]};
                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {// MediaStore
            // (and
            // general)
            return getDataColumn(context, uri, null, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {// File
            return uri.getPath();
        }
        return null;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static boolean isNotificationEnabled(Context context) {

        String CHECK_OP_NO_THROW = "checkOpNoThrow";
        String OP_POST_NOTIFICATION = "OP_POST_NOTIFICATION";

        AppOpsManager mAppOps = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
        ApplicationInfo appInfo = context.getApplicationInfo();
        String pkg = context.getApplicationContext().getPackageName();
        int uid = appInfo.uid;

        Class appOpsClass = null;
        /* Context.APP_OPS_MANAGER */
        try {
            appOpsClass = Class.forName(AppOpsManager.class.getName());
            Method checkOpNoThrowMethod = appOpsClass.getMethod(CHECK_OP_NO_THROW, Integer.TYPE, Integer.TYPE,
                    String.class);
            Field opPostNotificationValue = appOpsClass.getDeclaredField(OP_POST_NOTIFICATION);

            int value = (Integer) opPostNotificationValue.get(Integer.class);
            Integer invoke = (Integer) checkOpNoThrowMethod.invoke(mAppOps, value, uid, pkg);
            return (invoke == AppOpsManager.MODE_ALLOWED) || invoke == AppOpsManager.MODE_DEFAULT;

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static String getDateThisTimeZone(String scenicTime, String scenicTimeZone) {
        //Asia/Tokyo,Asia/Shanghai
        if (TextUtils.isEmpty(scenicTime)) {
            return "";
        }
        scenicTime = "2017-03-03T" + scenicTime;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        formatter.setTimeZone(TimeZone.getTimeZone(scenicTimeZone));
        Date value = null;
        try {
            value = formatter.parse(scenicTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        dateFormatter.setTimeZone(TimeZone.getDefault());
        String dt = dateFormatter.format(value);
        String[] ts = dt.split("T");
        return ts[1];
    }

    /**
     * ??????????????????24??????????????????0???,????????????????????????0?????????????????????
     * ???????????????
     *
     * @param currentTime ????????????
     * @param pastTime    ???????????????
     */
    public static boolean judgeTime(long currentTime, long pastTime) {
        long current = currentTime / (1000 * 3600 * 24);
        long past = pastTime / (1000 * 3600 * 24);
        if (current - past >= 1) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * ??????
     *
     * @param plaintext ??????
     * @return ciphertext ??????
     */
    public static String encrypt(String plaintext) {
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'a', 'b', 'c', 'd', 'e', 'f'};
        try {
            byte[] btInput = plaintext.getBytes();
            // ??????MD5??????????????? MessageDigest ??????
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // ?????????????????????????????????
            mdInst.update(btInput);
            // ????????????
            byte[] md = mdInst.digest();
            // ????????????????????????????????????????????????
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Return pseudo unique ID,Serial?????????-->UUID
     *
     * @return ID
     */
    public static String getDeviceId() {

        // If all else fails, if the user does have lower than API 9 (lower
        // than Gingerbread), has reset their phone or 'Secure.ANDROID_ID'
        // returns 'null', then simply the ID returned will be solely based
        // off their Android device information. This is where the collisions
        // can happen.
        // Thanks http://www.pocketmagic.net/?p=1662!
        // Try not to use DISPLAY, HOST or ID - these items could change.
        // If there are collisions, there will be overlapping data
        String m_szDevIDShort = "35" + (Build.BOARD.length() % 10) + (Build.BRAND.length() % 10) + (Build.CPU_ABI.length() % 10) + (Build.DEVICE.length() % 10) + (Build.MANUFACTURER.length() % 10) + (Build.MODEL.length() % 10) + (Build.PRODUCT.length() % 10);

        String serial = null;
        try {
            serial = Build.class.getField("SERIAL").get(null).toString();
            // Go ahead and return the serial for api => 9
            return new UUID(m_szDevIDShort.hashCode(), serial.hashCode()).toString();
        } catch (Exception e) {
            // String needs to be initialized
            serial = "serial"; // some value
        }

        return new UUID(m_szDevIDShort.hashCode(), serial.hashCode()).toString();
    }

    /**
     * ????????????????????????bitmap
     */
    public static Bitmap convertRes2Bitmap(Context context, int resId) {
        return BitmapFactory.decodeResource(context.getResources(), resId);
    }

    public static String getIP(Context context) {

        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress() && (inetAddress instanceof Inet4Address)) {
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (SocketException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * ??????ip
     *
     * @return
     */
    public static String GetNetIp() {
        URL infoUrl = null;
        InputStream inStream = null;
        String line = "";
        try {
            infoUrl = new URL("http://pv.sohu.com/cityjson?ie=utf-8");
            URLConnection connection = infoUrl.openConnection();
            HttpURLConnection httpConnection = (HttpURLConnection) connection;
            int responseCode = httpConnection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                inStream = httpConnection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inStream, "utf-8"));
                StringBuilder strber = new StringBuilder();
                while ((line = reader.readLine()) != null)
                    strber.append(line + "\n");
                inStream.close();
                // ??????????????????????????????IP??????
                int start = strber.indexOf("{");
                int end = strber.indexOf("}");
                String json = strber.substring(start, end + 1);
                if (json != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(json);
                        line = jsonObject.optString("cip");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                return line;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return line;
    }
}
