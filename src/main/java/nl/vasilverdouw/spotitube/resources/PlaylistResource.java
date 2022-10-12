package nl.vasilverdouw.spotitube.resources;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import nl.vasilverdouw.spotitube.services.PlaylistService;
import nl.vasilverdouw.spotitube.services.dto.requests.PlaylistRequestDTO;
import nl.vasilverdouw.spotitube.services.dto.requests.TrackRequestDTO;

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
        try {
            return Response.ok(playlistService.getPlaylists(token)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deletePlaylist(@PathParam("id") int id, @QueryParam("token") String token) {
        try {
            return Response.ok(playlistService.deletePlaylist(id, token)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addPlaylist(PlaylistRequestDTO playlist, @QueryParam("token") String token) {
        try {
            return Response.ok(playlistService.addPlaylist(playlist, token)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public Response editPlaylist(PlaylistRequestDTO playlist, @QueryParam("token") String token) {
        try {
            return Response.ok(playlistService.renamePlaylist(playlist, token)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GET
    @Path("/{id}/tracks")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTracksInPlaylist(@QueryParam("token") String token, @PathParam("id") int id) {
        try {
            return Response.ok(playlistService.getTracksInPlaylist(id)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @POST
    @Path("/{id}/tracks")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addTrackToPlaylist(@QueryParam("token") String token, @PathParam("id") int id, TrackRequestDTO track) {
        try {
            return Response.ok(playlistService.addTrackToPlaylist(track, id)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DELETE
    @Path("/{id}/tracks/{trackId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response removeTrackFromPlaylist(@QueryParam("token") String token, @PathParam("id") int id, @PathParam("trackId") int trackId) {
        try {
            return Response.ok(playlistService.removeTrackFromPlaylist(id, trackId)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }
}
