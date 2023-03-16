package nl.vasilverdouw.spotitube.datasource.dao;

import jakarta.inject.Inject;
import nl.vasilverdouw.spotitube.datasource.util.DatabaseProperties;
import nl.vasilverdouw.spotitube.exceptions.ActionFailedException;
import nl.vasilverdouw.spotitube.dto.data.TrackDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TrackDao extends BaseDao {
    public List<TrackDTO> getTracksForPlaylist(int playlistId) {
        try (PreparedStatement preparedStatement = prepareStatement("SELECT id, title, performer, duration, album, playcount, publicationDate, description FROM tracks WHERE id NOT IN (SELECT track FROM tracksInPlaylists WHERE playlist = ?)");
             ResultSet resultSet = executeQuery(preparedStatement, playlistId)) {
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
                tracks.add(new TrackDTO(id, title, performer, duration, album, playcount, publicationDate, description, false));
            }
            return tracks;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error while getting tracks for playlist", e);
        }
        return null;
    }
}
