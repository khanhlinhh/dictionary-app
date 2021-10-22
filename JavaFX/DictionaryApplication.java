package dictionary.dictionary;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class DictionaryApplication extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
            Parent root = FXMLLoader.load(getClass().getResource("Dictionary.fxml"));
            //FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Dictionary.fxml"));
            primaryStage.setTitle("Dictionary");
            Scene scene = new Scene(root);
            Image image = new Image("dictionary_iicon.png");
            primaryStage.getIcons().add(image);
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.show();
    }

}
