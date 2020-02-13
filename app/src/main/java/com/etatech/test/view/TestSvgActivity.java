package com.etatech.test.view;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.etatech.test.R;
import com.etatech.test.databinding.ActivityTestSvgBinding;
import com.etatech.test.utils.BaseActivity;

/**
 * Created by Michael
 * Date:  2020/2/13
 * Func: SVG pathData详解 参考文档:https://blog.csdn.net/zwlove5280/article/details/73196543
 */
public class TestSvgActivity extends BaseActivity<ActivityTestSvgBinding> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_svg);
    }

    @Override
    public ActivityTestSvgBinding onCreateView(Bundle savedInstanceState) {
        return DataBindingUtil.setContentView(this,R.layout.activity_test_svg);
    }

    @Override
    public void init() {
        
    }
}
