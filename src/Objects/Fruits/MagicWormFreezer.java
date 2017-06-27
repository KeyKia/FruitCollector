package Objects.Fruits;

import Game.GameScene;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;

/**
 * Created by kiarash on 6/27/17.
 */
public class MagicWormFreezer extends Fruits {
    static private final Image img = new Image("file:Resources/images/MagicFruit2.png");
    private boolean left;
    private int angel;
    private double horizantalSpeed;
    public MagicWormFreezer() {
        super(((int) (GameScene.UNIT * 20)), ( (150 * GameScene.UNIT / GameScene.SPEED_CONVERTER)), 0, img);
        if (position > 10 && position < 90)
            left = random.nextBoolean();
        else left = position > 10;
        angel = random.nextInt(30) + 30;
        double a = speed / Math.sin(angel);
        horizantalSpeed = a * Math.cos(angel);
    }

    @Override
    public void move(Canvas fruitCanvas, double width, double start) {
        if (left) {
            fruitCanvas.setLayoutX(fruitCanvas.getLayoutX() - horizantalSpeed);
            fruitCanvas.setLayoutY(fruitCanvas.getLayoutY() + speed);
        } else {
            fruitCanvas.setLayoutX(fruitCanvas.getLayoutX() + horizantalSpeed);
            fruitCanvas.setLayoutY(fruitCanvas.getLayoutY() + speed);
        }
    }
}
