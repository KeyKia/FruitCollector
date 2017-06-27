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
    private double horizontalSpeed;
    public Orange() {
        super(((int) (GameScene.UNIT * 10)), ( (40 * GameScene.UNIT / GameScene.SPEED_CONVERTER)), 40, img);
        if (position > 10 && position < 90)
            left = random.nextBoolean();
        else left = position > 10;
        int angel = random.nextInt(60) + 30;
        double a = speed / Math.sin(angel);
        horizontalSpeed = a * Math.cos(angel);
    }

    @Override
    public void move(Canvas fruitCanvas, double width, double start) {
        if (left) {
            fruitCanvas.setLayoutX(fruitCanvas.getLayoutX() - horizontalSpeed);
            fruitCanvas.setLayoutY(fruitCanvas.getLayoutY() + speed);
        } else {
            fruitCanvas.setLayoutX(fruitCanvas.getLayoutX() + horizontalSpeed);
            fruitCanvas.setLayoutY(fruitCanvas.getLayoutY() + speed);
        }
    }
}
