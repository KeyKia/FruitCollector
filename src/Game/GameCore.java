package Game;

import Objects.Fruits.*;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import javafx.util.Pair;

import java.io.File;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;

/**
 * Created by Ali Jamadi on 6/23/17.
 * 9512762630
 */
class GameCore {

    private static final int TIME = 120;
    private static boolean a = false, d = false, left = false, right = false;
    private boolean singlePlayer;
    private ArrayList<GameScene> scenes = new ArrayList<>();
    private ArrayList<ArrayList<Fruits>> fruits = new ArrayList<>();
    private int time = 0;
    private int wormPer3Secs = 2;

    //sound vars
    private MediaPlayer backgroundEffectPlayer = null;

    private Timeline movementHandler = new Timeline(new KeyFrame(Duration.millis(GameScene.RENDER_SPEED), event -> {
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

        // Plan the quantity of fruit drops, in the beginning of each 30 seconds.
        if ( time%30==0 ) {
            fruits.clear();
            for ( int i=0; i<40; i++ )
                fruits.add(new ArrayList<>());
            Random random = new Random();
            for ( int i=0; i<30; i+=3 ) {
                int a[] = new int[10];
                a[0] = random.nextInt(3);
                a[1] = random.nextInt(3);
                a[2] = random.nextInt(3);
                fruits.get(i+a[0]).add(new Orange());
                fruits.get(i+a[1]).add(new Orange());
                fruits.get(i+a[2]).add(new Orange());

                a[0] = random.nextInt(3);
                a[1] = random.nextInt(3);
                a[2] = random.nextInt(3);
                a[3] = random.nextInt(3);
                fruits.get(i+a[0]).add(new Apricot());
                fruits.get(i+a[1]).add(new Apricot());
                fruits.get(i+a[2]).add(new Apricot());
                fruits.get(i+a[3]).add(new Apricot());

                a[0] = random.nextInt(3);
                a[1] = random.nextInt(3);
                fruits.get(i+a[0]).add(new Watermelon());
                fruits.get(i+a[1]).add(new Watermelon());

                for ( int j=0; j<wormPer3Secs; j++ ) {
                    a[j] = random.nextInt(3);
                    int type = random.nextInt(3);
                    if ( type==0 )
                        fruits.get(i+a[j]).add(new WormFreezer());
                    else if ( type==1 )
                        fruits.get(i+a[j]).add(new WormHalfer());
                    else
                        fruits.get(i+a[j]).add(new WormKiller());
                }
            }
            int a = random.nextInt(30);
            int type = random.nextInt(4);
            if ( type==1 )
                fruits.get(a).add(new MagicDoubler());
            else if ( type==2 )
                fruits.get(a).add(new MagicHeartBonus());
            else if ( type==3 )
                fruits.get(a).add(new MagicTimeExtender());
            else
                fruits.get(a).add(new MagicWormFreezer());
        }

        for ( GameScene gs: scenes ) {
            for ( Fruits fruit: fruits.get(time%30) ) {
                if ( (fruit instanceof WormFreezer || fruit instanceof WormHalfer
                || fruit instanceof WormKiller) && gs.wormFreezeTime>0 ) continue;
                gs.addFruits(fruit);
            }
        }

        //check if freeze worm or magicFruit had collision
        for(GameScene gs: scenes){
            if(gs.getFreezeTime() > 0){
                gs.minusFreezeTime();
            }
            if(gs.getHalfTime() > 0){
                //System.out.println(gs.getHalfTime());
                gs.minusHalfTime();
            }
            if(gs.getDoubleTime() > 0)
                gs.minusDoubleTime();
            if(gs.wormFreezeTime>0)
                gs.wormFreezeTime--;
        }
        time++;
    }));

    GameCore(boolean music, boolean soundEffect, boolean singlePlayer, Scene scene, Group root) {

        this.singlePlayer = singlePlayer;

        //FOR DEBUGGING ONLY

        Platform.runLater(() -> {
            //////////////////////////////////
            //add username dialog
            // Create the custom dialog.
            Dialog<Pair<String, String>> dialog = new Dialog<>();
            dialog.setTitle("whats your name(s)?");

            // Set the button types.
            ButtonType loginButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

            //initiate grid panel for names
            GridPane gridPane = new GridPane();
            gridPane.setHgap(10);
            gridPane.setVgap(10);
            gridPane.setPadding(new Insets(20, 150, 10, 10));

            //add username one -> always available
            TextField player1 = new TextField();
            player1.setPromptText("Player One");
            if (singlePlayer) {
                gridPane.add(new Label("Player's Name:"), 0, 0);
            } else {
                gridPane.add(new Label("Player One's Name:"), 0, 0);
            }
            gridPane.add(player1, 1, 0);

            //add username two -> available if multi player
            TextField player2 = new TextField();
            player2.setPromptText("Player Two");
            if (!singlePlayer) {
                gridPane.add(new Label("Player Two's Name:"), 0, 1);
                gridPane.add(player2, 1, 1);
            }

            dialog.getDialogPane().setContent(gridPane);

            // Request focus on the username field by default.
            Platform.runLater(player1::requestFocus);

            // Convert the result to a username-password-pair when the login button is clicked.
            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == loginButtonType) {
                    return new Pair<>(player1.getText(), player2.getText());
                }
                return null;
            });

            Optional<Pair<String, String>> result = dialog.showAndWait();
            //dialog add section finished here
            //////////////////////////////////


            result.ifPresent(names -> {
                GameCore.this.player1 = new PlayerInfo(names.getKey());
                GameCore.this.player2 = new PlayerInfo(names.getValue());

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
                    scenes.add(new GameScene(scene.getWidth(), scene.getHeight(), root, 0.0, TIME, GameCore.this.player1));
                } else {
                    scenes.add(new GameScene(scene.getWidth() / 2 - 5, scene.getHeight(), root, scene.getWidth() / 2 + 5, TIME, GameCore.this.player1));
                    scenes.add(new GameScene(scene.getWidth() / 2 - 5, scene.getHeight(), root, 0.0, TIME, GameCore.this.player2));
                    Canvas line = new Canvas(5, scene.getHeight());
                    line.getGraphicsContext2D().setFill(Color.LIGHTGRAY);
                    line.getGraphicsContext2D().fillRect(0, 0, 5, line.getHeight());
                    line.setLayoutX(scene.getWidth() / 2);
                    root.getChildren().add(line);
                }

                movementHandler.setCycleCount(Timeline.INDEFINITE);
                movementHandler.play();

                gameTimer.setCycleCount(Animation.INDEFINITE);
                gameTimer.play();

            });

        });

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
