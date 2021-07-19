package com.example.liqian.huarong.mvp.p;


import com.example.liqian.huarong.base.BaseMvpPresenter;
import com.example.liqian.huarong.base.ResultCallback;
import com.example.liqian.huarong.mvp.m.ReadingModel;
import com.example.liqian.huarong.mvp.v.ReadingView;

public class ReadingPresenter extends BaseMvpPresenter<ReadingView> {
    private ReadingModel readingModel;
    @Override
    protected void initModel() {
        readingModel = new ReadingModel();
        addModel(readingModel);
    }

    public void readingData(String string){
        readingModel.getData(string, new ResultCallback<String>() {
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
}
