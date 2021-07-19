package com.example.liqian.huarong.mvp.v;


import com.example.liqian.huarong.base.BaseMvpView;
import com.example.liqian.huarong.bean.EntityOA;

public interface BudgetView extends BaseMvpView {

    void setData(String data);

    void oaListData(String data);

    void attList(String data);

    void getStr(String data);

    void smsData(String data);

    void wfTaskSms(String data);

    void upDateStr(String data, EntityOA listBean);

    void upDate(String data);

    void MyinitiateStr(String data);

    void MyinitiateData(String data);

    void oaMainStr(String data);

    void oaMainData(String data);
}
