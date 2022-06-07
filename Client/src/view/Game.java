package view;

import game.*;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.Optional;
import java.util.Vector;


public class Game {
    private static Game instance;

    public static synchronized Game getInstance() {
        if (instance == null) {
            instance = new Game();
        }
        return instance;
    }


    private boolean canMove;
    private final Stage stage = new Stage();
    private final Group root = new Group();
    private final Scene scene = new Scene(root, 600, 800);
    private GameGrid grid = new GameGrid();
    private final StackPane tileView = new StackPane();
    private final GridPane gridPane = new GridPane();
    private final Pane gameGrid = new Pane();

    private Text scoreIntText;
    private final Text scoreText = new Text("score:");
    private int currentScore = 0;
    private int highScore = 0;


    private final MenuBar menuBar = new MenuBar();
    private final Menu fileMenu = new Menu("File");
    private final Menu menuMenu = new Menu("Menu");
    private final Menu viewMenu = new Menu("View");
    private final Menu helpMenu = new Menu("Help");

    private final MenuItem saveMenuItem = new MenuItem("Save as");
    private final MenuItem loadMenuItem = new MenuItem("Load");
    private final MenuItem exitMenuItem = new MenuItem("Exit");
    private final MenuItem newGameMenuItem = new MenuItem("New Game");
    private final MenuItem showLeaderboard = new MenuItem("Show Leaderboard");
    public final MenuItem darkModeMenuItem = new MenuItem("Dark Mode");
    public final MenuItem lightModeMenuItem = new MenuItem("Light Mode");

    RegisterController registerController;


    public Game() {
        canMove = true;

        gridPane.setHgap(10);
        gridPane.setVgap(10);

        gameGrid.setMaxWidth(520);
        gridPane.setMaxWidth(520);
        tileView.getChildren().addAll(gridPane, gameGrid);
    }

    public void init() {
        registerController = RegisterController.getInstance();
        scoreIntText = new Text(Integer.toString(currentScore));
        scoreIntText.setFont(Font.font("YU Gothic", 35));
        scoreText.setFont(Font.font("YU Gothic", 40));
        scoreText.setFill(Color.rgb(60, 60, 60));
        scoreIntText.setFill(Color.rgb(60, 60, 60));
        RegisterView.getInstance().init();
        LeaderBoardView.getInstance().init();
        scoreText.setX(50);
        scoreText.setY(230);
        scoreIntText.setX(180);
        scoreIntText.setY(230);
        tileView.setLayoutX(50);
        tileView.setLayoutY(250);
        exitLogic();
        gridInit();
        redrawGrid();
        menuInit();
        stageInit();
        newGameMenuLogic();
        showLeaderboardLogic();
        root.getChildren().addAll(tileView, scoreIntText, scoreText);
    }


    private void stageInit() {
        Image icon = new Image("/048.png");
        stage.getIcons().add(icon);
        stage.setTitle("2048 by teenspirit");
        stage.setResizable(false);
        stage.setOnCloseRequest(windowEvent -> {
            Platform.exit();
            System.exit(0);
        });
        scene.setFill(Color.rgb(187, 173, 160));
        scene.setOnKeyPressed((event) -> {
            if (canMove) {
                switch (event.getCode()) {
                    case UP -> handleMovements(Direction.UP);
                    case DOWN -> handleMovements(Direction.DOWN);
                    case RIGHT -> handleMovements(Direction.RIGHT);
                    case LEFT -> handleMovements(Direction.LEFT);
                }
            }
        });
        stage.setScene(scene);
        stage.show();
    }

    public void menuInit() {
        menuBar.getMenus().addAll(fileMenu, menuMenu, viewMenu, helpMenu);
        menuBar.setPrefWidth(600);

        fileMenu.getItems().addAll(saveMenuItem, loadMenuItem, exitMenuItem);
        menuMenu.getItems().addAll(newGameMenuItem, showLeaderboard);
        viewMenu.getItems().addAll(darkModeMenuItem, lightModeMenuItem);
        helpMenu.getItems().addAll();

        root.getChildren().add(menuBar);
    }

