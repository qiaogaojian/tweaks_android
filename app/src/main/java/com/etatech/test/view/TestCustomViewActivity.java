package com.etatech.test.view;

import android.databinding.DataBindingUtil;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.etatech.test.R;
import com.etatech.test.databinding.ActivityTestCustomViewBinding;
import com.etatech.test.utils.BaseActivity;
import com.etatech.test.view.practice.PageFragment;
import com.etatech.test.view.practice.PageModel;
import com.etatech.test.view.practice.PagePageFragment;

import java.util.ArrayList;
import java.util.List;

public class TestCustomViewActivity extends BaseActivity<ActivityTestCustomViewBinding> {
    List<PageModel> pageModels = new ArrayList<>();
    List<List<PageModel>> pagePageModels = new ArrayList<>();


    @Override
    public ActivityTestCustomViewBinding onCreateView(Bundle savedInstanceState) {
        return DataBindingUtil.setContentView(this, R.layout.activity_test_custom_view);
    }

    @Override
    public void init() {
        pageModels = new ArrayList<>();
        pageModels.add(new PageModel(R.layout.sample_circle, R.string.title_cirle_view, R.layout.practice_circle_view));
        pageModels.add(new PageModel(R.layout.sample_color, R.string.title_draw_color, R.layout.practice_color));
        pageModels.add(new PageModel(R.layout.sample_circle, R.string.title_draw_circle, R.layout.practice_circle));
        pageModels.add(new PageModel(R.layout.sample_rect, R.string.title_draw_rect, R.layout.practice_rect));
        pageModels.add(new PageModel(R.layout.sample_point, R.string.title_draw_point, R.layout.practice_point));
        pageModels.add(new PageModel(R.layout.sample_oval, R.string.title_draw_oval, R.layout.practice_oval));
        pageModels.add(new PageModel(R.layout.sample_line, R.string.title_draw_line, R.layout.practice_line));
        pageModels.add(new PageModel(R.layout.sample_round_rect, R.string.title_draw_round_rect, R.layout.practice_round_rect));
        pageModels.add(new PageModel(R.layout.sample_arc, R.string.title_draw_arc, R.layout.practice_arc));
        pageModels.add(new PageModel(R.layout.sample_path, R.string.title_draw_path, R.layout.practice_path));
        pageModels.add(new PageModel(R.layout.sample_histogram, R.string.title_draw_histogram, R.layout.practice_histogram));
        pageModels.add(new PageModel(R.layout.sample_pie_chart, R.string.title_draw_pie_chart, R.layout.practice_pie_chart));
        pagePageModels.add(pageModels);


        FragmentPagerAdapter pagePageAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int pos) {
                List<PageModel> pageModels = pagePageModels.get(pos);
                PagePageFragment pagePageFragment = new PagePageFragment();
                pagePageFragment.setPageModels(pageModels);
                return pagePageFragment;
            }

            @Override
            public int getCount() {
                return pagePageModels.size();
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                return "Practice" + (position + 1);
            }
        };

        binding.viewpager.setOffscreenPageLimit(2); // 注意：特意设置预加载
        binding.viewpager.setAdapter(pagePageAdapter);
        binding.tablayout.setupWithViewPager(binding.viewpager);

    }


}
