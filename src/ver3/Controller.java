package ver3;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
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
  private APIggTranslate api = new APIggTranslate();

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

  @FXML
  private TextArea langTextFrom;

  @FXML
  private ChoiceBox<String> langToMenu;
  @FXML
  private ChoiceBox<String> langFromMenu;
  @FXML
  private Label langTextTo;

//  @FXML
//  private Label addNotiLabel;
//  @FXML
//  private Label deleteNotiLabel;
//  @FXML
//  private TextField editNotiLabel;

  public Controller() throws SQLException {}

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
//    if (!check) {
//      editNotiLabel.setText("Word does not exist!");
//    } else {
//      editNotiLabel.setText("Thanks for your contribution!");
//    }
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
  void deleteWordAction(ActionEvent event) throws SQLException {
    String word = wordDeleteTField.getText().toLowerCase();
    wordDeleteTField.setText("");
    dictionary.delete(word);
//    if (!check) {
//      deleteNotiLabel.setText("Word does not exist!");
//    } else {
//      deleteNotiLabel.setText("Thanks for your contribution!");
//    }
    wordDeleteTField.setText("");
  }

  @FXML
  void refreshDeleteAction(ActionEvent event) {
    wordDeleteTField.setText("");
  }

  @FXML
  void searchEnterTranslateAction(KeyEvent event) throws SQLException, IOException {
    if (event.getCode() == KeyCode.ENTER) {
      if (langFromMenu.getValue() == null || langToMenu.getValue() == null) {
        alertDisplay("Hãy chọn ngôn ngữ muốn dịch!");
      }
      else {
        String ans = api.translate(langFromMenu.getValue(), langToMenu.getValue(), langTextFrom.getText());
        langTextTo.setText(ans);
      }
    }
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    soundButton.setVisible(false);
    engine = wordImage.getEngine();
    String[] language = new String[0];
    try {
      language = api.language();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    langFromMenu.getItems().addAll(language);
    langToMenu.getItems().addAll(language);
  }

  public static void alertDisplay(String message) {
    Stage window = new Stage();

    window.initModality(Modality.APPLICATION_MODAL);
    window.setMinWidth(300);

    Label label = new Label();
    label.setText(message);
    Button close = new Button("Close");
    close.setOnAction(e -> window.close());

    VBox layout = new VBox(10);
    layout.getChildren().addAll(label, close);
    layout.setAlignment(Pos.CENTER);

    Scene scene = new Scene(layout);
    window.setScene(scene);
    window.showAndWait();
  }
}
