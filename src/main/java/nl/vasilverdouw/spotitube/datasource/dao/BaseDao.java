package nl.vasilverdouw.spotitube.datasource.dao;

import jakarta.inject.Inject;
import nl.vasilverdouw.spotitube.datasource.util.DatabaseProperties;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BaseDao {
    protected Logger logger = Logger.getLogger(getClass().getName());

    private DatabaseProperties databaseProperties;

    @Inject
    public void setDatabaseProperties(DatabaseProperties databaseProperties) {
        this.databaseProperties = databaseProperties;
    }

    protected PreparedStatement prepareStatement(String statement) throws SQLException {
        Connection connection = DriverManager.getConnection(databaseProperties.connectionString());
        return connection.prepareStatement(statement);
    }

    public int executeUpdate(String statement, Object... parameters) {
        // try (...) will implement auto closing making it unnecessary to close the connection and statement manually
        try (PreparedStatement preparedStatement = prepareStatement(statement)) {
            for (int i = 0; i < parameters.length; i++) {
                preparedStatement.setObject(i + 1, parameters[i]);
            }
            return preparedStatement.executeUpdate();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error while executing update: " + statement, e);
        }
        return 0;
    }

    public ResultSet executeQuery(PreparedStatement preparedStatement, Object... parameters) {
        try {
            for (int i = 0; i < parameters.length; i++) {
                preparedStatement.setObject(i + 1, parameters[i]);
            }
            return preparedStatement.executeQuery();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error while executing query", e);
        }
        return null;
    }
}
