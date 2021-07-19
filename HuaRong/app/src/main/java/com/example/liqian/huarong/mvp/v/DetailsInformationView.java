package com.example.liqian.huarong.mvp.v;


import com.example.liqian.huarong.base.BaseMvpView;

public interface DetailsInformationView extends BaseMvpView {

    void setData(String data);
    void setListData(String data);
    void getreplyData(String data);
    void getfile(String data);

    void getfileList(String data);
}