    public void handleMovements(Direction d) {
        if (grid.isGameOver() || grid.isGameOver() && grid.getHighestTile() < 4096) {
            currentScore += grid.move(d);
            doTransitions();
        } else if (grid.getHighestTile() == 4096) {
            openWinDialog();
        } else {
            if (currentScore > highScore)
                highScore = currentScore;
            openLoseDialog();
        }
    }

    private void newGameMenuLogic() {
        newGameMenuItem.setOnAction(actionEvent -> openNewGameDialog());
    }

    public void gridInit() {
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {

                Rectangle r = new Rectangle();
                r.setFill(Color.rgb(204, 192, 179));
                r.setWidth(120);
                r.setHeight(120);
                r.setArcWidth(20);
                r.setArcHeight(20);
                gridPane.add(r, y, x);

            }
        }
        addInitialTiles();
    }

    public void addInitialTiles() {
        for (int i = 0; i < 2; i++) {
            grid.randomNewTile();
        }
    }

    public void doTransitions() {
        canMove = false;
        Tile[][] tiles = grid.getTiles();
        Vector<Tile> transitions = new Vector<>();

        for (int y = 0; y < tiles.length; y++) {
            for (int x = 0; x < tiles.length; x++) {
                if (tiles[y][x] != null) {
                    tiles[y][x].setX(y);
                    tiles[y][x].setY(x);
                    transitions.add(tiles[y][x]);
                }
            }
        }
        final int size = transitions.size();
        for (int i = 0; i < transitions.size(); i++) {
            TranslateTransition tt = transitions.get(i).transition();
            final int x = i;
            final boolean isMerged = transitions.get(i).isMerged();
            tt.play();
            tt.setOnFinished(event -> {
                if (isMerged) {
                    transitions.get(x).update();
                    transitions.get(x).popTile();
                }
                if (x == size - 1) {
                    if (grid.moved) {
                        grid.randomNewTile();
                    }
                    redrawGrid();
                    canMove = true;
                }
            });
        }
    }

    public void redrawGrid() {
        Tile[][] tiles = grid.getTiles();
        gameGrid.getChildren().clear();
        for (int y = 0; y < tiles.length; y++) {
            for (Tile[] tile : tiles) {
                if (tile[y] != null && tile[y].getValue() >= 2) {
                    tile[y].setTranslateY(tile[y].getX());
                    tile[y].setTranslateX(tile[y].getY());
                    gameGrid.getChildren().add(tile[y]);
                }
            }
        }
        scoreIntText.setText("" + currentScore);
    }


    private void openWinDialog() {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("You are WINNER");
        alert.setHeaderText("WIN");
        alert.setContentText("Click  OK to play again \nClick Cancel to exit to desktop");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK)
            restartGame();
    }


    private void openLoseDialog() {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("GAME OVER");
        alert.setHeaderText("LOSE");
        alert.setContentText("Click  OK to play again \nClick Cancel to exit to desktop");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK)
            restartGame();

    }

    private void openNewGameDialog() {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Warning!");
        alert.setHeaderText("Warning! Are you sure yoy want to start a new game? ");
        alert.setContentText("This game will end a new one will start");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK)
            restartGame();
    }

    private void showLeaderboardLogic() {
        showLeaderboard.setOnAction(actionEvent -> {
            RegisterController.getInstance().showRegWindow();
        });

    }

    private void exitLogic() {
        exitMenuItem.setOnAction(actionEvent -> {
            Platform.exit();
            System.exit(0);
        });
    }


    private void restartGame() {
        currentScore = 0;
        grid = new GameGrid();
        gameGrid.getChildren().clear();
        gridInit();
        redrawGrid();
    }

    public int getHighScore() {
        return highScore;
    }

    public int getCurrentScore() {
        return currentScore;
    }

    public MenuItem getShowLeaderboard() {
        return showLeaderboard;
    }
}
