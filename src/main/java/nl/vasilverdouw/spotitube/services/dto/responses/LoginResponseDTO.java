package nl.vasilverdouw.spotitube.services.dto.responses;

public class LoginResponseDTO {
    private String token;
    private String user;

    public LoginResponseDTO(String token, String user) {
        this.token = token;
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public String getUser() {
        return user;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
