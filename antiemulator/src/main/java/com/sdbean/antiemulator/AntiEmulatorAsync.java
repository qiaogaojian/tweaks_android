package com.sdbean.antiemulator;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

import static com.sdbean.antiemulator.AntiEmulator.MESSAGE_FROM_CLIENT;
import static com.sdbean.antiemulator.AntiEmulator.MESSAGE_FROM_SERVICE;

/**
 * Created by Michael
 * Date:  2020/6/5
 * Func:
 */
public class AntiEmulatorAsync {

    private static final String TAG = "AntiEmulator";
    private static Messenger mService;

    /**
     * 用于构建客户端的Messenger对象，并处理服务端的消息
     */
    private static class MessengerHandler extends Handler {
        @Override
        public void handleMessage(Message message) {
            switch (message.what) {
                case MESSAGE_FROM_SERVICE:
                    Log.e(TAG, "receive message from service:" + message.getData().getBoolean("result", false));
                    listener.onResult(message.getData().getBoolean("result", false));
                    mContext.unbindService(mConnection);
                    mContext = null;
                    Log.e(TAG, "unbindService");
                    break;
                default:
                    super.handleMessage(message);
                    break;
            }
        }
    }

    /**
     * 客户端Messenger对象
     */
    private static Messenger mClientMessenger = new Messenger(new MessengerHandler());

    private static ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.e(TAG, "ServiceConnection-->" + System.currentTimeMillis());
            mService = new Messenger(iBinder);
            Message message = Message.obtain(null, MESSAGE_FROM_CLIENT);
            Bundle bundle = new Bundle();
            bundle.putString("msg", "hello service,this is client");
            message.setData(bundle);
            //将客户端的Messenger对象传递给服务端
            message.replyTo = mClientMessenger;
            try {
                mService.send(message);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            Log.e(TAG, "onServiceDisconnected-->binder died");
        }
    };

    private static CheckEmulatorListener listener;
    private static Context mContext;

    public static void checkEmulator(Context context, CheckEmulatorListener checkEmulatorListener) {
        mContext = context;
        listener = checkEmulatorListener;
        Intent intent = new Intent(context, EmulatorCheckService.class);
        mContext.bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }

    public interface CheckEmulatorListener {
        void onResult(boolean isEmulator);
    }

}
