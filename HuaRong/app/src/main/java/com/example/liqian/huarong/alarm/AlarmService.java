package com.example.liqian.huarong.alarm;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;

import com.example.liqian.huarong.R;


/**
 * Description: 定时提醒服务类
 */
public class AlarmService extends Service {
    public static final String ACTION = "com.example.liqian.huarong.alarm";
    private static final int NOTIFICATION_ID = 234;

/* @Override
    public void onCreate() {
        super.onCreate();
        //如果API在26以上即版本为O则调用startForefround()方法启动服务
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            createNotificationChannel();
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createNotificationChannel() {
        //设定的通知渠道名称
        String channelName = getString(R.string.channel_name);
        //设置通知的重要程度
        int importance = NotificationManager.IMPORTANCE_LOW;
        //构建通知渠道
        NotificationChannel channel = new NotificationChannel(ACTION, channelName, importance);
        channel.setDescription("打卡提醒");
        //在创建的通知渠道上发送通知
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, ACTION);
        builder.setSmallIcon(R.drawable.ic_launcher_background) //设置通知图标
                .setContentTitle("华容App")//设置通知标题
                .setContentText("闹钟提醒打卡")//设置通知内容
                .setAutoCancel(true) //用户触摸时，自动关闭
                .setOngoing(true);//设置处于运行状态
        //向系统注册通知渠道，注册后不能改变重要性以及其他通知行为
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.createNotificationChannel(channel);
        //将服务置于启动状态 NOTIFICATION_ID指的是创建的通知的ID
        startForeground(NOTIFICATION_ID, builder.build());
    }
    */


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Context context = getApplicationContext();
        long intervalMillis = intent.getLongExtra(AlarmManagerUtils.INTERVAL_MILLIS, 0);
        if (intervalMillis != 0)
            AlarmManagerUtils.setAlarmTime(context, System.currentTimeMillis() + intervalMillis, intent);
        Intent clockIntent = new Intent(context, ClockAlarmActivity.class);
        clockIntent.putExtra(AlarmManagerUtils.ID, intent.getIntExtra(AlarmManagerUtils.ID, 0));
        clockIntent.putExtra(AlarmManagerUtils.TIPS, intent.getStringExtra(AlarmManagerUtils.TIPS));
        clockIntent.putExtra(AlarmManagerUtils.SOUND_OR_VIBRATOR, intent.getIntExtra(AlarmManagerUtils.SOUND_OR_VIBRATOR, 0));
        clockIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(clockIntent);
        return super.onStartCommand(intent, flags, startId);
    }
}
