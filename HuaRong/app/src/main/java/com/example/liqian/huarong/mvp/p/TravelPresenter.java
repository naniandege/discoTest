package com.example.liqian.huarong.mvp.p;


import android.util.Log;

import com.example.liqian.huarong.api.ApiService;
import com.example.liqian.huarong.base.BaseMvpPresenter;
import com.example.liqian.huarong.base.ResultCallback;
import com.example.liqian.huarong.mvp.m.AddTaskModel;
import com.example.liqian.huarong.mvp.m.TravelModel;
import com.example.liqian.huarong.mvp.v.AddTaskView;
import com.example.liqian.huarong.mvp.v.TravelView;
import com.example.liqian.huarong.net.BaseObserver;
import com.example.liqian.huarong.net.HttpUtils;
import com.example.liqian.huarong.net.RxUtils;

import io.reactivex.disposables.Disposable;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class TravelPresenter extends BaseMvpPresenter<TravelView> {

    private TravelModel model;

    @Override
    protected void initModel() {
        model = new TravelModel();
        addModel(model);
    }


    public void reciPients(String loginJson) {
        model.addTaskData(loginJson, new ResultCallback<String>() {
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
        model.addTaskData(loginJson, new ResultCallback<String>() {
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
        model.addTaskData(loginJson, new ResultCallback<String>() {
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



    public void getCodeStr(String json) {
        model.getData(json, new ResultCallback<String>() {
            @Override
            public void onSuccess(String data) {
                if (view != null) {
                    view.getCodeStr(data);
                }
            }

            @Override
            public void onFail(String msg) {

            }
        });
    }

    public void getCodeData(String json) {
        model.upsertData(json, new ResultCallback<String>() {
            @Override
            public void onSuccess(String data) {
                if (view != null) {
                    view.getCodeData(data);
                }
            }

            @Override
            public void onFail(String msg) {

            }
        });
    }


    public void getFineStr(String loginJson) {
        model.getData(loginJson, new ResultCallback<String>() {
            @Override
            public void onSuccess(String data) {
                if (view != null) {
                    view.getFineStr(data);
                }
            }

            @Override
            public void onFail(String msg) {

            }
        });
    }

    public void addFineData(String loginJson) {
        model.addTaskData(loginJson, new ResultCallback<String>() {
            @Override
            public void onSuccess(String data) {
                if (view != null) {
                    view.addFineData(data);
                }
            }

            @Override
            public void onFail(String msg) {

            }
        });
    }

    public void getDetailStr(String loginJson) {
        model.getData(loginJson, new ResultCallback<String>() {
            @Override
            public void onSuccess(String data) {
                if (view != null) {
                    view.getDetailStr(data);
                }
            }

            @Override
            public void onFail(String msg) {

            }
        });
    }

    public void addDetailData(String loginJson) {
        model.addTaskData(loginJson, new ResultCallback<String>() {
            @Override
            public void onSuccess(String data) {
                if (view != null) {
                    view.addDetailData(data);
                }
            }

            @Override
            public void onFail(String msg) {

            }
        });
    }

    public void getPersStr(String in_oaPers_add) {
        model.getData(in_oaPers_add, new ResultCallback<String>() {
            @Override
            public void onSuccess(String data) {
                if (view != null) {
                    view.getPersStr(data);
                }
            }

            @Override
            public void onFail(String msg) {

            }
        });
    }


    public void addPersData(String loginJson) {
        model.addTaskData(loginJson, new ResultCallback<String>() {
            @Override
            public void onSuccess(String data) {
                if (view != null) {
                    view.addPersData(data);
                }
            }

            @Override
            public void onFail(String msg) {

            }
        });
    }

    public void uploadData(String json) {
        model.uploadData(json, new ResultCallback<String>() {
            @Override
            public void onSuccess(String data) {
                if (view != null) {
                    view.uploadData(data);
                }
            }

            @Override
            public void onFail(String msg) {

            }
        });
    }



    public void addFile(String json) {
        model.saveData(json, new ResultCallback<String>() {
            @Override
            public void onSuccess(String data) {
                if (view != null) {
                    view.addFile(data);
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
