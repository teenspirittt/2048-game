package TwentyFortyEight.view;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class MenuView {
    private final int sceneWidth = 600;
    private final int sceneHeight = 800;
    private final Group root = new Group();
    private final Stage stage = new Stage();
    private final Scene scene = new Scene(root, sceneWidth, sceneHeight, Color.web("#BBADA0"));
}
