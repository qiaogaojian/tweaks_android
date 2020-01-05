package com.etatech.test.vm;

import android.content.Context;

import com.etatech.test.bean.PhoneAreaBean;
import com.etatech.test.databinding.ActivityTestMvpBinding;
import com.etatech.test.interf.ITestMvpVM;
import com.etatech.test.interf.ITestMvpView;
import com.etatech.test.network.NetworkManager;
import com.etatech.test.utils.ClickUtil;
import com.google.gson.Gson;
import com.trello.rxlifecycle.android.ActivityEvent;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class TestMvpVM implements ITestMvpVM {
    private ITestMvpView           view;
    private ActivityTestMvpBinding binding;
    private Gson                   gson;

    public TestMvpVM(ITestMvpView view, ActivityTestMvpBinding binding) {
        this.view = view;
        this.binding = binding;
        gson = new Gson();
        init();
    }

    public void getData() {
        NetworkManager.getInstance()
                .getNetApi()
                .getPhoneArea()
                .compose(view.getActivity().<PhoneAreaBean>bindUntilEvent(ActivityEvent.DESTROY))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<PhoneAreaBean>() {
                    @Override
                    public void call(PhoneAreaBean ipInfoBean) {
                        String content = gson.toJson(ipInfoBean).toString();
                        binding.tvContent.setText(content);
                    }
                });
    }

    private void init() {
        ClickUtil.setOnClick(binding.btnGetContent, new Action1() {
            @Override
            public void call(Object o) {
                getData();
            }
        });
    }

    @Override
    public Context getContext() {
        return view.getContext();
    }

    @Override
    public void onDestroy() {

    }
}
