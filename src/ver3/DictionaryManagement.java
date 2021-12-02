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

    /** Constructor. */
    public DictionaryManagement() throws SQLException {
        con = dbConnection.getDBConnection();
        stmt = con.createStatement();
    }

    /** Lấy nghĩa của từ. */
    public String getMeaningHTML(String word) throws SQLException {
        String html;
        PreparedStatement ps = con.prepareStatement("select *from tudienanhviet where word = ?");
        word = word.trim();
        ps.setString(1, word);
        ResultSet rs = ps.executeQuery();
        if (!rs.next()) {
            html = "<font face=\"Comfortaa\" size=\"20px\" color=\"#1C0C5B\"><h3> " +
                    "The word does not exist! </h3></font>";
            return html;
        }
        html = rs.getString("html");
        return html;
    }

    /** Lấy danh sách từ gợi ý. */
    public ObservableList<String> dictionarySearcher(String wordSearch) {
        ObservableList<String> items = FXCollections.observableArrayList();
        wordSearch = wordSearch.trim();
        try {
            int n = 0;
            if (!items.isEmpty()) {
                items.clear();
            }
            this.rs = stmt.executeQuery("SELECT * FROM tudienanhviet WHERE word LIKE \"" + wordSearch + "%\";");
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

    /** Thêm từ. */
    public boolean addNewWord(String word, String pronun, String description) {
        word = word.toLowerCase().trim();
        pronun = pronun.toLowerCase().trim();
        description = description.toLowerCase().trim();
        String html = "<font face=\"Comfortaa\" size=\"20px\" color=\"#1C0C5B\"><h1>'||\"" + word + "\"||'</h1>";
        html += "<h3><i>" + pronun + "</i></h3>";
        html += "<p>" + description + "</p></font>";
        try {
            PreparedStatement ps = con.prepareStatement("select * from tudienanhviet where word = ?");
            ps.setString(1, word);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return false;
            }
            stmt.executeUpdate("INSERT INTO tudienanhviet (word, html, description, pronounce)"
                    + " VALUES (\"" + word + "\",'" + html + "',\"" + description + "\",\"" + pronun + "\")");
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return true;
    }

    /** Xóa từ. */
    public boolean delete(String word) throws SQLException {
        word = word.trim();
        try {
            PreparedStatement ps = con.prepareStatement("select *from tudienanhviet where word = ?");
            ps.setString(1, word);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                return false;
            }
            stmt.executeUpdate("DELETE FROM tudienanhviet WHERE word =\"" + word + "\";");
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return true;
    }

    /** Sửa từ. */
    public boolean update(String word, String pronun, String description) {
        word = word.toLowerCase().trim();
        pronun = pronun.toLowerCase().trim();
        description = description.toLowerCase().trim();
        try {
            PreparedStatement ps = con.prepareStatement("select *from tudienanhviet where word = ?");
            ps.setString(1, word);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                return false;
            }
            String html =
                    "<font face=\"Comfortaa\" size=\"20px\" color=\"#1C0C5B\"><h1>'||\"" + word + "\"||'</h1>"
                            + "<h3><i>" + pronun + "</i></h3>"
                            + "<p>" + description + "</p></font>";
            stmt.executeUpdate(
                    "UPDATE tudienanhviet SET description "
                            + "= \""
                            + description
                            + "\", html = '"
                            + html
                            + "', pronounce = '"
                            + pronun
                            + "' WHERE word = \""
                            + word
                            + "\";");
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return true;
    }
}
