package com.my.base.net;



import android.util.Log;

import io.reactivex.Observer;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;


//因为只用一个方法所以用一个抽象方法来占位

public abstract class BaseObserver implements Observer<String> {
    @Override
    public void onSubscribe(@NonNull Disposable d) {

    }
    @Override
    public void onNext(@NonNull String s) {
        onSuccess(s);
    }

    @Override
    public void onError(@NonNull Throwable e) {
        Log.d("NetError",e.toString());
    }

    @Override
    public void onComplete() {

    }
    public abstract void onSuccess(String result);
}
