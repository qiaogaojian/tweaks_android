package com.sdbean.antiemulator;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import androidx.annotation.Nullable;
import android.util.Log;

import static com.sdbean.antiemulator.AntiEmulator.MESSAGE_FROM_CLIENT;
import static com.sdbean.antiemulator.AntiEmulator.MESSAGE_FROM_SERVICE;


public class EmulatorCheckService extends Service {
    private final String TAG = "AntiEmulator";

    /**
     * 处理来自客户端的消息，并用于构建Messenger
     */
    private class MessengerHandler extends Handler {
        @Override
        public void handleMessage(Message message) {
            switch (message.what) {
                case MESSAGE_FROM_CLIENT:
                    Log.e(TAG, "receive message from client:" + message.getData().getString("msg"));
                    //获取客户端传递过来的Messenger，通过这个Messenger回传消息给客户端
                    Messenger client = message.replyTo;
                    //当然，回传消息还是要通过message
                    Message msg = Message.obtain(null, MESSAGE_FROM_SERVICE);
                    Bundle bundle = new Bundle();
                    bundle.putBoolean("result", AntiEmulator.checkEmulator(EmulatorCheckService.this));
                    msg.setData(bundle);
                    stopSelf();
                    Log.e(TAG, "stopSelf");
                    try {
                        client.send(msg);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    super.handleMessage(message);
                    break;
            }
        }
    }

    /**
     * 构建Messenger对象
     */
    private final Messenger mMessenger = new Messenger(new MessengerHandler());

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        //将Messenger对象的Binder返回给客户端
        return mMessenger.getBinder();
    }
}

