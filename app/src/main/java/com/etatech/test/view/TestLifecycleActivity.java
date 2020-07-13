package com.etatech.test.view;

import android.databinding.DataBindingUtil;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Html;
import android.text.Spannable;
import android.text.Spanned;

import com.etatech.test.R;
import com.etatech.test.adapter.LogAdapter;
import com.etatech.test.adapter.StateLogAdapter;
import com.etatech.test.databinding.ActivityTestLifecycleBinding;
import com.etatech.test.utils.BaseActivity;

import java.util.ArrayList;
import java.util.List;

public class TestLifecycleActivity extends BaseActivity<ActivityTestLifecycleBinding> {
    private LogAdapter adapter;
    private List<Spanned> logArr;

    @Override
    public ActivityTestLifecycleBinding onCreateView(Bundle savedInstanceState) {
        return DataBindingUtil.setContentView(this, R.layout.activity_test_lifecycle);
    }

    @Override
    public void init() {
        logArr = new ArrayList<>();
        adapter = new LogAdapter(logArr);
        binding.listStateLog.setLayoutManager(new LinearLayoutManager(this));
        binding.listStateLog.setAdapter(adapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        logArr.add(Html.fromHtml(
                String.format("<font color=\'#FFA41A\'>%s:</font>", "----------- OnCreate ------------")));
        adapter.setLogArrList(logArr);
    }

    @Override
    protected void onStart() {
        super.onStart();
        logArr.add(Html.fromHtml(
                String.format("<font color=\'#FFA41A\'>%s:</font>", "----------- OnCreate ------------")));
        adapter.setLogArrList(logArr);
    }

    @Override
    protected void onResume() {
        super.onResume();
        logArr.add(Html.fromHtml(
                String.format("<font color=\'#FFA41A\'>%s:</font>", "----------- OnCreate ------------")));
        adapter.setLogArrList(logArr);
    }

    @Override
    protected void onPause() {
        super.onPause();
        logArr.add(Html.fromHtml(
                String.format("<font color=\'#FFA41A\'>%s:</font>", "----------- OnCreate ------------")));
        adapter.setLogArrList(logArr);
    }

    @Override
    protected void onStop() {
        super.onStop();
        logArr.add(Html.fromHtml(
                String.format("<font color=\'#FFA41A\'>%s:</font>", "----------- OnCreate ------------")));
        adapter.setLogArrList(logArr);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        logArr.add(Html.fromHtml(
                String.format("<font color=\'#FFA41A\'>%s:</font>", "----------- OnCreate ------------")));
        adapter.setLogArrList(logArr);
    }
}
