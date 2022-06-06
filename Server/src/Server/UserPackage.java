package Server;

public class UserPackage {
    public int highScore;
    public String username;
    public String password;

    UserPackage(String username, String password, int highScore) {
        this.username = username;
        this.password = password;
        this.highScore = highScore;
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
