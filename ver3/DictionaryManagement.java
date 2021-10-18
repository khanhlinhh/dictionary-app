package ver3;

import java.util.*;
import java.io.*;
import java.sql.*;

import static java.sql.DriverManager.getConnection;

public class DictionaryManagement {
    public static final String dbURL = "jdbc:sqlite:Dictionary.db";
    Dictionary dictionary = new Dictionary();

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

    public void insertFromFile() {
        try {
            Connection c = getConnection(dbURL);
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT word, description FROM tudienanhviet LIMIT 1000;");

            int index = 1;
            System.out.println("NO\tEng\t\t\t\t\tVie");
            while (rs.next()) {
                String target = rs.getString("word");
                String explain = rs.getString("description");
                System.out.println(index++ + "\t" + target + "\t\t\t\t\t" + explain);
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

    public void dictionaryLookup() {
        try {
            Connection c = getConnection(dbURL);

            Scanner myvar = new Scanner(System.in);
            System.out.print("Từ bạn muốn tra cứu: ");
            String word = myvar.nextLine();

            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM tudienanhviet WHERE word ='" + word + "';");

            String explain = rs.getString("description");
            System.out.println("Nghĩa của từ đó là: " + explain);

            c.close();

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    public void dictionarySearcher() {
        try {
            Connection c = getConnection(dbURL);
            Statement stmt = c.createStatement();

            Scanner myvar = new Scanner(System.in);
            System.out.print("Từ bạn muốn tra cứu: ");
            String myQuery = myvar.nextLine();

            ResultSet rs = stmt.executeQuery("SELECT * FROM tudienanhviet WHERE word LIKE '" + myQuery + "%';");

            int index = 1;
            System.out.println("NO\tEng\t\t\t\t\tVie");
            while (rs.next()) {
                String target = rs.getString("word");
                String explain = rs.getString("description");
                System.out.println(index++ + "\t" + target + "\t\t\t\t" + explain);
            }
            c.close();

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
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
            ResultSet rs = stmt.executeQuery("SELECT word, description FROM tudienanhviet LIMIT 1000;");
            int index = 1;
            System.out.println("NO\tEng\t\t\t\t\tVie");
            while (rs.next()) {
                String target = rs.getString("word");
                String explain = rs.getString("description");
                System.out.println(index++ + "\t" + target + "\t\t\t\t\t" + explain);
            }
            c.close();

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    public void dictionaryExportToFile() {

    }
}
