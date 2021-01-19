package com.etatech.test.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.text.Html;

import com.etatech.test.R;
import com.etatech.test.databinding.ActivityTestHtmlTextViewBinding;
import com.etatech.test.utils.BaseActivity;
import com.etatech.test.utils.MyImageGetter;

public class TestHtmlTextViewActivity extends BaseActivity<ActivityTestHtmlTextViewBinding> {

    @Override
    public ActivityTestHtmlTextViewBinding onCreateView(Bundle savedInstanceState) {
        return DataBindingUtil.setContentView(TestHtmlTextViewActivity.this, R.layout.activity_test_html_text_view);
    }

    @Override
    public void init() {
        String name = "MM";
        String num = "9788";
        String htmlStr = "是否花费" + " <img src=\"" + R.drawable.icon_diamond + "\"> " + "<font color=\'#F1A803\'>" + num + "</font> " + "向" + name + "赠送礼物？";
        binding.tvHtmlText.setText(Html.fromHtml(htmlStr, new MyImageGetter(), null));
    }
}

