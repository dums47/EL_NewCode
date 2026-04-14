import java.sql.*;
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
}
