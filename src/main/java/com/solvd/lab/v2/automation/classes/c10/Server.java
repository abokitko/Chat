package com.solvd.lab.v2.automation.classes.c10;

import com.solvd.lab.v2.automation.constant.C10Constant;
import com.solvd.lab.v2.automation.util.PropertyUtil;


import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Logger;

public class Server {
    private static Socket clientSocket = null;
    private static ServerSocket serverSocket = null;
    private static final Logger LOGGER = Logger.getLogger(Server.class.getSimpleName());
    private static int PORT = Integer.parseInt(PropertyUtil.getValueByKey(C10Constant.PORT));
    private static int maxClients = 20;
    private static final ClientHandler[] handlers = new ClientHandler[maxClients];

    public static void main(String[] args) {
        LOGGER.info(String.format("Listening on %d", PORT));

        try {
            serverSocket = new ServerSocket(PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }

        while (true) {
            try {
                clientSocket = serverSocket.accept();
                for (int i = 0; i < maxClients; i++)
                    if (handlers[i] == null) {
                        (handlers[i] = new ClientHandler(clientSocket, handlers)).start();
                        break;
                    }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
