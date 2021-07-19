package com.example.liqian.huarong.mvp.p;


import com.example.liqian.huarong.base.BaseMvpPresenter;
import com.example.liqian.huarong.base.ResultCallback;
import com.example.liqian.huarong.bean.EntityWorkFlow;
import com.example.liqian.huarong.mvp.m.AnnounModel;
import com.example.liqian.huarong.mvp.m.BatchModel;
import com.example.liqian.huarong.mvp.v.BatchView;

public class BatchPresenter extends BaseMvpPresenter<BatchView> {

    private BatchModel model;

    @Override
    protected void initModel() {
        model = new BatchModel();
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


    public void todu(String tudoJson, final String lodTag) {
        model.todu(tudoJson, new ResultCallback<String>() {
            @Override
            public void onSuccess(String data) {
                if (view != null) {
                    view.setTudu(data, lodTag);
                }
            }

            @Override
            public void onFail(String msg) {

            }
        });
    }

    public void dataUp(String tudoJson, final EntityWorkFlow entity) {
        model.dataUp(tudoJson, new ResultCallback<String>() {
            @Override
            public void onSuccess(String data) {
                if (view != null) {
                    view.dataUp(data, entity);
                }
            }

            @Override
            public void onFail(String msg) {

            }
        });
    }

    public void backData(String tudoJson, final EntityWorkFlow mflow) {
        model.getListData(tudoJson, new ResultCallback<String>() {
            @Override
            public void onSuccess(String data) {
                if (view != null) {
                    view.backData(data,mflow);
                }
            }

            @Override
            public void onFail(String msg) {

            }
        });
    }
}
