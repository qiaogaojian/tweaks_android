package com.etatech.test.utils;


import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

/**
 * Created by Michael
 * Date:  2020/3/16
 * Func: RxJava 实现事件总线（EventBus，OttO）
 */
public class RxBus {
    private final Subject subject;

    public RxBus() {
        subject = PublishSubject.create().toSerialized();
    }

    public Throwable e;

    public static RxBus get() {
        return RxBus.RxBusHolder.IMPL;
    }

    private static class RxBusHolder {
        private final static RxBus IMPL = new RxBus();
    }

    /**
     * Double Checked locking单例
     * 保证多线程安全
     *
     * @return
     */
    public static RxBus getRxBus() {
        return RxBus.RxBusHolder.IMPL;
    }

    /**
     * 发送事件
     *
     * @param o
     */
    public void post(Object o) {
        subject.onNext(o);
    }

    /**
     * 接收事件
     * ofType是filter（过滤）+cast（类型判断）
     *
     * @param event
     * @param <T>
     * @return
     */
    public <T> Observable<T> toObservable(Class<T> event) {
        return subject.ofType(event);
    }
}
