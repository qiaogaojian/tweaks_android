package com.etatech.test;

import com.blankj.utilcode.util.AdaptScreenUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.etatech.test.activity.AdaptHeightActivity;
import com.etatech.test.activity.AdaptWidthActivity;
import com.etatech.test.utils.BaseActivity;


import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends BaseActivity implements View.OnClickListener
{
    private Button btnAdaptWidth;
    private Button btnAdaptHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAdaptWidth = (Button) findViewById(R.id.btn_adapt_width);
        btnAdaptHeight = (Button) findViewById(R.id.btn_adapt_height);
        btnAdaptWidth.setOnClickListener(this);
        btnAdaptHeight.setOnClickListener(this);
    }

    @Override
    public Resources getResources()
    {
        if (ScreenUtils.isPortrait())
        {
            return AdaptScreenUtils.adaptWidth(super.getResources(), 1080);
        } else
        {
            return AdaptScreenUtils.adaptHeight(super.getResources(), 1080);
        }
    }

    @Override
    public void onClick(View v)
    {
        Intent intent = new Intent();
        switch (v.getId())
        {
            case R.id.btn_adapt_width:
                intent.setClass(this, AdaptWidthActivity.class);
                this.startActivity(intent);
                break;
            case R.id.btn_adapt_height:
                intent.setClass(this, AdaptHeightActivity.class);
                this.startActivity(intent);
                break;
        }
    }
}
