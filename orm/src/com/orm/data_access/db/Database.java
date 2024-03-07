package com.orm.data_access.db;

import secrets.SecretManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private Database() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(SecretManager.getUrl(), SecretManager.getUsername(), SecretManager.getPassword());
        } catch (SQLException e) {
            System.out.println("Connection failed");
            e.printStackTrace();
        }

        return null;
    }
}
