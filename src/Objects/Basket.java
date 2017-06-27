package Objects;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * Created by Ali Jamadi on 6/23/17.
 * 9512762630
 */
public class Basket {
    private Canvas basketCanvas;
    private double width;
    private double height;
    private Image basketImg;
    private GraphicsContext gc;

    public Basket(double width, double height) {
        basketCanvas = new Canvas(width, height);
        this.width=width;
        this.height=height;
        gc = basketCanvas.getGraphicsContext2D();
        basketImg = new Image("file:Resources/images/basket.png");
        gc.drawImage(basketImg, 0, 0, width, height);
    }

    public Canvas getBasketCanvas() {
        return basketCanvas;
    }

    //TODO: Bug -> the if condition in the following methods doesn't seem to work properly

    public void halfTheBasket(){
        if ( basketCanvas.getWidth()==width*2 )
            this.renormalTheBasket();
        else {
            basketCanvas.setWidth(width / 2);
            gc.clearRect(0, 0, width, height);
            gc.drawImage(basketImg, 0, 0, width / 2, height);
        }
    }

    public void doubleTheBasket(){
        if ( basketCanvas.getWidth()==width/2 )
            this.renormalTheBasket();
        else {
            basketCanvas.setWidth(width * 2);
            gc.clearRect(0, 0, width, height);
            gc.drawImage(basketImg, 0, 0, width * 2, height);
        }
    }

    public void renormalTheBasket(){
        basketCanvas.setWidth(width);
        gc.clearRect(0, 0, width, height);
        gc.drawImage(basketImg, 0, 0, width, height);
    }

}
