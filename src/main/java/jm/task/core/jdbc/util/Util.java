package jm.task.core.jdbc.util;

import java.sql.*;


public class Util {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/mydbusers";
    private static final String DB_USER = "root7787";
    private static final String DB_PASSWORD = "root";
    private static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";

    public static Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName(DB_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            System.out.println("Connected to database successfully");
        } catch (SQLException | ClassNotFoundException e) {

            System.out.println("Connection failed");
        }
        return connection;
    }


}
