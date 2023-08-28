package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl extends Util implements UserDao {

    private static final Connection conn = Util.getConnection();


    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try (Statement statement = conn.createStatement()) {
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS users " +
                    "(id BIGINT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(40), lastName VARCHAR(40), age INT(3))");
            System.out.println("The table was created successfully");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        try (Statement statement = conn.createStatement()) {
            statement.executeUpdate("DROP TABLE IF EXISTS users");
            System.out.println("The table  was successfully deleted");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void saveUser(String name, String lastName, int age) {
        try (PreparedStatement pstm = conn
                .prepareStatement("INSERT INTO users (name, lastName, age) VALUES (?, ?, ?)")) {
            pstm.setString(1, name);
            pstm.setString(2, lastName);
            pstm.setInt(3, age);
            pstm.executeUpdate();
            System.out.println("User с именем – " + name + " добавлен в базу данных");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        try (PreparedStatement pstm = conn.prepareStatement("DELETE FROM users WHERE id = ?")) {
            pstm.setLong(1, id);
            pstm.executeUpdate();
            System.out.println("The user with index " + id + "   has been deleted ");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();

        try (ResultSet resultSet = conn.createStatement().executeQuery("SELECT * FROM users")) {
            while (resultSet.next()) {
                User user = new User(resultSet.getString("name"),
                        resultSet.getString("lastName"), resultSet.getByte("age"));
                user.setId(resultSet.getLong("id"));
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }

    public void cleanUsersTable() {
        try (Statement statement = conn.createStatement()) {
            statement.executeUpdate("TRUNCATE TABLE users");
            System.out.println("The table was cleared successfully");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
