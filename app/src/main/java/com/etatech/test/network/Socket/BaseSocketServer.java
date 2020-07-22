package com.etatech.test.network.Socket;

import com.blankj.utilcode.util.LogUtils;
import com.etatech.test.R;
import com.etatech.test.utils.App;
import com.etatech.test.utils.Tools;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Michael
 * Date:  2020/7/22
 * Func:
 */
public class BaseSocketServer {
    private ServerSocket server;
    private Socket socket;
    private int port;
    private InputStream inputStream;
    private static final int MAX_BUFFER_SIZE = 1024;
    private Thread thread;

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public BaseSocketServer(int port) {
        this.port = port;
    }

    public void runServerSingle() {
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    server = new ServerSocket(port);
                    Tools.addLog("base socket server started.", R.color.code_grey);
                    LogUtils.e("base socket server started.");
                    socket = server.accept();
                    inputStream = socket.getInputStream();

                    byte[] readBytes = new byte[MAX_BUFFER_SIZE];
                    int msgLen;
                    StringBuilder stringBuilder = new StringBuilder();

                    while ((msgLen = inputStream.read(readBytes)) != -1) {
                        stringBuilder.append(new String(readBytes, 0, msgLen, "UTF-8"));
                    }
                    Tools.addLog("get message from client:" + stringBuilder, R.color.code_grey);
                    LogUtils.e("get message from client:" + stringBuilder);

                    inputStream.close();
                    socket.close();
                    server.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}

