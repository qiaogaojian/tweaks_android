package com.etatech.test.view;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.etatech.test.R;
import com.etatech.test.databinding.ActivityTestSplashAdBinding;
import com.etatech.test.utils.BaseActivity;
import com.etatech.test.utils.ui.ClickUtil;
import com.sdbean.splashad.SplashAdBean;

import rx.functions.Action1;

public class TestSplashAdActivity extends BaseActivity<ActivityTestSplashAdBinding> {
    private Intent intent;

    @Override
    public ActivityTestSplashAdBinding onCreateView(Bundle savedInstanceState) {
        return DataBindingUtil.setContentView(this, R.layout.activity_test_splash_ad);
    }

    @Override
    public void init() {
        intent = new Intent();
        intent.setClass(TestSplashAdActivity.this, SplashActivity.class);

        ClickUtil.setOnClick(binding.btnTestImage, new Action1() {
            @Override
            public void call(Object o) {
                SplashAdBean adBean = new SplashAdBean();
                adBean.setAdUrl("https://www.douyu.com/510229");
                adBean.setResUrl("http://img.53site.com/Werewolf/AD/laoyangdouyuAD.jpg?a=2");
                adBean.setStayTime(3);
                adBean.setType("jpg");
                adBean.setMd5("ec8976c319478bc412f79ed12c679b3b");
                intent.putExtra("splashAdBean", adBean);

                startActivity(intent);
            }
        });

        ClickUtil.setOnClick(binding.btnTestDrawee, new Action1() {
            @Override
            public void call(Object o) {
                SplashAdBean adBean = new SplashAdBean();
                adBean.setAdUrl("https://www.douyu.com/510229");
                adBean.setResUrl("https://upload-images.jianshu.io/upload_images/2229730-5cd1dab1a302b122.gif");
                adBean.setStayTime(5);
                adBean.setType("gif");
                adBean.setMd5("ec8976c319478bc412f79ed12c679b3b");
                intent.putExtra("splashAdBean", adBean);

                startActivity(intent);
            }
        });

        ClickUtil.setOnClick(binding.btnTestVideo, new Action1() {
            @Override
            public void call(Object o) {
                SplashAdBean adBean = new SplashAdBean();
                adBean.setAdUrl("https://www.douyu.com/510229");
                adBean.setResUrl("https://vd3.bdstatic.com/mda-jbcku58bvs34kjav/mda-jbcku58bvs34kjav.mp4");
                adBean.setStayTime(15);
                adBean.setType("mp4");
                adBean.setMd5("ec8976c319478bc412f79ed12c679b3b");
                intent.putExtra("splashAdBean", adBean);

                startActivity(intent);
            }
        });

    }
}