package com.example.liqian.huarong.mvp.p;


import com.example.liqian.huarong.base.BaseMvpPresenter;
import com.example.liqian.huarong.base.ResultCallback;
import com.example.liqian.huarong.mvp.m.TaskHistoryModel;
import com.example.liqian.huarong.mvp.v.TaskHistoryView;

public class TaskHistoryPresenter extends BaseMvpPresenter<TaskHistoryView> {

    private TaskHistoryModel model;

    @Override
    protected void initModel() {
        model = new TaskHistoryModel();
        addModel(model);
    }

    public void taskHistory(String json){
        model.getData(json,new ResultCallback<String>() {
            @Override
            public void onSuccess(String data) {
                if (view!=null){
                    view.setData(data);
                }
            }

            @Override
            public void onFail(String msg) {

            }
        });
    }
}
