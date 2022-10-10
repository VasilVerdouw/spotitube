package nl.vasilverdouw.spotitube.services;

import jakarta.inject.Inject;
import nl.vasilverdouw.spotitube.datasource.LoginDao;
import nl.vasilverdouw.spotitube.services.dto.requests.LoginRequestDTO;
import nl.vasilverdouw.spotitube.services.dto.responses.LoginResponseDTO;

import java.util.UUID;

public class LoginService {

    private LoginDao loginDao;

    @Inject
    public void setLoginDao(LoginDao loginDao) {
        this.loginDao = loginDao;
    }

    public LoginResponseDTO login(LoginRequestDTO loginRequestDTO) {
        var username = loginRequestDTO.getUser();
        var password = loginRequestDTO.getPassword();
        var user = loginDao.getUser(username);
        if (user != null && user.getPassword().equals(password)) {
            var token = generateToken();
            if (loginDao.setToken(username, token)) {
                return new LoginResponseDTO(token, user.getFullname());
            }
        }
        return null;
    }

    // Source: https://stackoverflow.com/questions/41107/how-to-generate-a-random-alpha-numeric-string
    private String generateToken() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
