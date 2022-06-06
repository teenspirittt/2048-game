
import game.EchoClient;
import javafx.application.Application;
import javafx.stage.Stage;
import view.Game;

import java.nio.file.attribute.AclEntry;


public class Main extends Application {

    public static void main(String[] args) {
        launch(Main.class);
    }

    EchoClient client = new EchoClient();


    @Override
    public void start(Stage stage) throws Exception {
        Game game = Game.getInstance();
        client.startConnection("localhost",8000);
        game.init();
    }

}
