package com.etatech.test.view;

import android.databinding.DataBindingUtil;
import android.graphics.Color;
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
import com.etatech.test.netstate.NetWorkMonitor;
import com.etatech.test.netstate.NetWorkMonitorManager;
import com.etatech.test.netstate.NetWorkState;
import com.etatech.test.utils.BaseActivity;
import com.etatech.test.utils.Tools;

import java.util.ArrayList;
import java.util.List;

import static com.etatech.test.utils.App.logArr;

public class TestLifecycleActivity extends BaseActivity<ActivityTestLifecycleBinding> {
    private LogAdapter adapter;


    @Override
    public ActivityTestLifecycleBinding onCreateView(Bundle savedInstanceState) {
        return DataBindingUtil.setContentView(this, R.layout.activity_test_lifecycle);
    }

    @Override
    public void init() {
        adapter = new LogAdapter(logArr);
        binding.listStateLog.setLayoutManager(new LinearLayoutManager(this));
        binding.listStateLog.setAdapter(adapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Tools.addLog(this,"onCreate", R.color.code_green);
        adapter.setLogArrList(logArr);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Tools.addLog(this,"onStart", R.color.code_green_blue);
        adapter.setLogArrList(logArr);
        NetWorkMonitorManager.getInstance().register(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Tools.addLog(this,"onResume", R.color.code_blue);
        adapter.setLogArrList(logArr);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Tools.addLog(this,"onPause", R.color.code_yellow);
        adapter.setLogArrList(logArr);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Tools.addLog(this,"onStop", R.color.code_orange);
        adapter.setLogArrList(logArr);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Tools.addLog(this,"onDestroy", R.color.code_red);
        adapter.setLogArrList(logArr);
        NetWorkMonitorManager.getInstance().unregister(this);
    }

    //不加注解默认监听所有的状态，方法名随意，只需要参数是一个NetWorkState即可
    //@NetWorkMonitor(monitorFilter = {NetWorkState.GPRS})//只接受网络状态变为GPRS类型的消息
    public void onNetWorkStateChange(NetWorkState netWorkState) {
        Tools.addLog(this,"onNetWorkStateChange >>> :" + netWorkState.name(), R.color.code_red);
        adapter.setLogArrList(logArr);
    }

}
