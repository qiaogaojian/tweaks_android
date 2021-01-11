package com.etatech.test.view;

import androidx.databinding.DataBindingUtil;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.blankj.utilcode.util.ToastUtils;
import com.etatech.test.R;
import com.etatech.test.databinding.ActivityTestClickAreaBinding;
import com.etatech.test.utils.BaseActivity;
import com.etatech.test.utils.ui.ClickUtil;

import rx.functions.Action1;

public class TestClickAreaActivity extends BaseActivity<ActivityTestClickAreaBinding> {

    @Override
    public ActivityTestClickAreaBinding onCreateView(Bundle savedInstanceState) {
        return DataBindingUtil.setContentView(TestClickAreaActivity.this, R.layout.activity_test_click_area);
    }

    @Override
    public void init() {
        ClickUtil.setOnClick(binding.testView, new Action1() {
            @Override
            public void call(Object o) {
                ToastUtils.showShort("test click");
            }
        });
    }
}
