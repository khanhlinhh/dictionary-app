package ver3;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class dictionaryApplication implements Initializable {
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


    public dictionaryApplication() throws SQLException {
    }

    /** Nhấn enter để search từ và gợi ý từ. */
    @FXML
    void searchEnter(KeyEvent event) throws SQLException {
        if (event.getCode() == KeyCode.ENTER && searchBar.getText() != "") {
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


    /** Ấn để nghe phát âm. */
    @FXML
    void soundOnAction(ActionEvent event) {
        String word = searchBar.getText().toLowerCase();
        TextToSpeech pronunSearch = new TextToSpeech(word);
    }

    /** Ấ́n add để thêm từ. */
    @FXML
    void addWordAction(ActionEvent event) {
        String word = wordAddTField.getText().toLowerCase();
        String meaning = meaningAddTField.getText().toLowerCase();
        String pronun = pronunAddTField.getText().toLowerCase();
        if (word == "" || meaning == "" || pronun == "") {
            alertDisplay("Bạn ưi bạn điền thiếu kìa. Bạn điền thêm nha!");
            return;
        }
        boolean check = dictionary.addNewWord(word, pronun, meaning);
        if (!check) {
            alertDisplay("Từ này có rồi bạn ưi!");
        }
        wordAddTField.setText("");
        meaningAddTField.setText("");
        pronunAddTField.setText("");
    }

    /** Ấn refresh để sửa lại từ đầu. */
    @FXML
    void refreshAddAction(ActionEvent event) {
        wordAddTField.setText("");
        meaningAddTField.setText("");
        pronunAddTField.setText("");
    }

    /** Ấn edit để sửa từ. */
    @FXML
    void editWordAction(ActionEvent event) {
        String word = wordEditTField.getText().toLowerCase();
        String meaning = meaningEditTField.getText().toLowerCase();
        String pronun = pronunEditTField.getText().toLowerCase();
        if (word == "" || meaning == "" || pronun == "") {
            alertDisplay("Bạn ưi bạn điền thiếu kìa. Bạn điền thêm nha!");
            return;
        }
        boolean check = dictionary.update(word, pronun, meaning);
        if (!check) {
            alertDisplay("Bọn mình chưa có từ này đâu!!");
        }
        wordEditTField.setText("");
        meaningEditTField.setText("");
        pronunEditTField.setText("");
    }

    /** Ấn refresh để sửa lại từ đầu. */
    @FXML
    void refreshEditAction(ActionEvent event) {
        wordEditTField.setText("");
        meaningEditTField.setText("");
        pronunEditTField.setText("");
    }

    /** Ấn delete để xóa từ. */
    @FXML
    void deleteWordAction(ActionEvent event) throws SQLException {
        String word = wordDeleteTField.getText().toLowerCase();
        if (word == "") {
            alertDisplay("Bạn ưi bạn điền thiếu kìa. Bạn điền thêm nha!");
            return;
        }
        wordDeleteTField.setText("");
        boolean check = dictionary.delete(word);
        if (!check) {
            alertDisplay("Bọn mình chưa có từ này đâu!!");
        }
        wordDeleteTField.setText("");
    }

    /** Ấn refresh để sửa lại từ đầu. */
    @FXML
    void refreshDeleteAction(ActionEvent event) {
        wordDeleteTField.setText("");
    }

    /** Ấn Enter để dịch. */
    @FXML
    void searchEnterTranslateAction(KeyEvent event) throws SQLException, IOException {
        if (event.getCode() == KeyCode.ENTER) {
            if (langFromMenu.getValue() == null || langToMenu.getValue() == null) {
                alertDisplay("Bạn chưa chọn ngôn ngữ kìa!");
            } else {
                String ans = api.translate(langFromMenu.getValue(), langToMenu.getValue(), langTextFrom.getText());
                langTextTo.setText(ans);
            }
        }
    }

    /** Nhấp vào icon để copy văn bản đã được dịch. */
    @FXML
    void copyAction(ActionEvent event) {
        Clipboard clipboard = Clipboard.getSystemClipboard();
        ClipboardContent content = new ClipboardContent();
        content.putString(langTextTo.getText());
        clipboard.setContent(content);
    }

    /** Chọn từ từ danh sách gợi ý bằng chuột.*/
    @FXML
    void selectWordSearchByMouse(MouseEvent event) throws SQLException {
        String word = ListSearchWord.getSelectionModel().selectedItemProperty().getValue();
        String html = dictionary.getMeaningHTML(word);
        engine.loadContent(html);
        searchBar.setText(word);
    }

    /** Chọn từ từ danh sách gợi ý bằng bàn phím.*/
    @FXML
    void selectWordSearchByKeyBoard(KeyEvent event) throws SQLException {
        if (event.getCode() == KeyCode.ENTER) {
            String word = ListSearchWord.getSelectionModel().selectedItemProperty().getValue();
            String html = dictionary.getMeaningHTML(word);
            engine.loadContent(html);
            searchBar.setText(word);
        }
    }

    /** Hàm khởi tạo. */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
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

    /** Hiện cửa sổ nhắc nhở. */
    public static void alertDisplay(String message) {
        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setMinWidth(380);
        window.setMinHeight(120);

        Label label = new Label();
        label.setText(message);
        label.setFont(Font.font("Comfortaa"));
        label.setStyle("-fx-text-fill: #FFFFFF");
        JFXButton close = new JFXButton("Close");
        close.setFont(Font.font("Comfortaa"));
        close.setStyle("-fx-text-fill: #FFFFFF");
        close.setOnAction(e -> window.close());

        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, close);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-background-color:  rgb(145, 107, 191)");

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
    }
}
