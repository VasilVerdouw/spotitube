package nl.vasilverdouw.spotitube.datasource;

import nl.vasilverdouw.spotitube.datasource.dao.TrackDao;
import nl.vasilverdouw.spotitube.datasource.util.DatabaseProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class TrackDaoTest {
    private TrackDao trackDao;
    private DatabaseProperties databaseProperties;
    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    @BeforeEach
    public void setup() throws Exception {
        trackDao = new TrackDao();

        databaseProperties = mock(DatabaseProperties.class);
        trackDao.setDatabaseProperties(databaseProperties);
        connection = mock(Connection.class);
        preparedStatement = mock(PreparedStatement.class);
        resultSet = mock(ResultSet.class);

        when(databaseProperties.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
        when(preparedStatement.executeUpdate()).thenReturn(1);
        when(resultSet.next()).thenReturn(true).thenReturn(false);
    }

    @Test
    public void testGetTracksForPlaylist() throws Exception {
        // Arrange
        when(resultSet.getInt("id")).thenReturn(1);
        when(resultSet.getString("title")).thenReturn("title");
        when(resultSet.getString("performer")).thenReturn("performer");
        when(resultSet.getInt("duration")).thenReturn(1);
        when(resultSet.getString("album")).thenReturn("album");
        when(resultSet.getInt("playcount")).thenReturn(1);
        when(resultSet.getString("publicationDate")).thenReturn("publicationDate");
        when(resultSet.getString("description")).thenReturn("description");

        // Act
        var actual = trackDao.getTracksForPlaylist(1);

        // Assert
        assertEquals(1, actual.get(0).getId());
        assertEquals("title", actual.get(0).getTitle());
        assertEquals("performer", actual.get(0).getPerformer());
        assertEquals(1, actual.get(0).getDuration());
        assertEquals("album", actual.get(0).getAlbum());
        assertEquals(1, actual.get(0).getPlaycount());
        assertEquals("publicationDate", actual.get(0).getPublicationDate());
        assertEquals("description", actual.get(0).getDescription());
    }

    @Test
    public void testGetTracksForPlaylistCallsCorrectMethods() throws Exception {
        // Arrange
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
        verify(databaseProperties, times(1)).getConnection();
        verify(connection, times(1)).prepareStatement(anyString());
        verify(preparedStatement, times(1)).executeQuery();
        verify(resultSet, times(2)).next();
        verify(resultSet, times(1)).getInt("id");
        verify(resultSet, times(1)).getString("title");
        verify(resultSet, times(1)).getString("performer");
        verify(resultSet, times(1)).getInt("duration");
        verify(resultSet, times(1)).getString("album");
        verify(resultSet, times(1)).getInt("playcount");
        verify(resultSet, times(1)).getString("publicationDate");
        verify(resultSet, times(1)).getString("description");
    }
}
