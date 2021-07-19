package com.example.liqian.huarong.mvp.v;


import com.example.liqian.huarong.base.BaseMvpView;

public interface TurnView extends BaseMvpView {

    void setData(String data);
    void setListData(String data);
    void forWarDingData(String data);
    void submitData(String data);
}
