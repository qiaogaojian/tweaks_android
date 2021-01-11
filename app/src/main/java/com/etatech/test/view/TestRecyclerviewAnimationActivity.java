package com.etatech.test.view;

import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.etatech.test.R;
import com.etatech.test.adapter.RoleAdapter;
import com.etatech.test.databinding.ActivityTestRecyclerviewAnimationBinding;
import com.etatech.test.utils.BaseActivity;
import com.etatech.test.utils.ui.ClickUtil;

import java.util.ArrayList;
import java.util.List;

import rx.functions.Action1;

public class TestRecyclerviewAnimationActivity extends BaseActivity<ActivityTestRecyclerviewAnimationBinding> {
    RoleAdapter adapter;

    @Override
    public ActivityTestRecyclerviewAnimationBinding onCreateView(Bundle savedInstanceState) {
        return DataBindingUtil.setContentView(TestRecyclerviewAnimationActivity.this, R.layout.activity_test_recyclerview_animation);
    }

    @Override
    public void init() {
        adapter = new RoleAdapter();
        final List<Integer> roles = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            roles.add(i);
        }
        adapter.setData(roles);
        binding.rvList.setAdapter(adapter);
        binding.rvList.setLayoutManager(new LinearLayoutManager(this));

        ClickUtil.setOnClick(binding.btnTestAdd, new Action1() {
            @Override
            public void call(Object o) {
                adapter.add(8);
            }
        });

        ClickUtil.setOnClick(binding.btnTestRemove, new Action1() {
            @Override
            public void call(Object o) {
                adapter.remove(0, binding.rvList.getChildViewHolder(binding.rvList.getChildAt(0)));
            }
        });
    }
}
