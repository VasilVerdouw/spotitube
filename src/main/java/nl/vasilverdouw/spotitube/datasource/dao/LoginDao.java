package nl.vasilverdouw.spotitube.datasource.dao;

import nl.vasilverdouw.spotitube.dto.data.UserDTO;
import nl.vasilverdouw.spotitube.exceptions.ActionFailedException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Level;

public class LoginDao extends BaseDao {
    public UserDTO getUser(String username) {
        try (PreparedStatement preparedStatement = prepareStatement("SELECT * FROM users WHERE username = ?");
             ResultSet resultSet = executeQuery(preparedStatement, username)){
            if (resultSet != null && resultSet.next()) {
                return new UserDTO(resultSet.getString("username"), resultSet.getString("password"), resultSet.getString("token"), resultSet.getString("fullname"));
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error while getting user", e);
        }
        return null;
    }

    public int setToken(String username, String token) {
        return executeUpdate("UPDATE users SET token = ? WHERE username = ?", token, username);
    }
}
