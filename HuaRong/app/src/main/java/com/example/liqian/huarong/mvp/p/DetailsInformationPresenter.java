package com.example.liqian.huarong.mvp.p;


import com.example.liqian.huarong.base.BaseMvpPresenter;
import com.example.liqian.huarong.base.ResultCallback;
import com.example.liqian.huarong.mvp.m.DetailsInformationModel;
import com.example.liqian.huarong.mvp.v.DetailsInformationView;

public class DetailsInformationPresenter extends BaseMvpPresenter<DetailsInformationView> {

    private DetailsInformationModel model;

    @Override
    protected void initModel() {
        model = new DetailsInformationModel();
        addModel(model);
    }

    public void getData(String tudoJson) {
        model.getData(tudoJson, new ResultCallback<String>() {
            @Override
            public void onSuccess(String data) {
                if (view != null) {
                    view.setData(data);
                }
            }

            @Override
            public void onFail(String msg) {

            }
        });
    }


    public void getListData(String tudoJson) {
        model.getListData(tudoJson, new ResultCallback<String>() {
            @Override
            public void onSuccess(String data) {
                if (view != null) {
                    view.setListData(data);
                }
            }

            @Override
            public void onFail(String msg) {

            }
        });
    }

    public void getreplyData(String tudoJson) {
        model.getListData(tudoJson, new ResultCallback<String>() {
            @Override
            public void onSuccess(String data) {
                if (view != null) {
                    view.getreplyData(data);
                }
            }

            @Override
            public void onFail(String msg) {

            }
        });
    }

    public void getfile(String in_mongo_file_select) {
        model.getData(in_mongo_file_select, new ResultCallback<String>() {
            @Override
            public void onSuccess(String data) {
                if (view != null) {
                    view.getfile(data);
                }
            }

            @Override
            public void onFail(String msg) {

            }
        });
    }

    public void getfileList(String in_mongo_file_select) {
        model.getfileList(in_mongo_file_select, new ResultCallback<String>() {
            @Override
            public void onSuccess(String data) {
                if (view != null) {
                    view.getfileList(data);
                }
            }

            @Override
            public void onFail(String msg) {

            }
        });
    }
}
