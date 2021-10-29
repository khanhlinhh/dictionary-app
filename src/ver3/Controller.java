package ver3;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class Controller implements Initializable {
  DatabaseConnection connection = new DatabaseConnection();
  Connection conn = connection.getDBConnection();
  @FXML
  private ObservableList<String> items = FXCollections.observableArrayList();

  @FXML
  private ListView<String> ListSearchWord = new ListView(items);

  @FXML
  private TextArea meaningArea;

  @FXML
  private Label pronunLabel;

  @FXML
  private TextField searchBar;

  @FXML
  private Label wordLabel;

  @FXML
  void searchEnter(KeyEvent event) throws SQLException {
    if (event.getCode() == KeyCode.ENTER) {
      String word = searchBar.getText().toLowerCase();
      String sql = "select *from tudienanhviet where word = ?";
      PreparedStatement ps = conn.prepareStatement(sql);
      ps.setString(1,word);
      ResultSet rs = ps.executeQuery();
      String meaning = rs.getString(3);
      String pronun = rs.getString(4);
      String wordSearch = rs.getString(1);
      meaningArea.setText(meaning);
      pronunLabel.setText(pronun);
      wordLabel.setText(wordSearch);
      TextToSpeech pronunSound = new TextToSpeech(wordSearch);
    }
  }

  @FXML
  void soundOnAction(ActionEvent event) {
    String word = wordLabel.getText();
    TextToSpeech pronunSearch = new TextToSpeech(word);
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    DatabaseConnection connection = new DatabaseConnection();
    Connection Conn = connection.getDBConnection();
    Statement stmt = null;
    ResultSet rs = null;
    try {
      stmt = Conn.createStatement();
      rs = stmt.executeQuery("SELECT word FROM tudienanhviet");
      int n = 0;
      while (rs.next() && n < 200) {
        items.add(rs.getString(1));
        n++;
      }

      ListSearchWord.setItems(items);
    } catch (SQLException e) {
    }
  }
}
