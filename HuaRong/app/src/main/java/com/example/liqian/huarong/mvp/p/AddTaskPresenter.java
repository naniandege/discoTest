package com.example.liqian.huarong.mvp.p;


import com.example.liqian.huarong.base.BaseMvpPresenter;
import com.example.liqian.huarong.base.ResultCallback;
import com.example.liqian.huarong.mvp.m.AddTaskModel;
import com.example.liqian.huarong.mvp.v.AddTaskView;

public class AddTaskPresenter extends BaseMvpPresenter<AddTaskView> {

    private AddTaskModel model;

    @Override
    protected void initModel() {
        model = new AddTaskModel();
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

    public void getFineData(String loginJson) {
        model.addTaskData(loginJson, new ResultCallback<String>() {
            @Override
            public void onSuccess(String data) {
                if (view != null) {
                    view.getFineData(data);
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
}
