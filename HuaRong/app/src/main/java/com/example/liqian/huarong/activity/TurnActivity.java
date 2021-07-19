package com.example.liqian.huarong.activity;

import android.content.Intent;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.liqian.huarong.R;
import com.example.liqian.huarong.adapter.TurnAdapter;
import com.example.liqian.huarong.base.BaseActivity;
import com.example.liqian.huarong.bean.EntPages;
import com.example.liqian.huarong.bean.EntityBase;
import com.example.liqian.huarong.bean.EntityPersonal;
import com.example.liqian.huarong.bean.PubReturn;
import com.example.liqian.huarong.mvp.p.TurnPresenter;
import com.example.liqian.huarong.mvp.v.TurnView;
import com.example.liqian.huarong.net.BLL;
import com.example.liqian.huarong.net.Common;
import com.example.liqian.huarong.net.General;
import com.example.liqian.huarong.utils.ToastUtil;
import com.example.liqian.huarong.utils.Tools;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jaeger.library.StatusBarUtil;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class TurnActivity extends BaseActivity<TurnView, TurnPresenter> implements TurnView {

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
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private TurnAdapter adapter;
    private ArrayList<EntityPersonal> list;
    private EntityBase mEntityBase;


    @Override
    protected TurnPresenter initPresenter() {
        return new TurnPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_turn;
    }

    @Override
    protected void initView() {
        super.initView();

        Intent intent = getIntent();
        if (intent != null) {
            mEntityBase = (EntityBase) intent.getSerializableExtra("entityBase");
        }

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
    protected void initData() {
        super.initData();

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

    @Override
    public void forWarDingData(String data) {
        if (data != null) {
            String stringPick = Common.StringPick(data);
            Gson gson = new Gson();
            PubReturn pubReturn = gson.fromJson(stringPick, PubReturn.class);
            if (pubReturn.getState()) {
                EntityBase entityBase = new EntityBase();
                entityBase.setcorp_tn(Common.LoginUser.getcorp_tn());
                entityBase.setbase_code(mEntityBase.getbase_code());
                ArrayList<EntityPersonal> selectedItem = adapter.getSelectedItem();
                if (selectedItem.size() == 0) {
                    ToastUtil.showShort("请选择收件人");
                    return;
                } else {
                    for (EntityPersonal personal : selectedItem) {
                        entityBase.setpers_code(personal.getpers_code());
                    }
                }

                General<Object> general = new General<>();
                String Json = general.bllInJson(entityBase, Common.defDecode(pubReturn.getData()));
                String encode = Common.defEncode(Json);
                presenter.submitData(encode);
            }
        }
    }

    @Override
    public void submitData(String data) {
        String str = Common.StringPick(data);
        Gson gson = new Gson();
        PubReturn pubReturn = gson.fromJson(str, PubReturn.class);
        if (pubReturn.getState()) {
            Common.defDecode(pubReturn.getData());
            ToastUtil.showShort("转发成功");
            finish();
        } else {
            ToastUtil.showShort(Common.defDecode(pubReturn.getMessage()));
        }
    }


    @OnClick({R.id.query, R.id.submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.query: //查询
                presenter.getData(BLL.BLLJsonByStream("In_Personal_PageSelect"));
                break;
            case R.id.submit:   //转发提交
                presenter.forWarDing(BLL.BLLJsonByStream("In_BasePers_Add"));
                break;
        }
    }
}
