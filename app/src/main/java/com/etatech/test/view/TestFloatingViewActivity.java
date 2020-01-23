package com.etatech.test.view;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.etatech.test.R;
import com.etatech.test.databinding.ActivityTestFloatViewBinding;
import com.etatech.test.utils.BaseActivity;
import com.etatech.test.utils.ui.ClickUtil;

import rx.functions.Action1;

public class TestFloatingViewActivity extends BaseActivity<ActivityTestFloatViewBinding> {
    private             TextView mOpenFloat;
    public static final int      REQUEST_CODE = 114;

    @Override
    public ActivityTestFloatViewBinding onCreateView(Bundle savedInstanceState) {
        return DataBindingUtil.setContentView(this, R.layout.activity_test_float_view);
    }

    @Override
    public void init() {

        ClickUtil.setOnClick(binding.btnOpenFloat, new Action1() {
            @Override
            public void call(Object o) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (!Settings.canDrawOverlays(getApplicationContext())) {
                        //启动Activity让用户授权
                        Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
                        intent.setData(Uri.parse("package:" + getPackageName()));
                        startActivityForResult(intent, 100);
                    } else {
                        TestFloat testFloat = new TestFloat(TestFloatingViewActivity.this);
                        testFloat.show();
                    }
                } else {
                    TestFloat testFloat = new TestFloat(TestFloatingViewActivity.this);
                    testFloat.show();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (Settings.canDrawOverlays(this)) {
                    TestFloat testFloat = new TestFloat(TestFloatingViewActivity.this);
                    testFloat.show();
                } else {
                    Toast.makeText(this, "ACTION_MANAGE_OVERLAY_PERMISSION权限已被拒绝", Toast.LENGTH_SHORT).show();

                }
            }

        }
    }
}
