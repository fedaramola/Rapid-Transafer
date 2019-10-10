package com.ecobank.rapidtransfer.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Utilities {
    public static Connection getConnection() throws Exception {
        Connection conn = null;
        String host = "";
        String passwords = "";
        String Instance = "";
        String usernames = "";

        host = "localhost";
        usernames = "festus";
        passwords = "system";
        Instance = "ORCLPDB1";


        Class.forName("oracle.jdbc.driver.OracleDriver").newInstance();
        System.out.println("Connecting to the database..." + host + " : ---- : " + usernames);
        //conn = DriverManager.getConnection(host, usernames, (passwords));
        conn = DriverManager.getConnection(
                "jdbc:oracle:thin:@" + host + ":1521/" + Instance + "", usernames, passwords);
        System.out.println("Connected to the database");
        return conn;
    }
}
