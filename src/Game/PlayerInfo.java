package Game;

import javafx.scene.image.Image;

import java.io.Serializable;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Ali Jamadi on 6/24/17.
 * 9512762630
 */
public class PlayerInfo implements Comparable<PlayerInfo>, Serializable {
    static final long serialVersionUID = 1234567890;
    private transient Image avatar = null;
    private String nickName;
    private int score = 0;
    private int hearts = 3;

    PlayerInfo(String nickName) {
        this.nickName = nickName;
        setRandomAvatar();

    }

    String getNickName() {
        return nickName;
    }

    Image getAvatar() {
        return avatar;
    }

    int getScore() {
        return score;
    }

    void addScore(int score) {
        this.score += score;
    }

    int getHearts() {
        return hearts;
    }

    void addHearts() {
        this.hearts++;
    }

    void loseHart() {
        this.hearts--;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PlayerInfo that = (PlayerInfo) o;

        return nickName.equals(that.nickName);
    }

    @Override
    public int hashCode() {
        return nickName.hashCode();
    }


    @Override
    public int compareTo(PlayerInfo o) {
        if (this == o) return 0;
        return -1 * ((Integer) this.getScore()).compareTo(o.getScore());
    }

    void setRandomAvatar() {
        int randomNum = ThreadLocalRandom.current().nextInt(1, 10 + 1); //min, max+1
        avatar = new Image("file:Resources/images/avatars/" + randomNum + ".png");
    }
}
