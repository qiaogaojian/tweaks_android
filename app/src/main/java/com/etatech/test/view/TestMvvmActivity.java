package com.etatech.test.view;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.etatech.test.R;
import com.etatech.test.bean.BtcPriceBean;
import com.etatech.test.databinding.ActivityTestMvvmBinding;
import com.etatech.test.interf.ITestMvvmView;
import com.etatech.test.model.TestMvvmModel;
import com.etatech.test.utils.BaseActivity;
import com.etatech.test.utils.Tools;
import com.etatech.test.vm.TestMvvmVM;

public class TestMvvmActivity extends BaseActivity<ActivityTestMvvmBinding> implements ITestMvvmView {
    private TestMvvmVM vm;

    @Override
    public ActivityTestMvvmBinding onCreateView(Bundle savedInstanceState) {
        return DataBindingUtil.setContentView(this, R.layout.activity_test_mvvm);
    }

    @Override
    public void init() {
        vm = new TestMvvmVM(this);
        Tools.getViewModel(this, TestMvvmModel.class).getLiveData().observe(this, new Observer<BtcPriceBean>() {
            @Override
            public void onChanged(@Nullable BtcPriceBean btcPriceBean) {
                binding.tvPrice.setText(String.format("%.2f usdt",(btcPriceBean.getAsk() + btcPriceBean.getBid()) / 2.0));
            }
        });
    }

    @Override
    public TestMvvmActivity getActivity() {
        return this;
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        vm.onDestroy();
    }
}
