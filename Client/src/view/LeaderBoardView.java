package view;

import Server.UserPackage;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class LeaderBoardView {
    private static LeaderBoardView instance;

    public static synchronized LeaderBoardView getInstance() {
        if (instance == null) {
            instance = new LeaderBoardView();
        }
        return instance;
    }

    private final int sceneWidth = 500;
    private final int sceneHeight = 400;
    private final Group root = new Group();
    private final Stage stage = new Stage();
    private final Scene scene = new Scene(root, sceneWidth, sceneHeight, Color.web("#BBADA0"));
    private TableView<UserPackage> table = new TableView<>();
    private TableColumn<UserPackage, String> login = new TableColumn<>();
    private TableColumn<UserPackage, Integer> highScore = new TableColumn<>();

    public void init() {
        stage.setScene(scene);
        stage.initModality(Modality.NONE);
        stage.setTitle("leaderboard window");
        stage.setResizable(false);
    }

    public void tableInit() {
        table.setLayoutX(0);
        table.setLayoutX(0);
        login.setCellValueFactory(new PropertyValueFactory<>("username"));
        table.setPrefWidth(500);
        table.setPrefHeight(400);

        highScore.setCellValueFactory(new PropertyValueFactory<>("highScore"));
        table.getColumns().add(login);
        table.getColumns().add(highScore);
        LeaderBoardView.getInstance().getRoot().getChildren().addAll(table);
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

    public TableView<UserPackage> getTable() {
        return table;
    }
}



