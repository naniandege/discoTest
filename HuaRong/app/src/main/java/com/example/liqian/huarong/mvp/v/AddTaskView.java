package com.example.liqian.huarong.mvp.v;


import com.example.liqian.huarong.base.BaseMvpView;

public interface AddTaskView extends BaseMvpView {


    void addData(String data);

    void mainUpTaskData(String data);

    void taskAdd(String data);

    void getCodeData(String data);

    void getCodeStr(String data);

    void getFineData(String data);

    void getFineStr(String data);

    void smsStr(String data);

    void smsData(String data);

    void wfTaskSms(String data);

    void uploadData(String data);

    void addFile(String data);
}
