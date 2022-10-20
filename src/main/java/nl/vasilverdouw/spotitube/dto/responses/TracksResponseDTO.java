package nl.vasilverdouw.spotitube.dto.responses;

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
