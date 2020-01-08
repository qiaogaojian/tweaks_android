package com.etatech.test.vm;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;

import com.blankj.utilcode.util.GsonUtils;
import com.etatech.test.adapter.CountryCodeAdapter;
import com.etatech.test.bean.PhoneAreaBean;
import com.etatech.test.databinding.ActivityTestMvpBinding;
import com.etatech.test.interf.ITestMvpVM;
import com.etatech.test.interf.ITestMvpView;
import com.etatech.test.network.NetworkManager;
import com.etatech.test.utils.ClickUtil;
import com.trello.rxlifecycle.android.ActivityEvent;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class TestMvpVM implements ITestMvpVM {
    private ITestMvpView           view;
    private ActivityTestMvpBinding binding;
    private CountryCodeAdapter     countryCodeAdapter;

    public TestMvpVM(ITestMvpView view, ActivityTestMvpBinding binding) {
        this.view = view;
        this.binding = binding;
        init();
    }

    public void getData() {
        NetworkManager.getInstance()
                .getNetApi("http://jpwerewolf.53site.com/WerewolfJP/PHPApi/beta/v/")
                .getPhoneArea()
                .compose(view.getActivity().<PhoneAreaBean>bindUntilEvent(ActivityEvent.DESTROY))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<PhoneAreaBean>() {
                    @Override
                    public void call(PhoneAreaBean bean) {
                        countryCodeAdapter = new CountryCodeAdapter(bean.getData());
                        binding.listCountryCode.setAdapter(countryCodeAdapter);
                        binding.listCountryCode.setLayoutManager(new GridLayoutManager(view.getContext(),2));
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
