package nl.vasilverdouw.spotitube.exceptions.mappers;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.sql.SQLTimeoutException;

@Provider
public class SQLTimeoutExceptionMapper implements ExceptionMapper<SQLTimeoutException> {
    public Response toResponse(SQLTimeoutException e) {
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(e.getMessage())
                .type("text/plain")
                .build();
    }
}