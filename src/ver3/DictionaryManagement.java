package ver3;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;

public class DictionaryManagement {
    private DatabaseConnection dbConnection = new DatabaseConnection();
    private Connection con = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;
    private Statement stmt = null;
    private static final String dbURL = "jdbc:sqlite:Dictionary.db";

    public DictionaryManagement() throws SQLException {
        con = dbConnection.getDBConnection();
        stmt = con.createStatement();
    }

    public String getMeaningHTML(String word) throws SQLException {
        String html;
        PreparedStatement ps = con.prepareStatement("select *from tudienanhviet where word = ?");
        ps.setString(1,word);
        ResultSet rs = ps.executeQuery();
        if (rs == null) {
            html = "<font face=\"Comfortaa\" size=\"20px\" color=\"#1C0C5B\"><h1> " +
                  "The word does not exist! </h1></font>";
            return html;
        }
        html = rs.getString("html");
        return html;
    }

    public ObservableList<String> dictionarySearcher(String wordSearch) {
        ObservableList<String> items = FXCollections.observableArrayList();
        try {
            int n = 0;
            if (!items.isEmpty()) {
                items.clear();
            }
            this.rs = stmt.executeQuery("SELECT * FROM tudienanhviet WHERE word LIKE '" + wordSearch + "%';");
            while (rs.next() && n < 20) {
                n++;
                String word = rs.getString("word");
                items.add(word);
            }

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return items;
    }

    public void addNewWord(String word, String pronun, String description) {
        word = word.toLowerCase();
        pronun = pronun.toLowerCase();
        description.toLowerCase();
        String html = "<font face=\"Comfortaa\" size=\"20px\" color=\"#1C0C5B\"><h1>" + word + "</h1>";
        html += "<h3><i>" + pronun + "</i></h3>";
        html += "<p>" + description + "</p></font>";
        try {
            stmt.executeUpdate("INSERT INTO tudienanhviet (word, html, description, pronounce)"
                    + " VALUES ('" + word + "','" + html + "','" + description+ "','" + pronun + "')");
            System.out.println("Đã thêm thành công!");
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    public void delete(String word) {
        try {
            stmt.executeUpdate("DELETE FROM tudienanhviet WHERE word ='" + word + "';");
            System.out.print("Đã xóa thành công! ");
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    public void update(String word, String pronun, String description) {
        try {
            String html = "<font face=\"Comfortaa\" size=\"20px\" color=\"#1C0C5B\"><h1>" + word + "</h1>";
            html += "<h3><i>" + pronun + "</i></h3>";
            html += "<p>" + description + "</p></font>";
            stmt.executeUpdate("UPDATE tudienanhviet SET description " +
                    "= '" + description + "', html = '" + html + "', pronounce = '" + pronun + "' WHERE word = '" + word + "';");
            System.out.println("Đã sửa thành công!");
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }
}
