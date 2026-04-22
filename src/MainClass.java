import java.sql.*;
public class MainClass {
    public static void main(String[] args) {
        User user = new User();
        try {
            user.ask_user();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}