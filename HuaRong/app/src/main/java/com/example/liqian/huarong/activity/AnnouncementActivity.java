package com.example.liqian.huarong.activity;

import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.example.liqian.huarong.R;
import com.example.liqian.huarong.adapter.AnnounAdapter;
import com.example.liqian.huarong.base.BaseActivity;
import com.example.liqian.huarong.bean.EntityBase;
import com.example.liqian.huarong.bean.PubReturn;
import com.example.liqian.huarong.mvp.p.AnnounPresenter;
import com.example.liqian.huarong.mvp.v.AnnounView;
import com.example.liqian.huarong.net.BLL;
import com.example.liqian.huarong.net.Common;
import com.example.liqian.huarong.net.General;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jaeger.library.StatusBarUtil;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class AnnouncementActivity extends BaseActivity<AnnounView, AnnounPresenter> implements AnnounView {

    @BindView(R.id.toolbar_name)
    TextView toolbarName;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.lv)
    RecyclerView lv;
    private Gson gson;
    private ArrayList<EntityBase> list;
    private AnnounAdapter adapter;


    @Override
    protected AnnounPresenter initPresenter() {
        return new AnnounPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_announcement;
    }

    @Override
    protected void initView() {
        super.initView();
        StatusBarUtil.setLightMode(this);
        initToolbar();
        initRecy();
    }

    private void initRecy() {
        list = new ArrayList<>();
        adapter = new AnnounAdapter(list, this);
        lv.setAdapter(adapter);
        lv.setLayoutManager(new LinearLayoutManager(this));
        lv.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
    }

    private void initToolbar() {
        toolbar.setTitle(" ");
        toolbarName.setText("通知公告");
        toolbarName.setTextColor(getResources().getColor(R.color.c_000000));
        setSupportActionBar(toolbar);
    }

    @Override
    protected void initData() {
        super.initData();
        presenter.getData(BLL.BLLJsonByStream("In_Base_Select"));
    }

    @Override
    public void setData(String data) {
        String stringPick = Common.StringPick(data);
        if (gson == null) {
            gson = new Gson();
        }

        PubReturn pubReturn = gson.fromJson(stringPick, PubReturn.class);
        if (pubReturn.getState()) {
            EntityBase mBase = new EntityBase();
            mBase.setcorp_tn(Common.LoginUser.getcorp_tn());
            mBase.settb_code("8888");
            mBase.settype_code("001");
            mBase.setkind_code("001");
            mBase.setif_base_check_1(1);
            mBase.setbase_check_1(0);
            mBase.setorder_list("base_type,base_date_1 desc");
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
                list.addAll(entityList);
                adapter.notifyDataSetChanged();
            }
        } else {
            showToast(Common.defDecode(pubReturn.getMessage()));
        }
    }
}
