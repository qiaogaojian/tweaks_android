package com.etatech.test.view;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import com.etatech.test.R;
import com.etatech.test.adapter.LogAdapter;
import com.etatech.test.databinding.ActivityTestSocketBinding;
import com.etatech.test.network.Socket.BaseSocketClient;
import com.etatech.test.network.Socket.BaseSocketServer;
import com.etatech.test.network.Socket.TcpClient;
import com.etatech.test.utils.BaseActivity;
import com.etatech.test.utils.Tools;
import com.etatech.test.utils.ui.ClickUtil;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.ref.WeakReference;
import java.util.Arrays;

import rx.functions.Action1;

import static com.etatech.test.utils.App.logArr;

public class TestSocketActivity extends BaseActivity<ActivityTestSocketBinding> {
    private static WeakReference<LogAdapter> adapter;
    private static WeakReference<TestSocketActivity> curActivity;
    private BaseSocketClient client;
    private int serverPort = 9799;

    @Override
    public ActivityTestSocketBinding onCreateView(Bundle savedInstanceState) {
        return DataBindingUtil.setContentView(this, R.layout.activity_test_socket);
    }

    @Override
    public void init() {
        adapter = new WeakReference<LogAdapter>(new LogAdapter(logArr));
        binding.listChat.setLayoutManager(new LinearLayoutManager(this));
        binding.listChat.setAdapter(adapter.get());

        initDataReceiver();

        curActivity = new WeakReference<TestSocketActivity>(this);

        ClickUtil.setOnClick(binding.btnHost, new Action1() {
            @Override
            public void call(Object o) {
                BaseSocketServer server = new BaseSocketServer(serverPort);
                server.runServerSingle();
                String ip = Tools.getIPAddress(true);
                binding.tvAddress.setText(ip + ':' + serverPort);
                Tools.addLog(TestSocketActivity.this, "Server Hosted, the IP Address is:" + ip + "Host is:" + serverPort, R.color.code_orange);
                adapter.get().setLogArrList(logArr);
            }
        });
        ClickUtil.setOnClick(binding.btnConnect, new Action1() {
            @Override
            public void call(Object o) {
                String ip = binding.etAddress.getText().toString();
                int port = Integer.parseInt(binding.etPort.getText().toString());

                client = new BaseSocketClient(ip, port);
                try {
                    client.connectServer();
                    Tools.addLog(TestSocketActivity.this, "Server Connected, the IP Address is:" + ip + "Host is:" + port, R.color.code_orange);
                    adapter.get().setLogArrList(logArr);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        ClickUtil.setOnClick(binding.btnSend, new Action1() {
            @Override
            public void call(Object o) {
                String msg = binding.etMessage.getText().toString();
                try {
                    client.sendSingle(msg);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        ClickUtil.setOnClick(binding.btnConnect, new Action1() {
            @Override
            public void call(Object o) {
                connect();
            }
        });
        ClickUtil.setOnClick(binding.btnSend, new Action1() {
            @Override
            public void call(Object o) {
                send();
            }
        });
    }

    private void connect() {
        String ip = binding.etAddress.getText().toString();
        String port = binding.etPort.getText().toString();

        TcpClient.getInstance().connect(ip, Integer.parseInt(port));

    }

    private void send() {
        if (TcpClient.getInstance().isConnect()) {
            byte[] data = binding.etMessage.getText().toString().getBytes();
            TcpClient.getInstance().sendByteCmd(data, 1001);
        }
    }

    private void initDataReceiver() {
        TcpClient.getInstance().setOnDataReceiveListener(listener);
    }

    private static TcpClient.OnDataReceiveListener listener = new TcpClient.OnDataReceiveListener() {
        @Override
        public void onConnectSuccess() {
            Tools.addLog(curActivity.get(), "已连接", R.color.code_orange);
            adapter.get().setLogArrList(logArr);
        }

        @Override
        public void onConnectFail() {
            Tools.addLog(curActivity.get(), "未连接", R.color.code_orange);
            adapter.get().setLogArrList(logArr);
        }

        @Override
        public void onDataReceive(byte[] buffer, int size, int requestCode) {
            byte[] data = new byte[size];
            System.arraycopy(buffer, 0, data, 0, size);

            final String oxVlaue;
            try {
                oxVlaue = new String(data, "UTF-8");
                Tools.addLog(curActivity.get(), "onDataReceive requestCode = " + requestCode + ",content = " + oxVlaue, R.color.code_orange);
                adapter.get().setLogArrList(logArr);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        listener = null;
    }
}
