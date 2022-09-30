package nl.vasilverdouw.spotitube.resources;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import nl.vasilverdouw.spotitube.services.LoginService;
import nl.vasilverdouw.spotitube.services.dto.LoginRequestDTO;
import nl.vasilverdouw.spotitube.services.dto.LoginResponseDTO;

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
        if(loginService.login(loginRequestDTO.getUser(), loginRequestDTO.getPassword())) {
            String user = loginService.getUser(loginRequestDTO.getUser());
            String token = loginService.generateToken(loginRequestDTO.getUser());
            LoginResponseDTO loginResponseDTO = new LoginResponseDTO(token, user);

            return Response.ok().entity(loginResponseDTO).build();
        }
        return Response.status(Response.Status.UNAUTHORIZED).build();
    }
}
