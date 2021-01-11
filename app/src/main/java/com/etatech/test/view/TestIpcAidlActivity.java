package com.etatech.test.view;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import androidx.databinding.DataBindingUtil;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.blankj.utilcode.util.LogUtils;
import com.etatech.test.R;
import com.etatech.test.databinding.ActivityTestIpcAidlBinding;
import com.etatech.test.service.IpcAidlService;
import com.etatech.test.utils.BaseActivity;

import static com.etatech.test.service.IpcAidlService.MESSAGE_FROM_CLIENT;
import static com.etatech.test.service.IpcAidlService.MESSAGE_FROM_SERVICE;

public class TestIpcAidlActivity extends BaseActivity<ActivityTestIpcAidlBinding> {

    private static Messenger sendMessenger;
    private Messenger clientMessenger = new Messenger(new ClientMessengerHandler());
    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder iBinder) {
            sendMessenger = new Messenger(iBinder);
            Message msgToService = Message.obtain(null, MESSAGE_FROM_CLIENT);
            Bundle bundle = new Bundle();
            bundle.putString("msg","**********Client**********");
            msgToService.setData(bundle);
            msgToService.replyTo = clientMessenger;
            LogUtils.e("Send Message from Client to Server");
            showMessage("Send Message from Client to Server Process=>" + android.os.Process.myPid());
            try {
                sendMessenger.send(msgToService);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    public ActivityTestIpcAidlBinding onCreateView(Bundle savedInstanceState) {
        return DataBindingUtil.setContentView(this, R.layout.activity_test_ipc_aidl);
    }

    @Override
    public void init() {
        Intent intent = new Intent(this, IpcAidlService.class);
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    private void showMessage(String msg) {
        String oriText = binding.tvInfo.getText().toString();
        binding.tvInfo.setText(oriText + "\n" + msg);
    }

    private class ClientMessengerHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MESSAGE_FROM_SERVICE:
                    LogUtils.e("Receive Message from Server:" + msg.getData().getString("msg"));
                    showMessage("Receive Message from Server:" + msg.getData().getString("msg"));
                    break;
                default:
                    super.handleMessage(msg);
                    break;
            }
        }
    }
}
