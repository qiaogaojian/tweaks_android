package com.etatech.test.view;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.widget.Toast;

import com.etatech.test.R;
import com.etatech.test.databinding.ActivityTestMvpBinding;
import com.etatech.test.interf.ITestMvpView;
import com.etatech.test.utils.BaseActivity;
import com.etatech.test.vm.TestMvpVM;
import com.jakewharton.rxbinding.view.RxView;
import com.trello.rxlifecycle.android.ActivityEvent;

import java.util.concurrent.TimeUnit;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

public class TestMvpActivity extends BaseActivity<ActivityTestMvpBinding> implements ITestMvpView {
    private TestMvpVM vm;

    @Override
    public ActivityTestMvpBinding onCreateView(Bundle savedInstanceState) {
        return DataBindingUtil.setContentView(this, R.layout.activity_test_mvp);
    }

    @Override
    public void init() {
        vm = new TestMvpVM(this, binding);

        binding.tvContent.setText("Click Btn to Get Content");
    }

    @Override
    public TestMvpActivity getActivity() {
        return this;
    }

    @Override
    public Context getContext() {
        return this.getContext();
    }
}
