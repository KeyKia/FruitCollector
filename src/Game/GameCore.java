package Game;

import Objects.Fruits.Orange;
import Objects.Fruits.Watermelon;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Ali Jamadi on 6/23/17.
 * 9512762630
 */
class GameCore {

    private static final int TIME = 120;
    private static boolean a = false, d = false, left = false, right = false;
    private boolean singlePlayer;
    private ArrayList<GameScene> scenes = new ArrayList<>();
    private int time = 0;

    //sound vars
    private MediaPlayer backgroundEffectPlayer = null;

    private Timeline movementHandler = new Timeline(new KeyFrame(Duration.millis(15), event -> {
        if (left)
            scenes.get(0).moveBasket(true);
        else if (right)
            scenes.get(0).moveBasket(false);
        if (!singlePlayer) {
            if (a)
                scenes.get(1).moveBasket(true);
            else if (d)
                scenes.get(1).moveBasket(false);
        }
    }));

    private PlayerInfo player1, player2;

    private Timeline gameTimer = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
        time++;
        if (time % 3 == 0) {
            Orange t1 = new Orange();
            Orange t2 = new Orange();
            Orange t3 = new Orange();
            for (GameScene p : scenes) {
                p.addFruits(t1, t2, t3);
            }
        }
        if ((time - 2) % 3 == 0) {
            Watermelon t1 = new Watermelon();
            Watermelon t2 = new Watermelon();
            for (GameScene p : scenes)
                p.addFruits(t1, t2);
        }
    }));

    GameCore(boolean music, boolean soundEffect, boolean singlePlayer, Scene scene, Group root) {

        this.singlePlayer = singlePlayer;

        //TODO:dialog to get names of player and create player 1 and 2(using method)
        //FOR DEBUGGING ONLY
        player1 = new PlayerInfo("Jay");
        player2 = new PlayerInfo("Kia");
        ////////////////////////////////////////////


        if (music) {
            //loading sound effect file/files
            String musicFile = "Resources/sounds/" + "BirdInRain" + ".mp3";

            Media sound = new Media(new File(musicFile).toURI().toString());
            backgroundEffectPlayer = new MediaPlayer(sound);
            backgroundEffectPlayer.setOnEndOfMedia(() -> backgroundEffectPlayer.seek(Duration.ZERO));
            backgroundEffectPlayer.play();

        }

        scene.setOnKeyPressed(GameCore::keyBoardHandler);
        scene.setOnKeyReleased(GameCore::keyBoardHandler);


        if (singlePlayer) {
            scenes.add(new GameScene(scene.getWidth(), scene.getHeight(), root, 0.0, TIME, player1));
        } else {
            scenes.add(new GameScene(scene.getWidth() / 2 - 5, scene.getHeight(), root, scene.getWidth() / 2 + 5, TIME, player1));
            scenes.add(new GameScene(scene.getWidth() / 2 - 5, scene.getHeight(), root, 0.0, TIME, player2));
            Canvas line = new Canvas(10, scene.getHeight());
            line.getGraphicsContext2D().setFill(Color.LIGHTGRAY);
            line.getGraphicsContext2D().fillRect(0, 0, 10, line.getHeight());
            line.setLayoutX(scene.getWidth() / 2 - 5);
            root.getChildren().add(line);
        }

        movementHandler.setCycleCount(Timeline.INDEFINITE);
        movementHandler.play();

        gameTimer.setCycleCount(Animation.INDEFINITE);
        gameTimer.play();



    }

    private static void keyBoardHandler(KeyEvent event) {
        switch (event.getCode()) {
            case A:
                a = (event.getEventType() == KeyEvent.KEY_PRESSED);
                break;
            case D:
                d = (event.getEventType() == KeyEvent.KEY_PRESSED);
                break;
            case LEFT:
                left = (event.getEventType() == KeyEvent.KEY_PRESSED);
                break;
            case RIGHT:
                right = (event.getEventType() == KeyEvent.KEY_PRESSED);
                break;
            default:
                break;
        }
    }


}
