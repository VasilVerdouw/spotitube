package nl.vasilverdouw.spotitube.api.resources;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import nl.vasilverdouw.spotitube.services.LoginService;
import nl.vasilverdouw.spotitube.dto.requests.LoginRequestDTO;

@Path("/login")
public class LoginResource {
    private LoginService loginService;

    // Deze constructor is nodig voor JAXRS.
    public LoginResource() {}
    @Inject
    public LoginResource(LoginService loginService) {
        this.loginService = loginService;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(LoginRequestDTO loginRequestDTO) {
        return Response.ok(loginService.login(loginRequestDTO)).build();
    }
}












