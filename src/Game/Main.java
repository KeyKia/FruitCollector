package Game;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        GameInit game = new GameInit();

        primaryStage.setTitle("Hello World");
        primaryStage.setScene(game.getScene());
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
