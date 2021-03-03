package com.etatech.test.spine;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import com.badlogic.gdx.backends.android.AndroidFragmentApplication;
import com.etatech.spine.SpineBaseFragment;
import com.etatech.test.R;
import com.etatech.test.utils.BaseActivity;

public class SpineBoyActivity extends BaseActivity implements AndroidFragmentApplication.Callbacks {
    private SpineBaseFragment mSpineBaseFragment;
    private SpineBoyAdapter mSpineBoyAdapter;
    private GridLayout mGlButtons;
    private Button mBtnReplaceAttachment;
    private Button mBtnUnstallAttachment;
    private Button mBtnReplaceSkin;
    private Button mBtnJump;
    private Button mBtnRun;
    private FrameLayout mFlSpine;

    @Override
    public ViewDataBinding onCreateView(Bundle savedInstanceState) {
        return DataBindingUtil.setContentView(this,R.layout.activity_spine_boy);
    }

    @Override
    public void init() {
        assignViews();
        mSpineBaseFragment = new SpineBaseFragment();
        mSpineBoyAdapter = new SpineBoyAdapter();
        mSpineBaseFragment.setAdapter(mSpineBoyAdapter);
        FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction();
        transaction.replace(R.id.fl_spine, (Fragment) mSpineBaseFragment);
        transaction.commitAllowingStateLoss();
    }

    private void assignViews() {
        mGlButtons = (GridLayout) findViewById(R.id.gl_buttons);
        mBtnReplaceAttachment = (Button) findViewById(R.id.btn_replace_attachment);
        mBtnUnstallAttachment = (Button) findViewById(R.id.btn_unstall_attachment);
        mBtnJump = (Button) findViewById(R.id.btn_jump);
        mBtnRun = (Button) findViewById(R.id.btn_run);
        mFlSpine = (FrameLayout) findViewById(R.id.fl_spine);
        mBtnReplaceAttachment.setOnClickListener(new View.OnClickListener() {
            boolean flag;

            @Override
            public void onClick(View view) {
                flag = !flag;
                if (flag) {
                    mSpineBoyAdapter.setAttachment("gun", "");
                } else {
                    mSpineBoyAdapter.setAttachment("gun", "gun");
                }
            }
        });

        mBtnUnstallAttachment.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                mSpineBoyAdapter.setAttachment("gun", "");
            }
        });
        mBtnJump.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSpineBoyAdapter.doJump();
            }
        });
        mBtnRun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSpineBoyAdapter.doRun();
            }
        });
    }

    @Override
    public void exit() {

    }
}
