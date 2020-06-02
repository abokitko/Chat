package com.solvd.lab.v2.automation.classes;

import com.solvd.lab.v2.automation.classes.c10.Client;

public class Tests {
    public static void main(String[] args) {
        Client client = new Client();
        client.main(new String[]{"true", "Nastya", "hello jack",  "java", "leave"});
    }
}
