package com.etatech.test.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.etatech.test.R;
import com.etatech.test.databinding.ActivityTestLocalizeBinding;
import com.etatech.test.utils.BaseActivity;
import com.etatech.test.utils.ui.ClickUtil;
import com.sdbean.localize.Language;
import com.sdbean.localize.MultiLanguage;

import rx.functions.Action1;

public class TestLocalizeActivity extends BaseActivity<ActivityTestLocalizeBinding> {



    @Override
    public ActivityTestLocalizeBinding onCreateView(Bundle savedInstanceState) {
        return DataBindingUtil.setContentView(this,R.layout.activity_test_localize);
    }

    @Override
    public void init() {
        ClickUtil.setOnClick(binding.btnSys, new Action1() {
            @Override
            public void call(Object o) {
                selectLanguage(Language.SYS);
            }
        });
        ClickUtil.setOnClick(binding.btnCn, new Action1() {
            @Override
            public void call(Object o) {
                selectLanguage(Language.CN);
            }
        });
        ClickUtil.setOnClick(binding.btnEn, new Action1() {
            @Override
            public void call(Object o) {
                selectLanguage(Language.EN);
            }
        });
        ClickUtil.setOnClick(binding.btnJp, new Action1() {
            @Override
            public void call(Object o) {
                selectLanguage(Language.JA);
            }
        });
    }

    /**
     * 保存自定义语言
     */
    private void selectLanguage(Language select)
    {
        MultiLanguage.saveSelectLanguage(this, select);
        TestLocalizeActivity.reStart(this);
    }

    /**
     * 刷新Activity
     * @param context
     */
    public static void reStart(Context context)
    {
        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}