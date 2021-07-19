package com.example.liqian.huarong.mvp.p;


import com.example.liqian.huarong.base.BaseMvpPresenter;
import com.example.liqian.huarong.base.ResultCallback;
import com.example.liqian.huarong.mvp.m.CardAuditModel;
import com.example.liqian.huarong.mvp.v.CardAuditView;

public class CardAuditPresenter extends BaseMvpPresenter<CardAuditView> {

    private CardAuditModel model;

    @Override
    protected void initModel() {
        model = new CardAuditModel();
        addModel(model);
    }


    public void taskDetail(String json) {
        model.data(json, new ResultCallback<String>() {
            @Override
            public void onSuccess(String data) {
                if (view != null) {
                    view.setData(data);
                }
            }

            @Override
            public void onFail(String msg) {

            }
        });
    }


    public void insertData(String json) {
        model.insertData(json, new ResultCallback<String>() {
            @Override
            public void onSuccess(String data) {
                if (view != null) {
                    view.insertData(data);
                }
            }

            @Override
            public void onFail(String msg) {

            }
        });
    }


    public void getData(String json) {
        model.getData(json, new ResultCallback<String>() {
            @Override
            public void onSuccess(String data) {
                if (view != null) {
                    view.getData(data);
                }
            }

            @Override
            public void onFail(String msg) {

            }
        });
    }


    public void smsStr(String json) {
        model.getData(json, new ResultCallback<String>() {
            @Override
            public void onSuccess(String data) {
                if (view != null) {
                    view.getStr(data);
                }
            }

            @Override
            public void onFail(String msg) {

            }
        });
    }

    public void smsData(String json) {
        model.getSmsData(json, new ResultCallback<String>() {
            @Override
            public void onSuccess(String data) {
                if (view != null) {
                    view.smsData(data);
                }
            }

            @Override
            public void onFail(String msg) {

            }
        });
    }

    public void wfTaskSms(String json) {
        model.wfTaskSms(json, new ResultCallback<String>() {
            @Override
            public void onSuccess(String data) {
                if (view != null) {
                    view.wfTaskSms(data);
                }
            }

            @Override
            public void onFail(String msg) {

            }
        });
    }
}
