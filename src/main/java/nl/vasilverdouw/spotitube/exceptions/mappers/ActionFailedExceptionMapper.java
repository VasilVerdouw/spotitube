package nl.vasilverdouw.spotitube.exceptions.mappers;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import nl.vasilverdouw.spotitube.exceptions.ActionFailedException;

@Provider
public class ActionFailedExceptionMapper implements ExceptionMapper<ActionFailedException> {
    public Response toResponse(ActionFailedException e) {
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(e.getMessage())
                .type("text/plain")
                .build();
    }
}