package Objects.Fruits;

import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;

import java.util.Random;

/**
 * Created by Ali Jamadi on 6/24/17.
 * 9512762630
 */
public abstract class Fruits {
    private final Image image;
    Random random = new Random();
    int speed;
    int position;
    private int radius;
    private int score;

    Fruits(int radius, int speed, int score, Image image) {
        this.radius = radius;
        this.speed = speed;
        this.score = score;
        this.image = image;
        position = random.nextInt(80) + 10;

    }

    public int getRadius() {
        return radius;
    }

    public Image getImage() {
        return image;
    }

    public int getPosition() {
        return position;
    }

    public int getScore() {
        return score;
    }

    public abstract void move(Canvas fruitCanvas);


}
