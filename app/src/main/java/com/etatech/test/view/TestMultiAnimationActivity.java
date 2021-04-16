package com.etatech.test.view;

import androidx.databinding.DataBindingUtil;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.GridLayoutManager;

import com.etatech.test.R;
import com.etatech.test.adapter.MultiAniAdapter;
import com.etatech.test.databinding.ActivityTestMultiAnimationBinding;
import com.etatech.test.utils.BaseActivity;

import java.util.ArrayList;
import java.util.List;

public class TestMultiAnimationActivity extends BaseActivity<ActivityTestMultiAnimationBinding> {

    private List<String> imgList;
    private MultiAniAdapter multiAniAdapter;

    @Override
    public ActivityTestMultiAnimationBinding onCreateView(Bundle savedInstanceState) {
        return DataBindingUtil.setContentView(this, R.layout.activity_test_multi_animation);
    }

    @Override
    public void init() {
        initData();

        if (multiAniAdapter == null) {
            multiAniAdapter = new MultiAniAdapter(imgList);
        }
        binding.rvAni.setAdapter(multiAniAdapter);
        binding.rvAni.setLayoutManager(new GridLayoutManager(this,5));
    }

    private void initData() {
        imgList = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            imgList.add("http://werewolf-resource-jp.53site.com/s/avatarframe/823_player.webp");
        }
    }
}
