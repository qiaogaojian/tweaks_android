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
import com.etatech.test.utils.rxbus.Action1;

import org.reactivestreams.Subscription;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;


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
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<T>() {
                    @Override
                    public void accept(T t) {
                        if (null != t) {
                            if (null != liveObservableData) {
                                liveObservableData.postValue(t);
                            }
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
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

        liveObservableData = null;
    }

    @NonNull
    @Override
    public Lifecycle getLifecycle() {
        return lifecycleRegistry;
    }
}
