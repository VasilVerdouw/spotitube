package nl.vasilverdouw.spotitube.services;

import nl.vasilverdouw.spotitube.datasource.dao.PlaylistDao;
import nl.vasilverdouw.spotitube.exceptions.ActionFailedException;
import nl.vasilverdouw.spotitube.dto.requests.PlaylistRequestDTO;
import nl.vasilverdouw.spotitube.dto.requests.TrackRequestDTO;
import nl.vasilverdouw.spotitube.exceptions.BadRequestException;
import nl.vasilverdouw.spotitube.exceptions.UnauthorizedException;
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
        playlistDao = mock(PlaylistDao.class);
        playlistService = new PlaylistService(playlistDao);
    }

    @Test
    public void testGetPlaylistsReturnsEmpty() {
        // Arrange
        when(playlistDao.getPlaylists()).thenReturn(new ArrayList<>());
        when(playlistDao.getLengthOfAllPlaylists()).thenReturn(0);
        when(playlistDao.findAmountOfPlaylistsWithIdOwnedByToken(anyInt(), any())).thenReturn(1);

        // Act
        var playlistsResponseDTO = playlistService.getPlaylists("token");

        // Assert
        assertEquals(0, playlistsResponseDTO.getLength());
        assertEquals(0, playlistsResponseDTO.getPlaylists().size());
    }

    @Test
    public void testGetPlaylistCallsCorrectMethods() {
        // Arrange
        when(playlistDao.getPlaylists()).thenReturn(new ArrayList<>());
        when(playlistDao.getLengthOfAllPlaylists()).thenReturn(0);
        when(playlistDao.findAmountOfPlaylistsWithIdOwnedByToken(anyInt(), any())).thenReturn(1);

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
        when(playlistDao.findAmountOfPlaylistsWithIdOwnedByToken(anyInt(), any())).thenReturn(1);

        // Act
        playlistService.deletePlaylist(1, "token");

        // Assert
        verify(playlistDao, times(1)).deletePlaylist(1);
    }

    @Test
    public void testAddPlaylistCallsCorrectMethods() {
        // Arrange
        when(playlistDao.addPlaylist(any())).thenReturn(1);
        when(playlistDao.findAmountOfPlaylistsWithIdOwnedByToken(anyInt(), any())).thenReturn(1);

        // Act
        playlistService.addPlaylist(new PlaylistRequestDTO(), "token");

        // Assert
        verify(playlistDao, times(1)).addPlaylist(any());
    }

    @Test
    public void testRenamePlaylistCallsCorrectMethods() {
        // Arrange
        when(playlistDao.renamePlaylist(any())).thenReturn(1);
        PlaylistRequestDTO playlist = new PlaylistRequestDTO();
        playlist.setId(1);
        when(playlistDao.findAmountOfPlaylistsWithIdOwnedByToken(anyInt(), any())).thenReturn(1);

        // Act
        playlistService.renamePlaylist(1, "token", playlist);

        // Assert
        verify(playlistDao, times(1)).renamePlaylist(any());
    }

    @Test
    public void testGetTracksInPlaylistCallsCorrectMethods() {
        // Arrange
        when(playlistDao.getTracksInPlaylist(anyInt())).thenReturn(new ArrayList<>());
        when(playlistDao.findAmountOfPlaylistsWithIdOwnedByToken(anyInt(), any())).thenReturn(1);

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
        when(playlistDao.findAmountOfPlaylistsWithIdOwnedByToken(anyInt(), any())).thenReturn(1);

        // Act
        playlistService.addTrackToPlaylist(track, 1, "token");

        // Assert
        verify(playlistDao, times(1)).addTrackToPlaylist(1, 1, true);
    }

    @Test
    public void testRemoveTrackFromPlaylistCallsCorrectMethods() {
        // Arrange
        when(playlistDao.removeTrackFromPlaylist(anyInt(), anyInt())).thenReturn(1);
        when(playlistDao.findAmountOfPlaylistsWithIdOwnedByToken(anyInt(), any())).thenReturn(1);

        // Act
        playlistService.removeTrackFromPlaylist(1, 1, "token");

        // Assert
        verify(playlistDao, times(1)).removeTrackFromPlaylist(1, 1);
    }

    @Test
    public void testGetPlaylistThrowsException() {
        // Arrange
        when(playlistDao.getPlaylists()).thenReturn(null);
        when(playlistDao.findAmountOfPlaylistsWithIdOwnedByToken(anyInt(), any())).thenReturn(1);

        // Act & Assert
        assertThrows(ActionFailedException.class, () -> playlistService.getPlaylists("token"));
    }

    @Test
    public void testDeletePlaylistThrowsException() {
        // Arrange
        when(playlistDao.deletePlaylist(anyInt())).thenReturn(0);
        when(playlistDao.findAmountOfPlaylistsWithIdOwnedByToken(anyInt(), any())).thenReturn(1);

        // Act & Assert
        assertThrows(ActionFailedException.class, () -> playlistService.deletePlaylist(1, "token"));
    }

    @Test
    public void testAddPlaylistThrowsException() {
        // Arrange
        when(playlistDao.addPlaylist(any())).thenReturn(0);
        when(playlistDao.findAmountOfPlaylistsWithIdOwnedByToken(anyInt(), any())).thenReturn(1);

        // Act & Assert
        assertThrows(ActionFailedException.class, () -> playlistService.addPlaylist(new PlaylistRequestDTO(), "token"));
    }

    @Test
    public void testRenamePlaylistThrowsActionFailedException() {
        // Arrange
        when(playlistDao.renamePlaylist(any())).thenReturn(0);
        PlaylistRequestDTO playlist = new PlaylistRequestDTO();
        playlist.setId(1);
        when(playlistDao.findAmountOfPlaylistsWithIdOwnedByToken(anyInt(), any())).thenReturn(1);

        // Act & Assert
        assertThrows(ActionFailedException.class, () -> playlistService.renamePlaylist(1, "token", playlist));
    }

    @Test
    public void testRenamePlaylistThrowsBadRequestException() {
        // Arrange
        when(playlistDao.renamePlaylist(any())).thenReturn(1);
        when(playlistDao.findAmountOfPlaylistsWithIdOwnedByToken(anyInt(), any())).thenReturn(1);

        // Act & Assert
        assertThrows(BadRequestException.class, () -> playlistService.renamePlaylist(1, "token", new PlaylistRequestDTO()));
    }

    @Test
    public void testGetTracksInPlaylistThrowsException() {
        // Arrange
        when(playlistDao.getTracksInPlaylist(anyInt())).thenReturn(null);
        when(playlistDao.findAmountOfPlaylistsWithIdOwnedByToken(anyInt(), any())).thenReturn(1);

        // Act & Assert
        assertThrows(ActionFailedException.class, () -> playlistService.getTracksInPlaylist(anyInt()));
    }

    @Test
    public void testAddTrackToPlaylistThrowsException() {
        // Arrange
        when(playlistDao.addTrackToPlaylist(anyInt(), anyInt(), anyBoolean())).thenReturn(0);
        var track = new TrackRequestDTO();
        track.setId(1);
        track.setOfflineAvailable(true);
        when(playlistDao.findAmountOfPlaylistsWithIdOwnedByToken(anyInt(), any())).thenReturn(1);

        // Act & Assert
        assertThrows(ActionFailedException.class, () -> playlistService.addTrackToPlaylist(track, 1, "token"));
    }

    @Test
    public void testRemoveTrackFromPlaylistThrowsException() {
        // Arrange
        when(playlistDao.removeTrackFromPlaylist(anyInt(), anyInt())).thenReturn(0);
        when(playlistDao.findAmountOfPlaylistsWithIdOwnedByToken(anyInt(), any())).thenReturn(1);

        // Act & Assert
        assertThrows(ActionFailedException.class, () -> playlistService.removeTrackFromPlaylist(1, 1, "token"));
    }

    @Test
    public void testCannotDeletePlaylistFromOtherUser() {
        // Arrange
        when(playlistDao.findAmountOfPlaylistsWithIdOwnedByToken(anyInt(), any())).thenReturn(0);

        // Act & Assert
        assertThrows(UnauthorizedException.class, () -> playlistService.deletePlaylist(1, "token"));
    }

    @Test
    public void testCannotRenamePlaylistFromOtherUser() {
        // Arrange
        when(playlistDao.findAmountOfPlaylistsWithIdOwnedByToken(anyInt(), any())).thenReturn(0);
        PlaylistRequestDTO playlist = new PlaylistRequestDTO();
        playlist.setId(1);

        // Act & Assert
        assertThrows(UnauthorizedException.class, () -> playlistService.renamePlaylist(1, "token", playlist));
    }

    @Test
    public void testCannotAddTrackToPlaylistFromOtherUser() {
        // Arrange
        when(playlistDao.findAmountOfPlaylistsWithIdOwnedByToken(anyInt(), any())).thenReturn(0);

        // Act & Assert
        assertThrows(UnauthorizedException.class, () -> playlistService.addTrackToPlaylist(new TrackRequestDTO(), 1, "token"));
    }

    @Test
    public void testCannotRemoveTrackFromPlaylistFromOtherUser() {
        // Arrange
        when(playlistDao.findAmountOfPlaylistsWithIdOwnedByToken(anyInt(), any())).thenReturn(0);

        // Act & Assert
        assertThrows(UnauthorizedException.class, () -> playlistService.removeTrackFromPlaylist(1, 1, "token"));
    }
}
