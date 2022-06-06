package game;


import Server.DataBaseHandler;
import Server.UserPackage;
import javafx.scene.input.KeyCode;
import view.Game;
import view.RegisterView;

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
                UserPackage user = new UserPackage(username, password, Game.getInstance().getHighScore(), "LOGIN");
                EchoClient.sendObj(user);
                if (EchoClient.getResponse().equals("incorrect username")) {
                    WarningDialog.userNotFound(username);
                }
                if (EchoClient.getResponse().equals("incorrect password")) {
                    WarningDialog.incorrectPassword();
                }
                if (EchoClient.getResponse().equals("correct")) {
                    ResultSet rs = EchoClient.getLeaderBoard();
                    openLeaderBoardWindow(rs);
                }
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

            if (!password.equals("") && !confirmPassword.equals("") && !username.equals("")) {
                if (password.equals(confirmPassword)) {
                    EchoClient.sendObj(new UserPackage(username, password, Game.getInstance().getHighScore(), "REGISTER"));
                    if (EchoClient.getResponse().equals("already exist")) {
                        WarningDialog.usernameInUse(username);
                    } else {
                        ResultSet rs = EchoClient.getLeaderBoard();
                        openLeaderBoardWindow(rs);
                    }
                } else {
                    WarningDialog.mismatchedPasswords();
                }
            } else {
                WarningDialog.emptyField();
            }
        });
    }


    private void openLeaderBoardWindow(ResultSet rs) {
        registerView.getScene().getWindow().hide();
        LeaderBoardController.getInstance().showLBWindow();
        Game.getInstance().getShowLeaderboard().setOnAction(actionEvent -> LeaderBoardController.getInstance().showLBWindow());
    }
}
