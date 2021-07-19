package com.example.liqian.huarong.activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.liqian.huarong.R;
import com.example.liqian.huarong.adapter.WorkAdapter;
import com.example.liqian.huarong.base.BaseActivity;
import com.example.liqian.huarong.bean.EntPages;
import com.example.liqian.huarong.bean.EntityBase;
import com.example.liqian.huarong.bean.EntityWorkFlow;
import com.example.liqian.huarong.bean.PubReturn;
import com.example.liqian.huarong.mvp.p.EmptyPresenter;
import com.example.liqian.huarong.mvp.p.WorkPresenter;
import com.example.liqian.huarong.mvp.v.EmptyView;
import com.example.liqian.huarong.mvp.v.WorkView;
import com.example.liqian.huarong.net.BLL;
import com.example.liqian.huarong.net.Common;
import com.example.liqian.huarong.net.General;
import com.example.liqian.huarong.utils.ToastUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jaeger.library.StatusBarUtil;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WorkDistributionActivity extends BaseActivity<WorkView, WorkPresenter> implements WorkView {


    @BindView(R.id.toolbar_name)
    TextView toolbarName;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.startShow)
    TextView startShow;
    @BindView(R.id.startDate)
    LinearLayout startDate;
    @BindView(R.id.endShow)
    TextView endShow;
    @BindView(R.id.endDate)
    LinearLayout endDate;
    @BindView(R.id.detail_Query)
    Button detailQuery;
    @BindView(R.id.toDay)
    Button toDay;
    @BindView(R.id.Yesterday)
    Button Yesterday;
    @BindView(R.id.beforeDay)
    Button beforeDay;
    @BindView(R.id.lv)
    RecyclerView lv;

    private Calendar calendar;
    private int mYear;
    private int mMonth;
    private int mDay;
    private String stringPick;
    private String dateTag = null;
    private ArrayList<EntityBase> list;
    private WorkAdapter adapter;

    @Override
    protected WorkPresenter initPresenter() {
        return new WorkPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_work_distribution;
    }

    @Override
    protected void initView() {
        super.initView();

        StatusBarUtil.setLightMode(this);
        initToolbar();
        initCalendar();
        initRecy();

        String mDate = null;
        if (mMonth < 10 && mDay < 10) {
            mDate = mYear + "-0" + (mMonth + 1) + "-0" + mDay;
        } else if (mDay < 10 && mMonth >= 10) {
            mDate = mYear + "-" + (mMonth + 1) + "-0" + mDay;
        } else if (mMonth < 10 && mDay >= 10) {
            mDate = mYear + "-0" + (mMonth + 1) + "-" + mDay;
        } else {
            mDate = mYear + "-" + (mMonth + 1) + "-" + mDay;
        }
        startShow.setText(mDate);
        endShow.setText(mDate);
    }

    private void initCalendar() {
        //实例化日期类
        calendar = Calendar.getInstance();
        mYear = calendar.get(Calendar.YEAR);//获取年
        mMonth = calendar.get(Calendar.MONTH);//获取月
        mDay = calendar.get(Calendar.DAY_OF_MONTH); //获取日
    }

    private void initRecy() {
        list = new ArrayList<>();
        adapter = new WorkAdapter(list, this);
        lv.setAdapter(adapter);
        lv.setLayoutManager(new LinearLayoutManager(this));
        lv.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
    }

    private void initToolbar() {
        toolbar.setTitle(" ");
        if (Common.attachmentTag.equals("工作分配")) {
            toolbarName.setText("工作分配");
        } else if (Common.attachmentTag.equals("工作计划")) {
            toolbarName.setText("工作计划");
        } else if (Common.attachmentTag.equals("工作汇报")) {
            toolbarName.setText("工作汇报");
        }
        toolbarName.setTextColor(getResources().getColor(R.color.c_000000));
        setSupportActionBar(toolbar);

    }


    @Override
    protected void initData() {
        super.initData();
        presenter.getData(BLL.BLLJsonByStream("In_Base_PageSelect"));
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    @OnClick({R.id.startDate, R.id.endDate, R.id.detail_Query, R.id.toDay, R.id.Yesterday, R.id.beforeDay})
    public void onViewClicked(View view) {
        //当前时间
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar instance = Calendar.getInstance();
        switch (view.getId()) {
            case R.id.startDate:   //选择开始时间
                selectDate(startShow);
                break;
            case R.id.endDate:     //选择结束时间
                selectDate(endShow);
                break;
            case R.id.detail_Query://查询
                dateTag = "";
                queryData("");
                break;
            case R.id.toDay:       //今天
                dateTag = "今天";
                //当前时间
                String formatDate = sDateFormat.format(new Date(System.currentTimeMillis()));
                queryData(formatDate);
                break;
            case R.id.Yesterday:   //昨天
                dateTag = "昨天";
                //当前时间-1天
                instance.add(Calendar.DATE, -1);
                String formatDate1 = sDateFormat.format(instance.getTime());
                queryData(formatDate1);
                break;
            case R.id.beforeDay:   //前天
                dateTag = "前天";
                //当前时间-2天
                instance.add(Calendar.DATE, -2);
                String formatDate2 = sDateFormat.format(instance.getTime());
                queryData(formatDate2);
                break;
        }
    }

    private void queryData(String formatDate) {
        Gson gson = new Gson();
        PubReturn pubReturn = gson.fromJson(stringPick, PubReturn.class);
        if (pubReturn.getState()) {
            EntityBase mBase = new EntityBase();
            mBase.setcorp_tn(Common.LoginUser.getcorp_tn());
            mBase.setorder_list("make_date desc");
            mBase.setpage_current(1);
            mBase.setpage_size(30);
            if (Common.attachmentTag.equals("工作分配")) {
                mBase.settb_code("100001");
                mBase.settype_code("001");
                mBase.setkind_code("001");
                mBase.setbase_pers_codes(Common.LoginUser.getpers_code());
                mBase.setread_pers_code(Common.LoginUser.getpers_code());
            } else if (Common.attachmentTag.equals("工作计划")) {
                mBase.settb_code("100002");
                mBase.settype_code("001");
                mBase.setkind_code("001");
                mBase.setbase_nvar100_2(Common.LoginUser.getpers_code());
            } else if (Common.attachmentTag.equals("工作汇报")) {
                mBase.settb_code("100003");
                mBase.settype_code("001");
                mBase.setkind_code("001");
                mBase.setbase_nvar100_2(Common.LoginUser.getpers_code());
            }

            if (dateTag.equals("今天") || dateTag.equals("昨天") || dateTag.equals("前天")) {
                mBase.setmake_date_1(formatDate + " 00:00:00");
                mBase.setmake_date_2(formatDate + " 23:59:59");
            } else if (dateTag.isEmpty()) {
                mBase.setmake_date_1(startShow.getText().toString() + " 00:00:00");
                mBase.setmake_date_2(endShow.getText().toString() + " 23:59:59");
            }
            General<Object> general = new General<>();
            String Json = general.bllInJson(mBase, Common.defDecode(pubReturn.getData()));
            String encode = Common.defEncode(Json);
            presenter.getListData(encode);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void selectDate(final TextView textView) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, AlertDialog.THEME_HOLO_LIGHT);
        datePickerDialog.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                mYear = year;
                mMonth = month + 1;
                mDay = dayOfMonth;
                String mDate = null;
                if (mMonth < 10 && mDay < 10) {
                    mDate = mYear + "-0" + mMonth + "-0" + mDay;
                } else if (mDay < 10 && mMonth >= 10) {
                    mDate = mYear + "-" + mMonth + "-0" + mDay;
                } else if (mMonth < 10 && mDay >= 10) {
                    mDate = mYear + "-0" + mMonth + "-" + mDay;
                } else {
                    mDate = mYear + "-" + mMonth + "-" + mDay;
                }
                textView.setText(mDate);
            }
        });
        datePickerDialog.show();
    }

    @Override
    public void setData(String data) {
        stringPick = Common.StringPick(data);
    }

    @Override
    public void setListData(String data) {
        String str = Common.StringPick(data);
        Gson gson = new Gson();
        PubReturn pubReturn = gson.fromJson(str, PubReturn.class);
        if (pubReturn.getState()) {
            //解码
            String decode = Common.defDecode(pubReturn.getData());
            //网络解析数据
            Type type = new TypeToken<EntPages<EntityBase>>() {
            }.getType();
            EntPages<EntityBase> entPages = gson.fromJson(decode, type);
            List<EntityBase> pageResults1 = entPages.getPageResults();
            List<EntityBase> entityBases = Common.defDecodeEntityList(pageResults1);
            if (entityBases != null && entityBases.size() > 0) {
                list.clear();
                list.addAll(entityBases);
                adapter.notifyDataSetChanged();
            }else {
                list.clear();
                adapter.notifyDataSetChanged();
                ToastUtil.showShort("暂无数据");
            }
        } else {
            ToastUtil.showShort(Common.defDecode(pubReturn.getMessage()));
        }
    }
}
