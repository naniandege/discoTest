package com.example.liqian.huarong.email;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.liqian.huarong.R;
import com.example.liqian.huarong.adapter.TurnAdapter;
import com.example.liqian.huarong.base.BaseActivity;
import com.example.liqian.huarong.base.Constants;
import com.example.liqian.huarong.bean.EntPages;
import com.example.liqian.huarong.bean.EntityPersonal;
import com.example.liqian.huarong.bean.PubReturn;
import com.example.liqian.huarong.mvp.p.ReNamePresenter;
import com.example.liqian.huarong.mvp.v.ReNameView;
import com.example.liqian.huarong.net.BLL;
import com.example.liqian.huarong.net.Common;
import com.example.liqian.huarong.net.General;
import com.example.liqian.huarong.utils.ToastUtil;
import com.example.liqian.huarong.utils.Tools;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jaeger.library.StatusBarUtil;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RecipientNameActivity extends BaseActivity<ReNameView, ReNamePresenter> implements ReNameView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.pers_name)
    EditText persName;
    @BindView(R.id.dept_name)
    EditText deptName;
    @BindView(R.id.lv)
    RecyclerView lv;
    @BindView(R.id.query)
    Button query;
    @BindView(R.id.submit)
    Button submit;


    private TurnAdapter adapter;
    private ArrayList<EntityPersonal> list;

    @Override
    protected ReNamePresenter initPresenter() {
        return new ReNamePresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_recipient_name;
    }


    @Override
    protected void initView() {
        super.initView();
        StatusBarUtil.setLightMode(this);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);


        list = new ArrayList<>();
        adapter = new TurnAdapter(list, this);
        lv.setAdapter(adapter);
        lv.setLayoutManager(new LinearLayoutManager(this));
        lv.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));
    }

    @Override
    public void setData(String data) {
        if (data != null) {
            String stringPick = Common.StringPick(data);
            Gson gson = new Gson();
            PubReturn pubReturn = gson.fromJson(stringPick, PubReturn.class);
            if (pubReturn.getState()) {
                EntityPersonal mBase = new EntityPersonal();
                mBase.setcorp_tn(Common.LoginUser.getcorp_tn());
                mBase.setpers_name(persName.getText().toString());
                mBase.setdept_name(deptName.getText().toString());
                mBase.setpage_size(20);
                mBase.setpage_current(1);
                General<Object> general = new General<>();
                String Json = general.bllInJson(mBase, Common.defDecode(pubReturn.getData()));
                String encode = Common.defEncode(Json);
                presenter.getListData(encode);
            }
        }
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
            Type type = new TypeToken<EntPages<EntityPersonal>>() {
            }.getType();
            EntPages<EntityPersonal> entPages = gson.fromJson(decode, type);
            List<EntityPersonal> pageResults1 = entPages.getPageResults();
            List<EntityPersonal> entityPersonals = Common.defDecodeEntityList(pageResults1);
            if (entityPersonals != null && entityPersonals.size() > 0) {
                list.clear();
                list.addAll(entityPersonals);
                adapter.notifyDataSetChanged();
                //关闭软键盘
                Tools.closeKeyBoard(this);
            }
        } else {
            ToastUtil.showShort(Common.defDecode(pubReturn.getMessage()));
        }
    }


    @OnClick({R.id.query, R.id.submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.query:
                presenter.getData(BLL.BLLJsonByStream("In_Personal_PageSelect"));
                break;
            case R.id.submit:
                ArrayList<EntityPersonal> entityPersonals = adapter.getSelectedItem();
                Intent intent = getIntent();
                Gson gson = new Gson();
                String json = gson.toJson(entityPersonals);
                intent.putExtra("entityPersonals", json);
                setResult(Constants.RESULTCODE, intent);
                finish();
                break;
        }
    }
}
