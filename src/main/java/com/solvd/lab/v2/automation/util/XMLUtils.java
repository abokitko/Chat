package com.solvd.lab.v2.automation.util;

import com.solvd.lab.v2.automation.classes.MessageInfo;
import com.solvd.lab.v2.automation.constant.C10Constant;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

public class XMLUtils {
    private String filePath;
    private DocumentBuilder documentBuilder;
    private Document document;


    public XMLUtils() {
    }

    private void createNewUser() {
        try {
            File file = new File(filePath);
            file.createNewFile();
            System.out.println("Message added: " + file.getName());
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        try {
            FileWriter xmlStartLnes = new FileWriter(filePath);
            xmlStartLnes.write("<?xml version=\"1.0\" encoding=\"utf-8\" standalone=\"no\"?>");
            xmlStartLnes.write("<Message>");
            xmlStartLnes.write("</Message>");
            xmlStartLnes.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }


    public void writeMessage(MessageInfo messageInfo) throws ParserConfigurationException, IOException, SAXException {
        String currentDate = messageInfo.date;
        filePath = C10Constant.MESSAGES_PATH + "/" + currentDate + ".xml";
        documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();

        createNewUser();

        document = documentBuilder.parse(filePath);

        Node root = document.getDocumentElement();

        Element messageElement = document.createElement("String");
        Element nameElement = document.createElement("Name");
        Element dateElement = document.createElement("Date");
        Element hostElement = document.createElement("Host");
        Element portElement = document.createElement("Port");

        messageElement.setTextContent(messageInfo.message);
        nameElement.setTextContent(messageInfo.userName);
        dateElement.setTextContent(currentDate);
        hostElement.setTextContent(messageInfo.hostName);
        portElement.setTextContent(String.valueOf(messageInfo.port));

        root.appendChild(dateElement);
        root.appendChild(nameElement);
        root.appendChild(messageElement);
        root.appendChild(hostElement);
        root.appendChild(portElement);

        writeToDocument(document);
    }

    private void writeToDocument(Document document) throws TransformerFactoryConfigurationError {
        try {
            Transformer tr = TransformerFactory.newInstance().newTransformer();
            DOMSource source = new DOMSource(document);
            FileOutputStream fos = new FileOutputStream(filePath);
            StreamResult result = new StreamResult(fos);
            tr.transform(source, result);
        } catch (TransformerException | IOException e) {
            e.printStackTrace(System.out);
        }
    }

}
