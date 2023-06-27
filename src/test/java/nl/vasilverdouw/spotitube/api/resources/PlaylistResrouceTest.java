package nl.vasilverdouw.spotitube.api.resources;

import jakarta.ws.rs.core.Response;
import nl.vasilverdouw.spotitube.dto.responses.LoginResponseDTO;
import nl.vasilverdouw.spotitube.dto.responses.PlaylistsResponseDTO;
import nl.vasilverdouw.spotitube.dto.responses.TracksResponseDTO;
import nl.vasilverdouw.spotitube.services.LoginService;
import nl.vasilverdouw.spotitube.services.PlaylistService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

public class PlaylistResrouceTest {
    private PlaylistResource playlistResource;
    private PlaylistService playlistService;

    @BeforeEach
    public void setup() {
        playlistService = mock(PlaylistService.class);
        playlistResource = new PlaylistResource(playlistService);
        when(playlistService.isUserOwnerOfPlaylist(anyInt(), any())).thenReturn(true);
    }

    @Test
    public void testGetPlaylistsCallsCorrectMethod() {
        // Arrange
        when(playlistService.getPlaylists(any())).thenReturn(null);

        // Act
        playlistResource.getPlaylists(any());

        // Assert
        verify(playlistService, times(1)).getPlaylists(any());
    }

    @Test
    public void testGetPlaylistsResponseIsOk() {
        // Arrange
        var expectedResponse = new PlaylistsResponseDTO(null, 0);
        when(playlistService.getPlaylists(any())).thenReturn(expectedResponse);

        // Act
        Response response = playlistResource.getPlaylists(any());

        // Assert
        assertEquals(expectedResponse, response.getEntity());
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void testDeletePlaylistsCallsCorrectMethod() {
        // Arrange
        when(playlistService.deletePlaylist(anyInt(), any())).thenReturn(null);

        // Act
        playlistResource.deletePlaylist(anyInt(), any());

        // Assert
        verify(playlistService, times(1)).deletePlaylist(anyInt(), any());
    }

    @Test
    public void testDeletePlaylistsResponseIsOk() {
        // Arrange
        var expectedResponse = new PlaylistsResponseDTO(null, 0);
        when(playlistService.deletePlaylist(anyInt(), any())).thenReturn(expectedResponse);

        // Act
        Response response = playlistResource.deletePlaylist(anyInt(), any());

        // Assert
        assertEquals(expectedResponse, response.getEntity());
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void testAddPlaylistCallsCorrectMethod() {
        // Arrange
        when(playlistService.addPlaylist(any(), any())).thenReturn(null);

        // Act
        playlistResource.addPlaylist(any(), any());

        // Assert
        verify(playlistService, times(1)).addPlaylist(any(), any());
    }

    @Test
    public void testAddPlaylistResponseIsOk() {
        // Arrange
        var expectedResponse = new PlaylistsResponseDTO(null, 0);
        when(playlistService.addPlaylist(any(), any())).thenReturn(expectedResponse);

        // Act
        Response response = playlistResource.addPlaylist(any(), any());

        // Assert
        assertEquals(expectedResponse, response.getEntity());
        assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
    }

    @Test
    public void testEditPlaylistCallsCorrectMethod() {
        // Arrange
        when(playlistService.renamePlaylist(anyInt(), any(), any())).thenReturn(null);

        // Act
        playlistResource.editPlaylist(anyInt(), any(), any());

        // Assert
        verify(playlistService, times(1)).renamePlaylist(anyInt(), any(), any());
    }

    @Test
    public void testEditPlaylistResponseIsOk() {
        // Arrange
        var expectedResponse = new PlaylistsResponseDTO(null, 0);
        when(playlistService.renamePlaylist(anyInt(), any(), any())).thenReturn(expectedResponse);

        // Act
        Response response = playlistResource.editPlaylist(anyInt(), any(), any());

        // Assert
        assertEquals(expectedResponse, response.getEntity());
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void testGetTracksInPlaylistCallsCorrectMethod() {
        // Arrange
        when(playlistService.getTracksInPlaylist(anyInt())).thenReturn(null);

        // Act
        playlistResource.getTracksInPlaylist(null, 0);

        // Assert
        verify(playlistService, times(1)).getTracksInPlaylist(anyInt());
    }

    @Test
    public void testGetTracksInPlaylistResponseIsOk() {
        // Arrange
        var expectedResponse = new TracksResponseDTO(null);
        when(playlistService.getTracksInPlaylist(anyInt())).thenReturn(expectedResponse);

        // Act
        Response response = playlistResource.getTracksInPlaylist(null, 0);

        // Assert
        assertEquals(expectedResponse, response.getEntity());
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void testAddTrackToPlaylistCallsCorrectMethod() {
        // Arrange
        when(playlistService.addTrackToPlaylist(any(), anyInt(), anyString())).thenReturn(null);

        // Act
        playlistResource.addTrackToPlaylist("token", 0, null);

        // Assert
        verify(playlistService, times(1)).addTrackToPlaylist(any(), anyInt(), anyString());
    }

    @Test
    public void testAddTrackToPlaylistResponseIsOk() {
        // Arrange
        var expectedResponse = new TracksResponseDTO(null);
        when(playlistService.addTrackToPlaylist(any(), anyInt(), anyString())).thenReturn(expectedResponse);

        // Act
        Response response = playlistResource.addTrackToPlaylist("token", 0, null);

        // Assert
        assertEquals(expectedResponse, response.getEntity());
        assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
    }

    @Test
    public void testRemoveTrackFromCallsCorrectMethod() {
        // Arrange
        when(playlistService.removeTrackFromPlaylist(anyInt(), anyInt(), anyString())).thenReturn(null);

        // Act
        playlistResource.removeTrackFromPlaylist("token", 0, 0);

        // Assert
        verify(playlistService, times(1)).removeTrackFromPlaylist(anyInt(), anyInt(), anyString());
    }

    @Test
    public void testRemoveTrackFromPlaylistResponseIsOk() {
        // Arrange
        var expectedResponse = new TracksResponseDTO(null);
        when(playlistService.removeTrackFromPlaylist(anyInt(), anyInt(), anyString())).thenReturn(expectedResponse);

        // Act
        Response response = playlistResource.removeTrackFromPlaylist("token", 0, 0);

        // Assert
        assertEquals(expectedResponse, response.getEntity());
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void testEmptyConstructorReturnsNotNull() {
        // Arrange
        // Act
        playlistResource = new PlaylistResource();

        // Assert
        assertNotNull(playlistResource);
    }
}
