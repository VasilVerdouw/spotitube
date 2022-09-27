package nl.vasilverdouw.spotitube.services;

public class LoginService {
    public boolean login(String user, String password) {
        return "Admin".equals(user) && "Password".equals(password);
    }

    public String getUser(String user) {
        return "Admin";
    }

    public String generateToken(String user) {
        return "0000";
    }
}
