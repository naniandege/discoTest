package com.example.liqian.huarong.mvp.p;


import com.example.liqian.huarong.base.BaseMvpPresenter;
import com.example.liqian.huarong.base.ResultCallback;
import com.example.liqian.huarong.mvp.m.NodeModel;
import com.example.liqian.huarong.mvp.m.PlaceModel;
import com.example.liqian.huarong.mvp.v.NodeView;
import com.example.liqian.huarong.mvp.v.PlaceView;

public class NodePresenter extends BaseMvpPresenter<NodeView> {

    private NodeModel nodeModel;

    @Override
    protected void initModel() {
        nodeModel = new NodeModel();
        addModel(nodeModel);
    }

    public void setData(String tudoJson) {
        nodeModel.getData(tudoJson, new ResultCallback<String>() {
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


    public void addData(String tudoJson) {
        nodeModel.addData(tudoJson, new ResultCallback<String>() {
            @Override
            public void onSuccess(String data) {
                if (view != null) {
                    view.addData(data);
                }
            }

            @Override
            public void onFail(String msg) {

            }
        });
    }


    public void audit(String string) {
        nodeModel.audit(string, new ResultCallback<String>() {
            @Override
            public void onSuccess(String data) {
                if (view != null) {
                    view.audit(data);
                }
            }

            @Override
            public void onFail(String msg) {

            }
        });
    }
}
