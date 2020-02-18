package com.etatech.test.utils;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import com.blankj.utilcode.util.ToastUtils;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by Michael
 * Date:  2020/2/17
 * Func:
 */
public class BaseModel<T> extends ViewModel implements LifecycleOwner {

    private LifecycleRegistry lifecycleRegistry = new LifecycleRegistry(this);
    protected MutableLiveData<T> liveObservableData = new MutableLiveData<>();
    protected Subscription subscription;

    protected void requestData(Observable<T> observable) {
        subscription = observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<T>() {
                    @Override
                    public void call(T t) {
                        if (null != t) {
                            if (null != liveObservableData) {
                                liveObservableData.postValue(t);
                            }
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        ToastUtils.showShort("获取网络失败，请检查您的网络连接");
                    }
                });
    }

    public LiveData<T> getLiveData() {
        return liveObservableData;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (null != subscription && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
        liveObservableData = null;
    }

    @NonNull
    @Override
    public Lifecycle getLifecycle() {
        return lifecycleRegistry;
    }
}
