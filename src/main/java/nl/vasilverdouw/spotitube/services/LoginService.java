package nl.vasilverdouw.spotitube.services;

import jakarta.inject.Inject;
import nl.vasilverdouw.spotitube.datasource.dao.LoginDao;
import nl.vasilverdouw.spotitube.exceptions.ActionFailedException;
import nl.vasilverdouw.spotitube.exceptions.UnauthorizedException;
import nl.vasilverdouw.spotitube.dto.requests.LoginRequestDTO;
import nl.vasilverdouw.spotitube.dto.responses.LoginResponseDTO;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.sql.SQLException;
import java.util.Base64;
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
        if (user != null && passwordIsCorrect(password, user.getPassword())) {
            var token = generateRandomString();
            if (loginDao.setToken(username, token) > 0) {
                return new LoginResponseDTO(token, user.getFullname());
            } else {
                throw new ActionFailedException("Failed to set token");
            }
        }
        throw new UnauthorizedException("Invalid username or password");
    }

    private boolean passwordIsCorrect(String password, String databasePassword) {
        // Split the database password into the salt and the hashed password
        // The character ":" can be used because it doesn't occur in the salt or the hashed password
        // https://base64.guru/learn/base64-characters#:~:text=Characters%20of%20the%20Base64%20alphabet%20can%20be%20grouped,52-61%29%3A%200123456789%204%20Special%20symbols%20%28indices%2062-63%29%3A%20%2B%2F
        var splitPassword = databasePassword.split(":");
        var salt = splitPassword[0];
        var hashedPassword = splitPassword[1];
        var passwordToCheck = hash(salt + password + getPepper());
        return passwordToCheck.equals(hashedPassword);
    }

    // Currently unused, but example to show how to save a password on account creation
    public String generatePassword(String password) {
        var salt = generateRandomString();
        var hashedPassword = hash(salt + password + getPepper());
        return salt + ":" + hashedPassword;
    }

    private String hash(String text) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash =  md.digest(text.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(hash);
        } catch (Exception e) {
            throw new ActionFailedException("Failed to hash password");
        }
    }

    private String getPepper() {
        // Please note this should DEFINITELY not be stored in the code like this.
        // This is just for the sake of the exercise.
        return "Uy6qvZV0iA2/drm4zACDLCCm7BE9aCKZVQ16bg80XiU=";
    }

    // Source: https://stackoverflow.com/questions/41107/how-to-generate-a-random-alpha-numeric-string
    private String generateRandomString() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
