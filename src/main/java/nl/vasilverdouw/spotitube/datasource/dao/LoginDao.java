package nl.vasilverdouw.spotitube.datasource.dao;

import jakarta.inject.Inject;
import nl.vasilverdouw.spotitube.datasource.util.DatabaseProperties;
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

    public UserDTO getUser(String username) {
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
        return new UserDTO();
    }

    public int setToken(String username, String token) {
        try {
            Connection connection = DriverManager.getConnection(databaseProperties.connectionString());

            PreparedStatement statement = connection.prepareStatement("UPDATE users SET token = ? WHERE username = ?");
            statement.setString(1, token);
            statement.setString(2, username);

            // Returns amount of updated rows
            return statement.executeUpdate();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error while setting token", e);
        }
        return 0;
    }
}
