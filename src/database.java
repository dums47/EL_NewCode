import java.sql.*;

public class database {
    public static void main(String[] args){
     /*   Service object=new Service();
        try {
            object.Main();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }*/
        final String URL="jdbc:mysql://localhost:3306/student";
        final String User="root";
        final String password="el0rm@123$";
        try {
            Connection conn = DriverManager.getConnection(URL, User, password);
            generate_account account = new generate_account(conn);
            account.generateAccount();

        }catch (SQLException e){
            e.printStackTrace();
        }catch (RuntimeException e){
            e.printStackTrace();
        }

    }
    }
