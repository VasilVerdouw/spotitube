package nl.vasilverdouw.spotitube.services;

import jakarta.inject.Inject;
import nl.vasilverdouw.spotitube.datasource.dao.LoginDao;
import nl.vasilverdouw.spotitube.exceptions.ActionFailedException;
import nl.vasilverdouw.spotitube.exceptions.UnauthorizedException;
import nl.vasilverdouw.spotitube.dto.requests.LoginRequestDTO;
import nl.vasilverdouw.spotitube.dto.responses.LoginResponseDTO;

import java.util.UUID;

public class LoginService {

    private LoginDao loginDao;

    public LoginService() {}
    @Inject
    public LoginService(LoginDao loginDao) {
        this.loginDao = loginDao;
    }

    public LoginResponseDTO login(LoginRequestDTO loginRequestDTO) {
        var username = loginRequestDTO.getUser();
        var password = loginRequestDTO.getPassword();
        var user = loginDao.getUser(username);
        // This way we don't reveal if the user exists or not which would be a security risk.
        if (user != null && user.getPassword().equals(password)) {
            var token = generateToken();
            if (loginDao.setToken(username, token) > 0) {
                return new LoginResponseDTO(token, user.getFullname());
            } else {
                throw new ActionFailedException("Failed to set token");
            }
        }
        throw new UnauthorizedException("Invalid username or password");
    }

    // Source: https://stackoverflow.com/questions/41107/how-to-generate-a-random-alpha-numeric-string
    private String generateToken() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
