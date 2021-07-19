package com.example.liqian.huarong.mvp.p;


import com.example.liqian.huarong.base.BaseMvpPresenter;
import com.example.liqian.huarong.base.ResultCallback;
import com.example.liqian.huarong.mvp.m.UpsertModel;
import com.example.liqian.huarong.mvp.v.UpsertView;


public class UpsertPresenter extends BaseMvpPresenter<UpsertView> {

    private UpsertModel model;

    @Override
    protected void initModel() {
        model = new UpsertModel();
        addModel(model);
    }

    public void upsert(String tudoJson){
        model.getData(tudoJson, new ResultCallback<String>() {
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
