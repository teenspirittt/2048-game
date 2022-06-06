package view;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class LeaderBoardView {
    private static LeaderBoardView instance;

    public static synchronized LeaderBoardView getInstance() {
        if(instance == null) {
            instance = new LeaderBoardView();
        }
        return instance;
    }

    private final int sceneWidth = 500;
    private final int sceneHeight = 400;
    private final Group root = new Group();
    private final Stage stage = new Stage();
    private final Scene scene = new Scene(root, sceneWidth, sceneHeight, Color.web("#BBADA0"));


    public void init() {
        stage.setScene(scene);
        stage.initModality(Modality.NONE);
        stage.setTitle("leaderboard window");
        stage.setResizable(false);
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
}



