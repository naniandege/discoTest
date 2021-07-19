package com.example.liqian.huarong.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;


import com.example.liqian.huarong.net.Common;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


@SuppressLint("AppCompatCustomView")
public class LockScreenView extends ImageView {
    public float currentX = 40;
    public float currentY = 50;
    private Bitmap bmp;
    private Bitmap mBitmap;
    private Bitmap mBitmap0;

    private Canvas mCanvas;

    Handler myHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    invalidate();
                    break;
                default:
                    break;
            }
        }
    };


    public LockScreenView(Context context) {
        super(context);
    }


    public LockScreenView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LockScreenView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    //初始化你需要显示的光标样式
    /*private void init() {

        if (bmp == null) {
            bmp = BitmapFactory.decodeResource(getResources(), R.drawable.anpai);
        }
    }
*/
    private boolean isClickView = false;//标识是否是人为点击，是则为true

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mBitmap != null) {
            canvas.drawBitmap(mBitmap, 0, 0, null);
        }
 /*       if (isClickView == true && bmp != null) {

            //创建画笔
            Paint p = new Paint();
            mCanvas.drawBitmap(bmp, currentX - (bmp.getWidth() / 2), currentY - (bmp.getHeight() / 2), p);

            isClickView = false;
        }*/
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //当前组件的currentX、currentY两个属性
        this.currentX = event.getX();
        this.currentY = event.getY();
        Common.leftX = event.getX();
        Common.leftY = event.getY();

        isClickView = true;

        if (isClickView == true && bmp != null && mBitmap0 != null) {
            mBitmap = Bitmap.createBitmap(mBitmap0.copy(Bitmap.Config.ARGB_8888, true));
            if (mBitmap != null) {
                mCanvas = new Canvas(mBitmap);
                myHandler.sendEmptyMessage(0);
            }
            //创建画笔
            Paint p = new Paint();
            mCanvas.drawBitmap(bmp, currentX - (bmp.getWidth() / 2), currentY - (bmp.getHeight() / 2), p);

            isClickView = false;
        }

      /* if (event.getAction() == MotionEvent.ACTION_UP && bmp != null) {
           this.currentX = -bmp.getWidth();
           this.currentY = -bmp.getHeight();
           isClickView = false;
       }*/

        //通知改组件重绘
        this.invalidate();
        //返回true表明处理方法已经处理该事件

        return true;
    }


    public Bitmap InitBitmap(final String url) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                URL imageurl = null;
                try {
                    imageurl = new URL(url);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                try {
                    HttpURLConnection conn = (HttpURLConnection) imageurl.openConnection();
                    conn.setDoInput(true);
                    conn.connect();
                    InputStream is = conn.getInputStream();
                    Bitmap bitmap = BitmapFactory.decodeStream(is);
                    //设置Bitmap图片大小
                    Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, 125, 90, false);
                    bmp = Bitmap.createBitmap(scaledBitmap.copy(Bitmap.Config.ARGB_8888, true));

                    is.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        return bmp;
    }

    public Bitmap GetBitmap() {
        return mBitmap;
    }

    public Bitmap InitMBitmap(final String url) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                URL imageurl = null;
                try {
                    imageurl = new URL(url);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                try {
                    HttpURLConnection conn = (HttpURLConnection) imageurl.openConnection();
                    conn.setDoInput(true);
                    conn.connect();
                    InputStream is = conn.getInputStream();
                    Bitmap bitmap = BitmapFactory.decodeStream(is);//瓶颈
                    //设置Bitmap图片大小
                    // Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, 155, 120, false);
                    Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, 720, 384, false);//缩小
                    mBitmap0 = Bitmap.createBitmap(scaledBitmap.copy(Bitmap.Config.ARGB_8888, true));
                    mBitmap = Bitmap.createBitmap(scaledBitmap.copy(Bitmap.Config.ARGB_8888, true));
                    if (mBitmap != null) {
                        mCanvas = new Canvas(mBitmap);
                        myHandler.sendEmptyMessage(0);
                    }
                    is.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        return mBitmap;
    }


    public Bitmap pathBitmap(String path){

        Bitmap bitmap = BitmapFactory.decodeFile(path);//瓶颈
        //设置Bitmap图片大小
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, 720, 384, false);//缩小
        mBitmap0 = Bitmap.createBitmap(scaledBitmap.copy(Bitmap.Config.ARGB_8888, true));
        mBitmap = Bitmap.createBitmap(scaledBitmap.copy(Bitmap.Config.ARGB_8888, true));
        if (mBitmap != null) {
            mCanvas = new Canvas(mBitmap);
            myHandler.sendEmptyMessage(0);
        }
        return mBitmap;
    }
}
