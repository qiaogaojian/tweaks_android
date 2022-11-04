package com.etatech.test.view;

import androidx.databinding.DataBindingUtil;
import android.os.Bundle;

import com.blankj.utilcode.util.ToastUtils;
import com.etatech.test.R;
import com.etatech.test.databinding.ActivityLeakStaticBinding;
import com.etatech.test.utils.BaseActivity;
import com.etatech.test.utils.ui.ClickUtil;

import java.lang.ref.WeakReference;

import com.etatech.test.utils.rxbus.Action1;

public class LeakStaticActivity extends BaseActivity<ActivityLeakStaticBinding> {
    //        private static LeakStaticActivity                activity;
//    方法2 用弱引用来保存对象
    private static WeakReference<LeakStaticActivity> activityRef;

    @Override
    public ActivityLeakStaticBinding onCreateView(Bundle savedInstanceState) {
        return DataBindingUtil.setContentView(this, R.layout.activity_leak_static);
    }

    @Override
    public void init() {
        ClickUtil.setOnClick(binding.btnTestLeak1, new Action1() {
            @Override
            public void accept(Object o) {
//                activity = LeakStaticActivity.this;
//                activity.sayHello();
                activityRef = new WeakReference<LeakStaticActivity>(LeakStaticActivity.this);
                activityRef.get().sayHello();
            }
        });
    }

    private void sayHello() {
        ToastUtils.showShort("Hello!");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        方法1 不用时置为空
//        activity = null;
    }
}
