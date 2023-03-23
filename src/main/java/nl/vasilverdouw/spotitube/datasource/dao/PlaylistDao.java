package nl.vasilverdouw.spotitube.datasource.dao;

import nl.vasilverdouw.spotitube.exceptions.ActionFailedException;
import nl.vasilverdouw.spotitube.dto.data.PlaylistDTO;
import nl.vasilverdouw.spotitube.dto.data.TrackDTO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class PlaylistDao extends BaseDao {
    public List<PlaylistDTO> getPlaylists() {
        try (ResultSet resultSet = executeQuery(prepareStatement("SELECT id, name, (SELECT token FROM users u WHERE u.username = p.owner) AS 'token' FROM playlists p"))){
            List<PlaylistDTO> playlists = new ArrayList<>();
            while(resultSet.next()) {
                var id = resultSet.getInt("id");
                var name = resultSet.getString("name");
                var owner = resultSet.getString("token");
                playlists.add(new PlaylistDTO(id, name, owner));
            }

            return playlists;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error while getting playlists", e);
        }
        return null;
    }

    public int getLengthOfAllPlaylists() {
        try (ResultSet resultSet = executeQuery(prepareStatement("SELECT SUM(duration) FROM tracksInPlaylists tp INNER JOIN tracks t ON tp.track = t.id"))) {
            if(resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error while getting length of playlists", e);
        }
        // I've chosen to return 0 so if something goes wrong, the user will still be able to view the playlists.
        // -1 or an error could be used as well if you want to handle this differently.
        return 0;
    }

    public int deletePlaylist(int id) {
        return executeUpdate("DELETE FROM playlists WHERE id = ?", id);
    }

    public int addPlaylist(PlaylistDTO playlistDTO) {
        return executeUpdate("INSERT INTO playlists (name, owner) VALUES (?, (SELECT username FROM users WHERE token = ?))", playlistDTO.getName(), playlistDTO.getOwner());
    }

    public int renamePlaylist(PlaylistDTO playlistDTO) {
        return executeUpdate("UPDATE playlists SET name = ? WHERE id = ?", playlistDTO.getName(), playlistDTO.getId());
    }

    public List<TrackDTO> getTracksInPlaylist(int playlist) {
        try (PreparedStatement preparedStatement = prepareStatement("SELECT id, title, performer, duration, album, playcount, publicationDate, description, offlineAvailable FROM tracks t INNER JOIN tracksInPlaylists tp ON t.id = tp.track WHERE tp.playlist = ?");
                ResultSet resultSet = executeQuery(preparedStatement, playlist)){
            List<TrackDTO> tracks = new ArrayList<>();
            while(resultSet.next()) {
                var id = resultSet.getInt("id");
                var title = resultSet.getString("title");
                var performer = resultSet.getString("performer");
                var duration = resultSet.getInt("duration");
                var album = resultSet.getString("album");
                var playcount = resultSet.getInt("playcount");
                // Please note that publication date should most likely be a date value in the database.
                // Because of time constraints and the fact that it didn't matter in the current context,
                // I decided to leave it as a string.
                var publicationDate = resultSet.getString("publicationDate");
                var description = resultSet.getString("description");
                var offlineAvailable = resultSet.getBoolean("offlineAvailable");
                tracks.add(new TrackDTO(id, title, performer, duration, album, playcount, publicationDate, description, offlineAvailable));
            }
            return tracks;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error while getting tracks", e);
        }
        return null;
    }

    public int addTrackToPlaylist(int playlist, int track, boolean offlineAvailable) {
        return executeUpdate("INSERT INTO tracksInPlaylists (playlist, track, offlineAvailable) VALUES (?, ?, ?)", playlist, track, offlineAvailable);
    }

    public int removeTrackFromPlaylist(int playlist, int track) {
        return executeUpdate("DELETE FROM tracksInPlaylists WHERE playlist = ? AND track = ?", playlist, track);
    }
}
