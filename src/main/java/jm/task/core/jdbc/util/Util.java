package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    // реализуйте настройку соеденения с БД
    private final static String DB_URL = "jdbc:mysql://localhost:3306/dbfor1.1.3";
    private final static String DB_USER = "root";
    private final static String DB_PASSWORD = "root";
    private  Connection connection ;

    public Util() {
        try {
            connection = DriverManager.getConnection(DB_URL,
                    DB_USER,
                    DB_PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public Connection getConnection() {
        return connection;
    }
    public static Util getInstance() {
        return new Util();
    }
}
