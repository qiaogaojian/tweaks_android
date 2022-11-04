package com.etatech.test.vm;

import androidx.lifecycle.Lifecycle;

import android.content.Context;

import com.blankj.utilcode.util.LogUtils;
import com.etatech.test.interf.ITestMvvmVM;
import com.etatech.test.interf.ITestMvvmView;
import com.etatech.test.model.TestMvvmModel;
import com.etatech.test.utils.Tools;

import org.reactivestreams.Subscription;

import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.core.*;

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
        Observable.interval(0, 10, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .repeat()
                .subscribe(new Observer<Long>() {

                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull Long value) {
                        LogUtils.e(value);
                        priceModel.getBtcPrice();
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public Context getContext() {
        return mainView.getContext();
    }

    @Override
    public void onDestroy() {
        
    }
}
