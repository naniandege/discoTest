package com.example.liqian.huarong.base;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class BaseMvpModel {

    public CompositeDisposable compositeDisposable;

    public void onDestory() {
        //切换所有的Disposable对象
        if (compositeDisposable != null)                               //有些情况可能对象是空的  所以if一下
            compositeDisposable.clear();       //rxjava中的subscribe      也可以说是打断线程
    }

    public void addDisposable(Disposable disposable) {
        if (compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();         // disposable  加入集合   最后方便清除
        }
        compositeDisposable.add( disposable );

    }

}
