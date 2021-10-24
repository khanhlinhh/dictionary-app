package ver3;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.concurrent.atomic.AtomicBoolean;

import static java.lang.System.*;
import static javafx.scene.layout.GridPane.setConstraints;

public class DictionaryApplication extends Application {
    DictionaryManagement management;
    Stage window;
    Scene scene1, scene2, scene3;

    public static void main(String[] args) {
        launch(args);
    }

    public static boolean confirmDisplay(String title, String message) {
        Stage window = new Stage();
        AtomicBoolean ans = new AtomicBoolean(false);

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(300);

        Label label = new Label();
        label.setText(message);

        //Button
        Button yesButton = new Button("Yes");
        Button noButton = new Button("No");
        yesButton.setOnAction(e -> {
            ans.set(true);
            window.close();
        });
        noButton.setOnAction(e -> {
            window.close();
        });

        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, yesButton, noButton);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();

        return ans.get();
    }

    public static void alertDisplay(String title, String message) {
        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(300);

        Label label = new Label();
        label.setText(message);
        Button close = new Button("close");
        close.setOnAction(e -> window.close());

        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, close);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
    }

    private void closeProgram() {
        boolean ans = confirmDisplay("", "Are you sure you want to exit?");
        if (ans) {
            window.close();
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        window.setOnCloseRequest(e -> {
            e.consume();
            closeProgram();
        });


        /**
         * Scene 1.
         */
        HBox layout1 = new HBox();
        Button button1 = new Button("Tra từ");
        button1.setOnAction(e -> window.setScene(scene2));
        Button button2 = new Button("Dịch văn bản");
        button2.setOnAction(e -> window.setScene(scene3));
        layout1.getChildren().addAll(button1, button2);
        scene1 = new Scene(layout1, 600, 300);

        /**
         * Scene 2.
         */
        TextField textField = new TextField();      // Nhập từ
        textField.setPrefWidth(110);
        textField.setPromptText("Type here...");

        Button backButton = new Button("Back");    // Quay lại scene 1
        backButton.setOnAction(e -> window.setScene(scene1));

        Button searchButton = new Button("Search");     // Search
        searchButton.setOnAction(e -> System.out.println(textField.getText()));

        Button helpButton = new Button("HELP");        // Help
        helpButton.setOnAction(e -> alertDisplay("Tittle", "Here is the message!"));

        // layout scene 2
        GridPane layout2 = new GridPane();
        layout2.setPadding(new Insets(10, 10, 10, 10));
        layout2.setVgap(8);
        layout2.setHgap(10);

        setConstraints(textField, 0, 0);
        setConstraints(searchButton, 1, 0);
        setConstraints(backButton, 6, 9);
        setConstraints(helpButton, 5, 5);
        layout2.getChildren().addAll(textField, searchButton, backButton, helpButton);
        scene2 = new Scene(layout2, 600, 300);
        scene2.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
                    out.println(textField.getText());
                }
            }
        });

        /**
         * Scene 3
         */
        GridPane layout3 = new GridPane();
        layout3.setPadding(new Insets(10, 10, 10, 10));
        layout3.setVgap(8);
        layout3.setHgap(10);
        TextField textField2 = new TextField();      // Nhập từ
        textField2.setPrefWidth(110);
        textField2.setPromptText("Type here...");

        Button backButton2 = new Button("Back");    // Quay lại scene 1
        backButton2.setOnAction(e -> window.setScene(scene1));

        Label label1 = new Label("Translate from: ");
        Label label2 = new Label("To: ");
        ChoiceBox<String> langFrom = new ChoiceBox<>();
        langFrom.getItems().addAll("Vietnamese", "English", "Chinese", "Korean", "French", "Japanese");
        ChoiceBox<String> langTo = new ChoiceBox<>();
        langTo.getItems().addAll("Vietnamese", "English", "Chinese", "Korean", "French", "Japanese");
        setConstraints(label1, 0, 0);
        setConstraints(label2, 0, 1);
        setConstraints(langFrom, 1, 0);
        setConstraints(langTo, 1, 1);
        setConstraints(backButton2, 5, 5);
        setConstraints(textField2, 0, 2);
        layout3.getChildren().addAll(label1, label2, langFrom, langTo, backButton2, textField2);
        scene3 = new Scene(layout3, 600, 300);


        window.setScene(scene1);
        window.setTitle("Dictionary");
        window.show();
    }
}
