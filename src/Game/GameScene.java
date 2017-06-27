package Game;

import Objects.Basket;
import Objects.Fruits.*;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.effect.BlendMode;
import javafx.scene.image.Image;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ali Jamadi on 6/23/17.
 * 9512762630
 */
public class GameScene {
    public static final double SPEED_CONVERTER = 60;
    static final int RENDER_SPEED = 1000 / 60;
    public static double UNIT;
    private double width, height;
    private Group root;
    private Canvas basketCanvas;
    private double start;
    private int time;
    private Text timerLbl = new Text();
    private PlayerInfo player;
    private Basket basket;

    private Text score = new Text();

    private ArrayList<Canvas> heartsCanvas = new ArrayList<>();

    private Map<Fruits, Canvas> fruitsCanvasMap = new HashMap<>();

    //worm collisions
    private int freezeTime = 0;
    private int halfTime = 0;
    private int doubleTime = 0;
    int wormFreezeTime = 0;


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

        addHearts();

        objectsHandler.setCycleCount(Animation.INDEFINITE);
        objectsHandler.play();


    }

    private void addBasket() {
        basket = new Basket(UNIT * 50, height / 10);
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
        if(this.freezeTime>0) return;
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

    public void addTime(int t) {
        this.time += t;
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
            fruit.move(fruitsCanvasMap.get(fruit), width, start);
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
                if (canvas.getLayoutX() + canvas.getWidth() / 2 > basketCanvas.getLayoutX() + basketCanvas.getWidth() / 10 && canvas.getLayoutX() + canvas.getWidth() < basketCanvas.getLayoutX() + 9.00 / 10 * basketCanvas.getWidth()) {
                    if (canvas.getLayoutY() + canvas.getHeight() >= basketCanvas.getLayoutY() + basketCanvas.getHeight() / 2) {
                        Timeline removeAnimation = new Timeline(new KeyFrame(Duration.millis(RENDER_SPEED), event -> {
                            if (canvas.getScaleX() > 0) {
                                canvas.setScaleX(canvas.getScaleX() - 0.1);
                                canvas.setScaleY(canvas.getScaleY() - 0.1);
                            }
                        }));
                        removeAnimation.setCycleCount(10);
                        removeAnimation.play();
                        removeAnimation.setOnFinished(event -> {
                            root.getChildren().remove(canvas);
                            fruitCollected(fruit);
                            fruitsCanvasMap.remove(fruit);
                        });

                        //TODO:play soundEffect if possible related to fruit type
                    }
                }
            }
        }
    }

    private void fruitCollected(Fruits fruit) {
        //check if worm is collected
        if(fruit instanceof WormFreezer)
            this.freezeTime=3;
        if(fruit instanceof WormKiller)
            this.loseHeart();
        if(fruit instanceof WormHalfer) {
            this.halfTime = 10;
            basket.halfTheBasket();
        }

        //check if any magical fruit is collected
        if ( fruit instanceof MagicDoubler ) {
            this.doubleTime = 10;
            this.halfTime = 0;
            basket.doubleTheBasket();
        }
        if ( fruit instanceof MagicWormFreezer )
            wormFreezeTime = 10;
        if ( fruit instanceof MagicTimeExtender ) {
            addTime(10);
            timerLbl.setText(convertTimeToString());
        }

        player.addScore(fruit.getScore());
        score.setText(String.format("%05d", player.getScore()));

    }

    private void addHearts() {
        int hearts = player.getHearts();
        Image image = new Image("file:Resources/images/heart.png");

        for (int i = hearts; i > 0; i--) {
            Canvas canvas = new Canvas(20 * UNIT, 20 * UNIT);
            canvas.setLayoutX(start + width - (i * (canvas.getWidth() + 5)));
            canvas.setLayoutY(50);
            canvas.getGraphicsContext2D().drawImage(image, 0, 0, 20 * UNIT, 20 * UNIT);
            root.getChildren().add(canvas);
            heartsCanvas.add(canvas);
        }
    }

    private void loseHeart() {
        if (heartsCanvas.size() > 0) {

            Canvas canvas = heartsCanvas.get(0);
            canvas.getGraphicsContext2D().setGlobalBlendMode(BlendMode.SCREEN);

            Image image = new Image("file:Resources/images/heart.png");

            Timeline removeHeartAnimation = new Timeline(new KeyFrame(Duration.millis(RENDER_SPEED), event -> {
                if (canvas.getScaleX() < 2) {
                    canvas.setScaleX(canvas.getScaleX() + 0.1);
                    canvas.setScaleY(canvas.getScaleY() + 0.1);
                    canvas.getGraphicsContext2D().drawImage(image, 0, 0, canvas.getWidth(), canvas.getHeight());
                }
            }));

            removeHeartAnimation.setCycleCount(10);
            removeHeartAnimation.play();
            removeHeartAnimation.setOnFinished(event -> {
                root.getChildren().remove(canvas);
                heartsCanvas.remove(canvas);
            });
        } else {
            //TODO: Game Over
        }

    }

    public int getDoubleTime() { return this.doubleTime; }

    public void minusDoubleTime() {
        this.doubleTime--;
        if ( this.doubleTime < 1 )
            basket.renormalTheBasket();
    }

    public int getFreezeTime(){return this.freezeTime;}

    public void minusFreezeTime(){this.freezeTime--;}

    public int getHalfTime(){return this.halfTime;}

    public void minusHalfTime(){
        this.halfTime--;
        if(this.halfTime < 1) {
            basket.renormalTheBasket();
        }
    }
}
