package Game;


import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by Ali Jamadi on 6/25/17.
 * 9512762630
 */
public class HighScore implements Serializable {
    static final long serialVersionUID = 1234567890;
    private ArrayList<PlayerInfo> highScores = new ArrayList<>();

    HighScore() {
        readFromFile();
    }

    void addScore(PlayerInfo playerInfo) {
        highScores.add(playerInfo);
        highScores.sort((o1, o2) -> -1 * ((Integer) o1.getScore()).compareTo(o2.getScore()));
        writeToFile();
    }

    Group getHighScoreScene(double start, double width) {
        Group highScore = new Group();
        Canvas back = new Canvas(width / 2, 400);
        back.setLayoutX(start + width / 4);
        back.setLayoutY(200);
        highScore.getChildren().addAll(back);
        for (int i = 0; i < highScores.size(); i++) {
            PlayerInfo player = highScores.get(i);
            Canvas canvas = new Canvas(back.getWidth() / 2, 70);
            canvas.setLayoutX(back.getLayoutX());
            canvas.setLayoutY(back.getLayoutY() + i * 60);
            canvas.getGraphicsContext2D().strokeRoundRect(4, 4, 8, 8, 2, 2);
            canvas.getGraphicsContext2D().setStroke(Color.CYAN);
            canvas.getGraphicsContext2D().strokeText(player.getNickName() + "      " + player.getScore(), 20, 35);
            highScore.getChildren().add(canvas);
        }


        return highScore;
    }

    void writeToFile() {
        ObjectOutputStream out = null;
        try {
            out = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream("Highscores.dat")));
            out.writeObject(this);
        } catch (IOException e) {
            System.out.println("write unsuccessful");
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    System.out.println("close unsuccessful");
                }
            }
        }
    }

    void readFromFile() {
        ObjectInputStream in = null;
        try {
            in = new ObjectInputStream(new BufferedInputStream(new FileInputStream("Highscores.dat")));
            HighScore temp = ((HighScore) in.readObject());
            this.setArray(temp.getArray());
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("read unsuccessful");
        } finally {
            try {
                if (in != null)
                    in.close();
            } catch (IOException e) {
                System.out.println("close unsuccessful");
            }
        }

    }

    private ArrayList<PlayerInfo> getArray() {
        return highScores;
    }

    private void setArray(ArrayList<PlayerInfo> array) {
        highScores = array;
    }


}
