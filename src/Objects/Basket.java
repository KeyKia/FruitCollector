package Objects;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.util.Duration;

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
        double newValue = basketCanvas.getWidth() / 2;
        Timeline Animation = new Timeline(new KeyFrame(Duration.millis(10), event -> {
            if (basketCanvas.getWidth() > newValue) {
                gc.clearRect(0, 0, basketCanvas.getWidth(), basketCanvas.getHeight());
                basketCanvas.setWidth(basketCanvas.getWidth() - 1);
                gc.drawImage(basketImg, 0, 0, basketCanvas.getWidth() - 1, height);
            }
        }));
        Animation.setCycleCount(100);
        Animation.play();
    }

    public void doubleTheBasket(){
        double newValue = basketCanvas.getWidth() * 2;
        Timeline Animation = new Timeline(new KeyFrame(Duration.millis(10), event -> {
            if (basketCanvas.getWidth() < newValue) {
                gc.clearRect(0, 0, basketCanvas.getWidth(), basketCanvas.getHeight());
                basketCanvas.setWidth(basketCanvas.getWidth() + 1);
                gc.drawImage(basketImg, 0, 0, basketCanvas.getWidth() + 1, height);
            }
        }));
        Animation.setCycleCount(100);
        Animation.play();
    }

    public void renormalTheBasket(){
        basketCanvas.setWidth(width);
        gc.clearRect(0, 0, width, height);
        gc.drawImage(basketImg, 0, 0, width, height);
    }

}
