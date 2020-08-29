package com.etatech.test.utils.ui;


import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.blankj.utilcode.util.StringUtils;
import com.etatech.test.R;
import com.etatech.test.utils.App;
import com.jakewharton.rxbinding.view.RxView;
import com.trello.rxlifecycle.android.ActivityEvent;
import com.trello.rxlifecycle.android.FragmentEvent;
import com.trello.rxlifecycle.components.RxDialogFragment;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.trello.rxlifecycle.components.support.RxFragment;

import java.util.concurrent.TimeUnit;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * Created by Michael
 * Data: 2020/1/5 16:49
 * Desc: UI点击工具类
 */
public class ClickUtil {
    private static final String TAG = "ClickUtils";

    /**
     * 点击事件
     *
     * @param view
     * @param action
     */
    public static void setOnClick(final View view, final Action1 action) {
        RxView.clicks(view)
                .throttleFirst(1000, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(action, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();
                        setOnClick(view, action);//重新订阅
                        Toast.makeText(App.getInstance(), "操作失败", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    /**
     * 快速点击事件
     *
     * @param view
     * @param action
     */
    public static void setFastClick(final View view, final Action1 action) {
        RxView.clicks(view)
                .throttleFirst(100, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(action, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();
                        setFastClick(view, action);//重新订阅
                        Toast.makeText(App.getInstance(), "操作失败", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    /**
     * 点击事件
     *
     * @param view
     * @param activity
     * @param action
     */
    public static void setOnClick(final View view, final RxAppCompatActivity activity, final Action1 action) {
        RxView.clicks(view)
                .compose(activity.bindUntilEvent(ActivityEvent.DESTROY))
                .throttleFirst(1000, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(action, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();
                        setOnClick(view, activity, action);//重新订阅
                        Toast.makeText(App.getInstance(), "操作失败", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public static void setOnClick(final View view, final RxDialogFragment activity, final Action1 action) {
        RxView.clicks(view)
                .compose(activity.bindUntilEvent(FragmentEvent.DESTROY_VIEW))
                .throttleFirst(1000, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(action, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();
                        setOnClick(view, activity, action);//重新订阅
                        Toast.makeText(App.getInstance(), "操作失败", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public static void setOnClick(final int time, final View view, final RxAppCompatActivity activity, final Action1 action) {
        RxView.clicks(view)
                .compose(activity.bindUntilEvent(ActivityEvent.DESTROY))
                .throttleFirst(time, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(action, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();
                        setOnClick(time, view, activity, action);//重新订阅
                        Toast.makeText(App.getInstance(), "操作失败", Toast.LENGTH_SHORT).show();
                    }
                });
    }


    public static void setOnClick(final View view, final com.trello.rxlifecycle.components.support.RxDialogFragment activity, final Action1 action) {
        RxView.clicks(view)
                .compose(activity.bindUntilEvent(FragmentEvent.DESTROY_VIEW))
                .throttleFirst(1000, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(action, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();
                        setOnClick(view, activity, action);//重新订阅
                        Toast.makeText(App.getInstance(), "操作失败", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    /**
     * 长按点击事件
     *
     * @param view
     * @param action
     */
    public static void setOnLongClick(final View view, final Action1 action) {
        RxView.longClicks(view)
                .throttleFirst(1000, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(action, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();
                        setOnLongClick(view, action);//重新订阅
                        Toast.makeText(App.getInstance(), "操作失败", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public static void setOnLongClick(final View view, final RxAppCompatActivity activity, final Action1 action) {
        RxView.longClicks(view)
                .compose(activity.bindUntilEvent(ActivityEvent.DESTROY))
                .throttleFirst(1000, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(action, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();
                        setOnClick(view, activity, action);//重新订阅
                        Toast.makeText(App.getInstance(), "操作失败", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    /**
     * 触摸时间事件
     *
     * @param view
     * @param activity
     * @param action
     */
    public static void setOntouches(final View view, final RxAppCompatActivity activity, final Action1 action) {
        RxView.touches(view)
                .compose(activity.<MotionEvent>bindUntilEvent(ActivityEvent.DESTROY))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(action, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();
                        setOntouches(view, activity, action);//重新订阅
                        Toast.makeText(App.getInstance(), "操作失败", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    /**
     * RxAppCompatActivity 点击事件
     *
     * @param view
     * @param activity
     * @param action
     */
    public static Subscription setOnClicks(View view, RxAppCompatActivity activity, Action1 action) {
        return RxView.clicks(view)
                .compose(activity.bindUntilEvent(ActivityEvent.DESTROY))
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(action, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();
                        Toast.makeText(App.getInstance(), "操作失败", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public static void setOnClick(View view, RxFragment rxFragment, Action1 action) {
        RxView.clicks(view)
                .compose(rxFragment.bindUntilEvent(FragmentEvent.DESTROY_VIEW))
                .throttleFirst(1000, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(action, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Toast.makeText(App.getInstance(), "操作失败", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
