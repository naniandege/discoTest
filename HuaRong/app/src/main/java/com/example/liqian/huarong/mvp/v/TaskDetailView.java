package com.example.liqian.huarong.mvp.v;


import com.example.liqian.huarong.base.BaseMvpView;

public interface TaskDetailView extends BaseMvpView {

    void setData(String data);
    void oaListData(String data);
    void attList(String data);

    void getStr(String data);

    void smsData(String data);

    void wfTaskSms(String data);
}
