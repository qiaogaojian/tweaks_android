package com.etatech.test.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;

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
        binding.tvHtmlText.setText(getDiamondHtml(name, num));
    }

    public Spanned getDiamondHtml(String name, String num) {
        String htmlStr = getString(R.string.diamond_html,Integer.toString(R.drawable.icon_diamond) ,num,name);
        return Html.fromHtml(htmlStr, new MyImageGetter(), null);
    }
}

