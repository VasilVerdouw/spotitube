package nl.vasilverdouw.spotitube.api.resources;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import nl.vasilverdouw.spotitube.services.PlaylistService;
import nl.vasilverdouw.spotitube.dto.requests.PlaylistRequestDTO;
import nl.vasilverdouw.spotitube.dto.requests.TrackRequestDTO;

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
        return Response.ok(playlistService.getPlaylists(token)).build();
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deletePlaylist(@PathParam("id") int id, @QueryParam("token") String token) {
        return Response.ok(playlistService.deletePlaylist(id, token)).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addPlaylist(PlaylistRequestDTO playlist, @QueryParam("token") String token) {
        return Response.status(Response.Status.CREATED).entity(playlistService.addPlaylist(playlist, token)).build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public Response editPlaylist(PlaylistRequestDTO playlist, @QueryParam("token") String token) {
        return Response.ok(playlistService.renamePlaylist(playlist, token)).build();
    }

    @GET
    @Path("/{id}/tracks")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTracksInPlaylist(@QueryParam("token") String token, @PathParam("id") int id) {
        return Response.ok(playlistService.getTracksInPlaylist(id)).build();
    }

    @POST
    @Path("/{id}/tracks")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addTrackToPlaylist(@QueryParam("token") String token, @PathParam("id") int id, TrackRequestDTO track) {
        return Response.status(Response.Status.CREATED).entity(playlistService.addTrackToPlaylist(track, id)).build();
    }

    @DELETE
    @Path("/{id}/tracks/{trackId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response removeTrackFromPlaylist(@QueryParam("token") String token, @PathParam("id") int id, @PathParam("trackId") int trackId) {
        return Response.ok(playlistService.removeTrackFromPlaylist(id, trackId)).build();
    }
}
