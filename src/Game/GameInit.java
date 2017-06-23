package Game;


import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

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
        Button startGameBtn = new Button("Start");
        startGameBtn.setLayoutX(background.getWidth()/2-20);
        startGameBtn.setLayoutY(background.getHeight()/2+20);



        root.getChildren().add(background);
        root.getChildren().addAll(startGameBtn);

    }

    Scene getScene() {
        return scene;
    }
}
