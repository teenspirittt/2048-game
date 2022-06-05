package TwentyFortyEight.game;

import TwentyFortyEight.Server.DataBaseHandler;
import TwentyFortyEight.view.Game;
import TwentyFortyEight.view.RegisterView;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.KeyCode;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Optional;

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
                openEmptyFieldDialog();
            }
        });
    }

    private void signUpButtonLogic() {
        DataBaseHandler dataBaseHandler = new DataBaseHandler();
        registerView.getSignUp().setOnAction(actionEvent -> {
            String username = registerView.getRegUsernameField().getText();
            String password = registerView.getRegPasswordField().getText();
            String confirmPassword = registerView.getRegConfirmPasswordField().getText();
            boolean isUserInDb = checkUserInDb(username, password);
            if (!isUserInDb) {
                openHiredUsernameDialog(username);
            } else {
                if (Objects.equals(password, confirmPassword)) {
                    if (!password.equals(""))
                        dataBaseHandler.signUpUser(username, password, Game.getInstance().getScore());
                    else
                        openEmptyFieldDialog();
                } else {
                    openMismatchedPasswords();
                }
            }
        });
    }

    private void loginUser(String username, String password) {
        boolean isUserInDb = checkUserInDb(username, password);
        if (isUserInDb) {
            openHiredUsernameDialog(username);
        } else {
            registerView.getScene().getWindow().hide();
            LeaderBoardController.getInstance().showLBWindow();
        }
    }

    private boolean checkUserInDb(String username, String password) {
        DataBaseHandler dataBaseHandler = new DataBaseHandler();
        ResultSet res = dataBaseHandler.getUser(username, password);
        int counter = 0;
        while (true) {
            try {
                if (!res.next()) break;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            counter++;
        }
        return counter < 1;
    }


    private void openEmptyFieldDialog() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning!");
        alert.setHeaderText("Empty text fields!");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
        }
    }

    private void openHiredUsernameDialog(String name) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning!");
        alert.setHeaderText("User with nickname " + name + "already registered!");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {

        }
    }

    private void openMismatchedPasswords(){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning!");
        alert.setHeaderText("Mismatched passwords!");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {

        }
    }

    private void openIncorrectPasswordDialog() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning!");
        alert.setHeaderText("Incorrect password!");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {

        }
    }
}
