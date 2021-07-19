package com.example.liqian.huarong.mvp.p;


import com.example.liqian.huarong.base.BaseMvpPresenter;
import com.example.liqian.huarong.base.ResultCallback;
import com.example.liqian.huarong.mvp.m.TaskDetailModel;
import com.example.liqian.huarong.mvp.v.TaskDetailView;

public class IntegralPresenter extends BaseMvpPresenter<TaskDetailView> {

    private TaskDetailModel taskDetailModel;

    @Override
    protected void initModel() {
        taskDetailModel = new TaskDetailModel();
        addModel(taskDetailModel);
    }

    public void taskDetail(String json){
        taskDetailModel.data(json, new ResultCallback<String>() {
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


    public void oaList(String json){
        taskDetailModel.oaList(json, new ResultCallback<String>() {
            @Override
            public void onSuccess(String data) {
                if (view!=null){
                    view.oaListData(data);
                }
            }

            @Override
            public void onFail(String msg) {

            }
        });
    }



    public void attList(String json){
        taskDetailModel.attList(json, new ResultCallback<String>() {
            @Override
            public void onSuccess(String data) {
                if (view!=null){
                    view.attList(data);
                }
            }

            @Override
            public void onFail(String msg) {

            }
        });
    }
}
