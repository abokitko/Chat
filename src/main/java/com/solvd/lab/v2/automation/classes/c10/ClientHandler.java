package com.solvd.lab.v2.automation.classes.c10;

import com.solvd.lab.v2.automation.classes.c10.bo.MessageInfo;
import com.solvd.lab.v2.automation.filter.Filter;
import com.solvd.lab.v2.automation.util.XMLWriter;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.net.Socket;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Logger;

class ClientHandler extends Thread  {
    private static final Logger LOGGER = Logger.getLogger(Server.class.getSimpleName());
    private Socket clientSocket;
    private ObjectOutputStream dataPrintStream;
    private Filter filter = new Filter();
    private ObjectInputStream dataInputStream;
    private int maxClients;
    private final ClientHandler[] handlers;
    private XMLWriter xmlWriter;

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
            xmlWriter = new XMLWriter();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }

        try {
            dataPrintStream = new ObjectOutputStream(clientSocket.getOutputStream());
            dataInputStream = new ObjectInputStream(clientSocket.getInputStream());

            MessageInfo receivedObject;

            while (true) {
                receivedObject = (MessageInfo) dataInputStream.readObject();

                if (receivedObject.message.contains("leave")){
                    break;
                }

                receivedObject.message = filter.apply(receivedObject.message);

                for (int i = 0; i < maxClients; i++)
                    if (handlers[i] != null) {
                        handlers[i].dataPrintStream.writeObject(receivedObject);
                        LocalDateTime now = LocalDateTime.now();
                        xmlWriter.addNewMessage(receivedObject.hostName, receivedObject.port, Instant.now().getEpochSecond(), receivedObject.userName, receivedObject.message.trim());
                        LOGGER.info("[" + dtf.format(now) + "] " + receivedObject.userName + ": " + receivedObject.message.trim());
                    }

            }

            for (int i = 0; i < maxClients; i++)
                if (handlers[i] != null && handlers[i] != this) {
                    LocalDateTime now = LocalDateTime.now();
                    handlers[i].dataPrintStream.writeObject("[" + dtf.format(now) + "]"  + " User " + receivedObject.userName + " left the chat.");
                }

            LocalDateTime now = LocalDateTime.now();
            LOGGER.info("[" + dtf.format(now) + "] " + receivedObject.userName + " left the chat.");
            xmlWriter.addNewMessage(receivedObject.hostName, receivedObject.port, Instant.now().getEpochSecond(), receivedObject.userName, "left the chat");
            receivedObject.message = "Bye, " + receivedObject.userName;
            dataPrintStream.writeObject(receivedObject);

            for (int i = 0; i < maxClients; i++)
                if (handlers[i] == this) {
                    handlers[i] = null;
                }

            clientSocket.close();
            dataInputStream.close();
            dataPrintStream.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
