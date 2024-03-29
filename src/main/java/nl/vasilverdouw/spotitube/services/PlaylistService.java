package nl.vasilverdouw.spotitube.services;

import jakarta.inject.Inject;
import nl.vasilverdouw.spotitube.datasource.dao.PlaylistDao;
import nl.vasilverdouw.spotitube.exceptions.ActionFailedException;
import nl.vasilverdouw.spotitube.dto.data.PlaylistDTO;
import nl.vasilverdouw.spotitube.dto.data.TrackDTO;
import nl.vasilverdouw.spotitube.dto.requests.PlaylistRequestDTO;
import nl.vasilverdouw.spotitube.dto.requests.TrackRequestDTO;
import nl.vasilverdouw.spotitube.dto.responses.PlaylistResponseDTO;
import nl.vasilverdouw.spotitube.dto.responses.PlaylistsResponseDTO;
import nl.vasilverdouw.spotitube.dto.responses.TrackResponseDTO;
import nl.vasilverdouw.spotitube.dto.responses.TracksResponseDTO;
import nl.vasilverdouw.spotitube.exceptions.BadRequestException;
import nl.vasilverdouw.spotitube.exceptions.UnauthorizedException;

import java.util.ArrayList;
import java.util.List;

public class PlaylistService {
    private PlaylistDao playlistDao = new PlaylistDao();

    public PlaylistService() {}

    @Inject
    public PlaylistService(PlaylistDao playlistDao) {
        this.playlistDao = playlistDao;
    }

    public PlaylistsResponseDTO getPlaylists(String token) {
        var result = playlistDao.getPlaylists();

        if(result == null) {
            throw new ActionFailedException("Failed to get playlists");
        }

        var mappedPlaylists = mapPlaylistsToResponsePlaylists(result, token);
        return new PlaylistsResponseDTO(mappedPlaylists, playlistDao.getLengthOfAllPlaylists());
    }

    public PlaylistsResponseDTO deletePlaylist(int id, String token) {
        // Might want to check first if the playlist exists, but that's not necessary in this case.
        // Difference is not that big either way but will cost 1 more database call.
        if(!isUserOwnerOfPlaylist(id, token)) {
            throw new UnauthorizedException("User is not owner of playlist");
        }

        if(playlistDao.deletePlaylist(id) > 0){
            return getPlaylists(token);
        }

        throw new ActionFailedException("Failed to delete playlist");
    }

    public PlaylistsResponseDTO addPlaylist(PlaylistRequestDTO playlist, String token) {
        var mappedPlaylist = mapPlaylistRequestToPlaylist(playlist, token);
        if(playlistDao.addPlaylist(mappedPlaylist) > 0){
            return getPlaylists(token);
        }

        throw new ActionFailedException("Failed to add playlist");
    }

    public PlaylistsResponseDTO renamePlaylist(int id, String token, PlaylistRequestDTO playlist) {
        // First check to do because it doesn't require any database calls
        // This way we might save 1 call if this check fails.
        if(playlist.getId() != id) {
            throw new BadRequestException("Id in path does not match id in body");
        }

        if(!isUserOwnerOfPlaylist(id, token)) {
            throw new UnauthorizedException("User is not owner of playlist");
        }

        var mappedPlaylist = mapPlaylistRequestToPlaylist(playlist, token);
        if(playlistDao.renamePlaylist(mappedPlaylist) > 0){
            return getPlaylists(token);
        }

        throw new ActionFailedException("Failed to rename playlist");
    }

    public TracksResponseDTO getTracksInPlaylist(int id) {
        var tracks = playlistDao.getTracksInPlaylist(id);
        if(tracks != null) {
            return mapTracksListToTracksResponseDTO(tracks);
        }

        throw new ActionFailedException("Failed to get tracks in playlist");
    }

    public TracksResponseDTO addTrackToPlaylist(TrackRequestDTO track, int id, String token) {
        if(!isUserOwnerOfPlaylist(id, token)) {
            throw new UnauthorizedException("User is not owner of playlist");
        }

        if(playlistDao.addTrackToPlaylist(id, track.getId(), track.isOfflineAvailable()) > 0) {
            return getTracksInPlaylist(id);
        }

        throw new ActionFailedException("Failed to add track to playlist");
    }

    public TracksResponseDTO removeTrackFromPlaylist(int playlistId, int trackId, String token) {
        if(!isUserOwnerOfPlaylist(playlistId, token)) {
            throw new UnauthorizedException("User is not owner of playlist");
        }

        if(playlistDao.removeTrackFromPlaylist(playlistId, trackId) > 0){
            return getTracksInPlaylist(playlistId);
        }

        throw new ActionFailedException("Failed to remove track from playlist");
    }

    public boolean isUserOwnerOfPlaylist(int playlistId, String token) {
        return playlistDao.findAmountOfPlaylistsWithIdOwnedByToken(playlistId, token) > 0;
    }

    // Some simple mapping methods. Could be done with a library like MapStruct
    private ArrayList<PlaylistResponseDTO> mapPlaylistsToResponsePlaylists(List<PlaylistDTO> playlists, String token) {
        var responsePlaylists = new ArrayList<PlaylistResponseDTO>();
        for (PlaylistDTO playlist : playlists) {
            responsePlaylists.add(new PlaylistResponseDTO(playlist.getId(), playlist.getName(), token.equals(playlist.getOwner())));
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
