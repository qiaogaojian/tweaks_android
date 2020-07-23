package com.etatech.test.network.Socket;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.blankj.utilcode.util.LogUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Created by Michael
 * Date: 2020/7/23
 * Func: tcp client  长度无限制
 */
public class TcpClient {

    private static TcpClient instance;

    private TcpClient() {
    }

    public static TcpClient getInstance() {
        if (instance == null) {
            synchronized (TcpClient.class) {
                instance = new TcpClient();
            }
        }
        return instance;
    }

    private Socket mSocket;

    private OutputStream outputStream;
    private InputStream inputStream;

    private SocketThread socketThread;
    private boolean isStop = false;  // thread flag

    private OnDataReceiveListener onDataReceiveListener = null;
    private int requestCode = -1;

    public interface OnDataReceiveListener {
        public void onConnectSuccess();

        public void onConnectFail();

        public void onDataReceive(byte[] buffer, int size, int requestCode);
    }

    public void setOnDataReceiveListener(OnDataReceiveListener listener) {
        onDataReceiveListener = listener;
    }

    private class SocketThread extends Thread {
        private String ip;
        private int port;

        public SocketThread(String ip, int port) {
            this.ip = ip;
            this.port = port;
        }

        @Override
        public void run() {
            LogUtils.e("SocketThread start");
            super.run();

            // connect
            try {
                if (mSocket != null) {
                    mSocket.close();
                    mSocket = null;
                }

                InetAddress ipAddress = InetAddress.getByName(ip);
                mSocket = new Socket(ipAddress, port);

//                // 设置不延时发送
//                mSocket.setTcpNoDelay(true);
//                // 设置输入输出缓冲流大小
//                mSocket.setSendBufferSize(8 * 1024);
//                mSocket.setReceiveBufferSize(8 * 1024);

                if (isConnect()) {
                    outputStream = mSocket.getOutputStream();
                    inputStream = mSocket.getInputStream();

                    isStop = false;

                    uiHandler.sendEmptyMessage(1);
                } else {
                    uiHandler.sendEmptyMessage(-1);
                    LogUtils.e("SocketThread connect fail");
                    return;
                }
            } catch (IOException e) {
                uiHandler.sendEmptyMessage(-1);
                LogUtils.e("SocketThread connect ip exception = " + e.getMessage());
                e.printStackTrace();
                return;
            }
            LogUtils.e("SocketThread connect over");

            while (isConnect() && !isStop && !isInterrupted()) {
                int size;
                try {
                    byte[] buffer = new byte[1024];
                    if (inputStream == null) {
                        return;
                    }
                    size = inputStream.read(buffer);
                    if (size > 0) {
                        Message msg = new Message();
                        msg.what = 100;
                        Bundle bundle = new Bundle();
                        bundle.putByteArray("data", buffer);
                        bundle.putInt("size", size);
                        bundle.putInt("requestCode", requestCode);
                        msg.setData(bundle);
                        uiHandler.sendMessage(msg);
                    }
                    LogUtils.e("SocketThread read listening");
                } catch (IOException e) {
                    uiHandler.sendEmptyMessage(-1);
                    LogUtils.e("SocketThread read io exception = " + e.getMessage());
                    e.printStackTrace();
                    return;
                }
            }
        }
    }

    public void connect(String ip, int port) {
        socketThread = new SocketThread(ip, port);
        socketThread.start();
    }

    public boolean isConnect() {
        boolean flag = false;
        if (mSocket != null) {
            flag = mSocket.isConnected();
        }
        return flag;
    }

    public void disconnect() {
        isStop = true;

        try {
            if (outputStream != null) {
                outputStream.close();
            }
            if (inputStream != null) {
                inputStream.close();
            }
            if (mSocket != null) {
                mSocket.close();
                mSocket = null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (socketThread != null) {
            socketThread.interrupt();
        }
    }

    public void sendByteCmd(final byte[] mBuffer, int requestCode) {
        this.requestCode = requestCode;

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    if (outputStream != null) {
                        outputStream.write(mBuffer);
                        outputStream.flush();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void sendStrCmds(String cmd, int requestCode) {
        byte[] mBuffer = cmd.getBytes();
        sendByteCmd(mBuffer, requestCode);
    }

    public void sendChsPrtCmds(String content, int requestCode) {
        try {
            byte[] mBuffer = content.getBytes("UTF-8");
            sendByteCmd(mBuffer, requestCode);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }

    Handler uiHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case -1:
                    if (onDataReceiveListener != null) {
                        onDataReceiveListener.onConnectFail();
                        disconnect();
                    }
                    break;
                case 1:
                    if (onDataReceiveListener != null) {
                        onDataReceiveListener.onConnectSuccess();
                    }
                    break;
                case 100:
                    Bundle bundle = msg.getData();
                    byte[] buffer = bundle.getByteArray("data");
                    int size = bundle.getInt("size");
                    int mRequestCode = bundle.getInt("requestCode");
                    if (onDataReceiveListener != null) {
                        onDataReceiveListener.onDataReceive(buffer, size, mRequestCode);
                    }
                    break;
            }
        }
    };
}