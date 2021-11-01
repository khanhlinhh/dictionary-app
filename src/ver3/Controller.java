package ver3;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import com.jfoenix.controls.JFXButton;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class Controller implements Initializable {
  private DatabaseConnection connection = new DatabaseConnection();
  private DictionaryManagement dictionary = new DictionaryManagement();

  @FXML
  private TextField wordAddTField;
  @FXML
  private TextField pronunAddTField;
  @FXML
  private TextField meaningAddTField;

  @FXML
  private TextField wordEditTField;
  @FXML
  private TextField pronunEditTField;
  @FXML
  private TextField meaningEditTField;

  @FXML
  private TextField wordDeleteTField;

  @FXML
  private ObservableList<String> items = FXCollections.observableArrayList();

  @FXML
  private ListView<String> ListSearchWord = new ListView(items);

  @FXML
  private TextField searchBar;

  @FXML
  private Button soundButton;

  @FXML
  private WebView wordImage;
  private WebEngine engine;

  public Controller() throws SQLException {
  }

  @FXML
  void searchEnter(KeyEvent event) throws SQLException {
    if (event.getCode() == KeyCode.ENTER && searchBar.getText() != "") {
      if (!soundButton.isVisible()) {
        soundButton.setVisible(true);
      }
      String word = searchBar.getText().toLowerCase();
      String html = dictionary.getMeaningHTML(word);
      engine.loadContent(html);
    } else {
      searchBar
          .textProperty()
          .addListener(
              (observable, oldValue, newValue) -> {
                String wordSearch = searchBar.getText().toLowerCase();
                items = dictionary.dictionarySearcher(wordSearch);
                ListSearchWord.setItems(items);
              });
    }
  }

  @FXML
  void soundOnAction(ActionEvent event) {
    String word = searchBar.getText().toLowerCase();
    TextToSpeech pronunSearch = new TextToSpeech(word);
  }

  @FXML
  void addWordAction(ActionEvent event) {
    String word = wordAddTField.getText().toLowerCase();
    String meaning = meaningAddTField.getText().toLowerCase();
    String pronun = pronunAddTField.getText().toLowerCase();
    dictionary.addNewWord(word,pronun,meaning);
    wordAddTField.setText("");
    meaningAddTField.setText("");
    pronunAddTField.setText("");
  }

  @FXML
  void refreshAddAction(ActionEvent event) {
    wordAddTField.setText("");
    meaningAddTField.setText("");
    pronunAddTField.setText("");
  }

  @FXML
  void editWordAction(ActionEvent event) {
    String word = wordEditTField.getText().toLowerCase();
    String meaning = meaningEditTField.getText().toLowerCase();
    String pronun = pronunEditTField.getText().toLowerCase();
    dictionary.update(word, pronun, meaning);
    wordEditTField.setText("");
    meaningEditTField.setText("");
    pronunEditTField.setText("");
  }

  @FXML
  void refreshEditAction(ActionEvent event) {
    wordEditTField.setText("");
    meaningEditTField.setText("");
    pronunEditTField.setText("");
  }

  @FXML
  void deleteWordAction(ActionEvent event) {
    String word = wordDeleteTField.getText().toLowerCase();
    wordDeleteTField.setText("");
    dictionary.delete(word);
    wordDeleteTField.setText("");
  }

  @FXML
  void refreshDeleteAction(ActionEvent event) {
    wordDeleteTField.setText("");
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    soundButton.setVisible(false);
    engine = wordImage.getEngine();
  }

//  @FXML
//  void translate(KeyEvent event) throws SQLException, IOException {
//    if (event.getCode() == KeyCode.ENTER) {
//      if (langFrom.getValue() == null || langTo.getValue() == null) {
//        alertDisplay("Hãy chọn ngôn ngữ muốn dịch!");
//      }
//      else {
//        String ans = api.translate(langFrom.getValue(), langTo.getValue(), apiTranslate.getText());
//        translate.setText(ans);
//      }
//    }
//  }
//
//  @Override
//  public void initialize(URL location, ResourceBundle resources) {
//    String[] language = new String[0];
//    try {
//      language = api.language();
//    } catch (SQLException e) {
//      e.printStackTrace();
//    }
//    langFrom.getItems().addAll(language);
//    langTo.getItems().addAll(language);
//  }
//
//  public static void alertDisplay(String message) {
//    Stage window = new Stage();
//
//    window.initModality(Modality.APPLICATION_MODAL);
//    window.setMinWidth(300);
//
//    Label label = new Label();
//    label.setText(message);
//    Button close = new Button("Close");
//    close.setOnAction(e -> window.close());
//
//    VBox layout = new VBox(10);
//    layout.getChildren().addAll(label, close);
//    layout.setAlignment(Pos.CENTER);
//
//    Scene scene = new Scene(layout);
//    window.setScene(scene);
//    window.showAndWait();
//  }
}
