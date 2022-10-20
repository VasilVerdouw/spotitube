package nl.vasilverdouw.spotitube.datasource;

import nl.vasilverdouw.spotitube.datasource.dao.TrackDao;
import nl.vasilverdouw.spotitube.datasource.util.DatabaseProperties;
import nl.vasilverdouw.spotitube.exceptions.ActionFailedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class TrackDaoTest {
    private TrackDao trackDao;
    private DatabaseProperties databaseProperties;

    @BeforeEach
    public void setup() {
        trackDao = new TrackDao();
        databaseProperties = mock(DatabaseProperties.class);
        trackDao.setDatabaseProperties(databaseProperties);
    }

    @Test
    public void testGetTracksForPlaylistCallsCorrectMethods() throws SQLException {
        // Arrange
        Connection connection = mock(Connection.class);
        PreparedStatement statement = mock(PreparedStatement.class);
        ResultSet resultSet = mock(ResultSet.class);

        when(databaseProperties.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(statement);
        when(statement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(resultSet.getInt("id")).thenReturn(1);
        when(resultSet.getString("title")).thenReturn("title");
        when(resultSet.getString("performer")).thenReturn("performer");
        when(resultSet.getInt("duration")).thenReturn(1);
        when(resultSet.getString("album")).thenReturn("album");
        when(resultSet.getInt("playcount")).thenReturn(1);
        when(resultSet.getString("publicationDate")).thenReturn("publicationDate");
        when(resultSet.getString("description")).thenReturn("description");
        // Act
        trackDao.getTracksForPlaylist(1);

        // Assert
        verify(databaseProperties).getConnection();
        verify(connection).prepareStatement(anyString());
        verify(statement).setInt(1, 1);
        verify(statement).executeQuery();
        verify(resultSet, times(2)).next();
        verify(resultSet).getInt("id");
        verify(resultSet).getString("title");
        verify(resultSet).getString("performer");
        verify(resultSet).getInt("duration");
        verify(resultSet).getString("album");
        verify(resultSet).getInt("playcount");
        verify(resultSet).getString("publicationDate");
        verify(resultSet).getString("description");
    }

    @Test
    public void testGetPlaylistsThrowsActionFailedException() throws SQLException {
        // Arrange
        when(databaseProperties.getConnection()).thenThrow(SQLException.class);

        // Act & Assert
        assertThrows(ActionFailedException.class, () -> trackDao.getTracksForPlaylist(1));
    }
}
