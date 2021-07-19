package com.example.liqian.huarong.mvp.p;


import com.example.liqian.huarong.base.BaseMvpPresenter;
import com.example.liqian.huarong.base.ResultCallback;
import com.example.liqian.huarong.mvp.m.InboxModel;
import com.example.liqian.huarong.mvp.v.InboxView;

public class InboxPresenter extends BaseMvpPresenter<InboxView> {

    private InboxModel inboxModel;

    @Override
    protected void initModel() {
        inboxModel = new InboxModel();
        addModel(inboxModel);
    }

    public void getData(String json) {
        inboxModel.getData(json, new ResultCallback<String>() {
            @Override
            public void onSuccess(String data) {
                if (view != null){
                    view.getData(data);
                }
            }

            @Override
            public void onFail(String msg) {

            }
        });
    }


    public void emailData(String json) {
        inboxModel.emailData(json, new ResultCallback<String>() {
            @Override
            public void onSuccess(String data) {
                if (view != null){
                    view.emailData(data);
                }
            }

            @Override
            public void onFail(String msg) {

            }
        });
    }



    public void getUpdate(String tudoJson){
        inboxModel.getData(tudoJson, new ResultCallback<String>() {
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
        inboxModel.sendData(tudoJson, new ResultCallback<String>() {
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
