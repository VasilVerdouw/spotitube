package nl.vasilverdouw.spotitube.services;

import nl.vasilverdouw.spotitube.datasource.dao.PlaylistDao;
import nl.vasilverdouw.spotitube.exceptions.ActionFailedException;
import nl.vasilverdouw.spotitube.dto.requests.PlaylistRequestDTO;
import nl.vasilverdouw.spotitube.dto.requests.TrackRequestDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class PlaylistServiceTest {
    private PlaylistService playlistService;
    private PlaylistDao playlistDao;

    @BeforeEach
    public void setup() {
        playlistService = new PlaylistService();
        playlistDao = mock(PlaylistDao.class);
        playlistService.setPlaylistDao(playlistDao);
    }

    @Test
    public void testGetPlaylistsReturnsEmpty() {
        // Arrange
        when(playlistDao.getPlaylists()).thenReturn(new ArrayList<>());
        when(playlistDao.getLengthOfAllPlaylists()).thenReturn(0);

        // Act
        var playlistsResponseDTO = playlistService.getPlaylists("token");

        // Assert
        assertEquals(0, playlistsResponseDTO.getLength());
        assertEquals(0, playlistsResponseDTO.getPlaylists().length);
    }

    @Test
    public void testGetPlaylistCallsCorrectMethods() {
        // Arrange
        when(playlistDao.getPlaylists()).thenReturn(new ArrayList<>());
        when(playlistDao.getLengthOfAllPlaylists()).thenReturn(0);

        // Act
        playlistService.getPlaylists("token");

        // Assert
        verify(playlistDao, times(1)).getPlaylists();
        verify(playlistDao, times(1)).getLengthOfAllPlaylists();
    }

    @Test
    public void testDeletePlaylistCallsCorrectMethods() {
        // Arrange
        when(playlistDao.deletePlaylist(1)).thenReturn(1);

        // Act
        playlistService.deletePlaylist(1, "token");

        // Assert
        verify(playlistDao, times(1)).deletePlaylist(1);
    }

    @Test
    public void testAddPlaylistCallsCorrectMethods() {
        // Arrange
        when(playlistDao.addPlaylist(any())).thenReturn(1);

        // Act
        playlistService.addPlaylist(new PlaylistRequestDTO(), "token");

        // Assert
        verify(playlistDao, times(1)).addPlaylist(any());
    }

    @Test
    public void testRenamePlaylistCallsCorrectMethods() {
        // Arrange
        when(playlistDao.renamePlaylist(any())).thenReturn(1);

        // Act
        playlistService.renamePlaylist(1, "token", new PlaylistRequestDTO());

        // Assert
        verify(playlistDao, times(1)).renamePlaylist(any());
    }

    @Test
    public void testGetTracksInPlaylistCallsCorrectMethods() {
        // Arrange
        when(playlistDao.getTracksInPlaylist(anyInt())).thenReturn(new ArrayList<>());

        // Act
        playlistService.getTracksInPlaylist(anyInt());

        // Assert
        verify(playlistDao, times(1)).getTracksInPlaylist(anyInt());
    }

    @Test
    public void testAddTrackToPlaylistCallsCorrectMethods() {
        // Arrange
        when(playlistDao.addTrackToPlaylist(anyInt(), anyInt(), anyBoolean())).thenReturn(1);
        var track = new TrackRequestDTO();
        track.setId(1);
        track.setOfflineAvailable(true);

        // Act
        playlistService.addTrackToPlaylist(track, 1);

        // Assert
        verify(playlistDao, times(1)).addTrackToPlaylist(1, 1, true);
    }

    @Test
    public void testRemoveTrackFromPlaylistCallsCorrectMethods() {
        // Arrange
        when(playlistDao.removeTrackFromPlaylist(anyInt(), anyInt())).thenReturn(1);

        // Act
        playlistService.removeTrackFromPlaylist(1, 1);

        // Assert
        verify(playlistDao, times(1)).removeTrackFromPlaylist(1, 1);
    }

    @Test
    public void testDeletePlaylistThrowsException() {
        // Arrange
        when(playlistDao.deletePlaylist(anyInt())).thenReturn(1);

        // Act & Assert
        assertThrows(ActionFailedException.class, () -> playlistService.deletePlaylist(1, "token"));
    }

    @Test
    public void testAddPlaylistThrowsException() {
        // Arrange
        when(playlistDao.addPlaylist(any())).thenReturn(1);

        // Act & Assert
        assertThrows(ActionFailedException.class, () -> playlistService.addPlaylist(new PlaylistRequestDTO(), "token"));
    }

    @Test
    public void testRenamePlaylistThrowsException() {
        // Arrange
        when(playlistDao.renamePlaylist(any())).thenReturn(1);

        // Act & Assert
        assertThrows(ActionFailedException.class, () -> playlistService.renamePlaylist(1, "token", new PlaylistRequestDTO()));
    }

    @Test
    public void testAddTrackToPlaylistThrowsException() {
        // Arrange
        when(playlistDao.addTrackToPlaylist(anyInt(), anyInt(), anyBoolean())).thenReturn(1);
        var track = new TrackRequestDTO();
        track.setId(1);
        track.setOfflineAvailable(true);

        // Act & Assert
        assertThrows(ActionFailedException.class, () -> playlistService.addTrackToPlaylist(track, 1));
    }

    @Test
    public void testRemoveTrackFromPlaylistThrowsException() {
        // Arrange
        when(playlistDao.removeTrackFromPlaylist(anyInt(), anyInt())).thenReturn(1);

        // Act & Assert
        assertThrows(ActionFailedException.class, () -> playlistService.removeTrackFromPlaylist(1, 1));
    }
}
