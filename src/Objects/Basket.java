package Objects;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * Created by Ali Jamadi on 6/23/17.
 * 9512762630
 */
public class Basket {
    private final Image basketImg = new Image("file:Resources/images/basket.png");
    private Canvas basketCanvas;

    public Basket(double width, double height) {
        basketCanvas = new Canvas(width, height);
        GraphicsContext gc = basketCanvas.getGraphicsContext2D();
        gc.drawImage(basketImg, 0, 0, width, height);
    }

    public Canvas getBasketCanvas() {
        return basketCanvas;
    }


}
