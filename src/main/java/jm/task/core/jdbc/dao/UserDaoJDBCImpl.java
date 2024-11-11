package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    private static final Connection connection = Util.getInstance().getConnection();


    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() throws SQLException {
        try {
            connection.setAutoCommit(false);
            connection.createStatement().
                    executeUpdate("CREATE TABLE IF NOT EXISTS users (" +
                            "id serial," +
                            "name varchar(45)," +
                            "lastName varchar(45)," +
                            "age tinyint)");
            connection.commit();

        } catch (SQLException e) {
            connection.rollback();
        }

    }

    public void dropUsersTable() throws SQLException {
        try {
            connection.setAutoCommit(false);
            connection.createStatement().executeUpdate("DROP TABLE IF EXISTS users");
            connection.commit();

        } catch (SQLException e) {
            connection.rollback();
            throw new RuntimeException(e);
        }

    }

    public void saveUser(String name, String lastName, byte age) throws SQLException {
        try {
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.
                    prepareStatement("INSERT INTO users(name, lastname, age) VALUES(?, ?, ?)");
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setInt(3, age);
            preparedStatement.executeUpdate();

            System.out.println("User " + name + " has been saved");
            connection.commit();


        } catch (SQLException e) {
            connection.rollback();

        }

    }

    public void removeUserById(long id) throws SQLException {
        try {
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM users WHERE id=?");
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            connection.commit();


        } catch (SQLException e) {
            connection.rollback();

        }

    }

    public List<User> getAllUsers() throws SQLException {
        try {
            connection.setAutoCommit(false);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM users");
            List<User> users = new ArrayList<User>();
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));
                users.add(user);
            }
            connection.commit();

            return users;
        } catch (SQLException e) {
            connection.rollback();
            throw new RuntimeException(e);

        }
    }

    public void cleanUsersTable() throws SQLException {
        try {
            connection.setAutoCommit(false);
            connection.createStatement()
                    .executeUpdate("DELETE FROM users");
            connection.commit();

        } catch (SQLException e) {
            connection.rollback();
            throw new RuntimeException(e);
        }

    }
}
