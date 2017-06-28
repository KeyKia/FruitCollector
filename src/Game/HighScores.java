package Game;

import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by Ali Jamadi on 6/27/17.
 * 9512762630
 */
class HighScores implements Serializable {
    static final long serialVersionUID = 987654321;
    private ArrayList<PlayerInfo> highScores = new ArrayList<>();

    HighScores() {
        readFromFile();
    }

    void addScore(PlayerInfo player) {
        int n = highScores.indexOf(player);
        boolean shouldAdd = true;
        if (n != -1) {
            shouldAdd = false;
            if (highScores.get(n).getScore() < player.getScore()) {
                highScores.remove(n);
                shouldAdd = true;
            }
        }
        if (shouldAdd) {
            highScores.add(player);
            highScores.sort(PlayerInfo::compareTo);
            writeToFile();
        }

    }


    Group getHighScoreScene(double x, double y, double width, double height) {
        Group highScoresGroup = new Group();

        Canvas back = new Canvas(width, height);
        back.setLayoutX(x);
        back.setLayoutY(y);
        highScoresGroup.getChildren().add(back);



        int limit = (highScores.size()>5)? 5 : highScores.size();
        for (int i = 0; i < limit; i++) {

            PlayerInfo player = highScores.get(i);
            ImageView avatar = new ImageView(player.getAvatar());
            if (avatar.getImage() == null) {
                player.setRandomAvatar();
                avatar.setImage(player.getAvatar());
            }
            avatar.setX(x);
            avatar.setY(y + (i * (width/6)) + (i * 15));
            avatar.setFitWidth(width / 6);
            avatar.setFitHeight(width / 6);

            Text name = new Text(avatar.getX() + avatar.getFitWidth() + 15, avatar.getY() + avatar.getFitHeight() / 2 + 10, player.getNickName());
            name.setTextAlignment(TextAlignment.CENTER);
            name.resize(width / 2, 50);
            Font nameFont = name.getFont();
            name.setFont(new Font(nameFont.getName(), width/12));


            Text score = new Text(name.getX() + width / 2 + 5, name.getY(), "" + player.getScore());
            //score.setFont(button.getFont().deriveFont((float)(width/6)));
            Font scoreFont = score.getFont();
            score.setFont(new Font(scoreFont.getName(), width/12));


            highScoresGroup.getChildren().addAll(avatar, name, score);

        }

        return highScoresGroup;
    }

    private void writeToFile() {
        ObjectOutputStream out = null;
        try {
            out = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream("HighScores.dat")));
            out.writeObject(this);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null)
                try {
                    out.close();
                } catch (IOException e) {
                    System.out.println("close while writing unsuccessful");
                }
        }

    }

    private void readFromFile() {
        ObjectInputStream in = null;
        try {
            in = new ObjectInputStream(new BufferedInputStream(new FileInputStream("HighScores.dat")));
            HighScores temp = ((HighScores) in.readObject());
            highScores = temp.getHighScores();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("read unsuccessful ");
        } finally {
            if (in != null)
                try {
                    in.close();
                } catch (IOException e) {
                    System.out.println("close while reading unsuccessful");
                }
        }
    }

    private ArrayList<PlayerInfo> getHighScores() {
        return highScores;
    }
}
