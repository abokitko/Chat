package com.solvd.lab.v2.automation.classes.c10;

import com.solvd.lab.v2.automation.classes.c10.bo.MessageInfo;
import com.solvd.lab.v2.automation.constant.C10Constant;
import com.solvd.lab.v2.automation.util.PropertyUtil;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.logging.Logger;


public class Client implements Runnable {
    private static Socket clientSocket = null;
    private static ObjectOutputStream dataPrintStream = null;
    private static ObjectInputStream dataInputStream = null;
    private static BufferedReader message = null;
    private static boolean leaved = false;
    private static boolean test = false;
    private static InetAddress ip;
    private static String hostname;


    public static void main(String[] args) throws IOException {
        final String HOST = PropertyUtil.getValueByKey(C10Constant.HOSTNAME);
        final int PORT = Integer.parseInt(PropertyUtil.getValueByKey(C10Constant.PORT));

//        if (args[0] == "true") {
//            test = true;
//        }

        String userName = "";

        try {
            ip = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        hostname = ip.getHostName();

        try {
            clientSocket = new Socket(HOST, PORT);
            message = new BufferedReader(new InputStreamReader(System.in));
            dataPrintStream = new ObjectOutputStream(clientSocket.getOutputStream());
            dataInputStream = new ObjectInputStream(clientSocket.getInputStream());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.print("Enter username: ");
        userName = message.readLine().trim();
        int currentLine = 0;
        if (clientSocket != null && dataInputStream != null && dataPrintStream != null) {
            try {
                new Thread(new Client()).start();

                while (!leaved) {
                    if (test) {
                        for (int i = 0; i < args.length; i++){
                            if (i != 0) {
                                dataPrintStream.writeObject(args[i]);
                                Thread.sleep(200);
                            }
                        }
                    } else {
                        Thread.sleep(200);
                        System.out.print("Enter message: ");
                        String sendMessage = message.readLine().trim();

                        MessageInfo messageInfo = new MessageInfo(hostname, PORT, userName, sendMessage);

                        dataPrintStream.writeObject(messageInfo);
                    }
                }

                clientSocket.close();
                dataInputStream.close();
                dataPrintStream.close();
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void run() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

        MessageInfo response;
        try {
            while ((response = (MessageInfo)dataInputStream.readObject()) != null){
                LocalDateTime now = LocalDateTime.now();
                System.out.println("[" + dtf.format(now) + "] " + response.userName + " : " + response.message);
                if (response.message.indexOf("Bye") != -1) {
                    break;
                }
            }
            leaved = true;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
