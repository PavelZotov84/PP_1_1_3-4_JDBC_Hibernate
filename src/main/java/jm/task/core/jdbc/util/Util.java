package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class Util {
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String HOST = "jdbc:mysql://localhost:3306/mydbase?serverTimezone=Europe/Moscow";
    private static final String LOGIN = "Pavel";
    private static final String PASSWORD = "root";
    private static SessionFactory sessionFactory = null;

    public static SessionFactory getSessionFactory() {

        try {
            Configuration configuration = new Configuration()
                    .setProperty("hibernate.connection.driver_class", DRIVER)
                    .setProperty("hibernate.connection.url", HOST)
                    .setProperty("hibernate.connection.username", LOGIN)
                    .setProperty("hibernate.connection.password", PASSWORD)
                    .setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect")
                    .addAnnotatedClass(User.class);

            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties()).build();
            sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return sessionFactory;
    }

    public static void closeConnection() {
        if (sessionFactory != null)
            sessionFactory.close();
    }


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
