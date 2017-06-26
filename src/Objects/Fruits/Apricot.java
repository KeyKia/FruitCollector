package Objects.Fruits;

import Game.GameScene;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;

/**
 * Created by kiarash on 6/26/17.
 */
public class Apricot extends Fruits {

    static private final Image img = new Image("file:Resources/images/Apricot.png");
    private boolean direction;

    public Apricot() {
        super(((int) (GameScene.UNIT * 5)), ( (20 * GameScene.UNIT / GameScene.SPEED_CONVERTER)), 30, img);
        direction = true;
    }

    @Override
    public void move(Canvas fruitCanvas, double width, double start) {
        double startX = width * position / 100 + start;
        if ( direction && (fruitCanvas.getLayoutX()-startX)/width*100>=3 )
            direction = !direction;
        if ( !direction && (startX-fruitCanvas.getLayoutX())/width*100>=3 )
            direction = !direction;
        if ( direction ) {
            fruitCanvas.setLayoutX(fruitCanvas.getLayoutX() + speed);
            fruitCanvas.setLayoutY(fruitCanvas.getLayoutY() + speed);
        }
        else {
            fruitCanvas.setLayoutX(fruitCanvas.getLayoutX() - speed);
            fruitCanvas.setLayoutY(fruitCanvas.getLayoutY() + speed);
        }

    }

}
