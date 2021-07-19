package com.example.liqian.huarong.mvp.v;


import com.example.liqian.huarong.base.BaseMvpView;

public interface AttenDanView extends BaseMvpView {

    void setData(String data);
    void setListData(String data);


    void addData(String data);

    void mainUpTaskData(String data);

    void taskAdd(String data);

    void getTime(String data);

    void smsStr(String data);

    void smsData(String data);

    void wfTaskSms(String data);
}
