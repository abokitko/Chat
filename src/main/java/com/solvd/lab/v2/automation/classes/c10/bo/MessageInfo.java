package com.solvd.lab.v2.automation.classes.c10.bo;

import java.io.Serializable;

public class MessageInfo implements Serializable {
    public final static long serialVersionUID = 5;
    public String userName;
    public String message;
    public String hostName;
    public int port;

    public MessageInfo(String hostName, int port, String userName, String message) {
        this.hostName = hostName;
        this.port = port;
        this.userName = userName;
        this.message = message;
    }
}
