import com.aventrix.jnanoid.jnanoid.NanoIdUtils;
import java.io.*;
import java.sql.*;
import javax.swing.JOptionPane;
public class Service {
    private static final String URL = "";
    private static final String User = "root";
    private static final String password = "";
    private static String Firstname;
    private static String Lastname;
    private static String Email;
    private static String Gender;
    static logic_class logic;
    public Service() {

        logic = new logic_class(logic_class.conn);
    }
    public static void Main() throws SQLException {
        Connection conn = DriverManager.getConnection(URL, User, password);
        System.out.println("Connection successful");
        String id = JOptionPane.showInputDialog("Enter your ID");
        Aunthenticate auth = new Aunthenticate();
        auth.welcome_user(conn, id);
        switch (user_preference()) {
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
                JOptionPane.showMessageDialog(null, "Invalid Input Range(1-5)");

        }
    }
    private static int user_preference() {
        int response = Integer.parseInt(JOptionPane.showInputDialog("1. Create New Student" + "\n" +
                "2.Get Student Info" + "\n" + "3.Update Student Info" +
                "\n" + "4.Delete Student info" + "\n" + "5.Upload CSV "));
        return response;
    }
    public static void Create_Student(Connection conn) throws SQLException {
        Firstname = JOptionPane.showInputDialog("Enter your firstname");
        Lastname = JOptionPane.showInputDialog("Enter your Last name");
        Email = JOptionPane.showInputDialog("Enter your Email");
        Gender = JOptionPane.showInputDialog("Enter your Gender");
        logic.create_user(Firstname, Lastname, Email, Gender);
    }
    private static void Get_Student(Connection conn) throws SQLException {
        String id = JOptionPane.showInputDialog("Enter your ID");
        logic.get_user(id);
    }
    private static void Update_Student(Connection conn) throws SQLException {
        int response = Integer.parseInt(JOptionPane.showInputDialog(null, "1.Change UserName" + "\n" + "2.Change Email" + "\n" + "3.Change Gender"));
        if (response == 1) {
            String id = JOptionPane.showInputDialog("Enter ID");
            Lastname = JOptionPane.showInputDialog("Enter your Preferred Last name");
            Firstname = JOptionPane.showInputDialog("Enter your Preferred First name");
            logic.Update_Student_Name(conn, id, Firstname, Lastname);
        } else if (response == 2) {
            String id = JOptionPane.showInputDialog("Enter ID");
            Email = JOptionPane.showInputDialog("Enter your Preferred Email");
            logic.Update_Student_Email(conn, id, Email);
        } else if (response == 3) {
            String id = JOptionPane.showInputDialog("Enter ID");
            Gender = JOptionPane.showInputDialog("Enter your Gender");
            logic.Update_Student_Gender(conn, id, Gender);
        } else
            JOptionPane.showMessageDialog(null, "Invalid input allowed entry 1-3");
        conn.close();
    }
    private static void Delete_Student(Connection conn) throws SQLException {
        String id = JOptionPane.showInputDialog("Enter ID");
        logic.Delete_student(conn, id);
    }
    private static void Upload_CSV(Connection conn) throws SQLException {
        String filepath = "C://Users//HP//Downloads//MOCK_DATA.csv";
        logic.Upload_file(conn, filepath);
    }
    public static void Ask_credentials(Connection conn) throws SQLException {
        String username=JOptionPane.showInputDialog(null,"Enter Username");
        String password=JOptionPane.showInputDialog(null,"Enter Password");
        Aunthenticate auth=new Aunthenticate();
        auth.verify_user(conn,username,password);
    }
}
