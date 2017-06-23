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
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Created by Ali Jamadi on 6/23/17.
 * 9512762630
 */
class GameInit {
    private final Image back = new Image("file:Resources/JungleBack.png");
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


    GameInit(Stage stage) {
        this.stage = stage;

        background.getGraphicsContext2D().drawImage(back,0,0,500,700);
        //TODO: PlayGame sound & load soundEffects

        title.setTextAlignment(TextAlignment.CENTER);
        title.setScaleX(4);
        title.setScaleY(4);
        singlePlayerBtn.setLayoutX(background.getWidth()/2-110);
        singlePlayerBtn.setLayoutY(background.getHeight()/2+20);


        EventHandler<MouseEvent> startGame = event -> {
            if (event.getSource() == singlePlayerBtn) {
                startGameAnimation(true);
            } else if (event.getSource() == multiPlayerBtn) {
                startGameAnimation(false);
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
                //ToDO:play sound effect
            }
            setMusicSts(music.isSelected());
        });

        soundEffect.setSelected(true);
        soundEffect.setLayoutY(singlePlayerBtn.getLayoutY() + 50);
        soundEffect.setLayoutX(singlePlayerBtn.getLayoutX() + 10);
        soundEffect.setOnMouseClicked(event -> {
            if (isSoundEffectON()) {
                //ToDO:play sound effect
            }
            setSoundEffectSts(soundEffect.isSelected());
        });





        root.getChildren().add(background);
        root.getChildren().addAll(singlePlayerBtn, multiPlayerBtn, soundEffect, music, title);

    }

    private void setSoundEffectSts(boolean soundeffect) {
        this.soundEffectSts = soundeffect;
    }

    private void setMusicSts(boolean musicSts) {
        this.musicSts = musicSts;
        //ToDo:Stop and play Music
    }

    private boolean isSoundEffectON() {
        return soundEffectSts;
    }


    Scene getScene() {
        return scene;
    }

    private void startGameAnimation(boolean singlePlayer) {
        root.getChildren().removeAll(singlePlayerBtn, multiPlayerBtn, soundEffect, music);
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
}
