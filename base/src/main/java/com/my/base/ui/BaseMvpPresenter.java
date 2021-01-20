package com.my.base.ui;



public class BaseMvpPresenter<T> {
    private T mView;

    public void attach(T mView) {
        this.mView = mView;
    }

    public void dettach() {
        mView = null;
    }
}
