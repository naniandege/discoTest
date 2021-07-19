package com.example.liqian.huarong.mvp.p;


import com.example.liqian.huarong.base.BaseMvpPresenter;
import com.example.liqian.huarong.base.ResultCallback;
import com.example.liqian.huarong.mvp.m.SharedFileModel;
import com.example.liqian.huarong.mvp.v.SharedFileView;

public class SharedFilePresenter extends BaseMvpPresenter<SharedFileView> {

    private SharedFileModel model;

    @Override
    protected void initModel() {
        model = new SharedFileModel();
        addModel(model);
    }

    public void getData(String json){
        model.getData(json, new ResultCallback<String>() {
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


    public void getListData(String tudoJson){
        model.getListData(tudoJson, new ResultCallback<String>() {
            @Override
            public void onSuccess(String data) {
                if (view!=null){
                    view.setListData(data);
                }
            }

            @Override
            public void onFail(String msg) {

            }
        });
    }
}
