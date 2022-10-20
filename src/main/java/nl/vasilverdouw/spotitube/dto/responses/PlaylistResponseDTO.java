package nl.vasilverdouw.spotitube.dto.responses;

import java.util.ArrayList;

public class PlaylistResponseDTO {
    private int id;
    private String name;
    private boolean owner;
    private ArrayList<TrackResponseDTO> tracks;

    public PlaylistResponseDTO(int id, String name, boolean owner) {
        this.id = id;
        this.name = name;
        this.owner = owner;
        this.tracks = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isOwner() {
        return owner;
    }

    public ArrayList<TrackResponseDTO> getTracks() {
        return tracks;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOwner(boolean owner) {
        this.owner = owner;
    }

    public void setTracks(ArrayList<TrackResponseDTO> tracks) {
        this.tracks = tracks;
    }
}
