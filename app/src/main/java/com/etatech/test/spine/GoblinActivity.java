package com.etatech.test.spine;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidFragmentApplication;
import com.etatech.spine.SpineBaseFragment;
import com.etatech.test.R;
import com.etatech.test.databinding.ActivityGoblinBinding;
import com.etatech.test.utils.BaseActivity;
import com.etatech.test.utils.ui.ClickUtil;

import rx.functions.Action1;

public class GoblinActivity extends BaseActivity<ActivityGoblinBinding> implements AndroidFragmentApplication.Callbacks {
    private SpineBaseFragment mSpineBaseFragment;
    private GoblinAdapter mSpineAdapter;

    @Override
    public ActivityGoblinBinding onCreateView(Bundle savedInstanceState) {
        return DataBindingUtil.setContentView(this, R.layout.activity_goblin);
    }

    @Override
    public void init() {
        initSpine();
        initClick();
    }

    private void initSpine() {
        mSpineAdapter = new GoblinAdapter();
        mSpineBaseFragment = new SpineBaseFragment();
        mSpineBaseFragment.setAdapter(mSpineAdapter);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fl_spine, (Fragment) mSpineBaseFragment);
        transaction.commitAllowingStateLoss();
    }

    private void initClick() {
        ClickUtil.setOnClick(binding.btnMan, new Action1() {
            @Override
            public void call(Object o) {
                mSpineAdapter.setSkin("goblin");
            }
        });

        ClickUtil.setOnClick(binding.btnWoman, new Action1() {
            @Override
            public void call(Object o) {
                mSpineAdapter.setSkin("goblingirl");
            }
        });
    }

    @Override
    public void exit() {

    }
}