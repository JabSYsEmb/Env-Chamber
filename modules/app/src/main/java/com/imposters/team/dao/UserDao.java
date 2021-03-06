package com.imposters.team.dao;

import com.imposters.team.model.User;
import com.imposters.team.db.MyJDBC;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.ResultSet;
import java.util.List;

public class UserDao {

    private UserDao() {
        throw new IllegalStateException("Utility class");
    }

    public static User getUserFromDatabase(String username, MyJDBC db) {
        try (PreparedStatement preparedStatement =
                     db.getConnection().prepareStatement("SELECT * from User where Username = ?;")) {
            preparedStatement.setString(1, username);
            ResultSet rs = preparedStatement.executeQuery();
            User user = null;
            if (rs.next()) {
                user = new User(
                        rs.getInt("User_ID"),
                        rs.getString("Fname"),
                        rs.getString("Lname"),
                        rs.getString("Username"),
                        rs.getBoolean("AdminStatus"),
                        rs.getString("Password")
                );
            }
            return user;
        } catch (SQLException | NullPointerException ex) {
            System.out.println("NotFoundUserInfo, try again!");
            ex.printStackTrace();
            return null;
        }
    }

    public static List<User> getUsersFromDatabase(MyJDBC db) {
        List<User> user = new ArrayList<>();
        try (PreparedStatement preparedStatement =
                     db.getConnection().prepareStatement("SELECT * from User;")) {
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                user.add(new User(
                        rs.getInt("User_ID"),
                        rs.getString("Fname"),
                        rs.getString("Lname"),
                        rs.getString("Username"),
                        rs.getBoolean("AdminStatus"),
                        rs.getString("Password"))
                );
            }
        } catch (SQLException | NullPointerException ex) {
            ex.printStackTrace();
        }
        return user;
    }
}

