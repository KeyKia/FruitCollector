package Game;


import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;

/**
 * Created by Ali Jamadi on 6/23/17.
 * 9512762630
 */
class GameInit {
    private Group root = new Group();
    private Scene scene = new Scene(root);

    GameInit() {
        Canvas background = new Canvas(300,700);
        root.getChildren().add(background);
    }

    Scene getScene() {
        return scene;
    }
}
