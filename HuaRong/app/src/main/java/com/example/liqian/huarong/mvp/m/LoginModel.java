package com.example.liqian.huarong.mvp.m;

import android.util.Log;

import com.example.liqian.huarong.api.ApiService;
import com.example.liqian.huarong.base.BaseMvpModel;
import com.example.liqian.huarong.base.ResultCallback;
import com.example.liqian.huarong.net.BaseObserver;
import com.example.liqian.huarong.net.HttpUtils;
import com.example.liqian.huarong.net.RxUtils;

import io.reactivex.disposables.Disposable;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class LoginModel extends BaseMvpModel {
    private static final String TAG = "LoginModel";

    public void login(String json, final ResultCallback<String> callback) {
        int length = json.length();
        MediaType mediaType = MediaType.parse("text/x-markdown; charset=utf-8");
        RequestBody requestBody = RequestBody.create(mediaType, json);

        ApiService apiserver = HttpUtils.getInstance().getApiserver(
                ApiService.serverAddr, ApiService.class);
        apiserver.login(length, requestBody)
                .compose(RxUtils.<String>rxObserableSchedulerHelper())
                .subscribe(new BaseObserver<String>() {
                    @Override
                    public void onNext(String s) {
                        Log.d(TAG, "onNext: " + s);
                        callback.onSuccess(s);
                    }

                    @Override
                    public void error(String msg) {
                        callback.onFail(msg);
                    }

                    @Override
                    protected void subscribe(Disposable d) {
                        addDisposable(d);
                    }
                });
    }


    public void login1(String json, final ResultCallback<String> callback) {
        int length = json.length();
        MediaType mediaType = MediaType.parse("text/x-markdown; charset=utf-8");
        RequestBody requestBody = RequestBody.create(mediaType, json);

        ApiService apiserver = HttpUtils.getInstance().getApiserver(
                //每次改baseUrl
                ApiService.clientAddr, ApiService.class);
        apiserver.select(length, requestBody)
                .compose(RxUtils.<String>rxObserableSchedulerHelper())
                .subscribe(new BaseObserver<String>() {
                    @Override
                    public void onNext(String s) {
                        callback.onSuccess(s);
                    }

                    @Override
                    public void error(String msg) {
                        callback.onFail(msg);
                    }

                    @Override
                    protected void subscribe(Disposable d) {
                        addDisposable(d);
                    }
                });
    }


    public void login2(String json, final ResultCallback<String> callback) {
        int length = json.length();
        MediaType mediaType = MediaType.parse("text/x-markdown; charset=utf-8");
        RequestBody requestBody = RequestBody.create(mediaType, json);

        ApiService apiserver = HttpUtils.getInstance().getApiserver(
                //每次改baseUrl
                ApiService.clientAddr, ApiService.class);
        apiserver.pageSelect(length, requestBody)
                .compose(RxUtils.<String>rxObserableSchedulerHelper())
                .subscribe(new BaseObserver<String>() {
                    @Override
                    public void onNext(String s) {
                        callback.onSuccess(s);
                    }

                    @Override
                    public void error(String msg) {
                        callback.onFail(msg);
                    }

                    @Override
                    protected void subscribe(Disposable d) {
                        addDisposable(d);
                    }
                });
    }
}
