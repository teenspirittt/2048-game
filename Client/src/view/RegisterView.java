package view;

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
    private static RegisterView instance;

    public static synchronized RegisterView getInstance() {
        if(instance == null) {
            instance = new RegisterView();
        }
        return instance;
    }

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
    private final Button signUp = new Button("sign up");
    private final Button signIn = new Button("sign in");

    public void init() {
        textInit();
        buttonInit();
        textFieldInit();
        stage.setScene(scene);
        stage.initModality(Modality.NONE);
        stage.setTitle("login window");
        stage.setResizable(false);
    }


    private void textInit() {
        register.setX(45);
        register.setY(116);
        register.setFont(Font.font("YU Gothic", 24));
        register.setFill(Color.rgb(60, 60, 60));

        login.setX(306);
        login.setY(116);
        login.setFont(Font.font("YU Gothic", 24));
        login.setFill(Color.rgb(60, 60, 60));

        root.getChildren().addAll(register, login);
    }

    private void buttonInit() {
        signIn.setLayoutX(345);
        signIn.setLayoutY(208);
        signIn.setFont(Font.font("YU Gothic", 15));
        signIn.setTextFill(Color.rgb(60,60,60));
        signIn.setStyle("-fx-background-color: transparent");

        signUp.setLayoutX(84);
        signUp.setLayoutY(251);
        signUp.setFont(Font.font("YU Gothic", 15));
        signUp.setTextFill(Color.rgb(60,60,60));
        signUp.setStyle("-fx-background-color: transparent");
        root.getChildren().addAll(signIn, signUp);
    }

    private void textFieldInit() {
        regUsernameField.setLayoutX(45);
        regUsernameField.setLayoutY(130);
        regUsernameField.setFont(Font.font("YU Gothic", 15));
        regUsernameField.setPrefWidth(149);
        regUsernameField.setPrefHeight(25);
        regUsernameField.setStyle("-fx-background-color: #fffff7");
        regUsernameField.setPromptText("username");

        regPasswordField.setLayoutX(45);
        regPasswordField.setLayoutY(169);
        regPasswordField.setFont(Font.font("YU Gothic", 15));
        regPasswordField.setPrefWidth(149);
        regPasswordField.setPrefHeight(25);
        regPasswordField.setStyle("-fx-background-color: #fffff7");
        regPasswordField.setPromptText("password");

        regConfirmPasswordField.setLayoutX(45);
        regConfirmPasswordField.setLayoutY(208);
        regConfirmPasswordField.setFont(Font.font("YU Gothic", 15));
        regConfirmPasswordField.setPrefWidth(149);
        regConfirmPasswordField.setPrefHeight(25);
        regConfirmPasswordField.setStyle("-fx-background-color: #fffff7");
        regConfirmPasswordField.setPromptText("confirm password");

        logUsernameField.setLayoutX(306);
        logUsernameField.setLayoutY(130);
        logUsernameField.setFont(Font.font("YU Gothic", 15));
        logUsernameField.setPrefWidth(149);
        logUsernameField.setPrefHeight(25);
        logUsernameField.setStyle("-fx-background-color: #fffff7");
        logUsernameField.setPromptText("username");

        logPasswordField.setLayoutX(306);
        logPasswordField.setLayoutY(169);
        logPasswordField.setFont(Font.font("YU Gothic", 15));
        logPasswordField.setPrefWidth(149);
        logPasswordField.setPrefHeight(25);
        logPasswordField.setStyle("-fx-background-color: #fffff7");
        logPasswordField.setPromptText("password");

        root.getChildren().addAll(regPasswordField, regConfirmPasswordField, regUsernameField, logPasswordField, logUsernameField);
    }


    public int getSceneWidth() {
        return sceneWidth;
    }

    public int getSceneHeight() {
        return sceneHeight;
    }

    public Group getRoot() {
        return root;
    }

    public Stage getStage() {
        return stage;
    }

    public Scene getScene() {
        return scene;
    }

    public Text getRegister() {
        return register;
    }

    public Text getLogin() {
        return login;
    }

    public TextField getRegUsernameField() {
        return regUsernameField;
    }

    public TextField getRegPasswordField() {
        return regPasswordField;
    }

    public TextField getRegConfirmPasswordField() {
        return regConfirmPasswordField;
    }

    public TextField getLogPasswordField() {
        return logPasswordField;
    }

    public TextField getLogUsernameField() {
        return logUsernameField;
    }

    public Button getSignUp() {
        return signUp;
    }

    public Button getSignIn() {
        return signIn;
    }
}
