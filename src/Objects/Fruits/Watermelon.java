package Objects.Fruits;

import Game.GameScene;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;

/**
 * Created by Ali Jamadi on 6/25/17.
 * 9512762630
 */
public class Watermelon extends Fruits {
    static private final Image img = new Image("file:Resources/images/watermelon.png");

    public Watermelon() {
        super(((int) (GameScene.UNIT * 30)), ((int) (100 * GameScene.UNIT / GameScene.SPEED_CONVERTER)), 60, img);
    }

    @Override
    public void move(Canvas fruitCanvas) {
        fruitCanvas.setLayoutY(fruitCanvas.getLayoutY() + speed);
        //TODO:find a way to rotate this canvas to create a falling effect
    }
}
