package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    public static Connection getConnection() {
        Connection conn = null;
        try {
            conn = DriverManager.
                    getConnection("jdbc:mysql://localhost:3306/mydbase", "Pavel", "root");
            System.out.println("Connection ok");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

}
