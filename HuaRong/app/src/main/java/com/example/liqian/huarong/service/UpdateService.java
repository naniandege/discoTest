package com.example.liqian.huarong.service;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.FileProvider;
import android.util.Log;

import com.example.liqian.huarong.R;
import com.example.liqian.huarong.net.Common;
import com.example.liqian.huarong.utils.ToastUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;


public class UpdateService extends Service {
    private File file;
    private int preProgress = 0;
    private NotificationCompat.Builder builder1;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                cancelNotification(); //取消通知
                ToastUtil.showShort("下载完成");
                Common.Version = 0;
                File file = new File(UpdateService.this.file + "/" + "huarong.apk");
                installApk(file);
            }
        }
    };
    private NotificationManager notificationManager;


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String mUrl = intent.getStringExtra("url");
        downloadApk(mUrl);
        return super.onStartCommand(intent, flags, startId);
    }

    /*
     * 从服务器端下载最新apk
     * */
    private void downloadApk(String url) {
        initNotification(); //初始化通知

        OkHttpClient client = new OkHttpClient.Builder()
                .build();

        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        Call call = client.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("downloadApk", "onFailure: " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                ResponseBody body = response.body();
                InputStream inputStream = body.byteStream();
                long max = body.contentLength();

                if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                    file = Environment.getExternalStorageDirectory();
                }
                saveFile(inputStream, file + "/" + "huarong.apk", max);
            }
        });
    }


    private void saveFile(InputStream inputStream, final String string, long max) {

        //读写的进度
        long count = 0;
        try {
            FileOutputStream outputStream = new FileOutputStream(new File(string));

            byte[] bys = new byte[1024 * 10];
            int length = -1;

            while ((length = inputStream.read(bys)) != -1) {
                outputStream.write(bys, 0, length);
                count += length;
                long count1 = count * 100 / max;  //百分比
                updateNotification((int) max, count1);
                Log.d("outputStream", "progress: " + count + "  max:" + max);
            }

            Message message = handler.obtainMessage();
            message.what = 1;
            message.obj = string;
            handler.sendMessage(message);

            inputStream.close();
            outputStream.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化Notification通知
     */
    public void initNotification() {
        //1.获取通知管理器类
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        //2.构建通知类
        String channelId = "1";
        String channelName = "default";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT);
           /* channel.enableLights(true);//开启指示灯,如果设备有的话。
            channel.setLightColor(Color.RED);//设置指示灯颜色
            channel.setShowBadge(true);//检测是否显示角标*/
            notificationManager.createNotificationChannel(channel);
        }

        builder1 = new NotificationCompat.Builder(this, channelId);
        builder1.setSmallIcon(R.mipmap.ic_launcher);//设置小图标
        builder1.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));//设置大图标
        builder1.setContentTitle("高密华荣App更新");//标题
        builder1.setContentText("0%");//内容
        builder1.setAutoCancel(false);//自动消失
//        builder1.setDefaults(Notification.DEFAULT_ALL);//提示效果（震动、呼吸灯、声音提示）

        //3.获取通知
//        Notification notification = builder1.build();
        //4.发送通知
//        notificationManager.notify(100,notification);

    }


    /**
     * 更新通知
     */

    public void updateNotification(int max, long progress) {
        final int currProgress = (int) progress;
        if (preProgress < currProgress) {
            builder1.setContentText(progress + "%");
            builder1.setProgress(max, (int) progress, false);
            notificationManager.notify(1000, builder1.build());
        }
        preProgress = (int) progress;
    }

    /**
     * 取消通知
     */
    public void cancelNotification() {
        notificationManager.cancel(1000);
    }


    /**
     * 安装软件
     *
     * @param file
     */
    private void installApk(File file) {
        Intent intentUpdate = new Intent("android.intent.action.VIEW");
        intentUpdate.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {  //对Android N及以上的版本做判断
            Uri apkUri = FileProvider.getUriForFile(getApplication(), "com.example.liqian.huarong.fileprovider", file);
            intentUpdate.addCategory("android.intent.category.DEFAULT");
            intentUpdate.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);   //天假Flag 表示我们需要什么权限
            intentUpdate.setDataAndType(apkUri, "application/vnd.android.package-archive");
        } else {
            Uri apkUri = Uri.fromFile(file);
            intentUpdate.setDataAndType(apkUri, "application/vnd.android.package-archive");
        }

        startActivity(intentUpdate);
        //如果不加，最后不会提示完成、打开。
    }
}

