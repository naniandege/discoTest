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

public class CardAuditModel extends BaseMvpModel {
    private static final String TAG = "TaskDetailModel";

    public void data(String json, final ResultCallback<String> resultCallback) {
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
                        Log.d(TAG, "onNext: " + s);
                        resultCallback.onSuccess(s);
                    }

                    @Override
                    public void error(String msg) {
                        resultCallback.onFail(msg);
                    }

                    @Override
                    protected void subscribe(Disposable d) {
                        addDisposable(d);
                    }
                });
    }


    public void getData(String json, final ResultCallback<String> resultCallback) {
        int length = json.length();
        MediaType mediaType = MediaType.parse("text/x-markdown; charset=utf-8");
        RequestBody requestBody = RequestBody.create(mediaType, json);

        ApiService apiserver = HttpUtils.getInstance().getApiserver(
                //每次改baseUrl
                ApiService.serverAddr, ApiService.class);
        apiserver.bllJson(length, requestBody)
                .compose(RxUtils.<String>rxObserableSchedulerHelper())
                .subscribe(new BaseObserver<String>() {
                    @Override
                    public void onNext(String s) {
                        Log.d(TAG, "onNext: " + s);
                        resultCallback.onSuccess(s);
                    }

                    @Override
                    public void error(String msg) {
                        resultCallback.onFail(msg);
                    }

                    @Override
                    protected void subscribe(Disposable d) {
                        addDisposable(d);
                    }
                });
    }



    public void insertData(String json, final ResultCallback<String> resultCallback) {
        int length = json.length();
        MediaType mediaType = MediaType.parse("text/x-markdown; charset=utf-8");
        RequestBody requestBody = RequestBody.create(mediaType, json);

        ApiService apiserver = HttpUtils.getInstance().getApiserver(
                //每次改baseUrl
                ApiService.clientAddr, ApiService.class);
        apiserver.insertData(length, requestBody)
                .compose(RxUtils.<String>rxObserableSchedulerHelper())
                .subscribe(new BaseObserver<String>() {
                    @Override
                    public void onNext(String s) {
                        Log.d(TAG, "onNext: " + s);
                        resultCallback.onSuccess(s);
                    }

                    @Override
                    public void error(String msg) {
                        resultCallback.onFail(msg);
                    }

                    @Override
                    protected void subscribe(Disposable d) {
                        addDisposable(d);
                    }
                });
    }



    public void getSmsData(String json, final ResultCallback<String> resultCallback) {
        int length = json.length();
        MediaType mediaType = MediaType.parse("text/x-markdown; charset=utf-8");
        RequestBody requestBody = RequestBody.create(mediaType, json);

        ApiService apiserver = HttpUtils.getInstance().getApiserver(
                //每次改baseUrlice
                ApiService.clientAddr, ApiService.class);
        apiserver.select(length, requestBody)
                .compose(RxUtils.<String>rxObserableSchedulerHelper())
                .subscribe(new BaseObserver<String>() {
                    @Override
                    public void onNext(String s) {
                        Log.d(TAG, "onNext: " + s);
                        resultCallback.onSuccess(s);
                    }

                    @Override
                    public void error(String msg) {
                        resultCallback.onFail(msg);
                    }

                    @Override
                    protected void subscribe(Disposable d) {
                        addDisposable(d);
                    }
                });
    }

    public void wfTaskSms(String json, final ResultCallback<String> resultCallback) {
        int length = json.length();
        MediaType mediaType = MediaType.parse("text/x-markdown; charset=utf-8");
        RequestBody requestBody = RequestBody.create(mediaType, json);

        ApiService apiserver = HttpUtils.getInstance().getApiserver(
                //每次改baseUrl
                ApiService.serverAddr, ApiService.class);
        apiserver.wfTask(length, requestBody)
                .compose(RxUtils.<String>rxObserableSchedulerHelper())
                .subscribe(new BaseObserver<String>() {
                    @Override
                    public void onNext(String s) {
                        Log.d(TAG, "onNext: " + s);
                        resultCallback.onSuccess(s);
                    }

                    @Override
                    public void error(String msg) {
                        resultCallback.onFail(msg);
                    }

                    @Override
                    protected void subscribe(Disposable d) {
                        addDisposable(d);
                    }
                });
    }
}
