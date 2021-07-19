package com.example.liqian.huarong.mvp.p;


import com.example.liqian.huarong.base.BaseMvpPresenter;
import com.example.liqian.huarong.base.ResultCallback;
import com.example.liqian.huarong.bean.EntityFriend;
import com.example.liqian.huarong.mvp.m.HomeModel;
import com.example.liqian.huarong.mvp.v.HomeView;

public class HomePresenter extends BaseMvpPresenter<HomeView> {

    private HomeModel model;

    @Override
    protected void initModel() {
        model = new HomeModel();
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


    public void getData1(String tudoJson){
        model.getData(tudoJson, new ResultCallback<String>() {
            @Override
            public void onSuccess(String data) {
                if (view!=null){
                    view.setData1(data);
                }
            }

            @Override
            public void onFail(String msg) {

            }
        });
    }



    public void getData2(String tudoJson){
        model.getData(tudoJson, new ResultCallback<String>() {
            @Override
            public void onSuccess(String data) {
                if (view!=null){
                    view.setData2(data);
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

    public void allRead(String tudoJson){
        model.getAllReadData(tudoJson, new ResultCallback<String>() {
            @Override
            public void onSuccess(String data) {
                if (view!=null){
                    view.setAllReadData(data);
                }
            }

            @Override
            public void onFail(String msg) {

            }
        });
    }


    public void singleRead(String tudoJson, final int position){
        model.getAllReadData(tudoJson, new ResultCallback<String>() {
            @Override
            public void onSuccess(String data) {
                if (view!=null){
                    view.setSingleReadData(data,position);
                }
            }

            @Override
            public void onFail(String msg) {

            }
        });
    }
}
