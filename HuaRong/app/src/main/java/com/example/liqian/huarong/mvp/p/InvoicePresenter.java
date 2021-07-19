package com.example.liqian.huarong.mvp.p;


import com.example.liqian.huarong.base.BaseMvpPresenter;
import com.example.liqian.huarong.base.ResultCallback;
import com.example.liqian.huarong.mvp.m.InvoiceModel;
import com.example.liqian.huarong.mvp.v.InvoiceView;

public class InvoicePresenter extends BaseMvpPresenter<InvoiceView> {

    private InvoiceModel invoiceModel;

    @Override
    protected void initModel() {
        invoiceModel = new InvoiceModel();
        addModel(invoiceModel);
    }

    public void invoiceData(String string){
        invoiceModel.getData(string, new ResultCallback<String>() {
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

    public void binDing(String string){
        invoiceModel.binDingData(string, new ResultCallback<String>() {
            @Override
            public void onSuccess(String data) {
                if (view!=null){
                    view.binDing(data);
                }
            }

            @Override
            public void onFail(String msg) {

            }
        });
    }
}
