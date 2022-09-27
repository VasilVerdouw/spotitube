package nl.vasilverdouw.spotitube.resources;

import nl.vasilverdouw.spotitube.services.LoginService;
import nl.vasilverdouw.spotitube.services.dto.LoginRequestDTO;
import nl.vasilverdouw.spotitube.services.dto.LoginResponseDTO;
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

    @Test
    public void testLoginStatusCode() {
        // Arrange
        LoginRequestDTO loginRequestDTO = new LoginRequestDTO();
        loginRequestDTO.setUser("Admin");
        loginRequestDTO.setPassword("Password");

        // Act
        var response = loginResource.login(loginRequestDTO);

        // Assert
        assertEquals(200, response.getStatus());
    }

    @Test
    public void testLoginWithWrongPasswordStatusCode() {
        // Arrange
        LoginRequestDTO loginRequestDTO = new LoginRequestDTO();
        loginRequestDTO.setUser("Admin");
        loginRequestDTO.setPassword("WrongPassword");

        // Act
        var response = loginResource.login(loginRequestDTO);

        // Assert
        assertEquals(401, response.getStatus());
    }

    @Test
    public void testLoginResponseInstanceOfLoginResponseDTO() {
        // Arrange
        LoginRequestDTO loginRequestDTO = new LoginRequestDTO();
        loginRequestDTO.setUser("Admin");
        loginRequestDTO.setPassword("Password");

        // Act
        var response = loginResource.login(loginRequestDTO);

        // Assert
        assertEquals(LoginResponseDTO.class, response.getEntity().getClass());
    }

    @Test
    public void testLoginResponseUser() {
        // Arrange
        LoginRequestDTO loginRequestDTO = new LoginRequestDTO();
        loginRequestDTO.setUser("Admin");
        loginRequestDTO.setPassword("Password");

        // Act
        var response = loginResource.login(loginRequestDTO);

        // Assert
        assertEquals("Admin", ((LoginResponseDTO) response.getEntity()).getUser());
    }

    @Test
    public void testLoginResponseToken() {
        // Arrange
        LoginRequestDTO loginRequestDTO = new LoginRequestDTO();
        loginRequestDTO.setUser("Admin");
        loginRequestDTO.setPassword("Password");

        // Act
        var response = loginResource.login(loginRequestDTO);

        // Assert
        assertEquals("0000", ((LoginResponseDTO) response.getEntity()).getToken());
    }
}
