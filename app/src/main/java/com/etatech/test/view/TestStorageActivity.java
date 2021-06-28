package com.etatech.test.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.etatech.test.R;
import com.etatech.test.databinding.ActivityTestStorageBinding;
import com.etatech.test.utils.BaseActivity;
import com.etatech.test.utils.StorageUtil;

public class TestStorageActivity extends BaseActivity<ActivityTestStorageBinding> {

    @Override
    public ActivityTestStorageBinding onCreateView(Bundle savedInstanceState) {
        return DataBindingUtil.setContentView(this, R.layout.activity_test_storage);
    }

    @Override
    public void init() {
        String log = StorageUtil.getSDCardStorageLog();
        binding.tvLog.setText(log);
    }
}