package com.example.liqian.huarong.utils;

import android.graphics.Bitmap;
import android.os.Build;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;

public class Amount {


    /**
     * 千位符
     */

    private static String format;

    public static String setAm(String numStr) {
        NumberFormat nf = NumberFormat.getInstance();
        DecimalFormat df = new DecimalFormat("#,###");
        try {
            format = df.format(nf.parse(numStr));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return format;
    }


    /*
    * 防止快速点击
     两次点击按钮之间的点击间隔不能少于1000毫秒
    * */
    private static final int MIN_CLICK_DELAY_TIME = 1000;
    private static long lastClickTime;

    public static boolean isFastClick() {
        boolean flag = false;
        long curClickTime = System.currentTimeMillis();
        if ((curClickTime - lastClickTime) >= MIN_CLICK_DELAY_TIME) {
            flag = true;
        }
        lastClickTime = curClickTime;
        return flag;
    }

    /**
     * 得到bitmap的大小
     */
    public static int getBitmapSize(Bitmap bitmap) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {    //API 19
            return bitmap.getAllocationByteCount();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {//API 12
            return bitmap.getByteCount();
        }
        // 在低版本中用一行的字节x高度
        return bitmap.getRowBytes() * bitmap.getHeight();
    }


    /*
     * 显示小数点后几位数
     * */
    public static double setPoint(double d) {
        BigDecimal bg = new BigDecimal(d);
        double mValue = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        return mValue;
    }

    /*
     * 让Double类型完整显示，不用科学计数法显示E
     * */
    public static String NoScientificCount(double d) {
        BigDecimal bg = new BigDecimal(d + "");
        return bg.toString();
    }
}
