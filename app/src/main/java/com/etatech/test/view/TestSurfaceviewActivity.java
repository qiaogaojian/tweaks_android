package com.etatech.test.view;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.SurfaceHolder;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.etatech.test.R;
import com.etatech.test.adapter.StateLogAdapter;
import com.etatech.test.utils.BaseActivity;
import com.etatech.test.utils.ScrollTextView;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

public class TestSurfaceviewActivity extends BaseActivity implements View.OnClickListener
{
    private ScrollTextView  scrollTextView;
    private TextView        tvState;
    private Button          btnVisible;
    private Button          btnGone;
    private RecyclerView    logList;
    private List<String>    logArr;
    private StateLogAdapter adapter;

    private SurfaceHolder.Callback callback = new SurfaceHolder.Callback()
    {
        @Override
        public void surfaceCreated(SurfaceHolder holder)
        {
//            tvState.setText("----------- surfaceHolder被创建了------------");
            logArr.add("----------- surfaceHolder被创建了------------");
            adapter.setLogArrList(logArr);
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height)
        {
//            tvState.setText("----------- surfaceHolder发生改变了------------");
            logArr.add("----------- surfaceHolder发生改变了------------");
            adapter.setLogArrList(logArr);
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder)
        {
//            tvState.setText("----------- surfaceHolder被销毁了------------");
            logArr.add("----------- surfaceHolder被销毁了------------");
            adapter.setLogArrList(logArr);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        logArr = new ArrayList<String>();
        setContentView(R.layout.activity_test_surfaceview);
        scrollTextView = (ScrollTextView) findViewById(R.id.stv_surface_view);
        tvState = (TextView) findViewById(R.id.tv_surfaceview_state);
        btnVisible = (Button) findViewById(R.id.btn_surfaceview_visible);
        btnGone = (Button) findViewById(R.id.btn_surfaceview_gone);
        logList = (RecyclerView) findViewById(R.id.list_state_log);
        scrollTextView.getHolder().addCallback(callback);
        btnVisible.setOnClickListener(this);
        btnGone.setOnClickListener(this);
        adapter = new StateLogAdapter(logArr);
        logList.setLayoutManager(new LinearLayoutManager(this));
        logList.setAdapter(adapter);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.btn_surfaceview_visible:
                scrollTextView.setVisibility(View.VISIBLE);
                break;
            case R.id.btn_surfaceview_gone:
                scrollTextView.setVisibility(View.GONE);
                break;
        }
    }
}
