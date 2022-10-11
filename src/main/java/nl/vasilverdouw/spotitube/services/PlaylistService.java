package nl.vasilverdouw.spotitube.services;

import jakarta.inject.Inject;
import nl.vasilverdouw.spotitube.datasource.PlaylistDao;
import nl.vasilverdouw.spotitube.exceptions.ActionFailedException;
import nl.vasilverdouw.spotitube.exceptions.UnauthorizedException;
import nl.vasilverdouw.spotitube.services.dto.data.PlaylistDTO;
import nl.vasilverdouw.spotitube.services.dto.requests.PlaylistRequestDTO;
import nl.vasilverdouw.spotitube.services.dto.responses.PlaylistResponseDTO;
import nl.vasilverdouw.spotitube.services.dto.responses.PlaylistsResponseDTO;

import java.util.ArrayList;
import java.util.List;

public class PlaylistService {
    PlaylistDao playlistDao = new PlaylistDao();

    @Inject
    public void setPlaylistDao(PlaylistDao playlistDao) {
        this.playlistDao = playlistDao;
    }

    public PlaylistsResponseDTO getPlaylists(String token) throws ActionFailedException {
        var playlists = playlistDao.getPlaylists();
        var responsePlaylists = mapPlaylistsToResponsePlaylists(playlists, token);
        return new PlaylistsResponseDTO(responsePlaylists, playlistDao.getLengthOfAllPlaylists());
    }

    public PlaylistsResponseDTO deletePlaylist(int id, String token) throws ActionFailedException {
        if(playlistDao.deletePlaylist(id)){
            return getPlaylists(token);
        }
        throw new ActionFailedException("Failed to delete playlist");
    }

    public PlaylistsResponseDTO addPlaylist(PlaylistRequestDTO playlist, String token) throws ActionFailedException {
        var mappedPlaylist = mapPlaylistRequestToPlaylist(playlist, token);
        if(playlistDao.addPlaylist(mappedPlaylist)){
            return getPlaylists(token);
        }
        throw new ActionFailedException("Failed to add playlist");
    }

    public PlaylistsResponseDTO renamePlaylist(PlaylistRequestDTO playlist, String token) throws ActionFailedException {
        var mappedPlaylist = mapPlaylistRequestToPlaylist(playlist, token);
        if(playlistDao.renamePlaylist(mappedPlaylist)){
            return getPlaylists(token);
        }
        throw new ActionFailedException("Failed to rename playlist");
    }

    private ArrayList<PlaylistResponseDTO> mapPlaylistsToResponsePlaylists(List<PlaylistDTO> playlists, String token) {
        var responsePlaylists = new ArrayList<PlaylistResponseDTO>();
        for (PlaylistDTO playlist : playlists) {
            responsePlaylists.add(new PlaylistResponseDTO(playlist.getId(), playlist.getName(), playlist.getOwner().equals(token)));
        }
        return responsePlaylists;
    }

    private PlaylistDTO mapPlaylistRequestToPlaylist(PlaylistRequestDTO playlistRequest, String token) {
        return new PlaylistDTO(playlistRequest.getId(), playlistRequest.getName(), token);
    }
}
