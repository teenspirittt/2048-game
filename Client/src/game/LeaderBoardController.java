package game;


import Server.UserPackage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.input.KeyCode;
import view.LeaderBoardView;

import java.util.Vector;

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

    public void drawTable(Vector<UserPackage> userPackages) {

        LeaderBoardView.getInstance().getTable().setItems(vectorToObservable(userPackages));

    }

    private ObservableList<UserPackage> vectorToObservable(Vector<UserPackage> userPackages) {
        ObservableList<UserPackage> userPackageObservableList = FXCollections.observableArrayList();
        userPackageObservableList.addAll(userPackages);

        return userPackageObservableList;
    }


    private void hideRegWin() {
        rl.getStage().hide();
    }
}
