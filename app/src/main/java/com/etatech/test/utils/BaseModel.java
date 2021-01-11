package com.etatech.test.utils;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.databinding.ObservableField;
import androidx.annotation.NonNull;

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
