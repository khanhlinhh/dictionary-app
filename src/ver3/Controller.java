package ver3;

import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class Controller implements Initializable {
  @FXML
  private ObservableList<String> items = FXCollections.observableArrayList();
  @FXML private TextField searchBar;
  @FXML private ListView<String> ListSearchWord = new ListView(items);

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

  private List<String> searchList(String searchWords, List<String> listOfStrings) {

    List<String> searchWordsArray = Arrays.asList(searchWords.trim().split(" "));

    return listOfStrings.stream()
        .filter(
            input -> {
              return searchWordsArray.stream()
                  .allMatch(word -> input.toLowerCase().contains(word.toLowerCase()));
            })
        .collect(Collectors.toList());
  }
}
