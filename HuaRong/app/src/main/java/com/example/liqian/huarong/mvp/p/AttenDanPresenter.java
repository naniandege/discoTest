package com.example.liqian.huarong.mvp.p;


import com.example.liqian.huarong.base.BaseMvpPresenter;
import com.example.liqian.huarong.base.ResultCallback;
import com.example.liqian.huarong.mvp.m.AttenDanModel;
import com.example.liqian.huarong.mvp.v.AttenDanView;

public class AttenDanPresenter extends BaseMvpPresenter<AttenDanView> {

    private AttenDanModel model;

    @Override
    protected void initModel() {
        model = new AttenDanModel();
        addModel(model);
    }

    public void setPlaceModel(String tudoJson) {
        model.insertData(tudoJson, new ResultCallback<String>() {
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


    public void setPlaceList(String tudoJson) {
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


    //  任务添加


    public void reciPients(String loginJson) {
        model.insertData(loginJson, new ResultCallback<String>() {
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


    public void mainUpdateTask(String loginJson) {
        model.insertData(loginJson, new ResultCallback<String>() {
            @Override
            public void onSuccess(String data) {
                if (view != null) {
                    view.mainUpTaskData(data);
                }
            }

            @Override
            public void onFail(String msg) {

            }
        });
    }

    public void taskAdd(String loginJson) {
        model.insertData(loginJson, new ResultCallback<String>() {
            @Override
            public void onSuccess(String data) {
                if (view != null) {
                    view.taskAdd(data);
                }
            }

            @Override
            public void onFail(String msg) {

            }
        });
    }

    public void getTime(String loginJson) {
        model.getTime(loginJson, new ResultCallback<String>() {
            @Override
            public void onSuccess(String data) {
                if (view != null) {
                    view.getTime(data);
                }
            }

            @Override
            public void onFail(String msg) {

            }
        });
    }




    public void smsStr(String json) {
        model.getData(json, new ResultCallback<String>() {
            @Override
            public void onSuccess(String data) {
                if (view != null) {
                    view.smsStr(data);
                }
            }

            @Override
            public void onFail(String msg) {

            }
        });
    }

    public void smsData(String json) {
        model.getSmsData(json, new ResultCallback<String>() {
            @Override
            public void onSuccess(String data) {
                if (view != null) {
                    view.smsData(data);
                }
            }

            @Override
            public void onFail(String msg) {

            }
        });
    }

    public void wfTaskSms(String json) {
        model.wfTaskSms(json, new ResultCallback<String>() {
            @Override
            public void onSuccess(String data) {
                if (view != null) {
                    view.wfTaskSms(data);
                }
            }

            @Override
            public void onFail(String msg) {

            }
        });
    }
}
