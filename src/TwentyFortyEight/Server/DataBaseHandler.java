package TwentyFortyEight.Server;

import java.sql.*;


public class DataBaseHandler {
    private final String URL = "jdbc:postgresql://localhost:5432/2048";
    private final String username = "postgres";
    private final String password = "root";
    private final String tableName = "rabbits";



    public void addLogin(String login) {
        try (Connection connection = DriverManager.getConnection(URL, username, password)) {
            Class.forName("org.postgresql.Driver");
            Statement statement = connection.createStatement();

            ResultSet rs = statement.executeQuery("SELECT EXISTS(SELECT * FROM" + tableName + "WHERE login = '" + login + "');");
            boolean newLogin = true;
            if (rs.next())
                if (rs.getString(1).equals("1"))
                    newLogin = false;
            if (newLogin) {
                statement.executeUpdate("insert into " + tableName + " VALUES ('"+login+"',0);");
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}

