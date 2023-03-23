package nl.vasilverdouw.spotitube.dto.responses;

import java.util.ArrayList;

public class PlaylistsResponseDTO {
    private ArrayList<PlaylistResponseDTO> playlists = new ArrayList<>();
    private int length;

    public PlaylistsResponseDTO(ArrayList<PlaylistResponseDTO> playlists, int length) {
        this.playlists = playlists;
        this.length = length;
    }

    public void setPlaylists (ArrayList<PlaylistResponseDTO> playlists) {
        this.playlists = playlists;
    }

    public ArrayList<PlaylistResponseDTO> getPlaylists() {
        return playlists;
    }

    public void setLength (int length) {
        this.length = length;
    }

    public int getLength() {
        return length;
    }
}
