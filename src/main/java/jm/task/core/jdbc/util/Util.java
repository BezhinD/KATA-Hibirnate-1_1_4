package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    // реализуйте настройку соеденения с БД
    private final static String DB_URL = "jdbc:mysql://localhost:3306/dbfor1.1.3";
    private final static String DB_USER = "root";
    private final static String DB_PASSWORD = "root";
    private static Connection conn ;

    public Util() {
        try {
            conn = DriverManager.getConnection(DB_URL,
                    DB_USER,
                    DB_PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public Connection getConn() {
        return conn;
    }
}
