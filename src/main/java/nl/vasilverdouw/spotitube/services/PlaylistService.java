package nl.vasilverdouw.spotitube.services;

import nl.vasilverdouw.spotitube.services.dto.PlaylistDTO;
import nl.vasilverdouw.spotitube.services.dto.PlaylistsDTO;

import java.util.ArrayList;
public class PlaylistService {
    private PlaylistsDTO playlists;

    public PlaylistService() {
        var lists = new ArrayList<PlaylistDTO>();
        lists.add(new PlaylistDTO(1, "Playlist 1", true, 0, new ArrayList<>()));
        lists.add(new PlaylistDTO(2, "Playlist 2", false, 0, new ArrayList<>()));
        lists.add(new PlaylistDTO(3, "Playlist 3", false, 0, new ArrayList<>()));
        playlists = new PlaylistsDTO(lists, 0);
    }

    public PlaylistsDTO getPlaylists(String token) {
        return playlists;
    }
}
