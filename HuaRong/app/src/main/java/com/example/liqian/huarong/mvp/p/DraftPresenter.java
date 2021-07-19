package com.example.liqian.huarong.mvp.p;


import com.example.liqian.huarong.base.BaseMvpPresenter;
import com.example.liqian.huarong.base.ResultCallback;
import com.example.liqian.huarong.mvp.m.AnnounModel;
import com.example.liqian.huarong.mvp.m.DraftModel;
import com.example.liqian.huarong.mvp.v.AnnounView;
import com.example.liqian.huarong.mvp.v.DraftView;

public class DraftPresenter extends BaseMvpPresenter<DraftView> {

    private DraftModel model;

    @Override
    protected void initModel() {
        model = new DraftModel();
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



    public void getUpdate(String tudoJson){
        model.getData(tudoJson, new ResultCallback<String>() {
            @Override
            public void onSuccess(String data) {
                if (view!=null){
                    view.getUpdate(data);
                }
            }

            @Override
            public void onFail(String msg) {

            }
        });
    }


    public void sendData(String tudoJson){
        model.sendData(tudoJson, new ResultCallback<String>() {
            @Override
            public void onSuccess(String data) {
                if (view!=null){
                    view.sendData(data);
                }
            }

            @Override
            public void onFail(String msg) {

            }
        });
    }
}
