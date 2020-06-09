package com.solvd.lab.v2.automation.util;

import com.solvd.lab.v2.automation.classes.MessageInfo;
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
import java.io.FileOutputStream;
import java.io.IOException;

public class XMLHistoryWriter {
    private String filePath = "src/main/resources/history.xml";
    private DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
    private Document document = documentBuilder.parse(filePath);


    public XMLHistoryWriter() throws ParserConfigurationException, IOException, SAXException {
    }

    public void addNewMessage(MessageInfo messageInfo) {
        Node root = document.getDocumentElement();

        Element newMessageObject = document.createElement("Message");
        Element messageElement = document.createElement("String");
        Element nameElement = document.createElement("Name");
        Element dateElement = document.createElement("Date");
        Element hostElement = document.createElement("Host");
        Element portElement = document.createElement("Port");

        messageElement.setTextContent(messageInfo.message.trim());
        nameElement.setTextContent(messageInfo.userName);
        dateElement.setTextContent(messageInfo.date);
        hostElement.setTextContent(messageInfo.hostName);
        portElement.setTextContent(String.valueOf(messageInfo.port));

        newMessageObject.appendChild(dateElement);
        newMessageObject.appendChild(nameElement);
        newMessageObject.appendChild(messageElement);
        newMessageObject.appendChild(hostElement);
        newMessageObject.appendChild(portElement);

        root.appendChild(newMessageObject);

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