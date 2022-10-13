package nl.vasilverdouw.spotitube.resources;

import nl.vasilverdouw.spotitube.services.LoginService;
import nl.vasilverdouw.spotitube.services.dto.requests.LoginRequestDTO;
import nl.vasilverdouw.spotitube.services.dto.responses.LoginResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LoginResourceTest {
    private LoginResource loginResource;
    private LoginService loginService;

    @BeforeEach
    public void setup() {
        loginService = new LoginService();
        loginResource = new LoginResource(loginService);
    }

}
