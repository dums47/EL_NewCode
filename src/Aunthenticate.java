import java.sql.*;
import com.password4j.Hash;
import com.password4j.Password;
import javax.swing.JOptionPane;
public class Aunthenticate {
    static int response;

    public static void validate_user(Connection conn,String id) throws SQLException{
        String sql="SELECT COUNT(*) FROM student.enrolled_students WHERE id=?";
        String sql2="SELECT * FROM student.enrolled_students WHERE id=?";
        PreparedStatement stmt=conn.prepareStatement(sql);
        stmt.setString(1,id);
        ResultSet result=stmt.executeQuery();
        if(result.next()){
            int total=result.getInt(1);
            if(total>0){
                PreparedStatement statement=conn.prepareStatement(sql2);
                statement.setString(1,id);
                ResultSet rst=statement.executeQuery();
                rst.next();
                String Fname=rst.getNString("First_Name");
                String Lname=rst.getNString("Last_Name");
                JOptionPane.showMessageDialog(null,"Welcome "+Lname+" "+Fname);

            }else
                JOptionPane.showMessageDialog(null,"Student Not Found");
        }
    }
    public static void verify_user(Connection conn, String Username,String UPassword) throws SQLException{
        Hash hash_password=Password.hash(UPassword).withBcrypt();
        logic_class logic=new logic_class(conn);
        String username=logic.get_username(conn,Username);
        String password=logic.get_userpassword(conn,Username);
        boolean nameMatch=username==Username;
        boolean passwordMatch=(hash_password.getResult()).equals(password);
        if(nameMatch&&passwordMatch)
            JOptionPane.showMessageDialog(null,"Welcome Back");
        else
            JOptionPane.showMessageDialog(null,"Credentials Mismatch");
        }
    }

