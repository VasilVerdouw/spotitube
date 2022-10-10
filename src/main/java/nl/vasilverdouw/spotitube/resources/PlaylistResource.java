package nl.vasilverdouw.spotitube.resources;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import nl.vasilverdouw.spotitube.services.PlaylistService;

@Path("/playlists")
public class PlaylistResource {
    private PlaylistService playlistService;

    public PlaylistResource() {}

    public PlaylistResource(PlaylistService playlistService) {
        this.playlistService = playlistService;
    }

    @Inject
    public void setPlaylistService(PlaylistService playlistService) {
        this.playlistService = playlistService;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPlaylists(@QueryParam("token") String token) {
        var playlists = playlistService.getPlaylists(token);
        return Response.ok(playlists).build();
    }
}
