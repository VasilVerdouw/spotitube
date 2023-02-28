package nl.vasilverdouw.spotitube.dto.data;

public class UserDTO {
    private String username;
    private String password;
    private String token;
    private String fullname;

    public UserDTO() {
    }

    public UserDTO(String username, String password, String token, String fullname) {
        this.username = username;
        this.password = password;
        this.token = token;
        this.fullname = fullname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }
}
