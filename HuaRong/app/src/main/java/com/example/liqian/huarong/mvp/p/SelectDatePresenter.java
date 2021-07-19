package com.example.liqian.huarong.mvp.p;


import com.example.liqian.huarong.base.BaseMvpPresenter;
import com.example.liqian.huarong.base.ResultCallback;
import com.example.liqian.huarong.mvp.m.SelectDateModel;
import com.example.liqian.huarong.mvp.m.UpsertModel;
import com.example.liqian.huarong.mvp.v.SelectDateView;
import com.example.liqian.huarong.mvp.v.UpsertView;


public class SelectDatePresenter extends BaseMvpPresenter<SelectDateView> {

    private SelectDateModel model;

    @Override
    protected void initModel() {
        model = new SelectDateModel();
        addModel(model);
    }

/*    public void getDate(String tudoJson){
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
    }*/
}
