package com.etatech.test.view;

import com.blankj.utilcode.util.AdaptScreenUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.etatech.test.R;
import com.etatech.test.databinding.ActivityMainBinding;
import com.etatech.test.utils.ui.ClickUtil;
import com.etatech.test.utils.BaseActivity;
import com.etatech.test.widget.wallpaper.SettingsPrefActivity;
import com.jakewharton.rxbinding.view.RxView;
import com.trello.rxlifecycle.android.ActivityEvent;


import android.content.Intent;
import android.content.res.Resources;

import androidx.databinding.DataBindingUtil;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

public class MainActivity extends BaseActivity<ActivityMainBinding> implements View.OnClickListener {
    private Button btnAdaptWidth;
    private Button btnAdaptHeight;
    private Button btnTestTranslation;
    private Button btnTestSurfaceview;
    private Button btnTestDataBinding;

    private Intent intent;

    @Override
    public ActivityMainBinding onCreateView(Bundle savedInstanceState) {
        return DataBindingUtil.setContentView(this, R.layout.activity_main);
    }

    @Override
    public void init() {
        intent = new Intent();

        btnAdaptWidth = (Button) findViewById(R.id.btn_adapt_width);
        btnAdaptHeight = (Button) findViewById(R.id.btn_adapt_height);
        btnTestTranslation = (Button) findViewById(R.id.btn_test_translation);
        btnTestSurfaceview = (Button) findViewById(R.id.btn_test_surfaceview);
        btnTestDataBinding = (Button) findViewById(R.id.btn_test_databinding);
        btnAdaptWidth.setOnClickListener(this);
        btnAdaptHeight.setOnClickListener(this);
        btnTestTranslation.setOnClickListener(this);
        btnTestSurfaceview.setOnClickListener(this);
        btnTestDataBinding.setOnClickListener(this);

        test();

        RxView.clicks(binding.btnTestMvp)
                .compose(this.<Void>bindUntilEvent(ActivityEvent.DESTROY))
                .throttleFirst(1000, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        intent.setClass(MainActivity.this, TestMvpActivity.class);
                        MainActivity.this.startActivity(intent);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Toast.makeText(MainActivity.this, "操作失败", Toast.LENGTH_SHORT).show();
                    }
                });

        ClickUtil.setOnClick(binding.btnTestAudio, new Action1() {
            @Override
            public void call(Object o) {
                intent.setClass(MainActivity.this, TestAudioActivity.class);
                MainActivity.this.startActivity(intent);
            }
        });
        ClickUtil.setOnClick(binding.btnTestLeak, new Action1() {
            @Override
            public void call(Object o) {
                intent.setClass(MainActivity.this, TestLeakActivity.class);
                startActivity(intent);
            }
        });
        ClickUtil.setOnClick(binding.btnTestFloat, new Action1() {
            @Override
            public void call(Object o) {
                intent.setClass(MainActivity.this, TestFloatingViewActivity.class);
                startActivity(intent);
            }
        });
        ClickUtil.setOnClick(binding.btnTestSort, new Action1() {
            @Override
            public void call(Object o) {
                intent.setClass(MainActivity.this, ActivityTestSort.class);
                startActivity(intent);
            }
        });
        ClickUtil.setOnClick(binding.btnTestAnimation, new Action1() {
            @Override
            public void call(Object o) {
                intent.setClass(MainActivity.this, TestAnimationActivity.class);
                startActivity(intent);
            }
        });
        ClickUtil.setOnClick(binding.btnTestSvg, new Action1() {
            @Override
            public void call(Object o) {
                intent.setClass(MainActivity.this, TestSvgActivity.class);
                startActivity(intent);
            }
        });
        ClickUtil.setOnClick(binding.btnTestMvvm, new Action1() {
            @Override
            public void call(Object o) {
                intent.setClass(MainActivity.this, TestMvvmActivity.class);
                startActivity(intent);
            }
        });
        ClickUtil.setOnClick(binding.btnTestJavaRequest, new Action1() {
            @Override
            public void call(Object o) {
                intent.setClass(MainActivity.this, TestJavaRequestActivity.class);
                startActivity(intent);
            }
        });
        ClickUtil.setOnClick(binding.btnTestSplashAd, new Action1() {
            @Override
            public void call(Object o) {
                intent.setClass(MainActivity.this, TestSplashAdActivity.class);
                startActivity(intent);
            }
        });
        ClickUtil.setOnClick(binding.btnTestClickArea, new Action1() {
            @Override
            public void call(Object o) {
                intent.setClass(MainActivity.this, TestClickAreaActivity.class);
                startActivity(intent);
            }
        });
        ClickUtil.setOnClick(binding.btnTestDarkMode, new Action1() {
            @Override
            public void call(Object o) {
                intent.setClass(MainActivity.this, TestDarkModeActivity.class);
                startActivity(intent);
            }
        });
        ClickUtil.setOnClick(binding.btnTestVmant, new Action1() {
            @Override
            public void call(Object o) {
                intent.setClass(MainActivity.this, AntiEmulatorActivity.class);
                startActivity(intent);
            }
        });
        ClickUtil.setOnClick(binding.btnTestAidl, new Action1() {
            @Override
            public void call(Object o) {
                intent.setClass(MainActivity.this, TestIpcAidlActivity.class);
                startActivity(intent);
            }
        });
        ClickUtil.setOnClick(binding.btnTestShader, new Action1() {
            @Override
            public void call(Object o) {
                intent.setClass(MainActivity.this, TestShaderActivity.class);
                startActivity(intent);
            }
        });
        ClickUtil.setOnClick(binding.btnTestMultiAnimation, new Action1() {
            @Override
            public void call(Object o) {
                intent.setClass(MainActivity.this, TestMultiAnimationActivity.class);
                startActivity(intent);
            }
        });
        ClickUtil.setOnClick(binding.btnTestObbBuild, new Action1() {
            @Override
            public void call(Object o) {
                intent.setClass(MainActivity.this, TestObbBuildActivity.class);
                startActivity(intent);
            }
        });
        ClickUtil.setOnClick(binding.btnTestLifecycle, new Action1() {
            @Override
            public void call(Object o) {
                intent.setClass(MainActivity.this, TestLifecycleActivity.class);
                startActivity(intent);
            }
        });
        ClickUtil.setOnClick(binding.btnTestSocket, new Action1() {
            @Override
            public void call(Object o) {
                intent.setClass(MainActivity.this, TestSocketActivity.class);
                startActivity(intent);
            }
        });
        ClickUtil.setOnClick(binding.btnTestExportSvg, new Action1() {
            @Override
            public void call(Object o) {
                intent.setClass(MainActivity.this, TestExportSvgActivity.class);
                startActivity(intent);
            }
        });
        ClickUtil.setOnClick(binding.btnTestRecyclerviewAnimation, new Action1() {
            @Override
            public void call(Object o) {
                intent.setClass(MainActivity.this, TestRecyclerviewAnimationActivity.class);
                startActivity(intent);
            }
        });
        ClickUtil.setOnClick(binding.btnTestAstar, new Action1() {
            @Override
            public void call(Object o) {
                intent.setClass(MainActivity.this, TestAstarAlgorithmActivity.class);
                startActivity(intent);
            }
        });
        ClickUtil.setOnClick(binding.btnTestDungeon, new Action1() {
            @Override
            public void call(Object o) {
                intent.setClass(MainActivity.this, TestRandomDungeonActivity.class);
                startActivity(intent);
            }
        });
        ClickUtil.setOnClick(binding.btnTestMask, new Action1() {
            @Override
            public void call(Object o) {
                intent.setClass(MainActivity.this, TestMaskLayoutActivity.class);
                startActivity(intent);
            }
        });
        ClickUtil.setOnClick(binding.btnTestScrollto, new Action1() {
            @Override
            public void call(Object o) {
                intent.setClass(MainActivity.this, TestScrollToActivity.class);
                startActivity(intent);
            }
        });
        ClickUtil.setOnClick(binding.btnTestWheelpicker, new Action1() {
            @Override
            public void call(Object o) {
                intent.setClass(MainActivity.this, TestWheelPickerActivity.class);
                startActivity(intent);
            }
        });
        ClickUtil.setOnClick(binding.btnTestCustomview, new Action1() {
            @Override
            public void call(Object o) {
                intent.setClass(MainActivity.this, TestCustomViewActivity.class);
                startActivity(intent);
            }
        });
        ClickUtil.setOnClick(binding.btnTestCustomviewDemo, new Action1() {
            @Override
            public void call(Object o) {
                intent.setClass(MainActivity.this, TestCustomViewDemoActivity.class);
                startActivity(intent);
            }
        });
        ClickUtil.setOnClick(binding.btnTestBezier, new Action1() {
            @Override
            public void call(Object o) {
                intent.setClass(MainActivity.this, TestBezierActivity.class);
                startActivity(intent);
            }
        });
        ClickUtil.setOnClick(binding.btnTestPrintStack, new Action1() {
            @Override
            public void call(Object o) {
                intent.setClass(MainActivity.this, TestPrintStackActivity.class);
                startActivity(intent);
            }
        });
        ClickUtil.setOnClick(binding.btnTestAndroidId, new Action1() {
            @Override
            public void call(Object o) {
                intent.setClass(MainActivity.this, TestAndroidIdActivity.class);
                startActivity(intent);
            }
        });

        ClickUtil.setOnClick(binding.btnTestEmoji, new Action1() {
            @Override
            public void call(Object o) {
                intent.setClass(MainActivity.this, TestEmojiActivity.class);
                startActivity(intent);
            }
        });
        ClickUtil.setOnClick(binding.btnTestOpengl, new Action1() {
            @Override
            public void call(Object o) {
                intent.setClass(MainActivity.this, TestOpenglesActivity.class);
                startActivity(intent);
            }
        });
        ClickUtil.setOnClick(binding.btnTestHtml, new Action1() {
            @Override
            public void call(Object o) {
                intent.setClass(MainActivity.this, TestHtmlTextViewActivity.class);
                startActivity(intent);
            }
        });
        ClickUtil.setOnClick(binding.btnTestScheme, new Action1() {
            @Override
            public void call(Object o) {
                /**
                 * (1)在manifest配置文件中配置了scheme参数
                 * (2)网络端获取url
                 * (3)跳转
                 */
                String url = "https://www.hosttest.com:666/pathtest?from=MainActivity";

                Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse(url));
                startActivity(intent);
            }
        });
        ClickUtil.setOnClick(binding.btnTestShare, new Action1() {
            @Override
            public void call(Object o) {
                intent.setClass(MainActivity.this, TestShareActivity.class);
                startActivity(intent);
            }
        });
        ClickUtil.setOnClick(binding.btnTestSpine, new Action1() {
            @Override
            public void call(Object o) {
                intent.setClass(MainActivity.this, TestSpineActivity.class);
                startActivity(intent);
            }
        });
        ClickUtil.setOnClick(binding.btnTestLiveWallpaper, new Action1() {
            @Override
            public void call(Object o) {
                intent.setClass(MainActivity.this, SettingsPrefActivity.class);
                startActivity(intent);
            }
        });
        ClickUtil.setOnClick(binding.btnTestWebp, new Action1() {
            @Override
            public void call(Object o) {
                intent.setClass(MainActivity.this, TestWebpActivity.class);
                startActivity(intent);
            }
        });
        ClickUtil.setOnClick(binding.btnTestLocalization, new Action1() {
            @Override
            public void call(Object o) {
                intent.setClass(MainActivity.this, TestLocalizeActivity.class);
                startActivity(intent);
            }
        });
        ClickUtil.setOnClick(binding.btnTestTxlive, new Action1() {
            @Override
            public void call(Object o) {
                intent.setClass(MainActivity.this, TestTxLiveActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public Resources getResources() {
        if (ScreenUtils.isPortrait()) {
            return AdaptScreenUtils.adaptWidth(super.getResources(), 1080);
        } else {
            return AdaptScreenUtils.adaptHeight(super.getResources(), 1080);
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.btn_adapt_width:
                intent.setClass(this, AdaptWidthActivity.class);
                this.startActivity(intent);
                break;
            case R.id.btn_adapt_height:
                intent.setClass(this, AdaptHeightActivity.class);
                this.startActivity(intent);
                break;
            case R.id.btn_test_translation:
                intent.setClass(this, TestTranslationActivity.class);
                this.startActivity(intent);
                break;
            case R.id.btn_test_surfaceview:
                intent.setClass(this, TestSurfaceviewActivity.class);
                this.startActivity(intent);
                break;
            case R.id.btn_test_databinding:
                intent.setClass(this, TestDataBindingActivity.class);
                this.startActivity(intent);
                break;
        }
    }

    private void test(){
        testUriEncoder();
    }

    private void testUriEncoder(){
        String url = "https://werewolf.53site.com/line-auth/shareRoom/index.html?type=1&roomNo=645025&roomPw=&id=645025&borderId=4&levle=無制限&roomName=hddhdhの部屋&roomType=初心者部屋";
        String allowedChars="._-$,;:~()?/=&";
        String urlEncoded = Uri.encode(url, allowedChars);
        System.out.println(urlEncoded);
    }
}
