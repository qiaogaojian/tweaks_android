package com.etatech.test.view;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;

import android.os.Bundle;

import com.etatech.test.R;
import com.etatech.test.databinding.ActivityTestOpenglesBinding;
import com.etatech.test.opengl.GLModel;
import com.etatech.test.opengl.GLRender1Bg;
import com.etatech.test.opengl.GLRender2Triangle;
import com.etatech.test.opengl.GLRender3Rect;
import com.etatech.test.opengl.MyGLRender;
import com.etatech.test.opengl.MyGLSurfaceView;
import com.etatech.test.opengl.PageOneFragment;
import com.etatech.test.utils.BaseActivity;

import java.util.ArrayList;
import java.util.List;

public class TestOpenglesActivity extends BaseActivity<ActivityTestOpenglesBinding> {

    private List<GLModel> pageList;

    @Override
    public ActivityTestOpenglesBinding onCreateView(Bundle savedInstanceState) {
        return DataBindingUtil.setContentView(TestOpenglesActivity.this, R.layout.activity_test_opengles);
    }

    @Override
    public void init() {
        pageList = new ArrayList<>();
        pageList.add(new GLModel(R.string.title_triangle, new MyGLSurfaceView(this,new GLRender1Bg())));
        pageList.add(new GLModel(R.string.title_triangle, new MyGLSurfaceView(this,new GLRender2Triangle())));
        pageList.add(new GLModel(R.string.title_triangle, new MyGLSurfaceView(this,new GLRender3Rect())));

        FragmentPagerAdapter pagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                GLModel glModel = pageList.get(position);
                // 这里的布局就是Fragment的布局
                return new PageOneFragment(glModel.glView);
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