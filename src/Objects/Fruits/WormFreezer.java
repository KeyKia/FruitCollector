package Objects.Fruits;

import Game.GameScene;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;

/**
 * Created by jeem on 6/26/2017.
 */
public class WormFreezer extends Fruits {
    static private final Image img = new Image("file:Resources/images/wormFreezer.png");

    public WormFreezer() {
        super(((int) (GameScene.UNIT * 30)), ( (100 * GameScene.UNIT / GameScene.SPEED_CONVERTER)), 0, img);
    }

    @Override
    public void move(Canvas fruitCanvas, double width, double start) {
        fruitCanvas.setLayoutY(fruitCanvas.getLayoutY() + speed);
    }
}
