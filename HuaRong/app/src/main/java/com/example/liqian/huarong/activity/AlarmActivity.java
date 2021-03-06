package com.example.liqian.huarong.activity;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.liqian.huarong.R;
import com.example.liqian.huarong.alarm.AlarmManagerUtils;
import com.example.liqian.huarong.alarm.AlarmService;
import com.example.liqian.huarong.base.BaseActivity;
import com.example.liqian.huarong.mvp.p.EmptyPresenter;
import com.example.liqian.huarong.mvp.v.EmptyView;
import com.example.liqian.huarong.net.Common;
import com.example.liqian.huarong.utils.SpUtil;
import com.jaeger.library.StatusBarUtil;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.OnClick;

public class AlarmActivity extends BaseActivity<EmptyView, EmptyPresenter> implements EmptyView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.textShow)
    TextView textShow;
    @BindView(R.id.textShow1)
    TextView textShow1;
    @BindView(R.id.textShow2)
    TextView textShow2;
    @BindView(R.id.textShow3)
    TextView textShow3;
    @BindView(R.id.switch_btn)
    Switch switchBtn;
    @BindView(R.id.switch_btn1)
    Switch switchBtn1;
    @BindView(R.id.switch_btn2)
    Switch switchBtn2;
    @BindView(R.id.switch_btn3)
    Switch switchBtn3;
    @BindView(R.id.select_time)
    Button selectTime;
    @BindView(R.id.select_time1)
    Button selectTime1;
    @BindView(R.id.select_time2)
    Button selectTime2;
    @BindView(R.id.select_time3)
    Button selectTime3;
    @BindView(R.id.open_timing)
    Button openTiming;
    @BindView(R.id.open_timing1)
    Button openTiming1;
    @BindView(R.id.open_timing2)
    Button openTiming2;
    @BindView(R.id.open_timing3)
    Button openTiming3;
    @BindView(R.id.cancel_timing)
    Button cancelTiming;

    private int mHour;
    private int mMinute;

    private int mHour1;
    private int mMinute1;

    private int mHour2;
    private int mMinute2;

    private int mHour3;
    private int mMinute3;

    private int mSoundOrVibrator = 1;
    private int mSoundOrVibrator1 = 1;
    private int mSoundOrVibrator2 = 1;
    private int mSoundOrVibrator3 = 1;
    private Calendar calendar;
    private int hour;
    private int minute;
    private int mYear;
    private int mMonth;
    private int mDay;
    private String[] temp1;
    private String[] temp2;
    private String[] temp3;
    private String[] temp4;

    @Override
    protected EmptyPresenter initPresenter() {
        return new EmptyPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_alarm;
    }

    @Override
    protected void initView() {
        super.initView();
        initToolbar(); //??????Toolbar
        initCalendar(); //??????????????????

        boolean mCheced = (boolean) SpUtil.getParam("isChecked", false);
        boolean mCheced1 = (boolean) SpUtil.getParam("isChecked1", false);
        boolean mCheced2 = (boolean) SpUtil.getParam("isChecked2", false);
        boolean mCheced3 = (boolean) SpUtil.getParam("isChecked3", false);
        switchBtn.setChecked(mCheced);
        switchBtn1.setChecked(mCheced1);
        switchBtn2.setChecked(mCheced2);
        switchBtn3.setChecked(mCheced3);
//        textShow3.setText("???????????????" + SpUtil.getParam("sHour3", "") + "???" + SpUtil.getParam("sMinute3", "") + "???");

        String wDate1 = Common.wDate1;
        textShow.setText("???????????????" + wDate1);
        temp1 = null;
        temp1 = wDate1.split(":");

        String wDate2 = Common.wDate2;
        textShow1.setText("???????????????" + wDate2);
        temp2 = null;
        temp2 = wDate2.split(":");

        String wDate3 = Common.wDate3;
        textShow2.setText("???????????????" + wDate3);
        temp3 = null;
        temp3 = wDate3.split(":");

        String wDate4 = Common.wDate4;
        textShow3.setText("???????????????" + wDate4);
        temp4 = null;
        temp4 = wDate4.split(":");
    }


    private void initCalendar() {
        //??????????????????
        calendar = Calendar.getInstance();
        mYear = calendar.get(Calendar.YEAR);//?????????
        mMonth = calendar.get(Calendar.MONTH);//?????????
        mDay = calendar.get(Calendar.DAY_OF_MONTH); //?????????
        hour = calendar.get(Calendar.HOUR);//????????????
        minute = calendar.get(Calendar.MINUTE);//????????????
    }


    private void initToolbar() {
        StatusBarUtil.setLightMode(this);//???????????????????????????

        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//???????????????????????????????????????
        getSupportActionBar().setHomeButtonEnabled(true);//?????????????????????

        //??????Toolbar???????????????
        Drawable upArrow = ContextCompat.getDrawable(this, R.drawable.abc_ic_ab_back_material);
        if (upArrow != null) {
            upArrow.setColorFilter(ContextCompat.getColor(this, R.color.c_000000), PorterDuff.Mode.SRC_ATOP);
            if (getSupportActionBar() != null) {
                getSupportActionBar().setHomeAsUpIndicator(upArrow);
            }
        }


        //??????????????????
        switchBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mSoundOrVibrator = 2;
                    SpUtil.setParam("isChecked", true);
                } else {
                    mSoundOrVibrator = 1;
                    SpUtil.setParam("isChecked", false);
                }
            }
        });

        //??????????????????
        switchBtn1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mSoundOrVibrator1 = 2;
                    SpUtil.setParam("isChecked1", true);
                } else {
                    mSoundOrVibrator1 = 1;
                    SpUtil.setParam("isChecked1", false);
                }
            }
        });

        //??????????????????
        switchBtn2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mSoundOrVibrator2 = 2;
                    SpUtil.setParam("isChecked2", true);
                } else {
                    mSoundOrVibrator2 = 1;
                    SpUtil.setParam("isChecked2", false);
                }
            }
        });

        //??????????????????
        switchBtn3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mSoundOrVibrator3 = 2;
                    SpUtil.setParam("isChecked3", true);
                } else {
                    mSoundOrVibrator3 = 1;
                    SpUtil.setParam("isChecked3", false);
                }
            }
        });
    }


    @OnClick({R.id.select_time, R.id.select_time1, R.id.select_time2, R.id.select_time3,
            R.id.open_timing, R.id.open_timing1, R.id.open_timing2, R.id.open_timing3, R.id.cancel_timing})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.select_time:
                //??????????????????
