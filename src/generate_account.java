import java.security.SecureRandom;
import java.sql.*;
import com.password4j.Password;
import com.password4j.Hash;
public class generate_account {
 private Connection conn;
 private SecureRandom rand=new SecureRandom();
 public generate_account(Connection conn){
     this.conn=conn;
 }
 public void generateAccount()throws SQLException{
     String sql="SELECT id,First_Name FROM student.enrolled_students ";
     String sql2="INSERT INTO student.profiles_details(id,username,password) VALUES (?,?,?)";
     Statement stmt=conn.createStatement();
     ResultSet rst=stmt.executeQuery(sql);
     PreparedStatement pstmt=conn.prepareStatement(sql2);
     conn.setAutoCommit(false);
     while(rst.next()){
         String id=rst.getString("id");
         String Firstname=rst.getString("First_Name");
         String username=id+Firstname;
         String password="pass"+(rand.nextInt(9000)+1000);
         Hash hash=Password.hash(password).withBcrypt();
         String hash_pass=hash.getResult();
         pstmt.setString(1,id);
         pstmt.setString(2,username);
         pstmt.setString(3,hash_pass);
         pstmt.addBatch();
     }
     pstmt.executeBatch();
     conn.commit();
     System.out.println("accounts created");
 }
}
