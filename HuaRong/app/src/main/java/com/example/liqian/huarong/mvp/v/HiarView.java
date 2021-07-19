package com.example.liqian.huarong.mvp.v;


import com.example.liqian.huarong.base.BaseMvpView;

public interface HiarView extends BaseMvpView {

    void setData(String data);
    void setListData(String data);

    void getSendData(String data);

    void send(String data);
}
