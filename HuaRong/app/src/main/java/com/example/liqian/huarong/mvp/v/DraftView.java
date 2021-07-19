package com.example.liqian.huarong.mvp.v;


import com.example.liqian.huarong.base.BaseMvpView;

public interface DraftView extends BaseMvpView {

    void setData(String data);
    void setListData(String data);

    void sendData(String data);

    void getUpdate(String data);
}
