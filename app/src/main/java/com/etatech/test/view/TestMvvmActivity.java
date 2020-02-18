package com.etatech.test.view;

import android.app.Activity;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.etatech.test.R;
import com.etatech.test.databinding.ActivityTestMvvmBinding;
import com.etatech.test.interf.ITestMvvmView;
import com.etatech.test.utils.BaseActivity;
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
