import com.aventrix.jnanoid.jnanoid.NanoIdUtils;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
public class logic_class {
    static Connection conn;
    logic_class(Connection conn){
        this.conn=conn;
    }
    public static void create_user(String FirstName,String LastName,String Email,String Gender) throws SQLException{
        String sql="INSERT INTO enrolled_students(id,First_Name,Last_Name,Email,Gender) VALUES (?,?,?,?,?)";
        PreparedStatement statement=conn.prepareStatement(sql);
        statement.setString(1, NanoIdUtils.randomNanoId());
        statement.setString(2,FirstName);
        statement.setString(3,LastName);
        statement.setString(4,Email);
        statement.setString(5,Gender);
        if(statement.executeUpdate()>0)
            JOptionPane.showMessageDialog(null,"Student Added successfully");
        else
            JOptionPane.showMessageDialog(null,"Failed to add student");
        conn.close();
    }
    public static void get_user(String id) throws SQLException{
        String sql = "SELECT * FROM student.enrolled_students WHERE id=?";
        PreparedStatement psmt = conn.prepareStatement(sql);
        psmt.setString(1, id);
        ResultSet rset = psmt.executeQuery();
        rset.next();
        String fname = rset.getNString("First_Name");
        String lname = rset.getNString("Last_Name");
        String email = rset.getNString("Email");
        String gender = rset.getNString("Gender");
        JOptionPane.showMessageDialog(null, "Username: " + lname + " " + fname + "\n" + "Email: " + email + "\n" + " Gender: " +gender);
        conn.close();
    }
    public static void Update_Student_Name(Connection conn, String id, String FirstName, String LastName) throws SQLException{
        String sql="UPDATE student.enrolled_students SET First_Name=?,Last_Name=? Where id=?";
        PreparedStatement stmt= conn.prepareStatement(sql);
        stmt.setString(1,FirstName);
        stmt.setString(2,LastName);
        stmt.setString(3,id);
        if(stmt.executeUpdate()>0)
            JOptionPane.showMessageDialog(null,"Student Name Updated successfully");
        else
            JOptionPane.showMessageDialog(null,"Failed to update card");
        conn.close();
    }
    public static void Update_Student_Email(Connection conn, String id,String Email) throws SQLException{
        String sql = "UPDATE student.enrolled_students SET Email=? Where id=?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, Email);
        stmt.setString(2, id);
        if(stmt.execute())
            JOptionPane.showMessageDialog(null,"Student Email successfully");
        else
            JOptionPane.showMessageDialog(null," Failed to update email");
        conn.close();
    }
    public static void Update_Student_Gender(Connection conn,String id,String Gender) throws SQLException{
        String sql="UPDATE student.enrolled_students SET Gender=? Where id=?";
        PreparedStatement stmt= conn.prepareStatement(sql);
        stmt.setString(1,Gender);
        stmt.setString(2,id);
        if(stmt.execute())
            JOptionPane.showMessageDialog(null,"Student Gender Updated successfully");
        else
            JOptionPane.showMessageDialog(null,"Failed to Update Gender");
    }
    public static void Delete_student(Connection conn,String id)throws SQLException{
        String sql="DELETE FROM student.enrolled_students Where id=?";
        PreparedStatement stmt=conn.prepareStatement(sql);
        stmt.setString(1,id);
        if(stmt.executeUpdate()>0) { JOptionPane.showMessageDialog(null,"Student Deleted successfully");}
        else{JOptionPane.showMessageDialog(null,"Failed to delete students");}
        conn.close();
    }
    public static void Upload_file(Connection conn,String path) throws SQLException{
        String sql = "INSERT INTO student.enrolled_students(id,First_Name,Last_Name,Email,Gender) Values (?,?,?,?,?)";
        try(BufferedReader reader = new BufferedReader(new FileReader(path));
            PreparedStatement stmt = conn.prepareStatement(sql)){
            String line;
            int count = 0;
            int batchSize = 20;
            while ((line=reader.readLine())!= null) {

                if (!line.trim().isEmpty()) {
                    String[] data = line.split(",");
                    stmt.setString(1,NanoIdUtils.randomNanoId());
                    stmt.setString(2, data[0]);
                    stmt.setString(3, data[1]);
                    stmt.setString(4, data[2]);
                    stmt.setString(5, data[3]);
                    stmt.addBatch();
                    count++;
                    if (count % batchSize == 0) {
                        stmt.executeBatch();
                    }
                }

            }
            stmt.executeBatch();
        }
        catch(FileNotFoundException e){
            throw new RuntimeException(e);
        } catch(IOException e){
            throw new RuntimeException(e);
        }
        conn.close();
        JOptionPane.showMessageDialog(null,"Successfully Added Names");
    }
    public static String get_username(Connection conn,String Username)throws SQLException{
        String sql="SELECT * FROM student.profiles_details WHERE username=?";
        PreparedStatement statement=conn.prepareStatement(sql);
        statement.setString(1,Username);
        ResultSet rst=statement.executeQuery();
        String username=rst.getString("username");
        rst.next();
        return username;
    }
    public static String get_userpassword(Connection conn,String Username)throws SQLException{
        String sql="SELECT * FROM student.profiles_details WHERE username=?";
        PreparedStatement statement=conn.prepareStatement(sql);
        statement.setString(1,Username);
        ResultSet rst=statement.executeQuery();
        String password=rst.getString("password");
        rst.next();
        return password;
    }
}
