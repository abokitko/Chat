package com.solvd.lab.v2.automation;

import com.solvd.lab.v2.automation.classes.MySql;
import com.solvd.lab.v2.automation.constant.C10Constant;
import com.solvd.lab.v2.automation.filters.Filter;
import com.solvd.lab.v2.automation.classes.MessageInfo;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;

import com.solvd.lab.v2.automation.util.XMLHistoryWriter;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import java.io.File;

import java.io.IOException;
import java.sql.SQLException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Server {

    public static void main(String[] args) throws InterruptedException, IOException, SAXException, ParserConfigurationException, SQLException {
        MySql mySql = new MySql();
        mySql.createTable();
        listen();
    }

    public static List<String> listFilesForFolder(final File folder) {
        List<String> files;
        files = new ArrayList<String> ();
        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                listFilesForFolder(fileEntry);
            } else {
                files.add(fileEntry.getName());
            }
        }
        return files;
    }

    private static void listen() throws InterruptedException, ParserConfigurationException, SAXException, IOException, SQLException {
        List<String> filesPrevious = listFilesForFolder(new File(C10Constant.MESSAGES_PATH));
        List<String> files = filesPrevious;

        while (true) {
            File dir= new File(C10Constant.MESSAGES_PATH);
            files = listFilesForFolder(dir);

            if (!files.equals(filesPrevious)) {
                List<String> difference = new ArrayList<String>(files);
                difference.removeAll(filesPrevious);

                for (String file: difference) {
                    Connection conn = new Connection(file);
                    conn.run();
                }
            }

            Thread.sleep(2000);
            filesPrevious = files;
        }
    }

    static class Connection extends Thread {
        private MySql mysql = new MySql();

        private String path;
        private Filter filter = new Filter();
        private XMLHistoryWriter xmlHistoryWriter = new XMLHistoryWriter();

        public String convertTime(long time){
            Date date = new Date(time);
            Format format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            return format.format(date);
        }

        public Connection(String path) throws IOException, SAXException, ParserConfigurationException, SQLException {
            this.path = path;
        }

        public void run() {
            MessageInfo msg = readMessage(this.path);

            msg.message = filter.apply(msg.message);
            mysql.addNewMessage(msg);

            System.out.println("[" + convertTime(Long.valueOf(msg.date)) + "] " + msg.userName + ": " + msg.message);
        }
    }

    private static MessageInfo readMessage(String filePath) {
        try {
            File fXmlFile = new File(C10Constant.MESSAGES_PATH + "/" + filePath);

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);

            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("Message");
            Node nNode = nList.item(0);
            MessageInfo message;

            if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                Element eElement = (Element) nNode;
                message = new MessageInfo(eElement.getElementsByTagName("Host").item(0).getTextContent(),
                        Integer.parseInt(eElement.getElementsByTagName("Port").item(0).getTextContent()),
                        eElement.getElementsByTagName("Date").item(0).getTextContent(),
                        eElement.getElementsByTagName("Name").item(0).getTextContent(),
                        eElement.getElementsByTagName("String").item(0).getTextContent());

                return message;
            }
        } catch (ParserConfigurationException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (SAXException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
