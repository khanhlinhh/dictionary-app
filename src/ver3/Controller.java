package ver3;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class Controller implements Initializable {
  DatabaseConnection connection = new DatabaseConnection();

  @FXML
  private ObservableList<String> items = FXCollections.observableArrayList();

  @FXML
  private ListView<String> ListSearchWord = new ListView(items);

  @FXML
  private Label meaningArea;

  @FXML
  private Label pronunLabel;

  @FXML
  private TextField searchBar;

  @FXML
  private Button soundButton;

  @FXML
  private Label wordLabel;

  @FXML
  private WebView wordImage;
  private WebEngine engine;

  @FXML
  void searchEnter(KeyEvent event) throws SQLException {
    DictionaryManagement wordLookup = new DictionaryManagement();
    if (event.getCode() == KeyCode.ENTER && searchBar.getText() != "") {
      if (!soundButton.isVisible()) {
        soundButton.setVisible(true);
      }
      String word = searchBar.getText().toLowerCase();
      String meaning = wordLookup.getMeaning(word);
      String pronun = wordLookup.getPronun(word);
      meaningArea.setText(meaning);
      pronunLabel.setText(pronun);
      wordLabel.setText(word);
      //String url = "https://www.google.com/search?q=" + word + "&tbm=isch";
      String url = "https://pixabay.com/images/search/" + word + "/";
      engine.load(url);

    } else {
      searchBar
          .textProperty()
          .addListener(
              (observable, oldValue, newValue) -> {
                String wordSearch = searchBar.getText().toLowerCase();
                items = wordLookup.dictionarySearcher(wordSearch);
                ListSearchWord.setItems(items);
              });
    }
  }

  @FXML
  void soundOnAction(ActionEvent event) {
    String word = wordLabel.getText();
    TextToSpeech pronunSearch = new TextToSpeech(word);
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    soundButton.setVisible(false);
    engine = wordImage.getEngine();
//    DatabaseConnection connection = new DatabaseConnection();
//    Connection Conn = connection.getDBConnection();
//    Statement stmt = null;
//    ResultSet rs = null;
//    try {
//      stmt = Conn.createStatement();
//      rs = stmt.executeQuery("SELECT word FROM tudienanhviet");
//      int n = 0;
//      while (rs.next() && n < 200) {
//        items.add(rs.getString(1));
//        n++;
//      }
//
//      ListSearchWord.setItems(items);
//    } catch (SQLException e) {
//    }
  }
}
