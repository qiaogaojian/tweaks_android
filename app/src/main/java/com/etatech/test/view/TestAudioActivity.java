package com.etatech.test.view;

import android.Manifest;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.blankj.utilcode.util.SPUtils;
import com.etatech.test.R;
import com.etatech.test.databinding.ActivityTestAudioBinding;
import com.etatech.test.utils.BaseActivity;
import com.etatech.test.utils.ClickUtil;
import com.etatech.test.utils.GameConfig;
import com.etatech.test.utils.SoundManager;
import com.gun0912.tedpermission.TedPermissionResult;
import com.tedpark.tedpermission.rx1.TedRxPermission;

import java.util.ArrayList;

import rx.functions.Action1;

public class TestAudioActivity extends BaseActivity<ActivityTestAudioBinding> {


    @Override
    public ActivityTestAudioBinding onCreateView(Bundle savedInstanceState) {
        return DataBindingUtil.setContentView(this, R.layout.activity_test_audio);
    }

    @Override
    public void init() {

        ClickUtil.setFastClick(binding.btnPlaySound, new Action1() {
            @Override
            public void call(Object o) {
                SoundManager.getInstance().playMulti("sound");
            }
        });
        ClickUtil.setOnClick(binding.btnPlayMusic, new Action1() {
            @Override
            public void call(Object o) {
                SoundManager.getInstance().playSingle("music");
            }
        });

    }
}
