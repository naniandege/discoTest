package com.example.liqian.huarong.activity;

import android.content.Intent;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.example.liqian.huarong.R;
import com.example.liqian.huarong.adapter.HistoryAdapter;
import com.example.liqian.huarong.base.BaseActivity;
import com.example.liqian.huarong.bean.EntityWorkFlow;
import com.example.liqian.huarong.bean.PubReturn;
import com.example.liqian.huarong.mvp.p.TaskHistoryPresenter;
import com.example.liqian.huarong.mvp.v.TaskHistoryView;
import com.example.liqian.huarong.net.BLL;
import com.example.liqian.huarong.net.Common;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jaeger.library.StatusBarUtil;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class TaskCheckHistoryActivity extends BaseActivity<TaskHistoryView, TaskHistoryPresenter> implements TaskHistoryView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.lv)
    RecyclerView lv;
    private ArrayList<EntityWorkFlow> list;
    private EntityWorkFlow wfEnt;
    private HistoryAdapter adapter;

    @Override
    protected TaskHistoryPresenter initPresenter() {
        return new TaskHistoryPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_task_check_history;
    }

    @Override
    protected void initView() {
        super.initView();

        StatusBarUtil.setLightMode(this);//修改字体颜色

        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        wfEnt = (EntityWorkFlow) intent.getSerializableExtra("json");

        list = new ArrayList<>();

        adapter = new HistoryAdapter(list, this);
        lv.setAdapter(adapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        lv.setLayoutManager(layoutManager);

        //分割线
        lv.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

    }

    @Override
    protected void initData() {
        super.initData();

        EntityWorkFlow ent_check = new EntityWorkFlow();
        ent_check.settb_code(wfEnt.gettb_code());
        ent_check.settype_code(wfEnt.gettype_code());
        ent_check.setkind_code(wfEnt.getkind_code());
        ent_check.settask_key(wfEnt.gettask_key());
        ent_check.setstate_code("900");
        ent_check.setif_no_imp(true);
        ent_check.setorder_list("node_code");

        presenter.taskHistory(BLL.TaskSelect(ent_check));
    }

    @Override
    public void setData(String data) {
        String str = Common.StringPick(data);
        Gson gson = new Gson();
        PubReturn pubReturn = gson.fromJson(str, PubReturn.class);
        if (pubReturn.getState()) {
            String decode = Common.defDecode(pubReturn.getData());
            Type type = new TypeToken<List<EntityWorkFlow>>() {
            }.getType();
            List<EntityWorkFlow> entityWorkFlows = gson.fromJson(decode, type);
            List<EntityWorkFlow> entityList = Common.defDecodeEntityList(entityWorkFlows);

            list.addAll(entityList);
            adapter.notifyDataSetChanged();

        } else {
            showToast(Common.defDecode(pubReturn.getMessage()));
        }
    }
}
