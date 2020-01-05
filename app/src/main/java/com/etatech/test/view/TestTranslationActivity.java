package com.etatech.test.view;

import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.blankj.utilcode.util.AdaptScreenUtils;
import com.etatech.test.R;
import com.etatech.test.utils.BaseActivity;

public class TestTranslationActivity extends BaseActivity implements View.OnClickListener
{

    private Button    btnIn;
    private Button    btnOut;
    private ImageView ivIn;
    private ImageView ivOut;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_translation);

        btnIn = (Button) findViewById(R.id.btn_in);
        btnOut = (Button) findViewById(R.id.btn_out);
        ivIn = (ImageView) findViewById(R.id.iv_in);
        ivOut = (ImageView) findViewById(R.id.iv_out);
        btnIn.setOnClickListener(this);
        btnOut.setOnClickListener(this);
    }

    @Override
    public ViewDataBinding onCreateView(Bundle savedInstanceState) {
        return null;
    }

    @Override
    public void init() {

    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.btn_in:
                ivIn.setTranslationX(AdaptScreenUtils.pt2Px(540));
                break;
            case R.id.btn_out:
                ivOut.setTranslationX(AdaptScreenUtils.pt2Px(-540));
                break;
        }
    }
}
