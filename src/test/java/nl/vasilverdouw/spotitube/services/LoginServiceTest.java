package nl.vasilverdouw.spotitube.services;

import nl.vasilverdouw.spotitube.datasource.dao.LoginDao;
import nl.vasilverdouw.spotitube.dto.data.UserDTO;
import nl.vasilverdouw.spotitube.dto.requests.LoginRequestDTO;
import nl.vasilverdouw.spotitube.exceptions.UnauthorizedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class LoginServiceTest {
    private LoginService loginService;
    private LoginDao loginDao;
    private final String HASHED_PASSWORD = "94c861c03926489c97920d000b3de0ed:hjdo2P/RClYSdZWKggFdi1BSq2S/+1H2kZBo7CJUpmY=";
    private final String UNHASHED_PASSWORD = "1234";

    @BeforeEach
    public void setup() {
        loginDao = mock(LoginDao.class);
        loginService = new LoginService(loginDao);
    }

    @Test
    public void testLoginCallsGetUser() {
        // Arrange
        when(loginDao.getUser(anyString())).thenReturn(new UserDTO("username", HASHED_PASSWORD, "token", "fullname"));
        when(loginDao.setToken(anyString(), anyString())).thenReturn(1);

        // Act
        loginService.login(new LoginRequestDTO("username", UNHASHED_PASSWORD));

        // Assert
        verify(loginDao, times(1)).getUser("username");
    }

    @Test
    public void testLoginCallsSetToken() {
        // Arrange
        when(loginDao.getUser(anyString())).thenReturn(new UserDTO("username", HASHED_PASSWORD, "token", "fullname"));
        when(loginDao.setToken(anyString(), anyString())).thenReturn(1);

        // Act
        loginService.login(new LoginRequestDTO("username", UNHASHED_PASSWORD));

        // Assert
        verify(loginDao, times(1)).setToken(anyString(), anyString());
    }

    @Test
    public void testLoginWithWrongPasswordThrowsUnauthorizedException() {
        // Arrange
        when(loginDao.getUser(anyString())).thenReturn(new UserDTO("username", HASHED_PASSWORD, "token", "fullname"));
        when(loginDao.setToken(anyString(), anyString())).thenReturn(1);

        // Act & Assert
        assertThrows(UnauthorizedException.class, () -> loginService.login(new LoginRequestDTO("username", "wrongpassword")));
    }
}
