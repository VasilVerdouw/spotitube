package nl.vasilverdouw.spotitube.datasource;

import nl.vasilverdouw.spotitube.datasource.dao.PlaylistDao;
import nl.vasilverdouw.spotitube.datasource.util.DatabaseProperties;
import nl.vasilverdouw.spotitube.dto.data.PlaylistDTO;
import nl.vasilverdouw.spotitube.dto.data.TrackDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class PlaylistDaoTest {

    private PlaylistDao playlistDao;
    private DatabaseProperties databaseProperties;
    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    @BeforeEach
    public void setup() throws Exception {
        playlistDao = new PlaylistDao();

        databaseProperties = mock(DatabaseProperties.class);
        playlistDao.setDatabaseProperties(databaseProperties);
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
    public void testGetPlaylists() throws Exception {
        // Arrange
        when(resultSet.getInt("id")).thenReturn(1);
        when(resultSet.getString("name")).thenReturn("name");
        when(resultSet.getString("token")).thenReturn("owner");

        // Act
        List<PlaylistDTO> results = playlistDao.getPlaylists();
        PlaylistDTO result = results.get(0);

        // Assert
        assertEquals(1, result.getId());
        assertEquals("name", result.getName());
        assertEquals("owner", result.getOwner());
    }

    @Test
    public void testGetPlaylistsCallsCorrectMethods() throws Exception {
        // Arrange
        when(resultSet.getInt("id")).thenReturn(1);
        when(resultSet.getString("name")).thenReturn("name");
        when(resultSet.getString("token")).thenReturn("owner");

        // Act
        playlistDao.getPlaylists();

        // Assert
        verify(databaseProperties).getConnection();
        verify(connection).prepareStatement(anyString());
        verify(preparedStatement).executeQuery();
        verify(resultSet, times(2)).next();
        verify(resultSet).getInt("id");
        verify(resultSet).getString("name");
        verify(resultSet).getString("token");
    }

    @Test
    public void testGetLengthOfAllPlaylists() throws Exception {
        // Arrange
        when(resultSet.getInt(1)).thenReturn(1);

        // Act
        int result = playlistDao.getLengthOfAllPlaylists();

        // Assert
        assertEquals(1, result);
    }

    @Test
    public void testGetLengthOfAllPlaylistsCallsCorrectMethods() throws Exception {
        // Arrange
        when(resultSet.getInt(1)).thenReturn(1);

        // Act
        playlistDao.getLengthOfAllPlaylists();

        // Assert
        verify(databaseProperties).getConnection();
        verify(connection).prepareStatement(anyString());
        verify(preparedStatement).executeQuery();
        verify(resultSet).getInt(1);
    }

    @Test
    public void testDeletePlaylist() throws Exception {
        // Arrange

        // Act
        int result = playlistDao.deletePlaylist(1);

        // Assert
        assertEquals(1, result);
    }

    @Test
    public void testDeletePlaylistCallsCorrectMethods() throws Exception {
        // Arrange

        // Act
        playlistDao.deletePlaylist(1);

        // Assert
        verify(databaseProperties).getConnection();
        verify(connection).prepareStatement(anyString());
        verify(preparedStatement).setObject(1, 1);
        verify(preparedStatement).executeUpdate();
    }

    @Test
    public void testAddPlaylist() throws Exception {
        // Arrange

        // Act
        int result = playlistDao.addPlaylist(new PlaylistDTO(1, "name", "owner"));

        // Assert
        assertEquals(1, result);
    }

    @Test
    public void testAddPlaylistCallsCorrectMethods() throws Exception {
        // Arrange

        // Act
        playlistDao.addPlaylist(new PlaylistDTO(1, "name", "owner"));

        // Assert
        verify(databaseProperties).getConnection();
        verify(connection).prepareStatement(anyString());
        verify(preparedStatement).setObject(1, "name");
        verify(preparedStatement).setObject(2, "owner");
        verify(preparedStatement).executeUpdate();
    }

    @Test
    public void testRenamePlaylist() throws Exception {
        // Arrange

        // Act
        int result = playlistDao.renamePlaylist(new PlaylistDTO(1, "name", "owner"));

        // Assert
        assertEquals(1, result);
    }

    @Test
    public void testRenamePlaylistCallsCorrectMethods() throws Exception {
        // Arrange

        // Act
        playlistDao.renamePlaylist(new PlaylistDTO(1, "name", "owner"));

        // Assert
        verify(databaseProperties).getConnection();
        verify(connection).prepareStatement(anyString());
        verify(preparedStatement).setObject(1, "name");
        verify(preparedStatement).setObject(2, 1);
        verify(preparedStatement).executeUpdate();
    }

    @Test
    public void testGetTracksInPlaylist() throws Exception {
        // Arrange
        when(resultSet.getInt("id")).thenReturn(1);
        when(resultSet.getString("title")).thenReturn("title");
        when(resultSet.getString("performer")).thenReturn("performer");
        when(resultSet.getInt("duration")).thenReturn(1);
        when(resultSet.getString("album")).thenReturn("album");
        when(resultSet.getInt("playcount")).thenReturn(1);
        when(resultSet.getString("publicationDate")).thenReturn("publicationDate");
        when(resultSet.getString("description")).thenReturn("description");
        when(resultSet.getBoolean("offlineAvailable")).thenReturn(true);

        // Act
        List<TrackDTO> results = playlistDao.getTracksInPlaylist(1);
        TrackDTO result = results.get(0);

        // Assert
        assertEquals(1, result.getId());
        assertEquals("title", result.getTitle());
        assertEquals("performer", result.getPerformer());
        assertEquals(1, result.getDuration());
        assertEquals("album", result.getAlbum());
        assertEquals(1, result.getPlaycount());
        assertEquals("publicationDate", result.getPublicationDate());
        assertEquals("description", result.getDescription());
        assertEquals(true, result.isOfflineAvailable());
    }

    @Test
    public void testGetTracksInPlaylistCallsCorrectMethods() throws Exception {
        // Arrange
        when(resultSet.getInt(anyString())).thenReturn(1);
        when(resultSet.getString(anyString())).thenReturn("string");
        when(resultSet.getBoolean(anyString())).thenReturn(true);

        // Act
        playlistDao.getTracksInPlaylist(1);

        // Assert
        verify(databaseProperties).getConnection();
        verify(connection).prepareStatement(anyString());
        verify(preparedStatement).setObject(1, 1);
        verify(preparedStatement).executeQuery();
        verify(resultSet, times(2)).next();
        verify(resultSet).getInt("id");
        verify(resultSet).getString("title");
        verify(resultSet).getString("performer");
        verify(resultSet).getInt("duration");
        verify(resultSet).getString("album");
        verify(resultSet).getInt("playcount");
        verify(resultSet).getString("publicationDate");
        verify(resultSet).getString("description");
        verify(resultSet).getBoolean("offlineAvailable");
    }

    @Test
    public void testAddTrackToPlaylist() throws Exception {
        // Arrange

        // Act
        int result = playlistDao.addTrackToPlaylist(1, 1, false);

        // Assert
        assertEquals(1, result);
    }

    @Test
    public void testAddTrackToPlaylistCallsCorrectMethods() throws Exception {
        // Arrange

        // Act
        playlistDao.addTrackToPlaylist(1, 1, false);

        // Assert
        verify(databaseProperties).getConnection();
        verify(connection).prepareStatement(anyString());
        verify(preparedStatement).setObject(1, 1);
        verify(preparedStatement).setObject(2, 1);
        verify(preparedStatement).setObject(3, false);
        verify(preparedStatement).executeUpdate();
    }

    @Test
    public void testRemoveTrackFromPlaylist() throws Exception {
        // Arrange

        // Act
        int result = playlistDao.removeTrackFromPlaylist(1, 1);

        // Assert
        assertEquals(1, result);
    }

    @Test
    public void testRemoveTrackFromPlaylistCallsCorrectMethods() throws Exception {
        // Arrange

        // Act
        playlistDao.removeTrackFromPlaylist(1, 1);

        // Assert
        verify(databaseProperties).getConnection();
        verify(connection).prepareStatement(anyString());
        verify(preparedStatement).setObject(1, 1);
        verify(preparedStatement).setObject(2, 1);
        verify(preparedStatement).executeUpdate();
    }
}
