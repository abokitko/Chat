package com.solvd.lab.v2.automation.classes.c10;

import com.solvd.lab.v2.automation.constant.C10Constant;
import com.solvd.lab.v2.automation.util.PropertyUtil;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Logger;


public class Client implements Runnable {
    private static Socket clientSocket = null;
    private static PrintStream dataPrintStream = null;
    private static DataInputStream dataInputStream = null;
    private static BufferedReader message = null;
    private static boolean leaved = false;
    private static boolean test = false;

    public static void main(String[] args) {
        final String HOST = PropertyUtil.getValueByKey(C10Constant.HOSTNAME);
        final int PORT = Integer.parseInt(PropertyUtil.getValueByKey(C10Constant.PORT));

        if (args[0] == "true") {
            test = true;
        }

        try {
            clientSocket = new Socket(HOST, PORT);
            message = new BufferedReader(new InputStreamReader(System.in));
            dataInputStream = new DataInputStream(clientSocket.getInputStream());
            dataPrintStream = new PrintStream(clientSocket.getOutputStream());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        int currentLine = 0;
        if (clientSocket != null && dataInputStream != null && dataPrintStream != null) {
            try {
                new Thread(new Client()).start();

                while (!leaved) {
                    if (test) {
                        for (int i = 0; i < args.length; i++){
                            if (i != 0) {
                                dataPrintStream.println(args[i]);
                                Thread.sleep(200);
                            }
                        }
                    } else {
                        if (currentLine == 0) {
                            System.out.print("Enter username: ");
                            currentLine++;
                        } else {
                            Thread.sleep(200);
                            System.out.print("Enter message: ");
                        }
                        dataPrintStream.println(message.readLine().trim());
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

        String response;
        try {
            while ((response = dataInputStream.readLine()) != null){
                LocalDateTime now = LocalDateTime.now();
                System.out.println("[" + dtf.format(now) + "] " + response);
                if (response.indexOf("Bye") != -1) {
                    break;
                }
            }
            leaved = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
