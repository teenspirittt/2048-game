package TwentyFortyEight.game;

import TwentyFortyEight.Server.DataBaseHandler;
import TwentyFortyEight.view.Game;
import TwentyFortyEight.view.RegisterView;
import javafx.scene.input.KeyCode;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class RegisterController {
    private static RegisterController instance;

    public static synchronized RegisterController getInstance() {
        if (instance == null) {
            instance = new RegisterController();
        }
        return instance;
    }

    RegisterView registerView = RegisterView.getInstance();


    public void showRegWindow() {
        registerView.getStage().show();
        registerView.getScene().setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ESCAPE)
                hideRegWin();
        });
        signInButtonLogic();
        signUpButtonLogic();
    }

    private void hideRegWin() {
        registerView.getStage().hide();
    }

    private void signInButtonLogic() {
        registerView.getSignIn().setOnAction(actionEvent -> {
            String username = registerView.getLogUsernameField().getText().trim();
            String password = registerView.getLogPasswordField().getText();
            if (!username.equals("") && !password.equals("")) {
                loginUser(username, password);
            } else {
                WarningDialog.emptyField();
            }
        });
    }

    private void signUpButtonLogic() {
        DataBaseHandler dataBaseHandler = new DataBaseHandler();
        registerView.getSignUp().setOnAction(actionEvent -> {
            String username = registerView.getRegUsernameField().getText();
            String password = registerView.getRegPasswordField().getText();
            String confirmPassword = registerView.getRegConfirmPasswordField().getText();

            if (!isCorrectUsername(username)) {
                if (Objects.equals(password, confirmPassword)) {
                    if (!password.equals("")) {
                        dataBaseHandler.signUpUser(username, password, Game.getInstance().getHighScore());
                        openLeaderBoardWindow();
                    } else {
                        WarningDialog.emptyField();
                    }
                } else {
                    WarningDialog.mismatchedPasswords();
                }
            } else {
                WarningDialog.usernameInUse(username);
            }
        });
    }

    private void loginUser(String username, String password) {
        if (isCorrectUsername(username))
            if (isCorrectPassword(username, password))
                openLeaderBoardWindow();
            else
                WarningDialog.incorrectPassword();
        else
            WarningDialog.userNotFound(username);
    }

    private boolean isCorrectUsername(String username) {
        DataBaseHandler dataBaseHandler = new DataBaseHandler();
        ResultSet res = dataBaseHandler.getUser(username);
        return matchCounter(res) >= 1;
    }

    private boolean isCorrectPassword(String username, String password) {
        DataBaseHandler dataBaseHandler = new DataBaseHandler();
        ResultSet res = dataBaseHandler.getPassword(username, password);
        return matchCounter(res) >= 1;
    }

    private int matchCounter(ResultSet res) {
        int counter = 0;
        try {
            while (res.next())
                counter++;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return counter;
    }

    private void openLeaderBoardWindow() {
        registerView.getScene().getWindow().hide();
        LeaderBoardController.getInstance().showLBWindow();
        Game.getInstance().getShowLeaderboard().setOnAction(actionEvent -> LeaderBoardController.getInstance().showLBWindow());
    }
}
