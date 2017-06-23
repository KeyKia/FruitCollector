package Game;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        GameInit game = new GameInit();

        primaryStage.setTitle("Fruit Collector");
        primaryStage.setScene(game.getScene());
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
