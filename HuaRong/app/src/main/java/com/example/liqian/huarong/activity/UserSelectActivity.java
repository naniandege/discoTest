package com.example.liqian.huarong.activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
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

import com.example.liqian.huarong.R;
import com.example.liqian.huarong.adapter.UserAdapter;
import com.example.liqian.huarong.base.BaseActivity;
import com.example.liqian.huarong.bean.EntityBase;
import com.example.liqian.huarong.bean.EntityLogin;
import com.example.liqian.huarong.bean.EntityPersonal;
import com.example.liqian.huarong.bean.PubReturn;
import com.example.liqian.huarong.mvp.p.LoginPresenter;
import com.example.liqian.huarong.mvp.v.LoginView;
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

public class UserSelectActivity extends BaseActivity<LoginView, LoginPresenter> implements LoginView, UserAdapter.onItemCliclistnner {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.lv)
    RecyclerView lv;
    private UserAdapter adapter;
    private ArrayList<EntityLogin> list;

    @Override
    protected LoginPresenter initPresenter() {
        return new LoginPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_user_select_activity;
    }

    @Override
    protected void initView() {
        super.initView();
        StatusBarUtil.setLightMode(this);//修改字体颜色

        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        //列表数据
        list = new ArrayList<>();
        adapter = new UserAdapter(list, this);
        lv.setAdapter(adapter);
        //recycleView显示方式:线性
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        lv.setLayoutManager(linearLayoutManager);
        //分割线
        lv.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        adapter.setOnItemCliclistnner(this);

        //从Intent当中根据key取得value
        Intent intent = getIntent();
        if (intent != null) {
            String initvalue = intent.getStringExtra("key");
            Gson gson1 = new Gson();
            List<EntityLogin> logins = gson1.fromJson(initvalue, new TypeToken<List<EntityLogin>>() {
            }.getType());
            List<EntityLogin> logins1 = Common.defDecodeEntityList(logins);
            list.addAll(logins1);
            adapter.notifyDataSetChanged();
        }

        // 判断列表的条目数
        initSize();
    }

    private void initSize() {
        //如果长度==1   跳转到主页面
        if (list.size() == 1) {
            EntityLogin entityLogin = list.get(0);
            // //把对象写到流里
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            try {
                ObjectOutputStream oos = new ObjectOutputStream(baos);
                oos.writeObject(entityLogin);
                String temp = new String(Base64.encode(baos.toByteArray(), Base64.DEFAULT));
                SpUtil.setParam("login", temp);
            } catch (IOException e) {
                e.printStackTrace();
            }

            Common.LoginUser = list.get(0);
            presenter.login1(BLL.PersonalSelectBySelf());

            //跳转到主页面
            Intent mainForm = new Intent(this, MainActivity.class);
            startActivity(mainForm);
            finish();
        }
    }


    @Override
    public void setData(String data) {

    }

    @Override
    public void setData1(String data) {
        String str = Common.StringPick(data);
        Gson gson = new Gson();
        PubReturn pubReturn = gson.fromJson(str, PubReturn.class);
        if (pubReturn.getState()) {
            String decode = Common.defDecode(pubReturn.getData());
            Type type = new TypeToken<List<EntityPersonal>>() {
            }.getType();
            List<EntityPersonal> entityBases = gson.fromJson(decode, type);
            List<EntityPersonal> bases = Common.defDecodeEntityList(entityBases);
            if (bases.size() > 0) {
                EntityBase entityBase1 = new EntityBase();
                entityBase1.setbase_code(bases.get(0).getpers_worklocation_code());
                entityBase1.settb_code("9000");
                entityBase1.settype_code("001");
                entityBase1.setkind_code("001");
                if (entityBase1.getbase_code() != null
                        && !entityBase1.getbase_code().isEmpty()) {
                    presenter.login2(BLL.BaseSelect(entityBase1));
                }
            }

        } else {
            ToastUtil.showShort(Common.defDecode(pubReturn.getMessage()));
        }
    }

    @Override
    public void setData2(String data) {
        String str = Common.StringPick(data);
        Gson gson = new Gson();
        PubReturn pubReturn = gson.fromJson(str, PubReturn.class);
        if (pubReturn.getState()) {
            String decode = Common.defDecode(pubReturn.getData());
            Type type = new TypeToken<List<EntityBase>>() {
            }.getType();
            List<EntityBase> entityBases = gson.fromJson(decode, type);
            List<EntityBase> bases = Common.defDecodeEntityList(entityBases);
            if (bases.size() > 0) {
                Common.WorkLocation = bases.get(0).getbase_name();
                Common.WorkLongitude = bases.get(0).getbase_deci2_1();
                Common.WorkLatitude = bases.get(0).getbase_deci2_2();
            }

        } else {
            ToastUtil.showShort(Common.defDecode(pubReturn.getMessage()));
        }
    }

    @Override
    public void onItem(final int position) {
        EntityLogin entityLogin = list.get(position);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(entityLogin);//把对象写到流里
            String temp = new String(Base64.encode(baos.toByteArray(), Base64.DEFAULT));
            SpUtil.setParam("login", temp);
        } catch (IOException e) {
            e.printStackTrace();
        }


        AlertDialog dialog = new AlertDialog.Builder(UserSelectActivity.this)
                .setTitle(entityLogin.getcorp_name())
                .setMessage("是否要选择此单位？")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Common.LoginUser = list.get(position);
                        presenter.login1(BLL.PersonalSelectBySelf());

                        Intent mainForm = new Intent(UserSelectActivity.this, MainActivity.class);
                        startActivity(mainForm);
                        finish();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
        dialog.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(Color.BLUE);
        dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(Color.BLUE);
    }
}