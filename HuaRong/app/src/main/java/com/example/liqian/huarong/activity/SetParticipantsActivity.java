package com.example.liqian.huarong.activity;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.liqian.huarong.R;
import com.example.liqian.huarong.adapter.NodeAdapter;
import com.example.liqian.huarong.adapter.NodeSelectAdapter;
import com.example.liqian.huarong.base.BaseActivity;
import com.example.liqian.huarong.bean.EntityPersonal;
import com.example.liqian.huarong.bean.EntityWorkFlow;
import com.example.liqian.huarong.bean.PubReturn;
import com.example.liqian.huarong.mvp.p.NodePresenter;
import com.example.liqian.huarong.mvp.v.NodeView;
import com.example.liqian.huarong.net.BLL;
import com.example.liqian.huarong.net.Common;
import com.example.liqian.huarong.utils.ToastUtil;
import com.example.liqian.huarong.widget.TextDialog;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jaeger.library.StatusBarUtil;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class SetParticipantsActivity extends BaseActivity<NodeView, NodePresenter> implements NodeView, NodeAdapter.onItemClickListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.lv)
    RecyclerView lv;
    @BindView(R.id.lv1)
    RecyclerView lv1;
    @BindView(R.id.but1)
    Button but1;
    @BindView(R.id.but2)
    Button but2;

    private ArrayList<EntityWorkFlow> list;
    private ArrayList<EntityWorkFlow> flows;
    private NodeSelectAdapter selectAdapter;
    private EntityWorkFlow workFlow;
    private EntityWorkFlow wfEnt;
    private List<EntityWorkFlow> mWor;

    @Override
    protected NodePresenter initPresenter() {
        return new NodePresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_set_participants;
    }

    @Override
    protected void initView() {
        super.initView();

        StatusBarUtil.setLightMode(this);//修改状态栏字体颜色

        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        //获取node数据
        Intent intent = getIntent();
        if (intent != null) {
            Gson gson = new Gson();
            String json = intent.getStringExtra("json");
            Type listType = new TypeToken<List<EntityWorkFlow>>() {
            }.getType();
            mWor = gson.fromJson(json, listType);
            wfEnt = (EntityWorkFlow) intent.getSerializableExtra("wfEnt");
        }

        //集合数据源
        list = new ArrayList<>();
        list.addAll(mWor);
        //适配器绑定
        NodeAdapter adapter = new NodeAdapter(list, this);
        lv.setAdapter(adapter);
        lv.setLayoutManager(new LinearLayoutManager(this));
        lv.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        adapter.setOnItemClickListener(this);


        flows = new ArrayList<>();
        selectAdapter = new NodeSelectAdapter(flows, this);
        lv1.setAdapter(selectAdapter);
        lv1.setLayoutManager(new LinearLayoutManager(this));
        lv1.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
    }

    @Override
    protected void initData() {
        super.initData();
        if (workFlow != null) {
            EntityWorkFlow flow = new EntityWorkFlow();
            flow.setwf_code(workFlow.getwf_code());
            flow.settask_code(workFlow.gettask_code());
            flow.setnode_code(workFlow.getnode_code());
            flow.setinit_pers_ucode(workFlow.getinit_pers_ucode());
            flow.setinit_pcode(workFlow.getinit_pcode());
            presenter.setData(BLL.TaskNodePaptSelect(flow));
        } else {
            showToast("请设置参与人");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(1, 1, 1, "");
        return super.onCreateOptionsMenu(menu);
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
            flows.addAll(entityList);
            selectAdapter.notifyDataSetChanged();
        } else {
            ToastUtil.showShort(Common.defDecode(pubReturn.getMessage()));
        }
    }

    @Override
    public void addData(String data) {
        String str = Common.StringPick(data);
        Gson gson = new Gson();
        PubReturn pubReturn = gson.fromJson(str, PubReturn.class);
        if (pubReturn.getState()) {
            //解码
            Common.defDecode(pubReturn.getData());
        } else {
            String decode = Common.defDecode(pubReturn.getMessage());
            showToast(decode);
        }
    }

    @Override
    public void audit(String data) {
        String str = Common.StringPick(data);
        Gson gson = new Gson();
        PubReturn pubReturn = gson.fromJson(str, PubReturn.class);
        if (pubReturn.getState()) {
            Common.defDecode(pubReturn.getData());
            Intent intent = new Intent();
            intent.setClass(this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            String decode = Common.defDecode(pubReturn.getMessage());
            showToast(decode);
        }
    }

    @Override
    public void onItem(final int position, NodeAdapter.ViewHolder viewHolder) {

        workFlow = list.get(position);

        final TextDialog dialog = new TextDialog(this);
        dialog.setTv("设置参与人与查看参与人");
        dialog.setbutton_exit("设置参与人");
        dialog.setbutton_cancel("查看参与人");
        dialog.setOnExitListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SetParticipantsActivity.this, PersonaActivity.class);
                startActivityForResult(intent, 1);
                dialog.dismiss();
            }
        });
        dialog.setOnCancelListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flows.clear();
                initData();
                selectAdapter.notifyDataSetChanged();
                dialog.dismiss();
            }
        });
        dialog.show();
    }


    @OnClick({R.id.but1, R.id.but2})
    public void onClickLisnner(View view) {
        switch (view.getId()) {
            case R.id.but1:
                initAudit();
                break;

            case R.id.but2:
                finish();
                break;
        }
    }

    private void initAudit() {
        presenter.audit(BLL.TaskDispose(wfEnt));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == 2) {
            String json = data.getStringExtra("json");
            Gson gson = new Gson();
            Type listType = new TypeToken<List<EntityPersonal>>() {
            }.getType();
            List<EntityPersonal> mList = gson.fromJson(json, listType);

            EntityWorkFlow addFlow = new EntityWorkFlow();
            addFlow.setwf_code(workFlow.getwf_code());
            addFlow.settask_code(workFlow.gettask_code());
            addFlow.setnode_code(workFlow.getnode_code());
            addFlow.setinit_pers_ucode(workFlow.getinit_pers_ucode());
            addFlow.setinit_pers_uname(workFlow.getinit_pers_uname());
            addFlow.setinit_pcode(workFlow.getinit_pcode());
            addFlow.setinit_pun(workFlow.getinit_pun());
            addFlow.setinit_pname(workFlow.getinit_pname());
            addFlow.setpapt_tcode("01");
            addFlow.setpapt_tname("个人");

            for (EntityPersonal personal : mList) {
                addFlow.setpapt_code(personal.getpers_code());
                addFlow.setpapt_key(personal.getpers_code());
                addFlow.setpapt_pun(personal.getpers_un());
                addFlow.setpapt_name(personal.getpers_name());
                presenter.addData(BLL.TaskNodePaptAdd(addFlow));
            }

        }
    }

}
