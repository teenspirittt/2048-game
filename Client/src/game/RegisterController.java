package game;


import Server.DataBaseHandler;
import Server.UserPackage;
import javafx.scene.input.KeyCode;
import view.Game;
import view.LeaderBoardView;
import view.RegisterView;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Vector;

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
            System.out.println("LOG BUT");
            String username = registerView.getLogUsernameField().getText().trim();
            String password = registerView.getLogPasswordField().getText();
            if (!username.equals("") && !password.equals("")) {
                UserPackage user = new UserPackage(username, password, Game.getInstance().getHighScore(), "LOGIN");
                EchoClient.sendObj(user);
                String response = EchoClient.getResponse();
                if (response.equals("incorrect username")) {
                    WarningDialog.userNotFound(username);
                }
                if (response.equals("incorrect password")) {
                    WarningDialog.incorrectPassword();
                }
                if (response.equals("correct")) {
                    Vector<UserPackage> userPackages = EchoClient.getLeaderBoard();
                    openLeaderBoardWindow(userPackages);
                }
            } else {
                WarningDialog.emptyField();
            }
        });
    }

    private void signUpButtonLogic() {
        registerView.getSignUp().setOnAction(actionEvent -> {
            System.out.println("REG BUT");
            String username = registerView.getRegUsernameField().getText();
            String password = registerView.getRegPasswordField().getText();
            String confirmPassword = registerView.getRegConfirmPasswordField().getText();

            if (!password.equals("") && !confirmPassword.equals("") && !username.equals("")) {
                if (password.equals(confirmPassword)) {
                    EchoClient.sendObj(new UserPackage(username, password, Game.getInstance().getHighScore(), "REGISTER"));
                    if (EchoClient.getResponse().equals("already exist")) {
                        WarningDialog.usernameInUse(username);
                    } else {
                        Vector<UserPackage> userPackages = EchoClient.getLeaderBoard();
                        openLeaderBoardWindow(userPackages);
                    }
                } else {
                    WarningDialog.mismatchedPasswords();
                }
            } else {
                WarningDialog.emptyField();
            }
        });
    }


    private void openLeaderBoardWindow(Vector<UserPackage> userPackages) {

        registerView.getScene().getWindow().hide();
        LeaderBoardController.getInstance().showLBWindow();
        LeaderBoardView.getInstance().tableInit();


        Game.getInstance().getShowLeaderboard().setOnAction(actionEvent -> LeaderBoardController.getInstance().showLBWindow());
        LeaderBoardController.getInstance().drawTable(userPackages);
    }
}
