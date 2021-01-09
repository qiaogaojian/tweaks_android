package com.etatech.test.view;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.etatech.test.R;
import com.etatech.test.databinding.ActivityTestPrintStackBinding;
import com.etatech.test.utils.BaseActivity;
import com.etatech.test.utils.ui.ClickUtil;

import rx.functions.Action1;

public class TestPrintStackActivity extends BaseActivity<ActivityTestPrintStackBinding> {

    @Override
    public ActivityTestPrintStackBinding onCreateView(Bundle savedInstanceState) {
        return DataBindingUtil.setContentView(TestPrintStackActivity.this, R.layout.activity_test_print_stack);
    }

    @Override
    public void init() {
        ClickUtil.setOnClick(binding.btnTestPrintStack, new Action1() {
            @Override
            public void call(Object o) {
                // 第一种打印调用堆栈方式
                // Exception e = new Exception("this is a log");
                // e.printStackTrace();
                // 第二种方法 这种方法可以很方便的写入文件
                // Log.e("this is a log",Log.getStackTraceString(new Throwable()));
                binding.tvLog.setText(Log.getStackTraceString(new Throwable()));
            }
        });
    }
}