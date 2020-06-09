package com.solvd.lab.v2.automation;

import com.solvd.lab.v2.automation.classes.MessageInfo;
import com.solvd.lab.v2.automation.constant.C10Constant;
import com.solvd.lab.v2.automation.util.XMLUtils;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;


public class Client {
    private static BufferedReader bufferedReader = null;
    private static InetAddress ip;
    private static String hostname;
    private static String username = "";


    public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException {
        final String HOST = C10Constant.HOSTNAME;
        final int PORT = C10Constant.PORT;

        bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        try {
            ip = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        hostname = ip.getHostName();

        System.out.print("Enter username: ");
        username = bufferedReader.readLine().trim();
        System.out.print("Enter message: ");
        String message = bufferedReader.readLine().trim();

        XMLUtils xmlUtils = new XMLUtils();

        MessageInfo messageInfo = new MessageInfo(hostname, PORT, String.valueOf(new Date().getTime()), username, message);

        xmlUtils.writeMessage(messageInfo);
    }
}