//                selectTimingTime();
                break;
            case R.id.select_time1:
                //??????????????????
                break;
            case R.id.select_time2:
                //??????????????????
                break;
            case R.id.select_time3:
                //??????????????????
                break;
            case R.id.open_timing:
                //????????????
                mHour = Integer.valueOf(temp1[0]);
                mMinute = Integer.valueOf(temp1[1]);
                AlarmManagerUtils.setAlarm(AlarmActivity.this, 1, this.mHour, mMinute,
                        0, 0, 0, "??????????????????", mSoundOrVibrator);
                showToast("???????????????1");
                break;
            case R.id.open_timing1:
                //????????????1
                mHour1 = Integer.valueOf(temp2[0]);
                mMinute1 = Integer.valueOf(temp2[1]);
                AlarmManagerUtils.setAlarm(AlarmActivity.this, 1, this.mHour1, mMinute1,
                        0, 1, 0, "??????????????????", mSoundOrVibrator1);
                showToast("???????????????2");
                break;
            case R.id.open_timing2:
                //????????????2
                mHour2 = Integer.valueOf(temp3[0]);
                mMinute2 = Integer.valueOf(temp3[1]);
                AlarmManagerUtils.setAlarm(AlarmActivity.this, 1, this.mHour2, mMinute2,
                        0, 2, 0, "??????????????????", mSoundOrVibrator2);
                showToast("???????????????3");
                break;
            case R.id.open_timing3:
                //????????????3
                mHour3 = Integer.valueOf(temp4[0]);
                mMinute3 = Integer.valueOf(temp4[1]);
                AlarmManagerUtils.setAlarm(AlarmActivity.this, 1, this.mHour3, mMinute3,
                        0, 3, 0, "??????????????????", mSoundOrVibrator3);
                showToast("???????????????4");
                break;
            case R.id.cancel_timing:
                //????????????
                AlarmManagerUtils.cancelAlarm(AlarmActivity.this, AlarmService.ACTION, 0);
                AlarmManagerUtils.cancelAlarm(AlarmActivity.this, AlarmService.ACTION, 1);
                AlarmManagerUtils.cancelAlarm(AlarmActivity.this, AlarmService.ACTION, 2);
                AlarmManagerUtils.cancelAlarm(AlarmActivity.this, AlarmService.ACTION, 3);
                showToast("???????????????");
                break;
        }
    }

    private void selectTimingTime() {
        TimePickerDialog dialog = new TimePickerDialog(this, AlertDialog.THEME_HOLO_LIGHT, new TimePickerDialog.OnTimeSetListener() {

            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                SpUtil.setParam("mHour", hourOfDay);
                SpUtil.setParam("mMinute", minute);
                //??????????????????0??????????????????
                String sHour = hourOfDay < 10 ? "0" + hourOfDay : "" + hourOfDay;
                String sMinute = minute < 10 ? "0" + minute : "" + minute;
                SpUtil.setParam("sHour", sHour);
                SpUtil.setParam("sMinute", sMinute);
                textShow.setText("???????????????" + SpUtil.getParam("sHour", "") + "???" + SpUtil.getParam("sMinute", "") + "???");
            }
        }, hour, minute, true);
        dialog.show();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
