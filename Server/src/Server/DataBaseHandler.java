package Server;

import java.sql.*;


public class DataBaseHandler {
    private final String URL = "jdbc:postgresql://localhost:5432/2048";
    private final String username = "postgres";
    private final String password = "root";
    private final String tableName = "leaderboards";


    public Connection getDbConnection() throws ClassNotFoundException, SQLException {
        Connection dbConnection;
        Class.forName("org.postgresql.Driver");
        dbConnection = DriverManager.getConnection(URL, username, password);
        return dbConnection;
    }

    public ResultSet getUser(String name) {
        ResultSet rs = null;
        String select = "SELECT * FROM " + tableName + " WHERE login =? ";

        try {
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(select);
            preparedStatement.setString(1, name);
            rs = preparedStatement.executeQuery();

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return rs;
    }

    public ResultSet getPassword(String username, String password) {
        ResultSet rs = null;
        String select = "SELECT * FROM " + tableName + " WHERE login =? AND password =?";

        try {

            PreparedStatement preparedStatement = getDbConnection().prepareStatement(select);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            rs = preparedStatement.executeQuery();

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return rs;
    }


    public boolean isUsernameExist(String username) {
        try {
            String lesha = "SELECT EXISTS(SELECT * FROM " + tableName + " WHERE login = '" + username + "');";

            PreparedStatement preparedStatement = getDbConnection().prepareStatement(lesha);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next())
                return resultSet.getString(1).equals("t");


        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    public boolean isCorrectPassword(String username, String password) {
        try {
            String lesha = "SELECT EXISTS(SELECT * FROM " + tableName + " WHERE login = '" + username + "' AND password = '" + password + "' );";

            PreparedStatement preparedStatement = getDbConnection().prepareStatement(lesha);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next())
                return resultSet.getString(1).equals("t");

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    public ResultSet getTable() {
        ResultSet rs = null;
        String select = "SELECT * FROM " + tableName + "";
        try {
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(select);
            rs = preparedStatement.executeQuery();

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return rs;
    }


    public void signUpUser(String username, String password, int highScore) {
        String insert = "INSERT INTO " + tableName + " VALUES (?,?,?);";
        PreparedStatement preparedStatement = null;
        try {
            System.out.println(username + " sign up");
            preparedStatement = getDbConnection().prepareStatement(insert);
            preparedStatement.setString(1, username);
            preparedStatement.setInt(2, highScore);
            preparedStatement.setString(3, password);
            preparedStatement.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}

