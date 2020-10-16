package com.etatech.test.view.practice1;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.etatech.test.R;
import com.etatech.test.view.custom.ChildViewPager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Michael
 * Date:  2020/10/15
 * Desc:
 */
public class PagePageFragment extends Fragment {
    List<PageModel> pageModels = new ArrayList<>();

    public void setPageModels(List<PageModel> pageModels) {
        this.pageModels = pageModels;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_test_custom_view, container, false);

        // getChildFragmentManager() 解决viewpager中fragment消失的问题
        FragmentPagerAdapter pageAdapter = new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int pos) {
                PageModel pageModel = pageModels.get(pos);
                return PageFragment.newInstance(pageModel.sampleLayoutRes, pageModel.practiceLayoutRes);
            }

            @Override
            public int getCount() {
                return pageModels.size();
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                PageModel pageModel = pageModels.get(position);
                return getString(pageModel.titleRes);
            }
        };
        TabLayout tabLayout = view.findViewById(R.id.tablayout);
        ChildViewPager viewPager = view.findViewById(R.id.viewpager);
        viewPager.setAdapter(pageAdapter);
        tabLayout.setupWithViewPager(viewPager);

        return view;
    }
}
