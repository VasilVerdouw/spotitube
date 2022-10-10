package nl.vasilverdouw.spotitube.datasource;

import jakarta.inject.Inject;
import nl.vasilverdouw.spotitube.datasource.util.DatabaseProperties;

import java.util.logging.Logger;

public class PlaylistDao {
    private Logger logger = Logger.getLogger(getClass().getName());

    private DatabaseProperties databaseProperties;

    @Inject
    public void setDatabaseProperties(DatabaseProperties databaseProperties) {
        this.databaseProperties = databaseProperties;
    }

    public void getPlaylists() {

    }
}
