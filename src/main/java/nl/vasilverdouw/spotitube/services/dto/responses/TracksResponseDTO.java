package nl.vasilverdouw.spotitube.services.dto.responses;

import nl.vasilverdouw.spotitube.services.dto.data.TrackDTO;

import java.util.List;

public class TracksResponseDTO {
    private List<TrackResponseDTO> tracks;

    public TracksResponseDTO(List<TrackResponseDTO> tracks) {
        this.tracks = tracks;
    }

    public List<TrackResponseDTO> getTracks() {
        return tracks;
    }

    public void setTracks(List<TrackResponseDTO> tracks) {
        this.tracks = tracks;
    }
}
