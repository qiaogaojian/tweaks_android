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
//        YumiSplash splash = new YumiSplash(this,binding.splashContainer,"1");

        SplashAdBean adBean = new SplashAdBean();
        adBean.setAdUrl("https://www.douyu.com/510229");
//        adBean.setResUrl("http://img.53site.com/Werewolf/AD/laoyangdouyuAD.jpg?a=2");
//        adBean.setResUrl("" + R.drawable.splash);
        adBean.setResUrl("android.resource://" + getPackageName() + "/raw/splash");
        adBean.setStayTime(5);
        adBean.setType("mp4");
        adBean.setMd5("ec8976c319478bc412f79ed12c679b3b");
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
        });
        splashAd.setDefaultCover(getResources().getDrawable(R.drawable.splash_bg));
        splashAd.setTypeface(App.getInstance().getTypeface());
        splashAd.show();
    }
}
