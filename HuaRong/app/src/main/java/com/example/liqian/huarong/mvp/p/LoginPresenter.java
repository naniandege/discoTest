package com.example.liqian.huarong.mvp.p;


import com.example.liqian.huarong.base.BaseMvpPresenter;
import com.example.liqian.huarong.base.ResultCallback;
import com.example.liqian.huarong.mvp.m.LoginModel;
import com.example.liqian.huarong.mvp.v.LoginView;

public class LoginPresenter extends BaseMvpPresenter<LoginView> {

    private LoginModel loginModel;

    @Override
    protected void initModel() {
        loginModel = new LoginModel();
        addModel(loginModel);
    }

    public void login(String loginJson) {
        loginModel.login(loginJson, new ResultCallback<String>() {
            @Override
            public void onSuccess(String data) {
                if (view != null){
                    view.setData(data);
                }
            }

            @Override
            public void onFail(String msg) {

            }
        });
    }

    public void login1(String loginJson) {
        loginModel.login1(loginJson, new ResultCallback<String>() {
            @Override
            public void onSuccess(String data) {
                if (view != null){
                    view.setData1(data);
                }
            }

            @Override
            public void onFail(String msg) {

            }
        });
    }


    public void login2(String loginJson) {
        loginModel.login2(loginJson, new ResultCallback<String>() {
            @Override
            public void onSuccess(String data) {
                if (view != null){
                    view.setData2(data);
                }
            }

            @Override
            public void onFail(String msg) {

            }
        });
    }
}
