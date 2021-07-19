package com.example.liqian.huarong.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;


/**
 * @author lq
 * Created by asus on 2019/4/28.
 */

public class ImageLoader {
    /**
     * 通过链接加载网络图片
     *
     * @param context
     * @param url
     * @param iv
     * @param placeImg
     */
    public static void setImage(Context context, String url, ImageView iv, int placeImg) {
        RequestOptions options = new RequestOptions()
                .dontAnimate()
                .placeholder(placeImg);
        Glide.with(context).load(url).thumbnail(0.1f).apply(options).into(iv);
    }

    /**
     * 通过链接加载网络图片
     *
     * @param context
     * @param bitmap
     * @param iv
     * @param placeImg
     */
    public static void setImage(Context context, Bitmap bitmap, ImageView iv, int placeImg) {
        RequestOptions options = new RequestOptions()
                .dontAnimate()
                .placeholder(placeImg);
        Glide.with(context).load(bitmap).thumbnail(0.1f).apply(options).into(iv);
    }

    /**
     * 加载本地资源图片
     *
     * @param context
     * @param resId
     * @param iv
     * @param placeImg
     */
    public static void setImage(Context context, int resId, ImageView iv, int placeImg) {
        RequestOptions options = new RequestOptions()
                .placeholder(placeImg);
        Glide.with(context).load(resId).apply(options).into(iv);
    }

    /**
     * 加载本地资源图片--圆形
     *
     * @param context
     * @param resId
     * @param iv
     * @param placeImg
     */
    public static void setCircleImage(Context context, int resId, ImageView iv, int placeImg) {
        RequestOptions options = new RequestOptions()
                .placeholder(placeImg)
                .circleCrop();
        Glide.with(context).load(resId).apply(options).into(iv);
    }

    /**
     * 通过链接加载网络图片 -- 圆形
     *
     * @param context
     * @param url
     * @param iv
     * @param placeImg
     */
    public static void setCircleImage(Context context, String url, ImageView iv, int placeImg) {
        RequestOptions options = new RequestOptions()
                .placeholder(placeImg)
                .circleCrop();
        Glide.with(context).load(url).apply(options).into(iv);
    }

    /**
     * 通过链接加载网络图片 -- 圆角
     *
     * @param context
     * @param url
     * @param iv
     * @param radiusDp 圆角大小,单位dp
     * @param placeImg
     */
    public static void setRoundCornerImage(Context context, String url, ImageView iv,
                                           int radiusDp, int placeImg) {
        RequestOptions options = new RequestOptions()
                .placeholder(placeImg)
                .transform(new RoundedCornersTransformation(SystemUtil.dp2px(radiusDp), 0));
        Glide.with(context).load(url).apply(options).into(iv);
    }

    /**
     * 加载本地资源图片 -- 圆角
     *
     * @param context
     * @param resId
     * @param iv
     * @param radiusDp 圆角大小,单位dp
     * @param placeImg
     */
    public static void setRoundCornerImage(Context context, int resId, ImageView iv,
                                           int radiusDp, int placeImg) {
        RequestOptions options = new RequestOptions()
                .placeholder(placeImg)
                .transform(new RoundedCornersTransformation(SystemUtil.dp2px(radiusDp), 0));
        Glide.with(context).load(resId).apply(options).into(iv);
    }


    /**
     * 通过链接加载网络图片
     *
     * @param context
     * @param url
     * @param iv
     * @param placeImg
     */
    public static void setImage1(Context context, String url, ImageView iv, int placeImg) {
        RequestOptions options = new RequestOptions()
                .placeholder(placeImg);
        Glide.with(context).load(url).apply(options).into(iv);
    }
}
