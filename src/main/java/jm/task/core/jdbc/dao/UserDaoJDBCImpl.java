package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.*;


public class UserDaoJDBCImpl implements UserDao {
    private Connection connection = null;

    public UserDaoJDBCImpl() {
        this.connection = Util.getConnection();
    }

    public void createUsersTable() {
        String sql = "CREATE TABLE IF NOT EXISTS users (" +
                "id INT PRIMARY KEY AUTO_INCREMENT, " +
                "name VARCHAR(255), " +
                "lastName VARCHAR(255), " +
                "age INT)";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.executeUpdate();
            System.out.println("Users table created");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void dropUsersTable() {
        String sql = "DROP TABLE IF EXISTS users";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
        } catch (Exception e) {
            System.out.println("Failed to drop table: " + e.getMessage());
        }
    }

    public void saveUser(String name, String lastName, byte age) {

        String sql = "INSERT INTO users (name, lastName, age) VALUES (?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Connection failed");
            System.out.println(e);
        }
    }

    public void removeUserById(long id) {
        String sql = "DELETE FROM users WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Успешно удалён пользователь с ID: " + id);
            } else {
                System.out.println("Пользователь с ID " + id + " не найден");
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при удалении пользователя: ");
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM users");
        ResultSet resultSet = ps.executeQuery()) {
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));
                users.add(user);
            }
        } catch(SQLException e){
            System.out.println("Connection failed");
            System.out.println(e);
        }
        return users;
    }

    public void cleanUsersTable() {
    try (PreparedStatement ps = connection.prepareStatement("DELETE FROM users")) {
        ps.executeUpdate();
    }catch (SQLException e) {
        System.out.println("Connection failed");
        System.out.println(e);
    }
    }

}
