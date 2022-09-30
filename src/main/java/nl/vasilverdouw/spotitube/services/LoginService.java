package nl.vasilverdouw.spotitube.services;

import nl.vasilverdouw.spotitube.services.dto.LoginRequestDTO;
import nl.vasilverdouw.spotitube.services.dto.LoginResponseDTO;

public class LoginService {
    public LoginResponseDTO login(LoginRequestDTO loginRequestDTO) {
        var user = loginRequestDTO.getUser();
        var password = loginRequestDTO.getPassword();

        if(user.equals("Admin") && password.equals("Password")) {
            return new LoginResponseDTO(generateToken(user), getUser(user));
        }

        return null;
    }

    public String getUser(String user) {
        return "Admin";
    }

    public String generateToken(String user) {
        return "0000";
    }
}
