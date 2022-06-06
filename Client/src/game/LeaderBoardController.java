package game;


import javafx.scene.input.KeyCode;
import view.LeaderBoardView;

public class LeaderBoardController {

    private static LeaderBoardController instance;

    public static synchronized LeaderBoardController getInstance() {
        if (instance == null) {
            instance = new LeaderBoardController();
        }
        return instance;
    }

    LeaderBoardView rl = LeaderBoardView.getInstance();


    public void showLBWindow() {
        rl.getStage().show();
        rl.getScene().setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ESCAPE)
                hideRegWin();
        });
    }

    private void hideRegWin() {
        rl.getStage().hide();
    }
}
