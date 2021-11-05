package ver3;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws IOException {
        primaryStage.setTitle("Dictionary Vietnamese - English");
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("Dictionary.fxml"));
        Scene scene = new Scene(loader.load(),955,628);
        Image icon = new Image("file:Logo.png");
        primaryStage.getIcons().add(icon);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}