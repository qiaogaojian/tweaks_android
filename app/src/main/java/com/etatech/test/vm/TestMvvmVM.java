package com.etatech.test.vm;

import androidx.lifecycle.Lifecycle;
import android.content.Context;

import com.blankj.utilcode.util.LogUtils;
import com.etatech.test.interf.ITestMvvmVM;
import com.etatech.test.interf.ITestMvvmView;
import com.etatech.test.model.TestMvvmModel;
import com.etatech.test.utils.Tools;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Michael
 * Date:  2020/2/18
 * Func:
 */
public class TestMvvmVM implements ITestMvvmVM {
    public ITestMvvmView mainView;
    private TestMvvmModel priceModel;
    private Subscription subscription;


    public TestMvvmVM(ITestMvvmView mainView) {
        this.mainView = mainView;

        priceModel = Tools.getViewModel(mainView.getActivity(), TestMvvmModel.class);

        requestData();
    }

    private void requestData() {
        // 设置延迟为0 保证网络请求及时发送
        subscription = Observable.interval(0, 10, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .repeat()
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onNext(Long value) {
                        LogUtils.e(value);
                        priceModel.getBtcPrice();
                    }

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                    }
                });
    }

    @Override
    public Context getContext() {
        return mainView.getContext();
    }

    @Override
    public void onDestroy() {
        // 取消注册 避免无限循环
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
            LogUtils.e("destroy subscription");
        }
    }
}
