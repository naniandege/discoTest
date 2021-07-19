package com.example.liqian.huarong.activity;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.liqian.huarong.R;
import com.example.liqian.huarong.adapter.PlaceAdapter;
import com.example.liqian.huarong.base.BaseActivity;
import com.example.liqian.huarong.bean.EntityBase;
import com.example.liqian.huarong.bean.PubReturn;
import com.example.liqian.huarong.mvp.p.PlacePresenter;
import com.example.liqian.huarong.mvp.v.PlaceView;
import com.example.liqian.huarong.net.BLL;
import com.example.liqian.huarong.net.Common;
import com.example.liqian.huarong.utils.SpUtil;
import com.example.liqian.huarong.utils.ToastUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jaeger.library.StatusBarUtil;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class DialogActivity extends BaseActivity<PlaceView, PlacePresenter> implements PlaceView, PlaceAdapter.onItemLisnner {

    @BindView(R.id.toolbar_name)
    TextView toolbarName;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.lv)
    RecyclerView lv;
    private ArrayList<EntityBase> list;
    private PlaceAdapter adapter;
    private EntityBase entityBase;

    @Override
    protected PlacePresenter initPresenter() {
        return new PlacePresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_dialog;
    }

    @Override
    protected void initView() {
        super.initView();
        StatusBarUtil.setLightMode(this);//修改字体颜色

        toolbar.setTitle("");
        setSupportActionBar(toolbar);


        //位置信息列表数据集合
        list = new ArrayList<>();
        adapter = new PlaceAdapter(list, this);
        lv.setAdapter(adapter);
        lv.setLayoutManager(new LinearLayoutManager(this));
        lv.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        adapter.setOnItemLisnner(this);
    }


    @Override
    protected void initData() {
        super.initData();

        EntityBase base = new EntityBase();
        base.settb_code("9000");
        base.settype_code("001");
        base.setkind_code("001");
        presenter.setPlaceList(BLL.BaseSelect(base));
    }

    @Override
    public void setData(String data) {

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
            List<EntityBase> bases = Common.defDecodeEntityList(entityBases);

            list.addAll(bases);
            adapter.notifyDataSetChanged();
        } else {
            ToastUtil.showShort(Common.defDecode(pubReturn.getMessage()));
        }
    }

    @Override
    public void onPosition(int position) {
        entityBase = list.get(position);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(entityBase);//把对象写到流里
            String temp = new String(Base64.encode(baos.toByteArray(), Base64.DEFAULT));
            SpUtil.setParam("entityBase", temp);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Intent intent = new Intent();
        setResult(2, intent);
        finish();
    }

}
