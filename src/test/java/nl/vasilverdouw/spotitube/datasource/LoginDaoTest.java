package nl.vasilverdouw.spotitube.datasource;

import nl.vasilverdouw.spotitube.datasource.dao.LoginDao;
import nl.vasilverdouw.spotitube.datasource.util.DatabaseProperties;
import nl.vasilverdouw.spotitube.dto.data.UserDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class LoginDaoTest {
    private LoginDao loginDao;
    private DatabaseProperties databaseProperties;
    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    @BeforeEach
    public void setup() throws Exception {
        loginDao = new LoginDao();

        connection = mock(Connection.class);
        preparedStatement = mock(PreparedStatement.class);
        resultSet = mock(ResultSet.class);
        databaseProperties = mock(DatabaseProperties.class);
        loginDao.setDatabaseProperties(databaseProperties);

        when(databaseProperties.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(preparedStatement.executeUpdate()).thenReturn(1);
        when(resultSet.next()).thenReturn(true).thenReturn(false);
    }

    @Test
    public void testGetUser() throws Exception {
        // Arrange
        when(resultSet.getString("username")).thenReturn("username");
        when(resultSet.getString("password")).thenReturn("password");
        when(resultSet.getString("token")).thenReturn("token");
        when(resultSet.getString("fullname")).thenReturn("fullname");

        // Act
        UserDTO result = loginDao.getUser("username");

        // Assert
        assertEquals("username", result.getUsername());
        assertEquals("password", result.getPassword());
        assertEquals("token", result.getToken());
        assertEquals("fullname", result.getFullname());
    }

    @Test
    public void testGetUserCallsCorrectMethods() throws Exception {
        // Arrange
        when(resultSet.getString("username")).thenReturn("username");
        when(resultSet.getString("password")).thenReturn("password");
        when(resultSet.getString("token")).thenReturn("token");
        when(resultSet.getString("fullname")).thenReturn("fullname");

        // Act
        loginDao.getUser("username");

        // Assert
        verify(databaseProperties).getConnection();
        verify(connection).prepareStatement(anyString());
        verify(preparedStatement).setObject(1, "username");
        verify(preparedStatement).executeQuery();
        verify(resultSet).next();
        verify(resultSet).getString("username");
        verify(resultSet).getString("password");
        verify(resultSet).getString("token");
        verify(resultSet).getString("fullname");
    }

    @Test
    public void testSetToken() throws Exception {
        // Arrange

        // Act
        int result = loginDao.setToken("username", "token");

        // Assert
        assertEquals(1, result);
    }

    @Test
    public void testSetTokenCallsCorrectMethods() throws Exception {
        // Arrange

        // Act
        loginDao.setToken("username", "token");

        // Assert
        verify(databaseProperties).getConnection();
        verify(connection).prepareStatement(anyString());
        verify(preparedStatement).setObject(1, "token");
        verify(preparedStatement).setObject(2, "username");
        verify(preparedStatement).executeUpdate();
    }
}
