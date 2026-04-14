import com.aventrix.jnanoid.jnanoid.NanoIdUtils;
import java.io.*;
import java.sql.*;
import javax.swing.JOptionPane;
public class Service {
    private static final String URL="";
    private static final String User="root";
    private static final String password="";
    private static String Firstname;
    private static String Lastname;
    private static String Email;
    private static String Gender;
    public static void Main() throws SQLException{
            Connection conn=DriverManager.getConnection(URL,User,password);
            System.out.println("Connection successful");
            String id = JOptionPane.showInputDialog("Enter your ID");
            Aunthenticate auth=new Aunthenticate();
            auth.validate_user(conn,id);
            switch(user_preference()) {
                case 1:
                    Create_Student(conn);
                    break;
                case 2:
                    Get_Student(conn);
                    break;
                case 3:
                    Update_Student(conn);
                    break;
                case 4:
                    Delete_Student(conn);
                    break;
                case 5:
                    Upload_CSV(conn);
                    break;
                default:
                    JOptionPane.showMessageDialog(null,"Invalid Input Range(1-5)");

            }
    }
    private static int user_preference(){
        int response=Integer.parseInt(JOptionPane.showInputDialog("1. Create New Student"+"\n"+
                "2.Get Student Info"+"\n"+"3.Update Student Info"+
                "\n"+"4.Delete Student info"+"\n"+"5.Upload CSV "));
        return response;
    }
    private static void Create_Student(Connection conn) throws SQLException {
        Firstname=JOptionPane.showInputDialog("Enter your firstname");
        Lastname=JOptionPane.showInputDialog("Enter your Last name");
        Email=JOptionPane.showInputDialog("Enter your Email");
        Gender=JOptionPane.showInputDialog("Enter your Gender");
        String sql="INSERT INTO enrolled_students(id,First_Name,Last_Name,Email,Gender) VALUES (?,?,?,?,?)";
        PreparedStatement statement=conn.prepareStatement(sql);
        statement.setString(1,NanoIdUtils.randomNanoId());
        statement.setString(2,Firstname);
        statement.setString(3,Lastname);
        statement.setString(4,Email);
        statement.setString(5,Gender);
        if(statement.executeUpdate()>0)
            JOptionPane.showMessageDialog(null,"Student Added successfully");
        else
            JOptionPane.showMessageDialog(null,"Failed to add student");
        conn.close();
    }
    private static void Get_Student(Connection conn) throws SQLException {
        String id = JOptionPane.showInputDialog("Enter your ID");
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

    }
    private static void Update_Student( Connection conn) throws SQLException {
        int response= Integer.parseInt(JOptionPane.showInputDialog(null,"1.Change UserName"+"\n"+"2.Change Email"+"\n"+"3.Change Gender"));
        if(response==1){
            String sql="UPDATE student.enrolled_students SET First_Name=?,Last_Name=? Where id=?";
            PreparedStatement stmt= conn.prepareStatement(sql);
            String id=JOptionPane.showInputDialog("Enter ID");
            Lastname= JOptionPane.showInputDialog("Enter your Preferred Last name");
            Firstname=JOptionPane.showInputDialog("Enter your Preferred First name");
            stmt.setString(1,Firstname);
            stmt.setString(2,Lastname);
            stmt.setString(3,id);
            if(stmt.executeUpdate()>0)
                JOptionPane.showMessageDialog(null,"Student Name Updated successfully");
            else
                JOptionPane.showMessageDialog(null,"Failed to update card");
        }else if(response==2) {
            String id =JOptionPane.showInputDialog("Enter ID");
            Email = JOptionPane.showInputDialog("Enter your Preferred Email");
            String sql = "UPDATE student.enrolled_students SET Email=? Where id=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, Email);
            stmt.setString(2, id);
            if(stmt.execute())
                JOptionPane.showMessageDialog(null,"Student Email successfully");
            else
                JOptionPane.showMessageDialog(null," Failed to update email");
        }else if(response==3){
            String id=JOptionPane.showInputDialog("Enter ID");
            Gender= JOptionPane.showInputDialog("Enter your Gender");
            String sql="UPDATE student.enrolled_students SET Gender=? Where id=?";
            PreparedStatement stmt= conn.prepareStatement(sql);
            stmt.setString(1,Gender);
            stmt.setString(2,id);
            if(stmt.execute())
                JOptionPane.showMessageDialog(null,"Student Gender Updated successfully");
            else
                JOptionPane.showMessageDialog(null,"Failed to Update Gender");
        }
        else
            JOptionPane.showMessageDialog(null,"Invalid input allowed entry 1-3");
        conn.close();
    }
    private static void Delete_Student(Connection conn) throws SQLException{
       String id=JOptionPane.showInputDialog("Enter ID");
        String sql="DELETE FROM student.enrolled_students Where id=?";
        PreparedStatement stmt=conn.prepareStatement(sql);
        stmt.setString(1,id);
        if(stmt.executeUpdate()>0) { JOptionPane.showMessageDialog(null,"Student Deleted successfully");}
        else{JOptionPane.showMessageDialog(null,"Failed to delete students");}
        conn.close();
    }
    private static void Upload_CSV(Connection conn) throws SQLException {
        String filepath = "C://Users//HP//Downloads//MOCK_DATA.csv";
        String sql = "INSERT INTO student.enrolled_students(id,First_Name,Last_Name,Email,Gender) Values (?,?,?,?,?)";
        try( BufferedReader reader = new BufferedReader(new FileReader(filepath));
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
}
