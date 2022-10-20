package nl.vasilverdouw.spotitube.services;

import jakarta.inject.Inject;
import nl.vasilverdouw.spotitube.datasource.dao.TrackDao;
import nl.vasilverdouw.spotitube.dto.data.TrackDTO;
import nl.vasilverdouw.spotitube.dto.responses.TrackResponseDTO;
import nl.vasilverdouw.spotitube.dto.responses.TracksResponseDTO;

import java.util.ArrayList;
import java.util.List;

public class TrackService {
    private TrackDao trackDao = new TrackDao();

    @Inject
    public void setTrackDao(TrackDao trackDao) {
        this.trackDao = trackDao;
    }

    public TracksResponseDTO getTracksForPlaylist(int playlistId) {
        var tracks = trackDao.getTracksForPlaylist(playlistId);
        return mapTracksListToTracksResponseDTO(tracks);
    }

    public TracksResponseDTO mapTracksListToTracksResponseDTO(List<TrackDTO> tracks) {
        var mappedTracks = new ArrayList<TrackResponseDTO>();
        for (var track : tracks) {
            mappedTracks.add(new TrackResponseDTO(track.getId(), track.getTitle(), track.getPerformer(), track.getDuration(), track.getAlbum(), track.getPlaycount(), track.getPublicationDate(), track.getDescription(), track.isOfflineAvailable()));
        }
        return new TracksResponseDTO(mappedTracks);
    }
}
