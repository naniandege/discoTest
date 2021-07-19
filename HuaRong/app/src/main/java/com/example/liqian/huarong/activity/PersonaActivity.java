package com.example.liqian.huarong.activity;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.example.liqian.huarong.R;
import com.example.liqian.huarong.adapter.PersonalAdapter;
import com.example.liqian.huarong.base.BaseActivity;
import com.example.liqian.huarong.bean.EntPages;
import com.example.liqian.huarong.bean.EntityPersonal;
import com.example.liqian.huarong.bean.PubReturn;
import com.example.liqian.huarong.mvp.p.ToduPresenter;
import com.example.liqian.huarong.mvp.v.ToduView;
import com.example.liqian.huarong.net.BLL;
import com.example.liqian.huarong.net.Common;
import com.example.liqian.huarong.utils.SpUtil;
import com.example.liqian.huarong.utils.ToastUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jaeger.library.StatusBarUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class PersonaActivity extends BaseActivity<ToduView, ToduPresenter> implements ToduView, PersonalAdapter.onItemListnner {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.ed_name)
    EditText edName;
    @BindView(R.id.lv)
    RecyclerView lv;
    @BindView(R.id.to_view)
    TextView to_view;
    @BindView(R.id.srl)
    SmartRefreshLayout srl;
    @BindView(R.id.but_ok)
    Button but_ok;
    @BindView(R.id.but_refresh)
    Button but_refresh;


    private ArrayList<EntityPersonal> list;
    private PersonalAdapter adapter;
    private int total_page;
    private int count = 1;


    @Override
    protected ToduPresenter initPresenter() {
        return new ToduPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_persona;
    }

    @Override
    protected void initView() {
        super.initView();
        StatusBarUtil.setLightMode(this);//修改状态栏字体颜色

        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        showLoading();
        list = new ArrayList<>();
        adapter = new PersonalAdapter(list, this);
        lv.setAdapter(adapter);
        //recycleView显示方式:线性
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        lv.setLayoutManager(linearLayoutManager);
        //分割线
        lv.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        //上拉加载  下拉刷新
        srl.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                count = 1;
                list.clear();
                initData();
                adapter.notifyDataSetChanged();
                srl.finishRefresh(true);

            }
        });

        srl.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {

                if (count < total_page) {
                    ++count;
                    initData();
                    adapter.notifyDataSetChanged();
                    srl.finishLoadMore(true);
                } else {
                    srl.finishLoadMore(false);
                }
            }
        });

        adapter.setOnItemListnner(this);
    }


    @Override
    protected void initData() {
        super.initData();
        EntityPersonal entityPersonal = new EntityPersonal();
        entityPersonal.setpers_name(edName.getText().toString());
        presenter.todu(BLL.PersonalPageSelect(20, count, entityPersonal));
    }

    @OnClick({R.id.to_view, R.id.but_ok, R.id.but_refresh})
    public void onClickListnner(View view) {
        switch (view.getId()) {
            case R.id.to_view:
                showLoading();
                list.clear();
                initData();
                break;

            case R.id.but_ok:
                Intent intent = getIntent();
                ArrayList<EntityPersonal> entityPersonals = adapter.getSelectedItem();
                Gson gson = new Gson();
                String json = gson.toJson(entityPersonals);
                intent.putExtra("json", json);
                setResult(2, intent);
                finish();
                break;
            case R.id.but_refresh:
                adapter.updateDataSet(list);
                adapter.notifyDataSetChanged();
                break;
        }
    }

    @Override
    public void setTudu(String data) {
        String str = Common.StringPick(data);
        Gson gson = new Gson();
        PubReturn pubReturn = gson.fromJson(str, PubReturn.class);
        if (pubReturn.getState()) {
            //解码
            String decode = Common.defDecode(pubReturn.getData());
            //网络解析数据
            Type type = new TypeToken<EntPages<EntityPersonal>>() {
            }.getType();
            EntPages<EntityPersonal> entPages = gson.fromJson(decode, type);
            List<EntityPersonal> pageResults1 = entPages.getPageResults();
            List<EntityPersonal> entityPersonals = Common.defDecodeEntityList(pageResults1);
            total_page = entPages.getTotalPages();
            //添加列表数据
            list.addAll(entityPersonals);
            adapter.notifyDataSetChanged();
            hideLoading();
        } else {
            String defDecode = Common.defDecode(pubReturn.getMessage());
            ToastUtil.showShort(defDecode);
        }
    }


    //数据回调
    @Override
    public void onMyItem(PersonalAdapter.ViewHolder viewHolder, int position) {

    }
}
