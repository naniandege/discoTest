package com.example.liqian.huarong.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.example.liqian.huarong.R;
import com.example.liqian.huarong.base.BaseActivity;
import com.example.liqian.huarong.mvp.p.EmptyPresenter;
import com.example.liqian.huarong.mvp.v.EmptyView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddActivity extends BaseActivity<EmptyView, EmptyPresenter> implements EmptyView {


    @BindView(R.id.toolar)
    Toolbar toolar;
    @BindView(R.id.Qj)
    Button Qj;
    @BindView(R.id.Cl)
    Button Cl;
    @BindView(R.id.JF)
    Button JF;

    @Override
    protected EmptyPresenter initPresenter() {
        return new EmptyPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add;
    }


    @OnClick({R.id.Qj, R.id.Cl, R.id.JF})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.Qj:
                startActivity(new Intent(this, AskforleaveActivity.class));
                break;
            case R.id.Cl:
//                startActivity(new Intent(this, AddTravelActivity.class));
                break;
            case R.id.JF:
                startActivity(new Intent(this, AddIntegralActivity.class));
                break;
        }
    }

    @Override
    protected void initView() {
        super.initView();
        setSupportActionBar(toolar);
    }
}
