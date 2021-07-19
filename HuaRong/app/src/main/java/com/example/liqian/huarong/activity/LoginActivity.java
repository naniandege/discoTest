package com.example.liqian.huarong.activity;

import android.Manifest;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.liqian.huarong.R;
import com.example.liqian.huarong.api.ApiService;
import com.example.liqian.huarong.base.BaseActivity;
import com.example.liqian.huarong.bean.PubReturn;
import com.example.liqian.huarong.mvp.p.LoginPresenter;
import com.example.liqian.huarong.mvp.v.LoginView;
import com.example.liqian.huarong.net.Common;
import com.example.liqian.huarong.utils.Amount;
import com.example.liqian.huarong.utils.ImageLoader;
import com.example.liqian.huarong.utils.SpUtil;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/*
 * 登录页面
 * */
public class LoginActivity extends BaseActivity<LoginView, LoginPresenter> implements LoginView, View.OnClickListener {

    private static String MachineCode = "";
    private static String UserName = "";
    private static String Password = "";
    private static String LoginType = "";
    private RadioButton rb_work;
    private RadioButton rb_personal;
    private EditText ed_user;
    private EditText ed_password;
    private Button btn_login;
    private CheckBox btn_jizhu;
    private CheckBox btn_pc;
    private ImageView userDelete;
    private ImageView nameImg;
    private ImageView pswImg;
    private ImageView img_bag;


    @Override
    protected LoginPresenter initPresenter() {
        return new LoginPresenter();
    }

    @Override
    public void initView() {
//        setTheme(R.style.FullScreen);//还原回正常的Theme

        rb_work = (RadioButton) findViewById(R.id.rb_work);
        rb_work = (RadioButton) findViewById(R.id.rb_work);
        rb_personal = (RadioButton) findViewById(R.id.rb_personal);
        ed_user = (EditText) findViewById(R.id.ed_user);
        ed_password = (EditText) findViewById(R.id.ed_password);
        btn_login = (Button) findViewById(R.id.btn_login);
        btn_jizhu = (CheckBox) findViewById(R.id.btn_jizhu);
        btn_pc = (CheckBox) findViewById(R.id.btn_pc);
        userDelete = (ImageView) findViewById(R.id.user_delete);
        nameImg = (ImageView) findViewById(R.id.name_img);
        pswImg = (ImageView) findViewById(R.id.pswImage);
        img_bag = (ImageView) findViewById(R.id.img_bag);
        btn_login.setOnClickListener(this);

        //数据回显直接获取SP中的用户名与密码,并给EditText赋值
        final String name = (String) SpUtil.getParam("name", "");
        String psw = (String) SpUtil.getParam("psw", "");
        boolean flag = (boolean) SpUtil.getParam("flag", false);
        ed_user.setText(name);
        ed_password.setText(psw);
        btn_jizhu.setChecked(flag);

        //光标移动到EditText最后面
        ed_user.setSelection(name.length());
        ed_password.setSelection(psw.length());

        rb_work.setChecked(true);     //工作身份
        btn_pc.setChecked(false);     //是否手写签字
        btn_jizhu.setChecked(true);   //记住密码
        ImageLoader.setImage(this, R.drawable.login_bk, img_bag, R.drawable.login_bk);
        ed_user.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s)) {
                    userDelete.setVisibility(View.GONE);
                    ed_password.setText("");
                    nameImg.setImageResource(R.drawable.name_img1);
                } else {
                    nameImg.setImageResource(R.drawable.login);
                    userDelete.setVisibility(View.VISIBLE);
                }
            }
        });


        ed_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s)) {
                    pswImg.setImageResource(R.drawable.psw1);
                } else {
                    pswImg.setImageResource(R.drawable.psw);
                }
            }
        });

        userDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ed_user.setText("");
            }
        });
        initPer();
    }


    private void initPer() {
        String[] per = {Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.CAMERA};

        ActivityCompat.requestPermissions(this, per, 100);
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:

                String user = ed_user.getText().toString().trim();
                String password = ed_password.getText().toString().trim();

                if (TextUtils.isEmpty(user)) {
                    showToast("请输入开源通宝账号");
                } else if (TextUtils.isEmpty(password)) {
                    showToast("请输入密码");
                } else if (rb_work.isChecked() == false && rb_personal.isChecked() == false) {
                    showToast("请选择身份");
                } else {
                    login();
                }

                //如果是勾选状态, 直接保存    如果没有勾选, 啥也不存
                if (btn_jizhu.isChecked()) {
                    SpUtil.setParam("name", user);
                    SpUtil.setParam("psw", password);
                    SpUtil.setParam("flag", btn_jizhu.isChecked());
                } else {
                    SpUtil.setParam("name", "");
                    SpUtil.setParam("psw", "");
                    SpUtil.setParam("flag", btn_jizhu.isChecked());
                }

                //是否手写签字
                if (btn_pc.isChecked() == true) {
                    SpUtil.setParam("pc", true);
                } else {
                    SpUtil.setParam("pc", false);
                }

                break;
        }
    }


    /**
     * 登录
     */
    private void login() {
        if (!Amount.isFastClick()) {
            return;
        }

        String pwd = Common.MD5UnicodeOld("666666").toUpperCase();

        MachineCode = Common.getUniquePsuedoID();
        UserName = ed_user.getText().toString();
        Password = ed_password.getText().toString();

        if (pwd.equals("DF373E457D3288F22C917BAA9495F25B")) {
            Password = Common.MD5UnicodeOld(Password).toUpperCase();
        } else {
            Password = Common.MD5Unicode(Password).toUpperCase();
        }

        if (rb_work.isChecked() == true) {
            LoginType = "4";
        } else if (rb_personal.isChecked() == true) {
//            LoginType = "2";
            showToast("敬请期待！");
        }


        Common.MachineCode = MachineCode;
        Common.LoginType = LoginType;

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("machine_code", MachineCode);
            jsonObject.put("user_name", UserName);
            jsonObject.put("user_password", Password);
            jsonObject.put("LoginType", LoginType);
            jsonObject.put("login_equip", "2");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String LoginJson = jsonObject.toString();
        if (!TextUtils.isEmpty(LoginJson)) {
            LoginJson = Common.defEncode(LoginJson);
            presenter.login(LoginJson);

        }

    }


    public void InitUserList(final String str) {
        Intent userSelect = new Intent(this, UserSelectActivity.class);
        userSelect.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        userSelect.putExtra("key", str);
        startActivity(userSelect);
        finish();
    }


    /**
     * @param data
     */
    @Override
    public void setData(String data) {
        try {
            String str = Common.StringPick(data);
            Gson gson = new Gson();
            PubReturn pubReturn = gson.fromJson(str, PubReturn.class);
            if (pubReturn.getState()) {
                Common.RandomCode = Common.defDecode(pubReturn.getRandomCode());
                Common.OldRandomCode = Common.defDecode(pubReturn.getOldRandomCode());
                String dataStr = Common.defDecode(pubReturn.getData());
                InitUserList(dataStr);
            } else {
                showToast(Common.defDecode(pubReturn.getMessage()));
            }
        } catch (Exception e) {
            showToast("登录失败" + e.getMessage());
        }
    }

    @Override
    public void setData1(String data) {

    }

    @Override
    public void setData2(String data) {

    }
}
