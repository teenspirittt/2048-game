package TwentyFortyEight;

import javafx.application.Application;
import javafx.stage.Stage;
import TwentyFortyEight.view.Game;


public class Main extends Application {

    public static void main(String[] args) {
        launch(Main.class);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Game game = Game.getInstance();
        game.init();
    }

}
