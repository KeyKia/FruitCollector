package Game;

import Objects.Basket;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.text.Text;
import javafx.util.Duration;

/**
 * Created by Ali Jamadi on 6/23/17.
 * 9512762630
 */
class GameScene {
    private final double UNIT;
    private double width, height;
    private Group root;
    private Canvas basketCanvas;
    private Basket basket;
    private double start;
    private int time;
    private Text timerLbl = new Text();
    private Timeline timer = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
        time--;
        timerLbl.setText(convertTimeToString());

    }));

    GameScene(double width, double height, Group root, double start, int time) {
        this.width = width;
        this.height = height;
        this.root = root;
        this.start = start;
        UNIT = width / 300;
        this.time = time;
        timerLbl.setText(convertTimeToString());
        timerLbl.setScaleX(2);
        timerLbl.setScaleY(2);
        timerLbl.setX(start + width - 60);
        timerLbl.setY(30);
        root.getChildren().add(timerLbl);
        timer.setCycleCount(Timeline.INDEFINITE);
        timer.play();

        //TODO:if(music) play it

        addBasket();

    }

    private void addBasket() {
        basket = new Basket(UNIT * 50, height / 10);
        basketCanvas = basket.getBasketCanvas();
        basketCanvas.setLayoutX(start + width / 2 - basketCanvas.getWidth() / 2);
        basketCanvas.setLayoutY(height + basketCanvas.getHeight() + 10);
        root.getChildren().add(basketCanvas);
        Timeline basketAnimation = new Timeline(new KeyFrame(Duration.millis(15), event -> {
            if (basketCanvas.getLayoutY() > height - basketCanvas.getHeight() - 10) {
                basketCanvas.setLayoutY(basketCanvas.getLayoutY() - 2);
            }
        }));
        basketAnimation.setCycleCount(100);
        basketAnimation.play();
    }

    void moveBasket(boolean left) {
        double nextX;
        if (left)
            nextX = basketCanvas.getLayoutX() - (50 * UNIT) / 66;
        else
            nextX = basketCanvas.getLayoutX() + (50 * UNIT) / 66;
        if (nextX > start && nextX + basketCanvas.getWidth() < start + width)
            basketCanvas.setLayoutX(nextX);
    }

    private String convertTimeToString() {
        int min = time / 60;
        int sec = time % 60;
        return String.format("%02d:%02d", min, sec);
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    private void gameOver() {
        if (time < 0) {
            timer.stop();
        }
    }
}
