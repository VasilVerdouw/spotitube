package nl.vasilverdouw.spotitube.api.resources;

import jakarta.ws.rs.core.Response;
import nl.vasilverdouw.spotitube.api.resources.LoginResource;
import nl.vasilverdouw.spotitube.exceptions.ActionFailedException;
import nl.vasilverdouw.spotitube.exceptions.UnauthorizedException;
import nl.vasilverdouw.spotitube.services.LoginService;
import nl.vasilverdouw.spotitube.dto.responses.LoginResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class LoginResourceTest {
    private LoginResource loginResource;
    private LoginService loginService;

    @BeforeEach
    public void setup() {
        loginService = mock(LoginService.class);
        loginResource = new LoginResource(loginService);
    }

    @Test
    public void testLoginWithValidCredentials() {
        // Arrange
        var expectedResponse = new LoginResponseDTO("token", "user");
        when(loginService.login(any())).thenReturn(expectedResponse);

        // Act
        Response response = loginResource.login(null);

        // Assert
        assertEquals(expectedResponse, response.getEntity());
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void testLoginCallsCorrectMethods() {
        // Arrange
        when(loginService.login(any())).thenReturn(new LoginResponseDTO("token", "user"));

        // Act
        loginResource.login(null);

        // Assert
        verify(loginService, times(1)).login(any());
    }

    @Test
    public void testEmptyConstructorReturnsNotNull() {
        // Arrange
        loginResource = new LoginResource();

        // Act

        // Assert
        assertNotNull(loginResource);
    }
}
