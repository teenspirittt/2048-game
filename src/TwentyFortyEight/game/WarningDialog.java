package TwentyFortyEight.game;

import javafx.scene.control.Alert;

public class WarningDialog {

    public static void emptyField() {
        Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.WARNING);
        alert.setTitle("Warning!");
        alert.setHeaderText("Empty text fields!");
        alert.show();
    }

    public static void usernameInUse(String name) {
        Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.WARNING);
        alert.setTitle("Warning!");
        alert.setHeaderText("Username " + name + " already in use!");
        alert.setContentText("Please choose another one.");
        alert.show();
    }

    public static void userNotFound(String name) {
        Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.WARNING);
        alert.setTitle("Warning!");
        alert.setHeaderText("User " + name + " not found");
        alert.show();
    }


    public static void mismatchedPasswords() {
        Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.WARNING);
        alert.setTitle("Warning!");
        alert.setHeaderText("Mismatched passwords!");
        alert.show();
    }

    public static void incorrectPassword() {
        Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.WARNING);
        alert.setTitle("Warning!");
        alert.setHeaderText("Incorrect password!");
        alert.show();
    }
}
