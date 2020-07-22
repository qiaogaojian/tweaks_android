package com.etatech.test.network.Socket;

import android.os.Handler;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Created by Michael
 * Date:  2020/7/22
 * Func:
 */
public class BaseSocketClient {
    private String serverHost;
    private int serverPort;
    private Socket socket;
    private OutputStream outputStream;
    private Thread thread;

    public BaseSocketClient(String host, int port) {
        this.serverHost = host;
        this.serverPort = port;
    }

    public void connectServer() throws IOException {
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    socket = new Socket(serverHost, serverPort);
                    outputStream = socket.getOutputStream();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

    public void sendSingle(String message) throws IOException {
        try {
            this.outputStream.write(message.getBytes("UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.outputStream.close();
        this.socket.close();
    }
}
