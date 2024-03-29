package nl.vasilverdouw.spotitube.dto.requests;

public class LoginRequestDTO {
    private String user;
    private String password;

    public LoginRequestDTO(String user, String password) {
        this.user = user;
        this.password = password;
    }
    public LoginRequestDTO() {}

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
