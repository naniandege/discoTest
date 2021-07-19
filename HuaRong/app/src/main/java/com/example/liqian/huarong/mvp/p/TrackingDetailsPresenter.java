package com.example.liqian.huarong.mvp.p;


import com.example.liqian.huarong.base.BaseMvpPresenter;
import com.example.liqian.huarong.base.ResultCallback;
import com.example.liqian.huarong.mvp.m.TrackingDetailsModel;
import com.example.liqian.huarong.mvp.v.TrackingDetailsView;

public class TrackingDetailsPresenter extends BaseMvpPresenter<TrackingDetailsView> {
    private TrackingDetailsModel model;
    @Override
    protected void initModel() {
        model = new TrackingDetailsModel();
        addModel(model);
    }

    public void tracking(String string){
        model.details(string, new ResultCallback<String>() {
            @Override
            public void onSuccess(String data) {
                if (view!=null){
                    view.details(data);
                }
            }

            @Override
            public void onFail(String msg) {

            }
        });
    }


    public void history(String json){
        model.historylist(json,new ResultCallback<String>() {
            @Override
            public void onSuccess(String data) {
                if (view!=null){
                    view.list(data);
                }
            }

            @Override
            public void onFail(String msg) {

            }
        });
    }
}
