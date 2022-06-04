package TwentyFortyEight.view;

import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class RegisterView {
    private final int sceneWidth = 500;
    private final int sceneHeight = 400;
    private final Group root = new Group();
    private final Stage stage = new Stage();
    private final Scene scene = new Scene(root, sceneWidth, sceneHeight, Color.web("#BBADA0"));

    private final Text register = new Text("register");
    private final Text login = new Text("login");
    private final TextField regUsernameField = new TextField();
    private final TextField regPasswordField = new TextField();
    private final TextField regConfirmPasswordField = new TextField();
    private final TextField logPasswordField = new TextField();
    private final TextField logUsernameField = new TextField();
    private final Button signUp = new Button();
    private final Button signIn = new Button();

    public RegisterView() {
        textInit();
        buttonInit();
        textFieldInit();
        stage.setScene(scene);
        stage.initModality(Modality.NONE);
        stage.setTitle("login window");
        stage.setResizable(false);
        stage.setOnCloseRequest(windowEvent -> {
            Platform.exit();
            System.exit(0);
        });
        stage.show();
    }





    private void textInit() {
        register.setX(45);
        register.setY(116);
        register.setFont(Font.font("YU Gothic", 24));
        register.setFill(Color.rgb(60,60,60));

        login.setX(306);
        login.setY(116);
        login.setFont(Font.font("YU Gothic", 24));
        login.setFill(Color.rgb(60,60,60));

        root.getChildren().addAll(register,login);
    }

    private void buttonInit() {
        signIn.setLayoutX(355);
        signIn.setLayoutY(208);

        signUp.setLayoutX(94);
        signUp.setLayoutY(251);
        root.getChildren().addAll(signIn,signUp);
    }

    private void textFieldInit() {
        regUsernameField.setLayoutX(45);
        regUsernameField.setLayoutY(130);
        regUsernameField.setFont(Font.font("YU Gothic", 15));
        regUsernameField.setPrefWidth(149);
        regUsernameField.setPrefHeight(25);

        regUsernameField.setPromptText("username");

        regPasswordField.setLayoutX(45);
        regPasswordField.setLayoutY(169);
        regPasswordField.setFont(Font.font("YU Gothic", 15));
        regPasswordField.setPrefWidth(149);
        regPasswordField.setPrefHeight(25);
        regPasswordField.setPromptText("password");

        regConfirmPasswordField.setLayoutX(45);
        regConfirmPasswordField.setLayoutY(208);
        regConfirmPasswordField.setFont(Font.font("YU Gothic", 15));
        regConfirmPasswordField.setPrefWidth(149);
        regConfirmPasswordField.setPrefHeight(25);
        regConfirmPasswordField.setPromptText("confirm password");

        logUsernameField.setLayoutX(306);
        logUsernameField.setLayoutY(130);
        logUsernameField.setFont(Font.font("YU Gothic", 15));
        logUsernameField.setPrefWidth(149);
        logUsernameField.setPrefHeight(25);
        logUsernameField.setPromptText("username");

        logPasswordField.setLayoutX(306);
        logPasswordField.setLayoutY(169);
        logPasswordField.setFont(Font.font("YU Gothic", 15));
        logPasswordField.setPrefWidth(149);
        logPasswordField.setPrefHeight(25);
        logPasswordField.setPromptText("password");

        root.getChildren().addAll(regPasswordField, regConfirmPasswordField, regUsernameField, logPasswordField, logUsernameField);
    }


}
