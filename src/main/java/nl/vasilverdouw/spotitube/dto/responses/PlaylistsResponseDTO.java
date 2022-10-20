package nl.vasilverdouw.spotitube.dto.responses;

import java.util.ArrayList;

public class PlaylistsResponseDTO {
    private ArrayList<PlaylistResponseDTO> playlists = new ArrayList<>();
    private int length;

    public PlaylistsResponseDTO(ArrayList<PlaylistResponseDTO> playlists, int length) {
        this.playlists = playlists;
        this.length = length;
    }

    public PlaylistResponseDTO[] getPlaylists() {
        return playlists.toArray(new PlaylistResponseDTO[0]);
    }

    public int getLength() {
        return length;
    }
}
