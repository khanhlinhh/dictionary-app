package ver3;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.*;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class Controller implements Initializable {
  @FXML
  private TextField searchBar;

  @FXML
  private ListView<String> ListSearchWord;

  ObservableList<String> items = FXCollections.observableArrayList();

    @Override
  public void initialize(URL location, ResourceBundle resources) {
      Connection Conn = null;

      try {
          Conn = DriverManager.getConnection("jdbc:sqlite:Dictionary.db");
      } catch (SQLException e) {
          System.out.println(e.getMessage());
      }

      Statement stmt = null;
      ResultSet rs = null;
      try {
          stmt = Conn.createStatement();
          rs = stmt.executeQuery("SELECT word FROM tudienanhviet");
          int n = 0;
          while (rs.next() && n < 100) {
              items.add(rs.getString(1));
              n++;
          }

          ListSearchWord.setItems(items);
      } catch (SQLException e) {}
  }
    private List<String> searchList(String searchWords, List<String> listOfStrings) {

        List<String> searchWordsArray = Arrays.asList(searchWords.trim().split(" "));

        return listOfStrings.stream().filter(input -> {
            return searchWordsArray.stream().allMatch(word ->
                    input.toLowerCase().contains(word.toLowerCase()));
        }).collect(Collectors.toList());
    }
}