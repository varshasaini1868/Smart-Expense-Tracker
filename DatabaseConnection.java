package com.expense;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {

    public static Connection getConnection() {
        try {
            String url = "jdbc:mysql://localhost:3306/expense_tracker";
            String user = "root";
            String password = "Passwoard"; 

            Connection conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connected Successfully!");
            return conn;

        } catch (Exception e) {
            System.out.println("Connection Failed!");
            e.printStackTrace();
            return null;
        }
    }
}

