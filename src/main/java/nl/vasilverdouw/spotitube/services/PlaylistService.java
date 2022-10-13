package nl.vasilverdouw.spotitube.services;

import jakarta.inject.Inject;
import nl.vasilverdouw.spotitube.datasource.PlaylistDao;
import nl.vasilverdouw.spotitube.exceptions.ActionFailedException;
import nl.vasilverdouw.spotitube.services.dto.data.PlaylistDTO;
import nl.vasilverdouw.spotitube.services.dto.data.TrackDTO;
import nl.vasilverdouw.spotitube.services.dto.requests.PlaylistRequestDTO;
import nl.vasilverdouw.spotitube.services.dto.requests.TrackRequestDTO;
import nl.vasilverdouw.spotitube.services.dto.responses.PlaylistResponseDTO;
import nl.vasilverdouw.spotitube.services.dto.responses.PlaylistsResponseDTO;
import nl.vasilverdouw.spotitube.services.dto.responses.TrackResponseDTO;
import nl.vasilverdouw.spotitube.services.dto.responses.TracksResponseDTO;

import java.util.ArrayList;
import java.util.List;

public class PlaylistService {
    private PlaylistDao playlistDao = new PlaylistDao();

    @Inject
    public void setPlaylistDao(PlaylistDao playlistDao) {
        this.playlistDao = playlistDao;
    }

    public PlaylistsResponseDTO getPlaylists(String token) throws ActionFailedException {
        var playlists = playlistDao.getPlaylists();
        var mappedPlaylists = mapPlaylistsToResponsePlaylists(playlists, token);
        return new PlaylistsResponseDTO(mappedPlaylists, playlistDao.getLengthOfAllPlaylists());
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

    public TracksResponseDTO getTracksInPlaylist(int id) throws ActionFailedException {
        var tracks = playlistDao.getTracksInPlaylist(id);
        return mapTracksListToTracksResponseDTO(tracks);
    }

    public TracksResponseDTO addTrackToPlaylist(TrackRequestDTO track, int id) throws ActionFailedException {
        if(playlistDao.addTrackToPlaylist(id, track.getId(), track.isOfflineAvailable())) {
            return getTracksInPlaylist(id);
        }
        throw new ActionFailedException("Failed to add track to playlist");
    }

    public TracksResponseDTO removeTrackFromPlaylist(int playlistId, int trackId) throws ActionFailedException {
        if(playlistDao.removeTrackFromPlaylist(playlistId, trackId)){
            return getTracksInPlaylist(playlistId);
        }
        throw new ActionFailedException("Failed to remove track from playlist");
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

    public TracksResponseDTO mapTracksListToTracksResponseDTO(List<TrackDTO> tracks) {
        var mappedTracks = new ArrayList<TrackResponseDTO>();
        for (var track : tracks) {
            mappedTracks.add(new TrackResponseDTO(track.getId(), track.getTitle(), track.getPerformer(), track.getDuration(), track.getAlbum(), track.getPlaycount(), track.getPublicationDate(), track.getDescription(), track.isOfflineAvailable()));
        }
        return new TracksResponseDTO(mappedTracks);
    }
}
