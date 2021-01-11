package com.etatech.test.view;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;

import android.os.Bundle;

import com.etatech.test.R;
import com.etatech.test.databinding.ActivityTestOpenglesBinding;
import com.etatech.test.opengl.PageOneFragment;
import com.etatech.test.utils.BaseActivity;
import com.etatech.test.view.practice1.PageFragment;
import com.etatech.test.view.practice1.PageModel;
import com.etatech.test.view.practice1.PagePageFragment;

import java.util.ArrayList;
import java.util.List;

public class TestOpenglesActivity extends BaseActivity<ActivityTestOpenglesBinding> {

    private List<PageModel> pageList;

    @Override
    public ActivityTestOpenglesBinding onCreateView(Bundle savedInstanceState) {
        return DataBindingUtil.setContentView(TestOpenglesActivity.this, R.layout.activity_test_opengles);
    }

    @Override
    public void init() {
        pageList = new ArrayList<>();
        pageList.add(new PageModel(R.layout.opengl_triangle, R.string.title_triangle, R.layout.opengl_triangle));

        FragmentPagerAdapter pagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                PageModel pageModel = pageList.get(position);
                // 这里的布局就是Fragment的布局
                return PageOneFragment.newInstance(pageModel.practiceLayoutRes);
            }

            @Override
            public int getCount() {
                return pageList == null ? 0 : pageList.size();
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                return  getString(pageList.get(position).titleRes);
            }
        };

        binding.viewpager.setOffscreenPageLimit(2);
        binding.viewpager.setAdapter(pagerAdapter);
        binding.tablayout.setupWithViewPager(binding.viewpager);
    }
}