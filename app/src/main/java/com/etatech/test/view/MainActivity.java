package com.etatech.test.view;

import com.blankj.utilcode.util.AdaptScreenUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.etatech.test.R;
import com.etatech.test.databinding.ActivityMainBinding;
import com.etatech.test.utils.ui.ClickUtil;
import com.etatech.test.utils.BaseActivity;
import com.etatech.test.view.custom.floatmenu.FloatItem;
import com.etatech.test.view.custom.floatmenu.FloatLogoMenu;
import com.etatech.test.view.custom.floatmenu.FloatMenuView;
import com.jakewharton.rxbinding.view.RxView;
import com.trello.rxlifecycle.android.ActivityEvent;


import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.databinding.DataBindingUtil;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
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

        for (int i = 0; i < menuIcons.length; i++)
        {
            itemList.add(new FloatItem(MENU_ITEMS[i], 0xB2000000, 0xB2000000, BitmapFactory.decodeResource(this.getResources(), menuIcons[i]), String.valueOf(i + 1)));
        }

        showFloatView(MainActivity.this);
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

    private FloatLogoMenu        mFloatMenu;
    private  int[]                menuIcons  = new int[]{R.drawable.floating_icon2, R.drawable.floating_icon3, R.drawable.floating_icon4};
    private  ArrayList<FloatItem> itemList   = new ArrayList<>();
    private  String[]             MENU_ITEMS = {"", "", ""};

    private void showFloatView(final Activity activity)
    {
        mFloatMenu = new FloatLogoMenu.Builder()
                .withActivity(activity)
                .logo(BitmapFactory.decodeResource(getResources(), R.drawable.floating_icon1))
                .drawCicleMenuBg(true)
                .backMenuColor(0xB2000000)
                .setBgDrawable(activity.getResources().getDrawable(R.drawable.floating_view_bg))
                .setFloatItems(itemList)
                .defaultLocation(FloatLogoMenu.RIGHT)
                .drawRedPointNum(false)
                .showWithListener(new FloatMenuView.OnMenuClickListener()
                {
                    @Override
                    public void onItemClick(int position, String title)
                    {
                        Toast.makeText(activity, "position " + position + " title:" + title + " is clicked.", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void dismiss()
                    {

                    }
                });
    }
}
