package com.etatech.test.view;

import com.blankj.utilcode.util.AdaptScreenUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.etatech.test.R;
import com.etatech.test.databinding.ActivityMainBinding;
import com.etatech.test.utils.Tools;
import com.etatech.test.utils.ui.ClickUtil;
import com.etatech.test.utils.BaseActivity;
import com.jakewharton.rxbinding.view.RxView;
import com.trello.rxlifecycle.android.ActivityEvent;


import android.content.Intent;
import android.content.res.Resources;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.TimeUnit;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

import static java.lang.Character.isHighSurrogate;
import static java.lang.Character.isLowSurrogate;

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
                // 第一种打印调用堆栈方式
                // Exception e = new Exception("this is a log");
                // e.printStackTrace();
                // 第二种方法 这种方法可以很方便的写入文件
                // Log.e("this is a log",Log.getStackTraceString(new Throwable()));
                try {
                    String name_utf8 = "小鲤鱼\uD83E\uDD80历\uD83E\uDD80险\uD83E\uDD80记\uD83E\uDD80";
                    String name_gbk = new String(name_utf8.getBytes("GBK"), "GBK");
                    String name_2312 = new String(name_utf8.getBytes("GBK"), "GB2312");

                    if (name_utf8.length() >= 8) {
                        binding.btnTestPrintStack.setText(String.format("%s...", subString(name_utf8, 0, 7)));
                    } else {
                        binding.btnTestPrintStack.setText(name_utf8);
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        });
        ClickUtil.setOnClick(binding.btnTestAndroidId, new Action1() {
            @Override
            public void call(Object o) {
                String android_id = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);

                binding.btnTestAndroidId.setText(android_id);
            }
        });
    }

    // 获取
    private String subString(String input, int start, int end) {
        int unicodeCount = codePointCount(input, start, end);             // 获取unicode个数
        int offsetIndex = input.offsetByCodePoints(start, unicodeCount); // 根据unicode个数获取偏移位置
        return input.substring(start, offsetIndex);
    }

    public static int codePointCount(CharSequence seq, int beginIndex, int endIndex) {
        int length = seq.length();
        if (beginIndex < 0 || endIndex > length || beginIndex > endIndex) {
            throw new IndexOutOfBoundsException();
        }
        int n = endIndex - beginIndex;
        for (int i = beginIndex; i < endIndex; ) {
            if (isHighSurrogate(seq.charAt(i++)) && i < endIndex &&
                    isLowSurrogate(seq.charAt(i))) {
                n--;
                i++;
            }
        }
        return n;
    }

    public static int offsetByCodePoints(CharSequence seq, int index,
                                         int codePointOffset) {
        int length = seq.length();
        if (index < 0 || index > length) {
            throw new IndexOutOfBoundsException();
        }

        int x = index;
        if (codePointOffset >= 0) {
            int i;
            for (i = 0; x < length && i < codePointOffset; i++) {
                if (isHighSurrogate(seq.charAt(x++)) && x < length &&
                        isLowSurrogate(seq.charAt(x))) {
                    x++;
                }
            }
            if (i < codePointOffset) {
                throw new IndexOutOfBoundsException();
            }
        } else {
            int i;
            for (i = codePointOffset; x > 0 && i < 0; i++) {
                if (isLowSurrogate(seq.charAt(--x)) && x > 0 &&
                        isHighSurrogate(seq.charAt(x-1))) {
                    x--;
                }
            }
            if (i < 0) {
                throw new IndexOutOfBoundsException();
            }
        }
        return x;
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


}
