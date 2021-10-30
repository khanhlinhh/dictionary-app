package ver3;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.Map;
import java.util.Scanner;

import static java.sql.DriverManager.getConnection;

public class DictionaryManagement {
    public static DatabaseConnection dbConnection = new DatabaseConnection();
    public static String sql = "select *from tudienanhviet where word = ?";
    public static final String dbURL = "jdbc:sqlite:Dictionary.db";
    Dictionary dictionary = new Dictionary();

    public String getMeaning(String word) throws SQLException {
        Connection conn = dbConnection.getDBConnection();
        String meaning;
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1,word);
        ResultSet rs = ps.executeQuery();
        meaning = rs.getString("description");
        conn.close();
        return meaning;
    }

    public String getPronun(String word) throws SQLException {
        Connection con = dbConnection.getDBConnection();
        String pronun;
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1,word);
        ResultSet rs = ps.executeQuery();
        pronun = rs.getString("pronounce");
        con.close();
        return pronun;
    }

    public void insertFromCommandline() {
        Scanner myvar = new Scanner(System.in);
        System.out.print("Số từ bạn muốn nhập: ");
        int num = myvar.nextInt();

        Scanner sc = new Scanner(System.in);
        for (int i = 0; i < num; i++) {
            Word newWord = new Word();
            System.out.print("Từ mới: ");
            newWord.setWord_target(sc.nextLine());
            System.out.print("Nghĩa của từ: ");
            newWord.setWord_explain(sc.nextLine());
            dictionary.add(newWord);
        }
    }

    public void dictionarySuggest() {
        try {
            Connection c = dbConnection.getDBConnection();
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM tudienanhviet LIMIT 1000;");

            System.out.println("Eng\t\tPronounce\t\tPOS\t\tVie");
            while (rs.next()) {
                String target = rs.getString("word");
                String pronounce = rs.getString("pronounce");
                String explain[] = rs.getString("description").split(":", 2);
                String partOfSpeech = explain[0];
                String meaning = explain[1];
                System.out.println(target + "\t\t" + pronounce + "\t\t" + partOfSpeech + "\t\t" + meaning);
            }
            c.close();

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    public void show() {
        System.out.println("No\t\tEnglish\t\t\tVietnamese");
        int index = 1;
        for (Map.Entry<String, String> e : dictionary.words.entrySet())
            System.out.println(index++ + "\t\t" + e.getKey() + "\t\t\t" + e.getValue());
    }

    public void dictionaryLookup(String word, String meaning, String pronounce) {
        try {
            Connection c = getConnection(dbURL);
            String sql = "SELECT * FROM tudienanhviet WHERE word = ?";

            PreparedStatement ps = c.prepareStatement(sql);
            ps.setString(1, word);
            ResultSet rs = ps.executeQuery();

            word = rs.getString(1);
            pronounce = rs.getString("pronounce");
            meaning = rs.getString("description");

            c.close();

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    public ObservableList<String> dictionarySearcher(String wordSearch) {
        ObservableList<String> items = FXCollections.observableArrayList();
        try {
            int n = 0;
            Connection c = dbConnection.getDBConnection();
            Statement stmt = c.createStatement();
            if (!items.isEmpty()) {
                items.clear();
            }
            ResultSet rs = stmt.executeQuery("SELECT * FROM tudienanhviet WHERE word LIKE '" + wordSearch + "%';");
            while (rs.next() && n < 20) {
                String word = rs.getString("word");
                items.add(word);
            }

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return items;
    }

    public void delete() {
        try {
            Connection c = getConnection(dbURL);
            Statement stmt = c.createStatement();

            Scanner myvar = new Scanner(System.in);
            System.out.print("Từ bạn muốn xóa: ");
            String myQuery = myvar.nextLine();

            stmt.executeUpdate("DELETE FROM tudienanhviet WHERE word ='" + myQuery + "';");
            System.out.print("Đã xóa thành công! ");

            c.close();

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    public void update() {
        try {
            Connection c = getConnection(dbURL);
            Statement stmt = c.createStatement();

            Scanner myvar = new Scanner(System.in);
            System.out.print("Từ bạn muốn sửa nghĩa: ");
            String word = myvar.nextLine();
            System.out.print("Nghĩa mới: ");
            String description = myvar.nextLine();

            stmt.executeUpdate("UPDATE tudienanhviet SET description = '" + description + "' WHERE word = '" + word + "';");
            System.out.println("Đã sửa thành công!");

            c.close();

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    public void addNewWord() {
        try {
            Connection c = getConnection(dbURL);
            Statement stmt = c.createStatement();

            Scanner myvar = new Scanner(System.in);
            System.out.print("Từ bạn muốn thêm: ");
            String word = myvar.nextLine();
            System.out.print("Nghĩa của từ đó: ");
            String description = myvar.nextLine();

            stmt.executeUpdate("INSERT INTO tudienanhviet (word, html, description, pronounce)"
                    + " VALUES ('" + word + "',NULL,'" + description + "',NULL);");
            System.out.println("Đã thêm thành công!");
            c.close();

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    public void dictionaryExportToFile() {

    }
}
