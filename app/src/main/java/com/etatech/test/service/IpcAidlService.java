package com.etatech.test.service;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;

import com.blankj.utilcode.util.LogUtils;

public class IpcAidlService extends Service {
    public static final int MESSAGE_FROM_CLIENT = 100;
    public static final int MESSAGE_FROM_SERVICE = 200;
    private final Messenger serviceMessenger = new Messenger(new ServiceMessengerHandler());

    @Override
    public IBinder onBind(Intent intent) {
        return serviceMessenger.getBinder();
    }

    private class ServiceMessengerHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MESSAGE_FROM_CLIENT:
                    LogUtils.e("Receive Message from Client:" + msg.getData().getString("msg"));

                    // 获取客户端传递过来的Messenger，通过这个Messenger回传消息给客户端
                    Messenger clientMessenger = msg.replyTo;
                    // 当然，回传消息还是要通过message
                    Message msgToClient = Message.obtain(null, MESSAGE_FROM_SERVICE);
                    Bundle bundle = new Bundle();
                    bundle.putString("msg", "**********Service**********");
                    msgToClient.setData(bundle);
                    try {
                        clientMessenger.send(msgToClient);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    super.handleMessage(msg);
                    break;
            }
        }
    }
}
