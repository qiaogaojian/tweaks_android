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
                LogUtils.e("on SplashAd Success To Show");
            }

            @Override
            public void onSplashAdFailToShow() {
                LogUtils.e("on SplashAd Fail To Show");
            }

            @Override
            public void onSplashAdClicked() {
                LogUtils.e("on SplashAd Clicked");
            }

            @Override
            public void onSplashAdClose() {
                LogUtils.e("on SplashAd Close");
            }

            @Override
            public void onSplashAdFinish() {
                LogUtils.e("on SplashAd Finish");
            }
        });
        splashAd.setDefaultCover(getResources().getDrawable(R.drawable.splash_bg));
        splashAd.setTypeface(App.getInstance().getTypeface());
        splashAd.show();
    }
}
