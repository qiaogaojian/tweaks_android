package com.etatech.test.view;

import androidx.databinding.ViewDataBinding;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.SurfaceHolder;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.etatech.test.R;
import com.etatech.test.adapter.StateLogAdapter;
import com.etatech.test.utils.BaseActivity;
import com.etatech.test.view.custom.ScrollTextView;

import java.util.ArrayList;
import java.util.List;

public class TestSurfaceviewActivity extends BaseActivity implements View.OnClickListener
{
    private View            scrollRoot;
    private ScrollTextView  scrollTextView;
    private TextView        tvState;
    private Button          btnVisible;
    private Button          btnGone;
    private Button          btnClear;
    private RecyclerView    logList;
    private List<String>    logArr;
    private StateLogAdapter adapter;
    private int             index = 0;

    private SurfaceHolder.Callback callback = new SurfaceHolder.Callback()
    {
        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            logArr.add("----------- surfaceHolder被创建了------------");
            adapter.setLogArrList(logArr);
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            logArr.add("----------- surfaceHolder发生改变了------------");
            adapter.setLogArrList(logArr);
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            logArr.add("----------- surfaceHolder被销毁了------------");
            adapter.setLogArrList(logArr);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_surfaceview);

        scrollRoot = findViewById(R.id.include_scroll_text);
        scrollTextView = (ScrollTextView) findViewById(R.id.stv_surface_view);
        tvState = (TextView) findViewById(R.id.tv_surfaceview_state);
        btnVisible = (Button) findViewById(R.id.btn_surfaceview_visible);
        btnGone = (Button) findViewById(R.id.btn_surfaceview_gone);
        btnClear = (Button) findViewById(R.id.btn_clear);
        logList = (RecyclerView) findViewById(R.id.list_state_log);

        scrollTextView.getHolder().addCallback(callback);

        btnVisible.setOnClickListener(this);
        btnGone.setOnClickListener(this);
        btnClear.setOnClickListener(this);

        logArr = new ArrayList<String>();
        adapter = new StateLogAdapter(logArr);
        logList.setLayoutManager(new LinearLayoutManager(this));
        logList.setAdapter(adapter);
    }

    @Override
    public ViewDataBinding onCreateView(Bundle savedInstanceState) {
        return null;
    }

    @Override
    public void init() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_surfaceview_visible:
                index++;
                scrollTextView.setText(index + ": Test Surface View Test Surface View Test Surface View");
                scrollTextView.setVisibility(View.VISIBLE);
                scrollRoot.setVisibility(View.VISIBLE);
                break;
            case R.id.btn_surfaceview_gone:
                scrollTextView.setVisibility(View.GONE);
                scrollRoot.setVisibility(View.GONE);
                break;
            case R.id.btn_clear:
                logArr.clear();
                adapter.setLogArrList(logArr);
                break;
        }
    }
}
