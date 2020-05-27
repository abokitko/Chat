package com.solvd.lab.v2.automation.classes.c10;

import com.solvd.lab.v2.automation.classes.c10.bo.ConnectMessage;
import com.solvd.lab.v2.automation.classes.c10.bo.ResponseMessage;
import com.solvd.lab.v2.automation.io.interfaces.Packable;
import com.solvd.lab.v2.automation.util.SerializationUtil;
import static java.nio.file.StandardWatchEventKinds.*;
import com.vdurmont.emoji.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.sql.Connection;
import java.util.*;
import java.util.logging.Logger;

public class Server {
    private static final Logger LOGGER = Logger.getLogger(Server.class.getSimpleName());

    private static final List<String> AVAILABLE_CLIENTS = Arrays.asList("user");
    private static final String HOST = "127.0.0.1";
    private static final int PORT = 8000;
    private static final String TOKEN = "user";


    public static void main(String[] args) {
        LOGGER.info(String.format("Listening on %s:%d", HOST, PORT));

        while (true) {
            try {
                listen();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // TODO: filter msgs
    private static String changeChar(String str){
        char[] characters = str.toCharArray();
        int rand = (int)(Math.random()*str.length());
        characters[rand] = '*';
        return new String(characters);
    }
    // TODO: filter msgs
    private static void listen() throws IOException, InterruptedException {
        WatchService watchService = FileSystems.getDefault().newWatchService();
        String watchingDir = System.getProperty("user.dir") + "/src/main/resources";
        Path dir = Paths.get(watchingDir);
        WatchKey watchKey = dir.register(watchService, ENTRY_MODIFY, ENTRY_CREATE);


        watchKey = watchService.take();
        ArrayList<String> offenses = new ArrayList<>();
        try (Scanner sc = new Scanner(new File("src/main/resources/offenses.txt"))){
            while (sc.hasNextLine()) {
                String[] lines = sc.nextLine().split(",");
                for(String line : lines){
                    offenses.add(String.valueOf(line));
                }

            }
        }
        for (WatchEvent<?> event : watchKey.pollEvents()) {
            if (String.valueOf(event.context()).equals("serial")) {
                Packable obj = SerializationUtil.readObject();
                ConnectMessage msg = ((ConnectMessage) obj);

                if (msg.getHost().equals(HOST) && msg.getPort() == PORT) {
                    String message = msg.getMessage();
                    message = EmojiParser.parseToUnicode(message);
                    if (offenses.contains(message.toLowerCase())) {
                        message = changeChar(message);
                    }
                    LOGGER.info(String.format("Received message from %s: %s ", msg.getToken(), message));
                    Packable resp = new ResponseMessage(HOST, PORT, TOKEN, "SUCCESS", 200);
                    sendResponse(resp);
                }

            }
        }
    }

    private static void sendResponse(Packable pkg) {
        SerializationUtil.writeResponse(pkg);
    }


}
