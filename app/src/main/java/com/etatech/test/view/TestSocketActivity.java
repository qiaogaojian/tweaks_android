package com.etatech.test.view;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import com.etatech.test.R;
import com.etatech.test.adapter.LogAdapter;
import com.etatech.test.databinding.ActivityTestSocketBinding;
import com.etatech.test.network.Socket.BaseSocketClient;
import com.etatech.test.network.Socket.BaseSocketServer;
import com.etatech.test.utils.BaseActivity;
import com.etatech.test.utils.Tools;
import com.etatech.test.utils.ui.ClickUtil;

import java.io.IOException;

import rx.functions.Action1;

import static com.etatech.test.utils.App.logArr;

public class TestSocketActivity extends BaseActivity<ActivityTestSocketBinding> {
    private LogAdapter adapter;
    BaseSocketClient client;
    private int serverPort = 9799;

    @Override
    public ActivityTestSocketBinding onCreateView(Bundle savedInstanceState) {
        return DataBindingUtil.setContentView(this, R.layout.activity_test_socket);
    }

    @Override
    public void init() {
        adapter = new LogAdapter(logArr);
        binding.listChat.setLayoutManager(new LinearLayoutManager(this));
        binding.listChat.setAdapter(adapter);

        ClickUtil.setOnClick(binding.btnHost, new Action1() {
            @Override
            public void call(Object o) {
                BaseSocketServer server = new BaseSocketServer(serverPort);
                server.runServerSingle();
                String ip = Tools.getIPAddress(true);
                binding.tvAddress.setText(ip + ':' + serverPort);
                Tools.addLog(TestSocketActivity.this, "Server Hosted, the IP Address is:" + ip + "Host is:" + serverPort, R.color.code_orange);
                adapter.setLogArrList(logArr);
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
                    adapter.setLogArrList(logArr);
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
    }
}
