package com.example.liqian.huarong.mvp.p;


import com.example.liqian.huarong.base.BaseMvpPresenter;
import com.example.liqian.huarong.base.ResultCallback;
import com.example.liqian.huarong.mvp.m.QueryModel;
import com.example.liqian.huarong.mvp.v.QueryView;

public class QueryPresenter extends BaseMvpPresenter<QueryView> {

    private QueryModel model;

    @Override
    protected void initModel() {
        model = new QueryModel();
        addModel(model);
    }

    public void getListData(String tudoJson) {
        model.select(tudoJson, new ResultCallback<String>() {
            @Override
            public void onSuccess(String data) {
                if (view != null) {
                    view.getListData(data);
                }
            }

            @Override
            public void onFail(String msg) {

            }
        });
    }

    public void getListStr(String tudoJson) {
        model.getData(tudoJson, new ResultCallback<String>() {
            @Override
            public void onSuccess(String data) {
                if (view != null) {
                    view.getListStr(data);
                }
            }

            @Override
            public void onFail(String msg) {

            }
        });
    }

}
