package nl.vasilverdouw.spotitube.resources;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import nl.vasilverdouw.spotitube.exceptions.ActionFailedException;
import nl.vasilverdouw.spotitube.exceptions.UnauthorizedException;
import nl.vasilverdouw.spotitube.services.LoginService;
import nl.vasilverdouw.spotitube.services.dto.requests.LoginRequestDTO;
import nl.vasilverdouw.spotitube.services.dto.responses.LoginResponseDTO;

@Path("/login")
public class LoginResource {
    private LoginService loginService;

    public LoginResource() {}
    public LoginResource(LoginService loginService) {
        this.loginService = loginService;
    }

    @Inject
    public void setLoginService(LoginService loginService) {
        this.loginService = loginService;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(LoginRequestDTO loginRequestDTO) {
        try {
            return Response.ok(loginService.login(loginRequestDTO)).build();
        } catch (UnauthorizedException e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        } catch (ActionFailedException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }
}
