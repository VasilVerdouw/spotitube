package nl.vasilverdouw.spotitube.resources;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import nl.vasilverdouw.spotitube.services.dto.TrackService;

@Path("/tracks")
public class TrackResource {
    private TrackService trackService;

    public TrackResource() {}

    public TrackResource(TrackService trackService) {
        this.trackService = trackService;
    }

    @Inject
    public void setTrackService(TrackService trackService) {
        this.trackService = trackService;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTracksForPlaylist(@QueryParam("token") String token, @QueryParam("forPlaylist") int playlistId) {
        try {
            return Response.ok(trackService.getTracksForPlaylist(playlistId)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }
}