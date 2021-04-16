package com.etatech.test.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.etatech.test.R;
import com.etatech.test.databinding.ActivityTestWebpBinding;
import com.etatech.test.utils.BaseActivity;
import com.etatech.test.utils.FileUtils;
import com.etatech.test.utils.ui.ClickUtil;
import com.mega.imageloader.ImageLoader;

import rx.functions.Action1;

public class TestWebpActivity extends BaseActivity<ActivityTestWebpBinding> {


    @Override
    public ActivityTestWebpBinding onCreateView(Bundle savedInstanceState) {
        return DataBindingUtil.setContentView(this, R.layout.activity_test_webp);
    }

    @Override
    public void init() {
        ClickUtil.setOnClick(binding.btnTestWebp, new Action1() {
            @Override
            public void call(Object o) {
                ImageLoader.loadWebpForLisForFirst(binding.ivPreview,R.drawable.yxlc_qbrw_sfg, null, 1);
            }
        });
    }
}