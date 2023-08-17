package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    private static final String create = "CREATE TABLE IF NOT EXISTS mytest.users " +
            "(id BIGINT NOT NULL AUTO_INCREMENT, " +
            " name VARCHAR(255), " +
            " lastname VARCHAR(255), " +
            " age TINYINT, " +
            " PRIMARY KEY (id));";

    private static final String drop = "DROP TABLE IF EXISTS mytest.users;";
    private static final String save = "INSERT INTO mytest.users (name, lastname, age) VALUES (?,?,?);";
    private static final String delete = "DELETE FROM mytest.users WHERE id = ?;";
    private static final String getAll = "SELECT * FROM mytest.users;";
    private static final String clean = "DELETE FROM mytest.users;";
    public UserDaoJDBCImpl() {

    }

private static Connection connection;

    public void createUsersTable() {

        try(Connection connection = Util.getConnection()) {
            connection.setAutoCommit(false);
            Statement statement = connection.createStatement();
            statement.executeUpdate(create);
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    public void dropUsersTable() {
        try(Connection connection = Util.getConnection()) {
            connection.setAutoCommit(false);
            Statement statement = connection.createStatement();
            statement.executeUpdate(drop);
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try(Connection connection = Util.getConnection()) {
            connection.setAutoCommit(false);
            PreparedStatement prepareStatement = connection.prepareStatement(save);
            prepareStatement.setString(1, name);
            prepareStatement.setString(2, lastName);
            prepareStatement.setByte(3, age);
            prepareStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
        System.out.println("User с именем – " + name + " добавлен в базу данных");
    }

    public void removeUserById(long id) {

        try(Connection connection = Util.getConnection()) {
            connection.setAutoCommit(false);
            PreparedStatement prepareStatement = connection.prepareStatement(delete);
            prepareStatement.setLong(1, id);
            prepareStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    public List<User> getAllUsers() {

        List<User> list = new ArrayList<>();
        try(Connection connection = Util.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(getAll);

            while(rs.next()){
                User user = new User();
                user.setId(rs.getLong("id"));
                user.setName(rs.getString("name"));
                user.setLastName(rs.getString("lastname"));
                user.setAge(rs.getByte("age"));
                list.add(user);


            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;

    }

    public void cleanUsersTable() {

        try(Connection connection = Util.getConnection()) {
            connection.setAutoCommit(false);
            Statement statement = connection.createStatement();
            statement.executeUpdate(clean);
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}
