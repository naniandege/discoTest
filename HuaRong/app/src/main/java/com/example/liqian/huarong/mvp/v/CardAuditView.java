package com.example.liqian.huarong.mvp.v;


import com.example.liqian.huarong.base.BaseMvpView;

public interface CardAuditView extends BaseMvpView {

    void setData(String data);

    void insertData(String data);

    void getData(String data);



    void getStr(String data);

    void smsData(String data);

    void wfTaskSms(String data);
}
