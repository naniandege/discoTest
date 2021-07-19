package com.example.liqian.huarong.mvp.p;


import com.example.liqian.huarong.base.BaseMvpPresenter;
import com.example.liqian.huarong.base.ResultCallback;
import com.example.liqian.huarong.mvp.m.AnnounModel;
import com.example.liqian.huarong.mvp.v.AnnounView;

public class AnnounPresenter extends BaseMvpPresenter<AnnounView> {

    private AnnounModel model;

    @Override
    protected void initModel() {
        model = new AnnounModel();
        addModel(model);
    }

    public void getData(String tudoJson){
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
