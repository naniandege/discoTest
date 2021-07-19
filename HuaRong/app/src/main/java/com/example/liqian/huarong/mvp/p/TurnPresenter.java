package com.example.liqian.huarong.mvp.p;


import com.example.liqian.huarong.base.BaseMvpPresenter;
import com.example.liqian.huarong.base.ResultCallback;
import com.example.liqian.huarong.mvp.m.AnnounModel;
import com.example.liqian.huarong.mvp.m.TurnModel;
import com.example.liqian.huarong.mvp.v.AnnounView;
import com.example.liqian.huarong.mvp.v.TurnView;

public class TurnPresenter extends BaseMvpPresenter<TurnView> {

    private TurnModel model;

    @Override
    protected void initModel() {
        model = new TurnModel();
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

    public void forWarDing(String in_basePers_add) {
        model.getData(in_basePers_add, new ResultCallback<String>() {
            @Override
            public void onSuccess(String data) {
                if (view!=null){
                    view.forWarDingData(data);
                }
            }

            @Override
            public void onFail(String msg) {

            }
        });
    }

    public void submitData(String encode) {
        model.submitData(encode, new ResultCallback<String>() {
            @Override
            public void onSuccess(String data) {
                if (view!=null){
                    view.submitData(data);
                }
            }

            @Override
            public void onFail(String msg) {

            }
        });
    }
}
