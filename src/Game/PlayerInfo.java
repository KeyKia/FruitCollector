package Game;

import javafx.scene.image.Image;

/**
 * Created by Ali Jamadi on 6/24/17.
 * 9512762630
 */
public class PlayerInfo {
    Image avatar;
    private String nickName;
    private int score = 0;

    public PlayerInfo(String nickName) {
        this.nickName = nickName;
        //TODO:Set avatar;
    }

    public String getNickName() {
        return nickName;
    }

    public Image getAvatar() {
        return avatar;
    }

    public int getScore() {
        return score;
    }

    public void addScore(int score) {
        this.score += score;
    }
}
