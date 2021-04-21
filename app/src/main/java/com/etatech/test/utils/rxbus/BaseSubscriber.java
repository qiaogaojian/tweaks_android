package com.etatech.test.utils.rxbus;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public abstract class BaseSubscriber<T> implements Subscriber<T> {
    @Override
    public void onSubscribe(Subscription s) {

    }

    @Override
    public abstract void onNext(T t);

    @Override
    public void onError(Throwable t) {

    }

    @Override
    public void onComplete() {

    }
}
