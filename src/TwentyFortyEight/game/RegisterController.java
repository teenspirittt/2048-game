package TwentyFortyEight.game;

import TwentyFortyEight.view.RegisterView;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

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


    public void init() {
        signInButtonLogic();
    }

    private void signInButtonLogic() {
        registerView.getSignIn().setOnAction(actionEvent -> {
            String username = registerView.getLogUsernameField().getText().trim();
            String password = registerView.getLogPasswordField().getText();
            if (!username.equals("") && !password.equals(""))
                loginUser(username, password);
            else
                openEmptyFieldDialog();

        });
    }

    private void loginUser(String username, String password) {

    }

    private void openEmptyFieldDialog() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning!");
        alert.setHeaderText("Empty text fields!");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){

        }


    }


}
