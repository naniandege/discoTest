package com.example.liqian.huarong.base;

import java.util.ArrayList;

public abstract class BaseMvpPresenter<V extends BaseMvpView> {
    public V view;

    public ArrayList<BaseMvpModel> mModels = new ArrayList<>();        //创建 model层集合  最后方便清空 model曾

    public void BindView(V view) {
        this.view = view;
    }

    public BaseMvpPresenter() {
        initModel();
    }

    protected abstract void initModel();

    public void addModel(BaseMvpModel model){
        mModels.add(model);
    }

    public void OnDestory() {
        view = null;
        if (mModels.size() > 0) {
            for (BaseMvpModel model : mModels) {
                model.onDestory();              // 当调用onDestory  时候清空  线程  model层
            }
            mModels.clear();
        }
    }

}
