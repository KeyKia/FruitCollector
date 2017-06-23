package Game;


import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.image.Image;

/**
 * Created by Ali Jamadi on 6/23/17.
 * 9512762630
 */
class GameInit {
    private Group root = new Group();
    private Scene scene = new Scene(root);

    GameInit() {
        Canvas background = new Canvas(500,700);
        Image back = new Image("file:Resources/JungleBack.png");
        background.getGraphicsContext2D().drawImage(back,0,0,500,700);

        Button singlePlayerBtn = new Button("Single Player");
        singlePlayerBtn.setLayoutX(background.getWidth()/2-110);
        singlePlayerBtn.setLayoutY(background.getHeight()/2+20);

        Button multiPlayerBtn = new Button("Multi Player");
        multiPlayerBtn.setLayoutX(singlePlayerBtn.getLayoutX()+150 );
        multiPlayerBtn.setLayoutY(background.getHeight()/2+20);

//        CheckBox music = new CheckBox("Music");
//        music.setSelected(true);
//        music.setLayoutY(multiPlayerBtn.getLayoutY()+20);
//        music.setLayoutX(singlePlayerBtn.getLayoutX());
//
//        CheckBox soundEffect = new CheckBox("Sound Effect");
//        soundEffect.setSelected(true);
//        soundEffect.setLayoutY(multiPlayerBtn.getLayoutY()+20);
//        soundEffect.setLayoutX(singlePlayerBtn.getLayoutX());




        root.getChildren().add(background);
        root.getChildren().addAll(singlePlayerBtn,multiPlayerBtn);

    }

    Scene getScene() {
        return scene;
    }
}
