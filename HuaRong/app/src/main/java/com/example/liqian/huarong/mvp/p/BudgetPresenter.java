package com.example.liqian.huarong.mvp.p;


import com.example.liqian.huarong.base.BaseMvpPresenter;
import com.example.liqian.huarong.base.ResultCallback;
import com.example.liqian.huarong.bean.EntityOA;
import com.example.liqian.huarong.mvp.m.BudgetlModel;
import com.example.liqian.huarong.mvp.m.TaskDetailModel;
import com.example.liqian.huarong.mvp.v.BudgetView;

public class BudgetPresenter extends BaseMvpPresenter<BudgetView> {

    private BudgetlModel model;

    @Override
    protected void initModel() {
        model = new BudgetlModel();
        addModel(model);
    }

    public void taskDetail(String json) {
        model.data(json, new ResultCallback<String>() {
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


    public void oaList(String json) {
        model.oaList(json, new ResultCallback<String>() {
            @Override
            public void onSuccess(String data) {
                if (view != null) {
                    view.oaListData(data);
                }
            }

            @Override
            public void onFail(String msg) {

            }
        });
    }


    public void attList(String json) {
        model.attList(json, new ResultCallback<String>() {
            @Override
            public void onSuccess(String data) {
                if (view != null) {
                    view.attList(data);
                }
            }

            @Override
            public void onFail(String msg) {

            }
        });
    }


    public void smsStr(String json) {
        model.getStr(json, new ResultCallback<String>() {
            @Override
            public void onSuccess(String data) {
                if (view != null) {
                    view.getStr(data);
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


    public void upDateStr(String json, final EntityOA listBean) {
        model.getData(json, new ResultCallback<String>() {
            @Override
            public void onSuccess(String data) {
                if (view != null) {
                    view.upDateStr(data,listBean);
                }
            }

            @Override
            public void onFail(String msg) {

            }
        });
    }

    public void upDate(String json) {
        model.upData(json, new ResultCallback<String>() {
            @Override
            public void onSuccess(String data) {
                if (view != null) {
                    view.upDate(data);
                }
            }

            @Override
            public void onFail(String msg) {

            }
        });
    }


    public void oaMainStr(String json) {
        model.getData(json, new ResultCallback<String>() {
            @Override
            public void onSuccess(String data) {
                if (view != null) {
                    view.oaMainStr(data);
                }
            }

            @Override
            public void onFail(String msg) {

            }
        });
    }



    public void oaMainData(String json) {
        model.upData(json, new ResultCallback<String>() {
            @Override
            public void onSuccess(String data) {
                if (view != null) {
                    view.oaMainData(data);
                }
            }

            @Override
            public void onFail(String msg) {

            }
        });
    }



    public void MyinitiateStr(String json) {
        model.getData(json, new ResultCallback<String>() {
            @Override
            public void onSuccess(String data) {
                if (view != null) {
                    view.MyinitiateStr(data);
                }
            }

            @Override
            public void onFail(String msg) {

            }
        });
    }



    public void MyinitiateData(String json) {
        model.upData(json, new ResultCallback<String>() {
            @Override
            public void onSuccess(String data) {
                if (view != null) {
                    view.MyinitiateData(data);
                }
            }

            @Override
            public void onFail(String msg) {

            }
        });
    }
}
