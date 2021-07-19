package com.example.liqian.huarong.mvp.v;


import com.example.liqian.huarong.base.BaseMvpView;

public interface InboxView extends BaseMvpView {

    void getData(String data);
    void emailData(String data);

    void getUpdate(String data);

    void sendData(String data);
}
