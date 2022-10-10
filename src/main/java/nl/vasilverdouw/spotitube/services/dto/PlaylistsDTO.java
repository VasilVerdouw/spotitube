package nl.vasilverdouw.spotitube.services.dto;

import java.util.ArrayList;

public class PlaylistsDTO {
    private ArrayList<PlaylistDTO> playlists = new ArrayList<>();
    private int length;

    public PlaylistsDTO(ArrayList<PlaylistDTO> playlists, int length) {
        this.playlists = playlists;
        this.length = length;
    }

    public PlaylistDTO[] getPlaylists() {
        return playlists.toArray(new PlaylistDTO[0]);
    }

    public int getLength() {
        return length;
    }
}
