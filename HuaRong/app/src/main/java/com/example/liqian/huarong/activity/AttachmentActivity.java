package com.example.liqian.huarong.activity;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.liqian.huarong.R;
import com.example.liqian.huarong.adapter.AttAdapter;
import com.example.liqian.huarong.adapter.AttchmenAdapter;
import com.example.liqian.huarong.base.BaseActivity;
import com.example.liqian.huarong.bean.EntityBase;
import com.example.liqian.huarong.bean.EntityFile;
import com.example.liqian.huarong.bean.PubReturn;
import com.example.liqian.huarong.mvp.p.AttachmenPresenter;
import com.example.liqian.huarong.mvp.v.AttachmenView;
import com.example.liqian.huarong.net.BLL;
import com.example.liqian.huarong.net.Common;
import com.example.liqian.huarong.net.General;
import com.example.liqian.huarong.utils.ToastUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jaeger.library.StatusBarUtil;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AttachmentActivity extends BaseActivity<AttachmenView, AttachmenPresenter> implements AttachmenView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.lv)
    RecyclerView lv;
    private Gson gson;
    private String baseCode;
    private ArrayList<EntityFile> list;
    private AttAdapter adapter;

    @Override
    protected AttachmenPresenter initPresenter() {
        return new AttachmenPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_attachment;
    }

    @Override
    protected void initView() {
        super.initView();
        initToolbar();   //初始化Toolbar
        initRecyclerView();  //初始化Recy
        Intent intent = getIntent();   //获取baseCode
        if (intent != null) {
            baseCode = intent.getStringExtra("baseCode");
        }
    }


    private void initRecyclerView() {
        list = new ArrayList<>();
        adapter = new AttAdapter(list, this);
        lv.setAdapter(adapter);
        lv.setLayoutManager(new LinearLayoutManager(this));
        lv.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
    }


    private void initToolbar() {
        StatusBarUtil.setLightMode(this);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
    }


    @Override
    protected void initData() {
        super.initData();
        presenter.getData(BLL.BLLJsonByStream("In_Mongo_File_Select"));

    }


    @Override
    public void setData(String data) {
        String stringPick = Common.StringPick(data);
        if (gson == null) {
            gson = new Gson();
        }
        PubReturn pubReturn = gson.fromJson(stringPick, PubReturn.class);
        if (pubReturn.getState()) {
            EntityFile entityFile = new EntityFile();
            entityFile.setcorp_tn(Common.LoginUser.getcorp_tn());
            entityFile.setfile_becode(baseCode);
            if (Common.attachmentTag.equals("邮箱")) {
                entityFile.settb_code("100");
                entityFile.settype_code("001");
                entityFile.setkind_code("003");
            } else if (Common.attachmentTag.equals("通知")) {
                entityFile.settb_code("17");
                entityFile.settype_code("3001");
                entityFile.setkind_code("001");
            } else if (Common.attachmentTag.equals("工作分配")) {
                entityFile.settb_code("100");
                entityFile.settype_code("001");
                entityFile.setkind_code("003");
            } else if (Common.attachmentTag.equals("工作计划")) {
                entityFile.settb_code("100");
                entityFile.settype_code("001");
                entityFile.setkind_code("001");
            } else if (Common.attachmentTag.equals("工作汇报")) {
                entityFile.settb_code("100");
                entityFile.settype_code("001");
                entityFile.setkind_code("002");
            }
            General<Object> general = new General<>();
            String Json = general.bllMonJson(entityFile, Common.defDecode(pubReturn.getData()));
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
            Type type = new TypeToken<List<EntityFile>>() {
            }.getType();
            List<EntityFile> entityFiles = gson.fromJson(decode, type);
            List<EntityFile> entityFileList = Common.defDecodeEntityList(entityFiles);
            if (entityFileList != null && entityFileList.size() > 0) {
                list.addAll(entityFileList);
                adapter.notifyDataSetChanged();
            }
        } else {
            ToastUtil.showShort(Common.defDecode(pubReturn.getMessage()));
        }
    }
}
