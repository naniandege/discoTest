package com.example.liqian.huarong.mvp.p;


import com.example.liqian.huarong.base.BaseMvpPresenter;
import com.example.liqian.huarong.base.ResultCallback;
import com.example.liqian.huarong.mvp.m.TodoModel;
import com.example.liqian.huarong.mvp.v.ToduView;

public class ToduPresenter extends BaseMvpPresenter<ToduView> {

    private TodoModel todoModel;

    @Override
    protected void initModel() {
        todoModel = new TodoModel();
        addModel(todoModel);
    }

    public void todu(String tudoJson){
        todoModel.todu(tudoJson, new ResultCallback<String>() {
            @Override
            public void onSuccess(String data) {
                if (view!=null){
                    view.setTudu(data);
                }
            }

            @Override
            public void onFail(String msg) {

            }
        });
    }
}
