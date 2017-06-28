package Game;


import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;

/**
 * Created by Ali Jamadi on 6/23/17.
 * 9512762630
 */
class GameInit {
    private final Image back = new Image("file:Resources/images/JungleBack.png");
    private Group root = new Group();
    private Scene scene = new Scene(root);
    private boolean soundEffectSts = true, musicSts = true;
    private Stage stage;
    private Canvas background = new Canvas(500, 700);
    private Button singlePlayerBtn = new Button("Single Player");
    private Button multiPlayerBtn = new Button("Multi Player");
    private CheckBox music = new CheckBox("Music");
    private CheckBox soundEffect = new CheckBox("Sound Effect");
    private Text title = new Text(background.getWidth() / 2 - 30, background.getHeight() / 2 - 100, "Fruit Collector");
    private Button showHighScores;
    //sound vars
    private MediaPlayer backgroundEffectPlayer  = null;

    GameInit(Stage stage) {
        this.stage = stage;

        background.getGraphicsContext2D().drawImage(back,0,0,500,700);
            //loading sound effect file/files
            String musicFile = "Resources/sounds/" + "RainAndThunderStrike" + ".mp3";

            Media sound = new Media(new File(musicFile).toURI().toString());
            backgroundEffectPlayer = new MediaPlayer(sound);
            backgroundEffectPlayer.setOnEndOfMedia(() -> backgroundEffectPlayer.seek(Duration.ZERO));
            backgroundEffectPlayer.play();


        title.setTextAlignment(TextAlignment.CENTER);
        title.setScaleX(4);
        title.setScaleY(4);
        singlePlayerBtn.setLayoutX(background.getWidth()/2-110);
        singlePlayerBtn.setLayoutY(background.getHeight()/2+20);


        EventHandler<MouseEvent> startGame = event -> {
            //TODO:play click Sound Effect
            if (event.getSource() == singlePlayerBtn) {
                startGameAnimation(true);
                this.backgroundEffectPlayer.stop();
            } else if (event.getSource() == multiPlayerBtn) {
                startGameAnimation(false);
                this.backgroundEffectPlayer.stop();
            }
        };


        singlePlayerBtn.setOnMouseClicked(startGame);

        multiPlayerBtn.setLayoutX(singlePlayerBtn.getLayoutX()+150 );
        multiPlayerBtn.setLayoutY(background.getHeight()/2+20);
        multiPlayerBtn.setOnMouseClicked(startGame);


        music.setSelected(true);
        music.setLayoutY(multiPlayerBtn.getLayoutY() + 50);
        music.setLayoutX(multiPlayerBtn.getLayoutX() + 10);
        music.setOnMouseClicked(event -> {
            if (isSoundEffectON()) {
                //ToDO:play click soundEffect
                this.backgroundEffectPlayer.play();
            }else{
                this.backgroundEffectPlayer.stop();
            }
            setMusicSts(music.isSelected());
        });

        soundEffect.setSelected(true);
        soundEffect.setLayoutY(singlePlayerBtn.getLayoutY() + 50);
        soundEffect.setLayoutX(singlePlayerBtn.getLayoutX() + 10);
        soundEffect.setOnMouseClicked(event -> setSoundEffectSts(soundEffect.isSelected()));

        showHighScores = new Button("High Scores");

        showHighScores.setLayoutX((soundEffect.getLayoutX() + multiPlayerBtn.getLayoutX()) / 2 - 20);
        showHighScores.setLayoutY(soundEffect.getLayoutY() + 40);
        showHighScores.setOnMouseClicked(event -> showScores());

        root.getChildren().add(background);
        root.getChildren().addAll(singlePlayerBtn, multiPlayerBtn, soundEffect, music, title, showHighScores);

    }

    Stage getStage() {
        return this.stage;
    }

    private void setSoundEffectSts(boolean soundEffect) {
        this.soundEffectSts = soundEffect;
    }

    private void setMusicSts(boolean musicSts) {
        this.musicSts = musicSts;
        if(musicSts){
            this.backgroundEffectPlayer.play();
        }else{
            this.backgroundEffectPlayer.stop();
        }

    }

    private boolean isSoundEffectON() {
        return soundEffectSts;
    }


    Scene getScene() {
        return scene;
    }

    private void startGameAnimation(boolean singlePlayer) {
        root.getChildren().removeAll(singlePlayerBtn, multiPlayerBtn, soundEffect, music, showHighScores);
        Timeline moveTitle = new Timeline(new KeyFrame(Duration.millis(15), event -> {
            if (title.getScaleX() > 1.5) {
                title.setScaleX(title.getScaleX() - 0.1);
                title.setScaleY(title.getScaleY() - 0.1);
            }
            if (background.getWidth() < 1000) {
                background.setWidth(background.getWidth() + 5);
                background.getGraphicsContext2D().drawImage(back, 0, 0, background.getWidth(), background.getHeight());
                stage.setWidth(background.getWidth());
            }
            if (title.getX() > 30) {
                title.setX(title.getX() - 5);
            }
            if (title.getY() > 20) {
                title.setY(title.getY() - 5);
            }
        }));
        moveTitle.setCycleCount(100);
        moveTitle.play();
        moveTitle.setOnFinished(event -> new GameCore(musicSts, soundEffectSts, singlePlayer, scene, root));
    }

    private void showScores() {
        root.getChildren().removeAll(singlePlayerBtn, multiPlayerBtn, soundEffect, music, title, showHighScores);
        root.getChildren().addAll(GameCore.getScoreBoard().getHighScoreScene(scene.getWidth() / 4, scene.getHeight() / 4, scene.getWidth() / 2, scene.getHeight() / 2));
        Button back = new Button("Back to menu");
        back.setLayoutX(scene.getWidth() / 2 - 40);
        back.setLayoutY(3.00 / 4 * scene.getHeight() + 10);
        back.setScaleX(1.5);
        back.setScaleY(1.5);
        back.setOnMouseClicked(event -> Main.resetGame());
        root.getChildren().add(back);
    }
}
