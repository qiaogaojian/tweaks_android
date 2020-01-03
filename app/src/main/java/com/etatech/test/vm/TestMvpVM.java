package com.etatech.test.vm;

import android.content.Context;

import com.blankj.utilcode.util.NetworkUtils;
import com.etatech.test.bean.IpInfoBean;
import com.etatech.test.databinding.ActivityTestMvpBinding;
import com.etatech.test.interf.ITestMvpVM;
import com.etatech.test.interf.ITestMvpView;
import com.etatech.test.network.NetworkManager;
import com.google.gson.Gson;
import com.trello.rxlifecycle.android.ActivityEvent;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class TestMvpVM implements ITestMvpVM
{
    private ITestMvpView           view;
    private ActivityTestMvpBinding binding;

    public TestMvpVM(ITestMvpView view, ActivityTestMvpBinding binding) {
        this.view = view;
        this.binding = binding;

        getData();
        initClick();
    }

    private void getData() {
        NetworkManager.getInstance()
                      .getAliNetApi()
                      .getIpInfo(NetworkUtils.getIPAddress(true))
                      .compose(view.getActivity().<IpInfoBean>bindUntilEvent(ActivityEvent.DESTROY))
                      .subscribeOn(Schedulers.io())
                      .observeOn(AndroidSchedulers.mainThread())
                      .subscribe(new Action1<IpInfoBean>()
                        {
                            @Override
                            public void call(IpInfoBean ipInfoBean) {
                                binding.tvContent.setText(new Gson().toJson(ipInfoBean));
                            }
                        });
    }

    private void initClick() {

    }

    @Override
    public Context getContext() {
        return view.getContext();
    }

    @Override
    public void onDestroy() {

    }
}
