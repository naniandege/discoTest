package com.example.liqian.huarong.mvp.v;


import com.example.liqian.huarong.base.BaseMvpView;
import com.example.liqian.huarong.bean.EntityFriend;

public interface HomeView extends BaseMvpView {

    void setData(String data);
    void setListData(String data);
    void setData1(String data);
    void setData2(String data);
    void setAllReadData(String data);
    void setSingleReadData(String data,int position);
}
