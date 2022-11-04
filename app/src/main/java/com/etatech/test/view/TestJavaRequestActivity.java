package com.etatech.test.view;

import androidx.databinding.DataBindingUtil;
import android.os.Handler;
import android.os.Message;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.etatech.test.R;
import com.etatech.test.databinding.ActivityTestJavaRequestBinding;
import com.etatech.test.utils.BaseActivity;
import com.etatech.test.utils.Tools;
import com.etatech.test.utils.ui.ClickUtil;

import com.etatech.test.utils.rxbus.Action1;

public class TestJavaRequestActivity extends BaseActivity<ActivityTestJavaRequestBinding> {
    private final int JavaRequest = 101;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {

            }
        }
    };

    @Override
    public ActivityTestJavaRequestBinding onCreateView(Bundle savedInstanceState) {
        return DataBindingUtil.setContentView(this, R.layout.activity_test_java_request);
    }

    @Override
    public void init() {
        ClickUtil.setOnClick(binding.btnRequest, new Action1() {
            @Override
            public void accept(Object o) {
                // Android 4.0 之后不能在主线程中请求HTTP请求
                new Thread(new Runnable(){
                    @Override
                    public void run() {
                        String responese = Tools.sendGet("https://api.gemini.com/v1/pubticker/btcusd", "");
                        binding.tvResponese.setText(responese);
                    }
                }).start();
            }
        });
    }

    private void sendMessage(int what, Object object) {
        if (null != mHandler) {
            Message msg = mHandler.obtainMessage();
            msg.what = what;
            msg.obj = object;
            mHandler.sendMessage(msg);
        }
    }
}
