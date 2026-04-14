import javax.swing.JOptionPane;
import java.sql.Connection;
import java.sql.SQLException;
public class User {
    public static void ask_user() throws SQLException{
        Connect_database  connection= new Connect_database();
        Connection conn=connection.connect();
        Service serve=new Service();
        int option= Integer.parseInt(JOptionPane.showInputDialog(null,"SELECT ACTION"+"\n"+"1.Login"+"\n"+"2.Create account"));
        if(option==1){
            serve.verify(conn);
        } else if (option ==2) {
            serve.Create_Student(conn);
        }else {
            JOptionPane.showMessageDialog(null,"Invalid Choice");
            ask_user();
        }
    }
}
