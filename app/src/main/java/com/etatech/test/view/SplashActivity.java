package com.etatech.test.view;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.blankj.utilcode.util.LogUtils;
import com.etatech.test.R;
import com.etatech.test.databinding.ActivitySplashBinding;
import com.etatech.test.utils.App;
import com.etatech.test.utils.BaseActivity;
import com.sdbean.splashad.SplashAd;
import com.sdbean.splashad.SplashAdBean;
import com.sdbean.splashad.SplashAdListener;

public class SplashActivity extends BaseActivity<ActivitySplashBinding> {

    @Override
    public ActivitySplashBinding onCreateView(Bundle savedInstanceState) {
        return DataBindingUtil.setContentView(this, R.layout.activity_splash);
    }

    @Override
    public void init() {
        if (getIntent() == null) {
            return;
        }
        SplashAdBean adBean = (SplashAdBean) getIntent().getExtras().get("splashAdBean");
        SplashAd splashAd = new SplashAd(this, adBean, binding.splashContainer, new SplashAdListener() {
            @Override
            public void onSplashAdSuccessToShow() {
                LogUtils.e("展示成功");
            }

            @Override
            public void onSplashAdFailToShow() {
                LogUtils.e("展示失败");
            }

            @Override
            public void onSplashAdClicked() {
                LogUtils.e("点击广告");
            }

            @Override
            public void onSplashAdClose() {
                LogUtils.e("跳过广告");
            }

            @Override
            public void onSplashAdFinish() {
                LogUtils.e("广告播放完成");
            }
        });

        // 网络差或失败时的默认广告图
        splashAd.setDefaultCover(getResources().getDrawable(R.drawable.splash_bg));
        // 字体
        splashAd.setTypeface(App.getInstance().getTypeface());
        // 应用icon
        splashAd.setLogo(getResources().getDrawable(R.drawable.logo));
        // 下方 copyright
        splashAd.setCopyRight("Copyright 2016 - 2019 Mega Information Technology Co.,Ltd. All Rights Reserved");
        // 倒计时后方文字
        splashAd.setJumpText("跳过");
        // 资源下载等待时间
        splashAd.setWaitTime(2);

        splashAd.show();
    }
}
