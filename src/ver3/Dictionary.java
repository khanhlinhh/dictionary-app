package ver3;

import java.util.*;
import java.io.*;
import java.sql.*;

public class Dictionary {
    Hashtable<String, String> words = new Hashtable<>();
    public void add(Word word) {
        words.put(word.getWord_target(), word.getWord_explain());
    }

  public void searchWord(String search) {
    Connection con = null;
    try {
      String url = "jdbc:sqlite:Dictionary.db";
      con = DriverManager.getConnection(url);
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
    PreparedStatement ps = null;
    ResultSet rs = null;
    String sql = "select *from tudienanhviet where word = ?";
    try {
        ps = con.prepareStatement(sql);
        ps.setString(1,search);
        rs = ps.executeQuery();

        int id = rs.getInt(1);
        String meaning = rs.getString(3);
        String pronounce = rs.getString(4);
        int count = 0;
        for (int i = 0; i < pronounce.length(); i++) {
            if (pronounce.charAt(i) == ';' || pronounce.charAt(i) == '"') {
                count++;
            }
        }
        pronounce = pronounce.substring(0,pronounce.length()-count);
        System.out.print(id + "\t" + search + "\t" + pronounce + "\t" + meaning + "\n");
    } catch (SQLException e) {
        e.getMessage();
    } finally {
        try {
            rs.close();
            ps.close();
            con.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    }
}
