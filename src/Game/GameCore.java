package Game;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.util.Duration;

/**
 * Created by Ali Jamadi on 6/23/17.
 * 9512762630
 */
class GameCore {

    private static final int TIME = 120;
    private static boolean a = false, d = false, left = false, right = false;
    private boolean music, soundEffect, singlePlayer;
    private Scene scene;
    private Group root;
    private GameScene scene1;
    private GameScene scene2;
    private Timeline movementHandler = new Timeline(new KeyFrame(Duration.millis(15), event -> {
        if (left)
            scene1.moveBasket(true);
        else if (right)
            scene1.moveBasket(false);
        if (!singlePlayer) {
            if (a)
                scene2.moveBasket(true);
            else if (d)
                scene2.moveBasket(false);
        }
    }));

    GameCore(boolean music, boolean soundEffect, boolean singlePlayer, Scene scene, Group root) {
        this.music = music;
        this.soundEffect = soundEffect;
        this.singlePlayer = singlePlayer;
        this.scene = scene;
        this.root = root;


        scene.setOnKeyPressed(GameCore::keyBoardHandler);
        scene.setOnKeyReleased(GameCore::keyBoardHandler);
        if (singlePlayer) {
            scene1 = new GameScene(scene.getWidth(), scene.getHeight(), root, 0.0, TIME);
        } else {
            scene1 = new GameScene(scene.getWidth() / 2 - 5, scene.getHeight(), root, 0.0, TIME);
            scene2 = new GameScene(scene.getWidth() / 2 - 5, scene.getHeight(), root, scene.getWidth() / 2 + 5, TIME);
            Canvas line = new Canvas(10, scene.getHeight());
            line.getGraphicsContext2D().setFill(Color.LIGHTGRAY);
            line.getGraphicsContext2D().fillRect(0, 0, 10, line.getHeight());
            line.setLayoutX(scene.getWidth() / 2 - 5);
            root.getChildren().add(line);
        }

        movementHandler.setCycleCount(Timeline.INDEFINITE);
        movementHandler.play();


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
