import java.sql.*;
public class Connect_database {
    private static final String URL="jdbc:mysql://localhost:3306/student";
    private static final String Username="root";
    private static final String password="el0rm@123$";
    public static Connection connect() throws SQLException{
        Connection conn=DriverManager.getConnection(URL,Username,password);
        System.out.println("Connection Successful");
        return conn;
    }
}
