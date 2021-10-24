package ver3;

import java.io.*;
import java.net.*;
import java.sql.*;
import java.util.*;

import static java.sql.DriverManager.getConnection;

public class APIggTranslate {
    /**public static final String dbURL = "jdbc:sqlite:Language.db";

    private static String translate(String langFrom, String langTo, String text) throws IOException {
        Connection c = getConnection(dbURL);
        String sql = "SELECT * FROM Language WHERE Language = ?";

        PreparedStatement ps = c.prepareStatement(sql);
        ps.setString(1, langFrom);
        ResultSet rs = ps.executeQuery();
        String idFrom = rs.getString(2);

        ps.setString(1, langTo);
        ResultSet rs = ps.executeQuery();
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

    public static void main(String[] args) throws IOException {
        Scanner myvar = new Scanner(System.in);
        System.out.print("Nhập đoạn văn bản bạn muốn dịch:");
        String target = myvar.nextLine();
        System.out.print("Bạn muốn dịch từ ngôn ngữ: ");
        String langFrom = myvar.nextLine();
        System.out.print("Bạn muốn dịch sang ngôn ngữ: ");
        String langTo = myvar.nextLine();
        System.out.println("Translated text: " + translate(langFrom, langTo, target));
    }
*/
}