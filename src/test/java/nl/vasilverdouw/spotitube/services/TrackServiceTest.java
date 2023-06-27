package nl.vasilverdouw.spotitube.services;

import nl.vasilverdouw.spotitube.datasource.dao.TrackDao;
import nl.vasilverdouw.spotitube.dto.data.TrackDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

public class TrackServiceTest {
    private TrackService trackService;
    private TrackDao trackDao;

    @BeforeEach
    public void setup() {
        trackDao = mock(TrackDao.class);
        trackService = new TrackService(trackDao);
    }

    @Test
    public void testGetTracksForPlaylistCallsCorrectMethods() {
        // Arrange
        when(trackDao.getTracksForPlaylist(1)).thenReturn(new ArrayList<>());

        // Act
        trackService.getTracksForPlaylist(1);

        // Assert
        verify(trackDao, times(1)).getTracksForPlaylist(1);
    }

    @Test
    public void testGetTracksForPlaylistReturnsCorrectTracks() {
        // Arrange
        List<TrackDTO> tracks = new ArrayList<>();
        tracks.add(new TrackDTO(1, "title", "performer", 1, "album", 1, "publicationDate", "description", true));
        when(trackDao.getTracksForPlaylist(1)).thenReturn(tracks);

        // Act
        var actual = trackService.getTracksForPlaylist(1);

        // Assert
        assert(actual.getTracks().size() == 1);
        assert(actual.getTracks().get(0).getId() == tracks.get(0).getId());
        assert(actual.getTracks().get(0).getTitle().equals(tracks.get(0).getTitle()));
        assert(actual.getTracks().get(0).getPerformer().equals(tracks.get(0).getPerformer()));
        assert(actual.getTracks().get(0).getDuration() == tracks.get(0).getDuration());
        assert(actual.getTracks().get(0).getAlbum().equals(tracks.get(0).getAlbum()));
        assert(actual.getTracks().get(0).getPlaycount() == tracks.get(0).getPlaycount());
        assert(actual.getTracks().get(0).getPublicationDate().equals(tracks.get(0).getPublicationDate()));
        assert(actual.getTracks().get(0).getDescription().equals(tracks.get(0).getDescription()));
        assert(actual.getTracks().get(0).isOfflineAvailable() == tracks.get(0).isOfflineAvailable());
    }
}
