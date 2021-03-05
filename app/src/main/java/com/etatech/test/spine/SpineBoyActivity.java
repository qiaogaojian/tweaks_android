package com.etatech.test.spine;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.Button;
import android.widget.FrameLayout;

import com.badlogic.gdx.backends.android.AndroidFragmentApplication;
import com.etatech.spine.SpineBaseFragment;
import com.etatech.test.R;
import com.etatech.test.databinding.ActivitySpineBoyBinding;
import com.etatech.test.utils.BaseActivity;
import com.etatech.test.utils.ui.ClickUtil;

import rx.functions.Action1;

public class SpineBoyActivity extends BaseActivity<ActivitySpineBoyBinding> implements AndroidFragmentApplication.Callbacks {
    private SpineBaseFragment mSpineBaseFragment;
    private SpineBoyAdapter mSpineBoyAdapter;
    private boolean flag;

    @Override
    public ActivitySpineBoyBinding onCreateView(Bundle savedInstanceState) {
        return DataBindingUtil.setContentView(this, R.layout.activity_spine_boy);
    }

    @Override
    public void init() {
        initSpine();
        initClick();
    }

    private void initSpine() {
        mSpineBaseFragment = new SpineBaseFragment();
        mSpineBoyAdapter = new SpineBoyAdapter();
        mSpineBaseFragment.setAdapter(mSpineBoyAdapter);
        FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction();
        transaction.replace(R.id.fl_spine, (Fragment) mSpineBaseFragment);
        transaction.commitAllowingStateLoss();
    }

    private void initClick() {
        ClickUtil.setOnClick(binding.btnRun, new Action1() {
            @Override
            public void call(Object o) {
                mSpineBoyAdapter.doRun();
            }
        });

        ClickUtil.setOnClick(binding.btnJump, new Action1() {
            @Override
            public void call(Object o) {
                mSpineBoyAdapter.doJump();
            }
        });

        ClickUtil.setOnClick(binding.btnReplaceAttachment, new Action1() {
            @Override
            public void call(Object o) {
                mSpineBoyAdapter.doJump();
                flag = !flag;
                if (flag) {
                    mSpineBoyAdapter.setAttachment("gun", "");
                } else {
                    mSpineBoyAdapter.setAttachment("gun", "gun");
                }
            }
        });

        ClickUtil.setOnClick(binding.btnUnstallAttachment, new Action1() {
            @Override
            public void call(Object o) {
                mSpineBoyAdapter.doJump();
            }
        });
    }

    @Override
    public void exit() {

    }
}
