import java.sql.*;

public class database {
    public static void main(String[] args){
     /*User user=new User();
    try {
         user.ask_user();

     } catch (SQLException e) {
         throw new RuntimeException(e);
     }*/
        Connect_database connection=new Connect_database();
        try {
            generate_account account = new generate_account(connection.connect());
            account.generateAccount();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    }
