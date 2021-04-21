package com.etatech.test.utils.rxbus;


import io.reactivex.functions.Consumer;

public abstract class Action1<T> implements Consumer<T> {
    @Override
    public void accept(T t) {
        call(t);
    }

    public abstract void call(T t);
}
