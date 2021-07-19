package com.example.liqian.huarong.mvp.v;


import com.example.liqian.huarong.base.BaseMvpView;

public interface NodeView extends BaseMvpView {

    void setData(String data);
    void addData(String data);
    void audit(String data);
}
