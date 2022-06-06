package Server;

import java.io.Serializable;

public class UserPackage implements Serializable {
    public int highScore;
    public String username;
    public String password;
    public String message;

    public UserPackage(String username, String password, int highScore, String message) {
        this.username = username;
        this.password = password;
        this.highScore = highScore;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public int getHighScore() {
        return highScore;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
