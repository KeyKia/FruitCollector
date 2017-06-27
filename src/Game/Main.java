package Game;

import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Main extends Application {
    public static GameInit game;
    public static void main(String... args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        //GameInit game = new GameInit(primaryStage);
        Main.game = new GameInit(primaryStage);
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();

        //set Stage boundaries to the lower right corner of the visible bounds of the main screen

        primaryStage.setX(primaryScreenBounds.getMinX() + 100);
        primaryStage.setY(primaryScreenBounds.getMinY());
        primaryStage.setTitle("Fruit Collector");
        primaryStage.setScene(game.getScene());
        primaryStage.show();
    }

    public static void resetGame(){
        System.out.println("reset");
        Stage primaryStage = Main.game.getStage();
        Main.game = new GameInit(primaryStage);
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();

        //set Stage boundaries to the lower right corner of the visible bounds of the main screen
        primaryStage.setWidth(510.00);
        primaryStage.setX(primaryScreenBounds.getMinX() + 100);
        primaryStage.setY(primaryScreenBounds.getMinY());
        primaryStage.setTitle("Fruit Collector");
        primaryStage.setScene(game.getScene());
        primaryStage.show();

    }
}
