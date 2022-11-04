package com.etatech.test.utils.rxbus;

import com.etatech.test.utils.BaseActivity;
import com.trello.rxlifecycle4.android.ActivityEvent;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.PublishSubject;
import io.reactivex.rxjava3.subjects.Subject;


/**
 * RxJava 实现事件总线（EventBus，OttO）
 * Created by sdbean-zlh on 16/6/23.
 */
public class RxBus {
    //    private static volatile RxBus staticRxBus;
    private final Subject subject;
    private final Map<Class<?>, Object> mStickyEventMap;

    public RxBus() {
        mStickyEventMap = new ConcurrentHashMap<>();
        subject = PublishSubject.create().toSerialized();
    }

    public Throwable e;


    /**
     * Double Checked locking单例
     * 保证多线程安全
     *
     * @return
     */
    public static RxBus getRxBus() {
        //        RxBus rxBus = staticRxBus;
        //        if (rxBus == null) {
        //            synchronized (RxBus.class) {
        //                rxBus = staticRxBus;
        //                if (rxBus == null) {
        //                    rxBus = new RxBus();
        //                    staticRxBus = rxBus;
        //                }
        //            }
        //        }
        return RxBus.RxBusHolder.IMPL;
    }

    private static class RxBusHolder {
        private static final RxBus IMPL = new RxBus();
    }


    /**
     * 发送事件
     *
     * @param o
     */
    public void post(Object o) {
        //        subject.req
        //        subject.onBackpressureBuffer();
        subject.onNext(o);

    }

    /**
     * 发送一个粘性事件，事件默认消费dieAfterExecuteCount次后消亡
     *
     * @param o 事件类型
     */
    public void postSticky(Object o) {


        synchronized (mStickyEventMap) {
            mStickyEventMap.put(o.getClass(), o);
        }
        post(o);
    }

    /**
     * 根据传递的 eventType 类型返回特定类型(eventType)的 被观察者
     */
    public <T> Observable<T> toObservableSticky(final Class<T> eventType) {
        synchronized (mStickyEventMap) {
            Observable<T> observable = subject.ofType(eventType);
            final Object event = mStickyEventMap.get(eventType);

            if (event != null) {
                return observable.mergeWith(Observable.just(eventType.cast(event)));
            } else {
                return observable;
            }
        }
    }

    /**
     * 根据eventType获取Sticky事件
     */
    public <T> T getStickyEvent(Class<T> eventType) {
        synchronized (mStickyEventMap) {
            return eventType.cast(mStickyEventMap.get(eventType));
        }
    }

    /**
     * 移除指定eventType的Sticky事件
     */
    public <T> T removeStickyEvent(Class<T> eventType) {
        synchronized (mStickyEventMap) {
            return eventType.cast(mStickyEventMap.remove(eventType));
        }
    }

    /**
     * 移除所有的Sticky事件
     */
    public void removeAllStickyEvents() {
        synchronized (mStickyEventMap) {
            mStickyEventMap.clear();
        }
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


    /**
     * 接收事件
     * ofType是filter（过滤）+cast（类型判断）
     *
     * @param event
     * @param <T>
     * @return
     */
    public <T> void observableEventForActivity(Class<T> event, BaseActivity baseRxAcivity, Action1 action1) {
        RxBus.getRxBus().toObservable(event)
                .compose(baseRxAcivity.bindUntilEvent(ActivityEvent.DESTROY))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(action1);
    }

}
