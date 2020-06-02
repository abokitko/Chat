package com.solvd.lab.v2.automation.classes.c10;

import com.solvd.lab.v2.automation.classes.c10.bo.ConnectMessage;
import com.solvd.lab.v2.automation.classes.c10.bo.ResponseMessage;
import com.solvd.lab.v2.automation.constant.C10Constant;
import com.solvd.lab.v2.automation.io.interfaces.Packable;
import com.solvd.lab.v2.automation.util.PropertyUtil;
import com.solvd.lab.v2.automation.util.SerializationUtil;


import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;



/**
 * 1. object streams
 * 2. task
 * 3. swap strings
 * 33. loggers (stdin, stdout, stderr)
 * 4. refactoring
 * 5. fixes
 */
public class Client {

    public static void main(String[] args) {
        final String HOST = PropertyUtil.getValueByKey(C10Constant.HOSTNAME);
        final int PORT = Integer.parseInt(PropertyUtil.getValueByKey(C10Constant.PORT));
        Scanner in = new Scanner(System.in);
        System.out.print("Enter username: ");
        final String userName = in.next();
        connect(HOST, PORT, userName);
        System.out.println(((ResponseMessage) getResponse()).getResp());
    }

    private static void connect(final String host, final int port, final String token) {
        Scanner in = new Scanner(System.in);
        System.out.print("Enter message: ");
        String msg = in.nextLine();
        Packable pkg = new ConnectMessage(host, port, token, msg);
        SerializationUtil.writeObject(pkg);

        in.close();
    }

    private static Packable getResponse() {
        return SerializationUtil.readResponse();
    }

}
