package com.example.liqian.huarong.mvp.v;


import com.example.liqian.huarong.base.BaseMvpView;
import com.example.liqian.huarong.bean.EntityWorkFlow;

public interface BatchView extends BaseMvpView {

    void setData(String data);
    void setListData(String data);

    void setTudu(String data,String lodTag);

    void dataUp(String data, EntityWorkFlow entity);

    void backData(String data,EntityWorkFlow workFlow);
}
