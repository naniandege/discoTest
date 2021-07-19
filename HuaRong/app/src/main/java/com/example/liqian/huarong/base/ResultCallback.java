
package com.example.liqian.huarong.base;
public interface ResultCallback<T> {
    void onSuccess(T data);
    void onFail(String msg);
}
