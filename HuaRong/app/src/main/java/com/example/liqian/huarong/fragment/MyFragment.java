package com.example.liqian.huarong.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.AlarmClock;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.liqian.huarong.R;
import com.example.liqian.huarong.activity.AddIntegralActivity;
import com.example.liqian.huarong.activity.AddTravelActivity;
import com.example.liqian.huarong.activity.AskforleaveActivity;
import com.example.liqian.huarong.activity.LoginActivity;
import com.example.liqian.huarong.activity.MainActivity;
import com.example.liqian.huarong.activity.PlaceActivity;
import com.example.liqian.huarong.activity.VersionActivity;
import com.example.liqian.huarong.base.BaseFragment;
import com.example.liqian.huarong.bean.EntityBase;
import com.example.liqian.huarong.bean.EntityLogin;
import com.example.liqian.huarong.bean.EntityWorkFlow;
import com.example.liqian.huarong.bean.PubReturn;
import com.example.liqian.huarong.mvp.p.AlarmPresenter;
import com.example.liqian.huarong.mvp.p.EmptyPresenter;
import com.example.liqian.huarong.mvp.v.AlarmView;
import com.example.liqian.huarong.mvp.v.EmptyView;
import com.example.liqian.huarong.net.BLL;
import com.example.liqian.huarong.net.Common;
import com.example.liqian.huarong.net.General;
import com.example.liqian.huarong.utils.SpUtil;
import com.example.liqian.huarong.utils.ToastUtil;
import com.example.liqian.huarong.widget.ClockDialog;
import com.github.iielse.switchbutton.SwitchView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyFragment extends BaseFragment<AlarmView, AlarmPresenter> implements AlarmView {


    @BindView(R.id.header)
    ImageView header;
    @BindView(R.id.switchBtn)
    SwitchView switchBtn;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.miao)
    TextView miao;
    @BindView(R.id.sms_show)
    TextView sms_show;
    @BindView(R.id.zhi)
    TextView zhi;
    @BindView(R.id.collect)
    LinearLayout collect;
    @BindView(R.id.set_place)
    LinearLayout set_place;
    @BindView(R.id.yi)
    LinearLayout yi;
    private EntityBase baseDate;


    public MyFragment() {
        // Required empty public constructor
    }


    @Override
    protected AlarmPresenter initPresenter() {
        return new AlarmPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_my;
    }

    @Override
    protected void initView(View inflate) {
        super.initView(inflate);
        boolean tag = (boolean) SpUtil.getParam("tag", false);
        if (tag) {
            boolean switchTag = (boolean) SpUtil.getParam("switchTag", false);
            switchBtn.toggleSwitch(switchTag);
            Common.smsTag = switchTag;
            if (switchTag) {
                sms_show.setText("已开启");
            } else {
                sms_show.setText("已关闭");
            }
        } else {
            SpUtil.setParam("switchTag", true);
            switchBtn.toggleSwitch(true);
            sms_show.setText("已开启");
            boolean switchTag = (boolean) SpUtil.getParam("switchTag", false);
            Common.smsTag = switchTag;
        }


        String temp = (String) SpUtil.getParam("login", "");
        ByteArrayInputStream bais = new ByteArrayInputStream(Base64.decode(temp.getBytes(), Base64.DEFAULT));
        try {
            ObjectInputStream ois = new ObjectInputStream(bais);
            EntityLogin entityLogin = (EntityLogin) ois.readObject();
            name.setText(entityLogin.getcorp_name());
            miao.setText(entityLogin.getpos_name() + "   ");
            zhi.setText(entityLogin.getpers_name());

        } catch (IOException e) {
        } catch (ClassNotFoundException e1) {

        }
    }


    @Override
    protected void initData() {
        super.initData();
        presenter.getData(BLL.BLLJsonByStream("In_Base_Select"));
    }


    @Override
    protected void initListener() {
        super.initListener();

        switchBtn.setOnStateChangedListener(new SwitchView.OnStateChangedListener() {
            @Override
            public void toggleToOn(SwitchView view) {
                sms_show.setText("已开启");
                SpUtil.setParam("tag", true);
                SpUtil.setParam("switchTag", true);
                boolean switchTag = (boolean) SpUtil.getParam("switchTag", false);
                switchBtn.toggleSwitch(switchTag);
                Common.smsTag = switchTag;
            }

            @Override
            public void toggleToOff(SwitchView view) {
                sms_show.setText("已关闭");
                SpUtil.setParam("tag", true);
                SpUtil.setParam("switchTag", false);
                boolean switchTag = (boolean) SpUtil.getParam("switchTag", false);
                switchBtn.toggleSwitch(switchTag);
                Common.smsTag = switchTag;
            }
        });
    }

    @OnClick({R.id.collect, R.id.set_place, R.id.yi})
    public void onClickListener(View view) {
        switch (view.getId()) {
            case R.id.collect:
                //版本升级
                startActivity(new Intent(getContext(), VersionActivity.class));
                break;
            case R.id.set_place:
                //设置公司位置
                startActivity(new Intent(getContext(), PlaceActivity.class));
                break;
            case R.id.yi:
                //设置提醒功能
//                startActivity(new Intent(getContext(), AlarmActivity.class));
                initDialog();
                break;
        }
    }

    private void initDialog() {
        View inflate = LayoutInflater.from(getActivity()).inflate(R.layout.clock_item, null);
        Button cancel = inflate.findViewById(R.id.btn_cancel);
        Button determine = inflate.findViewById(R.id.btn_determine);
        final ClockDialog dialog = new ClockDialog(getActivity(), inflate);
        dialog.show();
        determine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //闹铃设置
                String getdept_code = Common.LoginUser.getdept_code();
                if (getdept_code.equals("009")) {
                    createAlarm("打卡提醒", 7, 55, 1);
                } else {
                    createAlarm("打卡提醒", 8, 25, 1);
                }
                createAlarm("打卡提醒", 11, 30, 2);
                createAlarm("打卡提醒", 13, 55, 3);
                createAlarm("打卡提醒", 18, 00, 4);
                ToastUtil.showShort("闹钟已开启");
                dialog.dismiss();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialog.isShowing()) {
                 /*   dismissAlarm("1");
                    dismissAlarm("2");
                    dismissAlarm("3");
                    dismissAlarm("4");*/
                    dialog.dismiss();
                }
            }
        });
    }

    /**
     * @param message
     * @param hour
     * @param minutes
     * @param resId
     */
    private void createAlarm(String message, int hour, int minutes, int resId) {
        ArrayList<Integer> testDays = new ArrayList<>();
        testDays.add(Calendar.MONDAY);//周一
        testDays.add(Calendar.TUESDAY);//周二
        testDays.add(Calendar.WEDNESDAY);//周三
        testDays.add(Calendar.THURSDAY);//周四
        testDays.add(Calendar.FRIDAY);//周五

        String packageName = getActivity().getApplication().getPackageName();

        Uri ringtoneUri = Uri.parse("android.resource://" + packageName + "/" + resId);

        Intent intent = new Intent(AlarmClock.ACTION_SET_ALARM);
        //闹钟的小时
        intent.putExtra(AlarmClock.EXTRA_HOUR, hour);
        //闹钟的分钟
        intent.putExtra(AlarmClock.EXTRA_MINUTES, minutes);
        //响铃时提示的信息
        intent.putExtra(AlarmClock.EXTRA_MESSAGE, message);
        //用于指定该闹铃触发时是否振动
        intent.putExtra(AlarmClock.EXTRA_VIBRATE, true);
        //一个 content: URI，用于指定闹铃使用的铃声，也可指定 VALUE_RINGTONE_SILENT 以不使用铃声。
        //如需使用默认铃声，则无需指定此 extra。
//        intent.putExtra(AlarmClock.EXTRA_RINGTONE, ringtoneUri);
        intent.putExtra(AlarmClock.EXTRA_RINGTONE, AlarmClock.VALUE_RINGTONE_SILENT);
        //对于一次性闹铃，无需指定此 extra
        intent.putExtra(AlarmClock.EXTRA_DAYS, testDays);
        //如果为true，则调用startActivity()不会进入手机的闹钟设置界面
        intent.putExtra(AlarmClock.EXTRA_SKIP_UI, true);
        if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivity(intent);
        }
    }


    /**
     * 取消闹钟，国内手机都取消不了
     * 所以直接跳到系统闹钟页面
     * 提示用户手动取消
     *
     * @param content
     */
    public void dismissAlarm(String content) {
        if (Build.VERSION.SDK_INT < 23) {
            ToastUtil.showShort("手机版本过低,需手动取消闹钟");
            Intent alarmIntent = new Intent(AlarmClock.ACTION_SHOW_ALARMS);
            getActivity().startActivity(alarmIntent);
        } else {
            Intent alarmIntent = new Intent(AlarmClock.ACTION_DISMISS_ALARM);
            alarmIntent.putExtra(AlarmClock.ALARM_SEARCH_MODE_LABEL, content);
            getActivity().startActivity(alarmIntent);
        }
    }

    @Override
    public void setData(String data) {
        String stringPick = Common.StringPick(data);
        Gson gson = new Gson();
        PubReturn pubReturn = gson.fromJson(stringPick, PubReturn.class);
        if (pubReturn.getState()) {
            EntityBase mBase = new EntityBase();
            mBase.setcorp_tn(Common.LoginUser.getcorp_tn());
            mBase.settb_code("9000");
            mBase.setkind_code("001");
            mBase.settype_code("003");
            General<Object> general = new General<>();
            String Json = general.bllInJson(mBase, Common.defDecode(pubReturn.getData()));
            String encode = Common.defEncode(Json);
            presenter.getListData(encode);
        }
    }

    @Override
    public void setListData(String data) {
        String str = Common.StringPick(data);
        Gson gson = new Gson();
        PubReturn pubReturn = gson.fromJson(str, PubReturn.class);
        if (pubReturn.getState()) {
            String decode = Common.defDecode(pubReturn.getData());
            Type type = new TypeToken<List<EntityBase>>() {
            }.getType();
            List<EntityBase> entityBases = gson.fromJson(decode, type);
            List<EntityBase> entityList = Common.defDecodeEntityList(entityBases);
            if (entityList != null && entityList.size() > 0) {
                baseDate = entityList.get(1);
            } else {
                ToastUtil.showShort("暂无数据");
            }
        } else {
            ToastUtil.showShort(Common.defDecode(pubReturn.getMessage()));
        }
    }
}
