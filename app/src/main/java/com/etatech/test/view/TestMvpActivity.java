package com.etatech.test.view;

import android.content.Context;
import androidx.databinding.DataBindingUtil;
import android.os.Bundle;

import com.etatech.test.R;
import com.etatech.test.databinding.ActivityTestMvpBinding;
import com.etatech.test.interf.ITestMvpView;
import com.etatech.test.utils.BaseActivity;
import com.etatech.test.vm.TestMvpVM;

public class TestMvpActivity extends BaseActivity<ActivityTestMvpBinding> implements ITestMvpView {
    private TestMvpVM vm;

    @Override
    public ActivityTestMvpBinding onCreateView(Bundle savedInstanceState) {
        return DataBindingUtil.setContentView(this, R.layout.activity_test_mvp);
    }

    @Override
    public void init() {
        vm = new TestMvpVM(this, binding);
    }

    @Override
    public TestMvpActivity getActivity() {
        return this;
    }

    @Override
    public Context getContext() {
        return TestMvpActivity.this;
    }
}
