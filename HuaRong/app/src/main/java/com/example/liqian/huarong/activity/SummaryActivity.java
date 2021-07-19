package com.example.liqian.huarong.activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
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
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import com.example.liqian.huarong.R;
import com.example.liqian.huarong.adapter.DetailAdapter;
import com.example.liqian.huarong.adapter.SummarAdapter;
import com.example.liqian.huarong.base.BaseActivity;
import com.example.liqian.huarong.bean.EntityInformation;
import com.example.liqian.huarong.bean.PubReturn;
import com.example.liqian.huarong.mvp.p.QueryPresenter;
import com.example.liqian.huarong.mvp.v.QueryView;
import com.example.liqian.huarong.net.BLL;
import com.example.liqian.huarong.net.Common;
import com.example.liqian.huarong.utils.TimeUtils;
import com.example.liqian.huarong.utils.ToastUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jaeger.library.StatusBarUtil;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SummaryActivity extends BaseActivity<QueryView, QueryPresenter> implements QueryView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.selectStartdate)
    TextView selectStartdate;
    @BindView(R.id.dateStartShow)
    TextView dateStartShow;

    @BindView(R.id.detail_Query)
    Button detailQuery;
    @BindView(R.id.summar_lv)
    RecyclerView summar_lv;


    private Calendar calendar;
    private int hour;
    private int minute;
    private int mYear;
    private int mMonth;
    private int mDay;

    private String dataStart = "";
    private String dataEnd = "";
    private ArrayList<EntityInformation> list;
    private SummarAdapter adapter;

    @Override
    protected QueryPresenter initPresenter() {
        return new QueryPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_summary;
    }

    @Override
    protected void initView() {
        super.initView();
        initToolbar();//初始化Toolbar

        //实例化日期类
        calendar = Calendar.getInstance();
        mYear = calendar.get(Calendar.YEAR);//获取年
        mMonth = calendar.get(Calendar.MONTH);//获取月
        mDay = calendar.get(Calendar.DAY_OF_MONTH); //获取日
        hour = calendar.get(Calendar.HOUR);//获取小时
        minute = calendar.get(Calendar.MINUTE);//获取分钟


        list = new ArrayList<>();
        adapter = new SummarAdapter(list, this);
        summar_lv.setAdapter(adapter);
        summar_lv.setLayoutManager(new LinearLayoutManager(this));
        summar_lv.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
    }

    private void initToolbar() {
        StatusBarUtil.setLightMode(this);//修改字体颜色
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
    }

   /* @Override
    public void setData(String data) {
        String str = Common.StringPick(data);
        Gson gson = new Gson();
        PubReturn pubReturn = gson.fromJson(str, PubReturn.class);
        if (pubReturn.getState()) {
            String decode = Common.defDecode(pubReturn.getData());
            Type type = new TypeToken<List<EntityInformation>>() {
            }.getType();
            List<EntityInformation> information = gson.fromJson(decode, type);
            List<EntityInformation> entityList = Common.defDecodeEntityList(information);
            list.addAll(entityList);
            adapter.notifyDataSetChanged();
        } else {
            showToast(Common.defDecode(pubReturn.getMessage()));
        }
    }*/


    @RequiresApi(api = Build.VERSION_CODES.N)
    @OnClick({R.id.selectStartdate, R.id.detail_Query})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.selectStartdate:
                selectStartDate(); //选择开始时间
                break;
            case R.id.detail_Query:
                //查看数据
                if (dataStart.isEmpty() || dataEnd.isEmpty()) {
                    ToastUtil.showShort("请选择日期");
                } else {
                    showLoading();
                    EntityInformation information = new EntityInformation();
                    information.setcorp_tn(Common.LoginUser.getcorp_tn());
                    information.setinfo_key_1(Common.LoginUser.getpers_code());
                    information.setinfo_key_2(dataStart);
                    information.setinfo_key_3(dataEnd);
                    information.settb_code("9002");
                    information.settype_code("002");
                    information.setkind_code("001");
                    presenter.getListData(BLL.InfoSelect(information));
                }
                break;
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    private void selectStartDate() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, AlertDialog.THEME_HOLO_LIGHT);
        datePickerDialog.show();
        //只显示年月，隐藏掉日
        DatePicker dp = findDatePicker((ViewGroup) datePickerDialog.getWindow().getDecorView());
        if (dp != null) {
            ((ViewGroup) ((ViewGroup) dp.getChildAt(0)).getChildAt(0))
                    .getChildAt(2).setVisibility(View.GONE);
            //如果想隐藏掉年，将getChildAt(2)改为getChildAt(0)
        }
        datePickerDialog.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                mYear = year;
                mMonth = month + 1;
                mDay = dayOfMonth;

                dataStart = TimeUtils.getSupportBeginDayofMonth(mYear, mMonth);
                dataEnd = TimeUtils.getSupportEndDayofMonth(mYear, mMonth);

                dateStartShow.setText(TimeUtils.getSupportEndDayofMonth1(mYear, mMonth) + "月");
            }
        });

        datePickerDialog.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(this.getResources().getColor(R.color.c_608ce0));
        datePickerDialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(this.getResources().getColor(R.color.c_608ce0));
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


    private DatePicker findDatePicker(ViewGroup group) {
        if (group != null) {
            for (int i = 0, j = group.getChildCount(); i < j; i++) {
                View child = group.getChildAt(i);
                if (child instanceof DatePicker) {
                    return (DatePicker) child;
                } else if (child instanceof ViewGroup) {
                    DatePicker result = findDatePicker((ViewGroup) child);
                    if (result != null)
                        return result;
                }
            }
        }
        return null;
    }

    @Override
    public void getListStr(String data) {

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
}
