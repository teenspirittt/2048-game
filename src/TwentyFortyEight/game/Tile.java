package TwentyFortyEight.game;


import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

import javafx.scene.text.Text;
import javafx.util.Duration;


public class Tile extends StackPane {

    private int value;
    private final Rectangle r;
    private final Text text;
    private boolean merged;
    private int x;
    private int y;

    public Tile(int value) {
        merged = false;
        this.value = value;
        r = new Rectangle();
        text = new Text();
        text.setFont(Font.font("YU Gothic", 43));
        text.setFill(Color.rgb(60, 60, 60));

        r.setWidth(120);
        r.setHeight(120);
        r.setArcWidth(20);
        r.setArcHeight(20);

        getChildren().addAll(r, text);
    }

    public void setColor() {
        switch (value) {
            case 0 -> r.setFill(Color.TRANSPARENT);
            case 2 -> r.setFill(Color.web("#EEE4DA"));
            case 4 -> r.setFill(Color.web("#EDE0C8"));
            case 8 -> r.setFill(Color.web("#F2B179"));
            case 16 -> r.setFill(Color.web("#F59563"));
            case 32 -> r.setFill(Color.web("#F67C5F"));
            case 64 -> r.setFill(Color.web("#F65E3B"));
            case 128 -> r.setFill(Color.web("#EDCF72"));
            case 256 -> r.setFill(Color.web("#EDCC61"));
            case 512 -> r.setFill(Color.web("#EDC850"));
            case 1024 -> r.setFill(Color.web("#EDC53F"));
            case 2048 -> r.setFill(Color.web("#EDC22E"));
            case 4096 -> r.setFill(Color.web("#3E3933"));
        }
    }

    public void update() {
        text.setText("" + getValue());
        setColor();
    }


    public int getValue() {
        return this.value;
    }


    public void setValue(int value) {
        this.value = value;
    }

    public void setMerged(boolean merged) {
        this.merged = merged;
    }


    public boolean isMerged() {
        return merged;
    }


    public TranslateTransition transition() {
        TranslateTransition tt = new TranslateTransition(Duration.millis(100), this);
        tt.setToY(x);
        tt.setToX(y);
        tt.setCycleCount(1);
        tt.setAutoReverse(true);
        return tt;
    }


    public void popTile() {
        ScaleTransition st = new ScaleTransition(Duration.millis(100), this);
        st.setByX(0.1f);
        st.setByY(0.1f);
        st.setAutoReverse(true);
        st.setCycleCount(2);
        st.play();
        setMerged(false);
    }


    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x * 120 + x * 10;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y * 120 + y * 10;
    }

}
