package ver3;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    public Connection conn;

    /** Hàm kết nối cơ sở dữ liệu. */
    public Connection getDBConnection() {
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:Dictionary.db");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return this.conn;
    }
}
