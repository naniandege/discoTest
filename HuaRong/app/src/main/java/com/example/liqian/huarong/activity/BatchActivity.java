package com.example.liqian.huarong.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import com.example.liqian.huarong.R;
import com.example.liqian.huarong.adapter.ToduAdapter;
import com.example.liqian.huarong.base.BaseActivity;
import com.example.liqian.huarong.bean.EntPages;
import com.example.liqian.huarong.bean.EntityWorkFlow;
import com.example.liqian.huarong.bean.PubReturn;
import com.example.liqian.huarong.mvp.p.ToduPresenter;
import com.example.liqian.huarong.mvp.v.ToduView;
import com.example.liqian.huarong.net.BLL;
import com.example.liqian.huarong.net.Common;
import com.example.liqian.huarong.utils.ToastUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BatchActivity extends BaseActivity<ToduView,ToduPresenter> implements ToduView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.lv)
    RecyclerView lv;
    @BindView(R.id.srl)
    SmartRefreshLayout srl;
    @BindView(R.id.audit_img)
    LinearLayout auditImg;

    private ArrayList<EntityWorkFlow> list;
    private ToduAdapter adapter;
    private int count = 1;
    private List<EntityWorkFlow> entityWorkFlows;
    private LinearLayoutManager linearLayoutManager;
    private int total_page;

    @Override
    protected ToduPresenter initPresenter() {
        return new ToduPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_batch;
    }

    @Override
    protected void initData() {
        super.initData();

        String tudoJson = BLL.TaskPageSelect(5, count, "101");
        presenter.todu(tudoJson);
    }

    @Override
    protected void initView() {
        super.initView();

        //数据源
        list = new ArrayList<>();
        //适配器绑定
        adapter = new ToduAdapter(list, this);
        lv.setAdapter(adapter);

        //recycleView显示方式:线性
        linearLayoutManager = new LinearLayoutManager(this);
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
            Type type = new TypeToken<EntPages<EntityWorkFlow>>() {
            }.getType();
            EntPages<EntityWorkFlow> entPages = gson.fromJson(decode, type);
            List<EntityWorkFlow> pageResults1 = entPages.getPageResults();
            entityWorkFlows = Common.defDecodeEntityList(pageResults1);
            total_page = entPages.getTotalPages();
            if (entityWorkFlows != null && entityWorkFlows.size() > 0) {
                //添加列表数据
                list.addAll(entityWorkFlows);
                adapter.notifyDataSetChanged();
                auditImg.setVisibility(View.GONE);
            } else {
                auditImg.setVisibility(View.VISIBLE);
            }

        } else {
            ToastUtil.showShort(Common.defDecode(pubReturn.getMessage()));
        }
    }
}
