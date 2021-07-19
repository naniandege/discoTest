package com.example.liqian.huarong.base;

import android.app.Activity;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.liqian.huarong.R;
import com.example.liqian.huarong.activity.LoginActivity;
import com.example.liqian.huarong.activity.MainActivity;
import com.example.liqian.huarong.utils.ToastUtil;
import com.example.liqian.huarong.widget.LoadingDialog;

import butterknife.ButterKnife;


public abstract class BaseActivity<V extends BaseMvpView, P extends BaseMvpPresenter> extends AppCompatActivity implements BaseMvpView {

    public P presenter;
    private LoadingDialog mLoadingDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(getLayoutId());
        ButterKnife.bind(this);      //activity  不需要解绑
        presenter = initPresenter();
        if (presenter != null) {
            presenter.BindView((V) this);                  //其实 this就可以了
        }
        initView();
        initData();
        initListener();

        Activity activity = BaseActivity.this;
        if (!activity.getClass().equals(LoginActivity.class) && !activity.getClass().equals(MainActivity.class)) {
            callToolbar();
        }
    }

    protected void callToolbar() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        Drawable upArrow = ContextCompat.getDrawable(this, R.drawable.abc_ic_ab_back_material);
        if (upArrow != null) {
            upArrow.setColorFilter(ContextCompat.getColor(this, R.color.c_000000), PorterDuff.Mode.SRC_ATOP);
            if (getSupportActionBar() != null) {
                getSupportActionBar().setHomeAsUpIndicator(upArrow);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    protected void initListener() {

    }


    protected abstract P initPresenter();


    protected void initView() {

    }

    protected void initData() {

    }

    protected abstract int getLayoutId();

    @Override
    public void showLoading() {
        if (mLoadingDialog == null) {
            mLoadingDialog = new LoadingDialog(this);
        }
        mLoadingDialog.show();
    }


    @Override
    public void hideLoading() {
        if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
            mLoadingDialog.dismiss();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //打断关系 p.v
        if (presenter != null)
            presenter.OnDestory();    //销毁   MVP 对象   清空线程
        presenter = null;
    }

    public void showToast(String msg) {
        ToastUtil.showShort(msg);
    }
}
