package com.solvd.lab.v2.automation.classes;

import java.sql.*;
import java.util.Calendar;

public class MySql {

    private String table = "data";
    private String url = "jdbc:mysql://localhost:3306/chat?useUnicode=true&serverTimezone=UTC";
    private Connection conn;

    public MySql() throws SQLException {
        this.conn = DriverManager.getConnection(url, "root", "12345");
    }

    public void addNewMessage(MessageInfo msg)
    {
        try
        {
            String query = " insert into " + table + " (username, message, date)" + " values (?, ?, ?)";

            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString(1, msg.userName);
            preparedStmt.setString(2, msg.message);
            preparedStmt.setString(3, msg.date);

            preparedStmt.execute();

            conn.close();
        }
        catch (Exception e)
        {
            System.err.println("Got an exception!");
            e.printStackTrace();
        }

    }

    public void createTable() throws SQLException {
        String sqlCreate = "CREATE TABLE IF NOT EXISTS " + table
                + "  (username        VARCHAR(15),"
                + "   message         VARCHAR(50),"
                + "   date            VARCHAR(15))";

        Statement stmt = conn.createStatement();
        stmt.execute(sqlCreate);
    }
}
