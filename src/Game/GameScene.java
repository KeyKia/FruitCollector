package Game;

import Objects.Basket;
import Objects.Fruits.Fruits;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ali Jamadi on 6/23/17.
 * 9512762630
 */
public class GameScene {
    public static final int SPEED_CONVERTER = 60;
    private static final int RENDER_SPEED = 1000 / 60;
    public static double UNIT;
    private double width, height;
    private Group root;
    private Canvas basketCanvas;
    private double start;
    private int time;
    private Text timerLbl = new Text();
    private PlayerInfo player;

    private Text score = new Text();


    private Map<Fruits, Canvas> fruitsCanvasMap = new HashMap<>();


    private Timeline timer = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
        time--;
        timerLbl.setText(convertTimeToString());

    }));

    private Timeline objectsHandler = new Timeline(new KeyFrame(Duration.millis(RENDER_SPEED), event -> {
        try {
            moveFruits();
            collisionDetection();
        } catch (ConcurrentModificationException e) {
            //just to prevent default handling
        }

    }));


    GameScene(double width, double height, Group root, double start, int time, PlayerInfo player) {
        this.width = width;
        this.height = height;
        this.root = root;
        this.start = start;
        this.player = player;


        UNIT = width / 300;
        this.time = time;
        timerLbl.setText(convertTimeToString());
        timerLbl.setScaleX(2);
        timerLbl.setScaleY(2);
        timerLbl.setX(start + width - 60);
        timerLbl.setY(30);

        score.setScaleX(2);
        score.setScaleY(2);
        score.setX(start + width / 2.0 - 25);
        score.setY(30);
        score.setTextAlignment(TextAlignment.CENTER);
        score.setText(String.format("%05d", player.getScore()));
        root.getChildren().addAll(timerLbl, score);
        timer.setCycleCount(Timeline.INDEFINITE);
        timer.play();

        Text playerName = new Text(player.getNickName());
        playerName.setTextAlignment(TextAlignment.CENTER);
        playerName.setX(start + 15);
        playerName.setY(50);
        playerName.setScaleX(1.5);
        playerName.setScaleY(1.5);
        root.getChildren().add(playerName);


        addBasket();

        objectsHandler.setCycleCount(Animation.INDEFINITE);
        objectsHandler.play();


    }

    private void addBasket() {
        Basket basket = new Basket(UNIT * 50, height / 10);
        basketCanvas = basket.getBasketCanvas();
        basketCanvas.setLayoutX(start + width / 2 - basketCanvas.getWidth() / 2);
        basketCanvas.setLayoutY(height + basketCanvas.getHeight() + 10);
        root.getChildren().add(basketCanvas);
        Timeline basketAnimation = new Timeline(new KeyFrame(Duration.millis(RENDER_SPEED), event -> {
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
            nextX = basketCanvas.getLayoutX() - (50 * UNIT) / SPEED_CONVERTER;
        else
            nextX = basketCanvas.getLayoutX() + (50 * UNIT) / SPEED_CONVERTER;
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

    void addFruits(Fruits... fruits) {
        for (Fruits fruit : fruits) {
            Canvas canvas = new Canvas(fruit.getRadius(), fruit.getRadius());
            canvas.setLayoutY(0);
            canvas.setLayoutX(width * fruit.getPosition() / 100 + start);
            canvas.getGraphicsContext2D().drawImage(fruit.getImage(), 0, 0, fruit.getRadius(), fruit.getRadius());
            root.getChildren().add(canvas);
            fruitsCanvasMap.put(fruit, canvas);
        }
    }

    private void gameOver() {
        if (time < 0) {
            timer.stop();
            objectsHandler.stop();
        }
    }

    private void moveFruits() {
        for (Fruits fruit : fruitsCanvasMap.keySet()) {
            fruit.move(fruitsCanvasMap.get(fruit));
            Canvas canvas = fruitsCanvasMap.get(fruit);
            if (canvas.getLayoutX() < start || canvas.getLayoutX() > start + width || canvas.getLayoutY() > height) {
                fruitsCanvasMap.remove(fruit);
                root.getChildren().remove(canvas);
            }
        }
    }

    private void collisionDetection() {
        for (Fruits fruit : fruitsCanvasMap.keySet()) {
            Canvas canvas = fruitsCanvasMap.get(fruit);
            if (canvas.getBoundsInParent().intersects(basketCanvas.getBoundsInParent())) {
                if (canvas.getLayoutX() > basketCanvas.getLayoutX() + 4 && canvas.getLayoutX() + canvas.getWidth() < basketCanvas.getLayoutX() + basketCanvas.getWidth() - 4) {
                    if (canvas.getLayoutY() >= basketCanvas.getLayoutY() - 7) {
                        root.getChildren().remove(canvas);
                        fruitCollected(fruit);
                        fruitsCanvasMap.remove(fruit);
                        //TODO:play soundEffect if possible related to fruit type
                    }
                }
            }
        }
    }

    private void fruitCollected(Fruits fruit) {
        player.addScore(fruit.getScore());
        score.setText(String.format("%05d", player.getScore()));
    }
}
