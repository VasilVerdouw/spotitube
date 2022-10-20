package nl.vasilverdouw.spotitube.datasource.dao;

import jakarta.inject.Inject;
import nl.vasilverdouw.spotitube.datasource.util.DatabaseProperties;
import nl.vasilverdouw.spotitube.exceptions.UnauthorizedException;
import nl.vasilverdouw.spotitube.dto.data.UserDTO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoginDao {
    private Logger logger = Logger.getLogger(getClass().getName());

    private DatabaseProperties databaseProperties;

    @Inject
    public void setDatabaseProperties(DatabaseProperties databaseProperties) {
        this.databaseProperties = databaseProperties;
    }

    public UserDTO getUser(String username) throws UnauthorizedException {
        try  {
            Connection connection = DriverManager.getConnection(databaseProperties.connectionString());
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE username = ?");
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new UserDTO(resultSet.getString("username"), resultSet.getString("password"), resultSet.getString("token"), resultSet.getString("fullname"));
            }
            statement.close();
            connection.close();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error while getting user", e);
        }
        throw new UnauthorizedException("User not found");
    }

    public boolean setToken(String username, String token) {
        try {
            Connection connection = DriverManager.getConnection(databaseProperties.connectionString());
            PreparedStatement statement = connection.prepareStatement("UPDATE users SET token = ? WHERE username = ?");
            statement.setString(1, token);
            statement.setString(2, username);
            var result = statement.executeUpdate() > 0;
            statement.close();
            connection.close();
            return result;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error while setting token", e);
        }
        return false;
    }
}
