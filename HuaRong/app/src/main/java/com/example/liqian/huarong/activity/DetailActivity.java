package com.example.liqian.huarong.activity;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
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
import android.widget.TextView;

import com.example.liqian.huarong.R;
import com.example.liqian.huarong.adapter.DetailAdapter;
import com.example.liqian.huarong.base.BaseActivity;
import com.example.liqian.huarong.bean.EntityBase;
import com.example.liqian.huarong.bean.EntityInformation;
import com.example.liqian.huarong.bean.PubReturn;
import com.example.liqian.huarong.mvp.p.QueryPresenter;
import com.example.liqian.huarong.mvp.v.QueryView;
import com.example.liqian.huarong.net.BLL;
import com.example.liqian.huarong.net.Common;
import com.example.liqian.huarong.net.General;
import com.example.liqian.huarong.utils.ToastUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jaeger.library.StatusBarUtil;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class DetailActivity extends BaseActivity<QueryView, QueryPresenter> implements QueryView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbar_name)
    TextView toolbarName;
    @BindView(R.id.date_Show)
    TextView dateShow;
    @BindView(R.id.selectEndDate)
    TextView selectEndDate;
    @BindView(R.id.selectEndShow)
    TextView selectEndShow;
    @BindView(R.id.select_date)
    TextView selectDate;
    @BindView(R.id.detail_Query)
    Button detailQuery;
    @BindView(R.id.lv)
    RecyclerView lv;

    private Calendar calendar;
    private int hour;
    private int minute;
    private int mYear;
    private int mMonth;
    private int mDay;
    private ArrayList<EntityInformation> list;
    private DetailAdapter adapter;
    private String mDate = "";
    private String msDate;
    private String meDate;

    @Override
    protected QueryPresenter initPresenter() {
        return new QueryPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_detail;
    }

    @Override
    protected void initView() {
        super.initView();
        initToolbar(); //初始化Toolbar

        //实例化日期类
        calendar = Calendar.getInstance();
        mYear = calendar.get(Calendar.YEAR);//获取年
        mMonth = calendar.get(Calendar.MONTH);//获取月
        mDay = calendar.get(Calendar.DAY_OF_MONTH); //获取日
        hour = calendar.get(Calendar.HOUR);//获取小时
        minute = calendar.get(Calendar.MINUTE);//获取分钟

        list = new ArrayList<>();
        adapter = new DetailAdapter(list, this);
        lv.setAdapter(adapter);

        lv.setLayoutManager(new LinearLayoutManager(this));
        lv.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
    }

    private void initToolbar() {
        StatusBarUtil.setLightMode(this);//修改字体颜色

        toolbar.setTitle("");
        setSupportActionBar(toolbar);
    }


    @Override
    public void getListStr(String data) {
        String mData = Common.StringPick(data);
        Gson gson = new Gson();
        PubReturn pubReturn = gson.fromJson(mData, PubReturn.class);
        if (pubReturn != null) {
            if (pubReturn.getState()) {
                EntityInformation information = new EntityInformation();
                information.setcorp_tn(Common.LoginUser.getcorp_tn());
                information.setinfo_key_1(Common.LoginUser.getpers_code());
                information.setinfo_key_2(msDate);//开始时间
                information.setinfo_key_3(meDate);//结束时间
                information.settb_code("9002");
                information.settype_code("001");
                information.setkind_code("001");
                General<Object> general = new General<>();
                String Json = general.bllInJson(information, Common.defDecode(pubReturn.getData()));
                String encode = Common.defEncode(Json);
                presenter.getListData(encode);
            }
        }
    }

    @Override
    public void getListData(String data) {
        String str = Common.StringPick(data);
        Gson gson = new Gson();
        PubReturn pubReturn = gson.fromJson(str, PubReturn.class);
        if (pubReturn.getState()) {
            String decode = Common.defDecode(pubReturn.getData());
            Type type = new TypeToken<List<EntityInformation>>() {
            }.getType();
            List<EntityInformation> information = gson.fromJson(decode, type);
            List<EntityInformation> entityList = Common.defDecodeEntityList(information);
            list.clear();
            if (entityList != null && entityList.size() > 0) {
                list.addAll(entityList);
            } else {
                ToastUtil.showShort("暂无数据。");
            }
            adapter.notifyDataSetChanged();
            hideLoading();
        } else {
            showToast(Common.defDecode(pubReturn.getMessage()));
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    @OnClick({R.id.select_date, R.id.selectEndDate, R.id.detail_Query})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.select_date:
                //选择开始时间
                selectDate();
                break;
            case R.id.selectEndDate:
                //选择结束时间
                selectEndDate();
                break;
            case R.id.detail_Query:
                //查看数据
                msDate = dateShow.getText().toString().trim();
                meDate = selectEndShow.getText().toString().trim();
                if (!msDate.isEmpty() && !meDate.isEmpty()) {
                    showLoading();
                    EntityInformation information = new EntityInformation();
                    information.setcorp_tn(Common.LoginUser.getcorp_tn());
                    information.setinfo_key_1(Common.LoginUser.getpers_code());
                    information.setinfo_key_2_1(msDate);//开始时间
                    information.setinfo_key_2_2(meDate);//结束时间
                    information.settb_code("9002");
                    information.settype_code("001");
                    information.setkind_code("001");
                    presenter.getListData(BLL.InfoSelect(information));
                } else {
                    ToastUtil.showShort("请先选择日期");
                }

                break;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void selectDate() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, AlertDialog.THEME_HOLO_LIGHT);
        datePickerDialog.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                mMonth = month + 1;
                mDay = dayOfMonth;

                if (mMonth < 10 && mDay < 10) {
                    mDate = mYear + "-0" + mMonth + "-0" + mDay;
                } else if (mDay < 10 && mMonth >= 10) {
                    mDate = mYear + "-" + mMonth + "-0" + mDay;
                } else if (mMonth < 10 && mDay >= 10) {
                    mDate = mYear + "-0" + mMonth + "-" + mDay;
                } else {
                    mDate = mYear + "-" + mMonth + "-" + mDay;
                }

                dateShow.setText(mDate);

            }
        });
        datePickerDialog.show();
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    private void selectEndDate() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, AlertDialog.THEME_HOLO_LIGHT);
        datePickerDialog.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                mMonth = month + 1;
                mDay = dayOfMonth;

                if (mMonth < 10 && mDay < 10) {
                    mDate = mYear + "-0" + mMonth + "-0" + mDay;
                } else if (mDay < 10 && mMonth >= 10) {
                    mDate = mYear + "-" + mMonth + "-0" + mDay;
                } else if (mMonth < 10 && mDay >= 10) {
                    mDate = mYear + "-0" + mMonth + "-" + mDay;
                } else {
                    mDate = mYear + "-" + mMonth + "-" + mDay;
                }

                selectEndShow.setText(mDate);

            }
        });
        datePickerDialog.show();
    }
}
