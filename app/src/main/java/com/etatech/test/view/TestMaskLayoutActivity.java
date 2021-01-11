package com.etatech.test.view;

import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import androidx.recyclerview.widget.GridLayoutManager;

import com.etatech.test.R;
import com.etatech.test.adapter.PathNodeAdapter;
import com.etatech.test.bean.NodeBean;
import com.etatech.test.databinding.ActivityTestMaskLayoutBinding;
import com.etatech.test.utils.BaseActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TestMaskLayoutActivity extends BaseActivity<ActivityTestMaskLayoutBinding> {

    private PathNodeAdapter nodeAdapter;

    @Override
    public ActivityTestMaskLayoutBinding onCreateView(Bundle savedInstanceState) {
        return DataBindingUtil.setContentView(this, R.layout.activity_test_mask_layout);
    }

    @Override
    public void init() {
        binding.rvPathNode.setLayoutManager(new GridLayoutManager(this, 7));
        nodeAdapter = new PathNodeAdapter(getData());
        binding.rvPathNode.setAdapter(nodeAdapter);
        nodeAdapter.notifyDataSetChanged();
    }

    private List<NodeBean> getData() {
        List<NodeBean> nodeList = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            nodeList.add(new NodeBean());
        }

        // 障碍
        Random random = new Random();
        for (int i = 0; i < 30; i++) {
            nodeList.get(random.nextInt(100)).setReachSate(-1);
        }
        return nodeList;
    }
}
