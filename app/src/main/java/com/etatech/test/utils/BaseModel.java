package com.etatech.test.utils;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;

/**
 * Created by Michael
 * Date:  2020/2/17
 * Func:
 */
public class BaseModel<T> extends ViewModel implements LifecycleOwner {

    private LifecycleRegistry lifecycleRegistry = new LifecycleRegistry(this);
    protected MutableLiveData<T> liveObservableData = new MutableLiveData<>();

    @Override
    protected void onCleared() {
        super.onCleared();

    }

    @NonNull
    @Override
    public Lifecycle getLifecycle() {
        return lifecycleRegistry;
    }
}
