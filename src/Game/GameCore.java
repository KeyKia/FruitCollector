package Game;

import javafx.scene.Group;
import javafx.scene.Scene;

/**
 * Created by Ali Jamadi on 6/23/17.
 * 9512762630
 */
class GameCore {
    private boolean music, soundEffect, singlePlayer;
    private Scene scene;
    private Group root;

    GameCore(boolean music, boolean soundEffect, boolean singlePlayer, Scene scene, Group root) {
        this.music = music;
        this.soundEffect = soundEffect;
        this.singlePlayer = singlePlayer;
        this.scene = scene;
        this.root = root;
    }
}
