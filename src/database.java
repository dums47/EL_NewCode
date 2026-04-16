import java.sql.*;
public class database {
    public static void main(String[] args){
     User user=new User();
    try {
        Connect_database connection=new Connect_database();
        logic_class logic=new logic_class(connection.connect());
         user.ask_user();

     } catch (SQLException e) {
         throw new RuntimeException(e);
     }
      /*  Connect_database connection=new Connect_database();
        try {
            generate_account account = new generate_account(connection.connect());
            account.generateAccount();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }*/
    }
    }
