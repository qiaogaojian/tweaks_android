package com.etatech.test.java.socket;

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

    public BaseSocketServer(int port) {
        this.port = port;
    }

    public void runServerSingle() {

        try {
            server = new ServerSocket(port);

            System.out.println("base socket server started.");

            socket = server.accept();
            inputStream = socket.getInputStream();

            byte[] readBytes = new byte[MAX_BUFFER_SIZE];
            int msgLen;
            StringBuilder stringBuilder = new StringBuilder();

            while ((msgLen = inputStream.read(readBytes)) != -1) {
                stringBuilder.append(new String(readBytes, 0, msgLen, "UTF-8"));
            }

            System.out.println("get message from client:" + stringBuilder);

            inputStream.close();
            socket.close();
            server.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        System.out.println("Start Server");
        BaseSocketServer server = new BaseSocketServer(60000);
        server.runServerSingle();
    }
}

