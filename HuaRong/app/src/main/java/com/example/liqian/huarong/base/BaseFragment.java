package com.example.liqian.huarong.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.liqian.huarong.utils.ToastUtil;
import com.example.liqian.huarong.widget.LoadingDialog;

import butterknife.ButterKnife;
import butterknife.Unbinder;


public abstract class BaseFragment<V extends BaseMvpView, P extends BaseMvpPresenter> extends Fragment implements BaseMvpView {

    public P presenter;
    private Unbinder unbinder;
    private LoadingDialog mLoadingDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View inflate = inflater.inflate(getLayoutId(), container, false);
        //ButterKnife  绑定
        unbinder = ButterKnife.bind(this, inflate);
        presenter = initPresenter();
        if (presenter != null) {
            presenter.BindView((V) this);
        }
        //TODO  initVIew      可以用   ButterKnife  也可以用   Byid
        initView(inflate);
        initData();
        initListener();
        return inflate;
    }

    protected void initListener() {

    }

    protected void initView(View inflate) {

    }

    protected abstract P initPresenter();


    protected void initData() {

    }


    protected abstract int getLayoutId();

    // 网络加载时候动画方法
    @Override
    public void showLoading() {
        if (mLoadingDialog == null) {
            mLoadingDialog = new LoadingDialog(getActivity());
        }
        mLoadingDialog.show();
    }

    //网络加载时候动画完毕方法
    @Override
    public void hideLoading() {
        if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
            mLoadingDialog.dismiss();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //ButterKnife解绑
        unbinder.unbind();
        if (presenter != null)
            presenter.OnDestory();
        presenter = null;
    }
}
