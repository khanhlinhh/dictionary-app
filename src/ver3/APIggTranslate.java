package ver3;

import java.io.*;
import java.net.*;
import java.sql.*;
import java.util.*;

import static java.sql.DriverManager.getConnection;

public class APIggTranslate {
    public static final String dbURL = "jdbc:sqlite:Language.db";

    public String[] language() throws SQLException {
        String[] list = new String[187];
        int sz = 0;
        Connection c = getConnection(dbURL);
        Statement stmt = c.createStatement();

        ResultSet rs = stmt.executeQuery("SELECT * FROM Language;");

        while (rs.next()) {
            list[sz++] = rs.getString("Language");
        }
        return list;
    }

    public String translate(String langFrom, String langTo, String text) throws IOException, SQLException {
        Connection c = getConnection(dbURL);
        String sql = "SELECT * FROM Language WHERE Language = ?";

        PreparedStatement ps = c.prepareStatement(sql);
        ps.setString(1, langFrom);
        ResultSet rs = ps.executeQuery();
        String idFrom = rs.getString(2);

        ps.setString(1, langTo);
        rs = ps.executeQuery();
        String idTo = rs.getString(2);

        String urlStr = "https://script.google.com/macros/s/AKfycbzneZ3gK8lm1Sn1G2Z9utN23z23cbDVRnaHH7EgdlRGBPhOaEU/exec" +
                "?q=" + URLEncoder.encode(text, "UTF-8") +
                "&target=" + idTo +
                "&source=" + idFrom;
        URL url = new URL(urlStr);
        StringBuilder response = new StringBuilder();
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestProperty("User-Agent", "Mozilla/5.0");
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        return response.toString();
    }

}