package com.example.liqian.huarong.mvp.p;


import com.example.liqian.huarong.base.BaseMvpPresenter;
import com.example.liqian.huarong.base.ResultCallback;
import com.example.liqian.huarong.mvp.m.GeneraModel;
import com.example.liqian.huarong.mvp.v.GeneraView;

public class GeneraPresenter extends BaseMvpPresenter<GeneraView> {

    private GeneraModel model;

    @Override
    protected void initModel() {
        model = new GeneraModel();
        addModel(model);
    }

    public void getData(String tudoJson) {
        model.genera(tudoJson, new ResultCallback<String>() {
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

}
