package nl.vasilverdouw.spotitube.datasource.dao;

import jakarta.inject.Inject;
import nl.vasilverdouw.spotitube.datasource.util.DatabaseProperties;
import nl.vasilverdouw.spotitube.exceptions.ActionFailedException;
import nl.vasilverdouw.spotitube.dto.data.PlaylistDTO;
import nl.vasilverdouw.spotitube.dto.data.TrackDTO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PlaylistDao {
    private Logger logger = Logger.getLogger(getClass().getName());

    private DatabaseProperties databaseProperties;

    @Inject
    public void setDatabaseProperties(DatabaseProperties databaseProperties) {
        this.databaseProperties = databaseProperties;
    }

    public List<PlaylistDTO> getPlaylists() throws ActionFailedException {
        try {
            Connection connection = DriverManager.getConnection(databaseProperties.connectionString());
            PreparedStatement statement = connection.prepareStatement("SELECT id, name, (SELECT token FROM users u WHERE u.username = p.owner) AS 'token' FROM playlists p");
            ResultSet resultSet = statement.executeQuery();
            List<PlaylistDTO> playlists = new ArrayList<>();
            while(resultSet.next()) {
                var id = resultSet.getInt("id");
                var name = resultSet.getString("name");
                var owner = resultSet.getString("token");
                playlists.add(new PlaylistDTO(id, name, owner));
            }
            statement.close();
            connection.close();
            return playlists;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error while getting playlists", e);
        }
        throw new ActionFailedException("Failed to get playlists");
    }

    public int getLengthOfAllPlaylists() throws ActionFailedException {
        try {
            Connection connection = DriverManager.getConnection(databaseProperties.connectionString());
            PreparedStatement statement = connection.prepareStatement("SELECT SUM(duration) FROM tracksInPlaylists tp INNER JOIN tracks t ON tp.track = t.id");
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            int length = resultSet.getInt(1);
            statement.close();
            connection.close();
            return length;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error while getting length of playlists", e);
        }
        throw new ActionFailedException("Failed to get length of playlists");
    }

    public boolean deletePlaylist(int id) {
        try {
            Connection connection = DriverManager.getConnection(databaseProperties.connectionString());
            PreparedStatement statement = connection.prepareStatement("DELETE FROM playlists WHERE id = ?");
            statement.setInt(1, id);
            statement.execute();
            statement.close();
            connection.close();
            return true;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error while deleting playlist", e);
        }
        return false;
    }

    public boolean addPlaylist(PlaylistDTO playlistDTO) {
        try {
            Connection connection = DriverManager.getConnection(databaseProperties.connectionString());
            PreparedStatement statement = connection.prepareStatement("INSERT INTO playlists (name, owner) VALUES (?, (SELECT username FROM users WHERE token = ?))");
            statement.setString(1, playlistDTO.getName());
            statement.setString(2, playlistDTO.getOwner());
            var result = statement.executeUpdate() > 0;
            statement.close();
            connection.close();
            return result;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error while adding playlist", e);
        }
        return false;
    }

    public boolean renamePlaylist(PlaylistDTO playlistDTO) {
        try {
            Connection connection = DriverManager.getConnection(databaseProperties.connectionString());
            PreparedStatement statement = connection.prepareStatement("UPDATE playlists SET name = ? WHERE id = ?");
            statement.setString(1, playlistDTO.getName());
            statement.setInt(2, playlistDTO.getId());
            var result = statement.executeUpdate() > 0;
            statement.close();
            connection.close();
            return result;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error while renaming playlist", e);
        }
        return false;
    }

    public List<TrackDTO> getTracksInPlaylist(int playlist) {
        try {
            Connection connection = DriverManager.getConnection(databaseProperties.connectionString());
            PreparedStatement statement = connection.prepareStatement("SELECT id, title, performer, duration, album, playcount, publicationDate, description, offlineAvailable FROM tracks t INNER JOIN tracksInPlaylists tp ON t.id = tp.track WHERE tp.playlist = ?");
            statement.setInt(1, playlist);
            ResultSet resultSet = statement.executeQuery();
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
            statement.close();
            connection.close();
            return tracks;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error while getting tracks", e);
        }
        throw new ActionFailedException("Failed to get tracks");
    }

    public boolean addTrackToPlaylist(int playlist, int track, boolean offlineAvailable) {
        try {
            Connection connection = DriverManager.getConnection(databaseProperties.connectionString());
            PreparedStatement statement = connection.prepareStatement("INSERT INTO tracksInPlaylists (playlist, track, offlineAvailable) VALUES (?, ?, ?)");
            statement.setInt(1, playlist);
            statement.setInt(2, track);
            statement.setBoolean(3, offlineAvailable);
            var result = statement.executeUpdate() > 0;
            statement.close();
            connection.close();
            return result;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error while adding track to playlist", e);
        }
        return false;
    }

    public boolean removeTrackFromPlaylist(int playlist, int track) {
        try {
            Connection connection = DriverManager.getConnection(databaseProperties.connectionString());
            PreparedStatement statement = connection.prepareStatement("DELETE FROM tracksInPlaylists WHERE playlist = ? AND track = ?");
            statement.setInt(1, playlist);
            statement.setInt(2, track);
            var result = statement.executeUpdate() > 0;
            statement.close();
            connection.close();
            return result;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error while removing track from playlist", e);
        }
        return false;
    }
}
