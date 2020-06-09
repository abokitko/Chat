package com.solvd.lab.v2.automation.classes;

public class MessageInfo {
    public String userName;
    public String message;
    public String hostName;
    public int port;
    public String date;

    public MessageInfo(String hostName, int port, String date, String userName, String message) {
        this.hostName = hostName;
        this.port = port;
        this.date = date;
        this.userName = userName;
        this.message = message;
    }
}
