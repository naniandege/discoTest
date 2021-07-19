package com.example.liqian.huarong.mvp.p;


import com.example.liqian.huarong.base.BaseMvpPresenter;
import com.example.liqian.huarong.base.ResultCallback;
import com.example.liqian.huarong.mvp.m.PlaceModel;
import com.example.liqian.huarong.mvp.v.PlaceView;

public class PlacePresenter extends BaseMvpPresenter<PlaceView> {

    private PlaceModel placeModel;

    @Override
    protected void initModel() {
        placeModel = new PlaceModel();
        addModel(placeModel);
    }

    public void setPlaceModel(String tudoJson){
        placeModel.getData(tudoJson, new ResultCallback<String>() {
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


    public void setPlaceList(String tudoJson){
        placeModel.getListData(tudoJson, new ResultCallback<String>() {
            @Override
            public void onSuccess(String data) {
                if (view!=null){
                    view.setListData(data);
                }
            }

            @Override
            public void onFail(String msg) {

            }
        });
    }
}
