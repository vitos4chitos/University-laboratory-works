import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Test {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5432/studs";
        Properties props = new Properties();
        try {
            Connection conn = DriverManager.getConnection(url, "s284691", "eyv368");
            System.out.println("dbTestConnection!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
