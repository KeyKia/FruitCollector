package Objects.Fruits;

import Game.GameScene;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;


/**
 * Created by Ali Jamadi on 6/24/17.
 * 9512762630
 */
public class Orange extends Fruits {
    static private final Image img = new Image("file:Resources/images/Orange.png");
    private boolean left;

    public Orange() {
        super(((int) (GameScene.UNIT * 10)), ((int) (40 * GameScene.UNIT / GameScene.SPEED_CONVERTER)), 40, img);
        if (position > 10 && position < 90)
            left = random.nextBoolean();
        else left = position > 10;
    }

    @Override
    public void move(Canvas fruitCanvas) {
        if (left) {
            fruitCanvas.setLayoutX(fruitCanvas.getLayoutX() - speed / 2.00);
            fruitCanvas.setLayoutY(fruitCanvas.getLayoutY() + speed);
        } else {
            fruitCanvas.setLayoutX(fruitCanvas.getLayoutX() + speed / 2.00);
            fruitCanvas.setLayoutY(fruitCanvas.getLayoutY() + speed);
        }
    }
}
