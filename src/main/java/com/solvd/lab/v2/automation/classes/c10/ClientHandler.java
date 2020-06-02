package com.solvd.lab.v2.automation.classes.c10;

import com.solvd.lab.v2.automation.filter.Filter;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Logger;

class ClientHandler extends Thread  {
    private static final Logger LOGGER = Logger.getLogger(Server.class.getSimpleName());
    private Socket clientSocket;
    private PrintStream dataPrintStream;
    private Filter filter = new Filter();
    private DataInputStream dataInputStream;
    private int maxClients;
    private final ClientHandler[] handlers;

    public ClientHandler(Socket clientSocket, ClientHandler[] handlers) {
        this.clientSocket = clientSocket;
        this.handlers = handlers;
        maxClients = handlers.length;
    }

    @Override
    public void run() {
        ClientHandler[] handlers = this.handlers;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        try {
            dataInputStream = new DataInputStream(clientSocket.getInputStream());
            dataPrintStream = new PrintStream(clientSocket.getOutputStream());
            String name = dataInputStream.readLine().trim();
            while (true) {
                String message = dataInputStream.readLine();
                if (message.contains("leave")){
                    break;
                }
                message = filter.apply(message);

                for (int i = 0; i < maxClients; i++)
                    if (handlers[i] != null) {
                        handlers[i].dataPrintStream.println(name + ": " + message);
                        LocalDateTime now = LocalDateTime.now();
                        LOGGER.info("[" + dtf.format(now) + "] " + name + ": " + message);
                    }

            }

            for (int i = 0; i < maxClients; i++)
                if (handlers[i] != null && handlers[i] != this) {
                    LocalDateTime now = LocalDateTime.now();
                    handlers[i].dataPrintStream.println("[" + dtf.format(now) + "]"  + " User " + name + " left the chat.");
                }

            LocalDateTime now = LocalDateTime.now();
            LOGGER.info("[" + dtf.format(now) + "] " + name + " left the chat.");
            dataPrintStream.println("Bye, " + name);

            for (int i = 0; i < maxClients; i++)
                if (handlers[i] == this) {
                    handlers[i] = null;
                }

            clientSocket.close();
            dataInputStream.close();
            dataPrintStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
