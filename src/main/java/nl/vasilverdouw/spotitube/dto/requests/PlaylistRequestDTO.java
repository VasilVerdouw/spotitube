package nl.vasilverdouw.spotitube.dto.requests;

import nl.vasilverdouw.spotitube.dto.data.TrackDTO;

import java.util.List;

public class PlaylistRequestDTO {
    private int id;
    private String name;
    private boolean owner;
    private List<TrackDTO> tracks;

    public PlaylistRequestDTO() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isOwner() {
        return owner;
    }

    public void setOwner(boolean owner) {
        this.owner = owner;
    }

    public List<TrackDTO> getTracks() {
        return tracks;
    }

    public void setTracks(List<TrackDTO> tracks) {
        this.tracks = tracks;
    }
}
