import java.sql.*;
import java.util.Scanner;

public class MenuCRUD {

    static final String URL = "jdbc:mysql://localhost:3306/testdb";
    static final String USER = "root";
    static final String PASS = "password";

    public static void main(String[] args) throws Exception {

        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection(URL, USER, PASS);

        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n===== STUDENT MANAGEMENT SYSTEM =====");
            System.out.println("1. Insert Student");
            System.out.println("2. View Students");
            System.out.println("3. Update Student");
            System.out.println("4. Delete Student");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            int choice = sc.nextInt();

            switch (choice) {

                case 1:
                    insertStudent(con, sc);
                    break;

                case 2:
                    viewStudents(con);
                    break;

                case 3:
                    updateStudent(con, sc);
                    break;

                case 4:
                    deleteStudent(con, sc);
                    break;

                case 5:
                    con.close();
                    System.out.println("Exited Successfully!");
                    System.exit(0);

                default:
                    System.out.println("Invalid Choice!");
            }
        }
    }

    // INSERT
    public static void insertStudent(Connection con, Scanner sc) throws Exception {
        System.out.print("Enter ID: ");
        int id = sc.nextInt();
        sc.nextLine();

        System.out.print("Enter Name: ");
        String name = sc.nextLine();

        System.out.print("Enter Marks: ");
        int marks = sc.nextInt();

        String sql = "INSERT INTO students VALUES (?, ?, ?)";
        PreparedStatement pst = con.prepareStatement(sql);

        pst.setInt(1, id);
        pst.setString(2, name);
        pst.setInt(3, marks);

        pst.executeUpdate();
        System.out.println("Student Inserted Successfully!");
    }

    // SELECT
    public static void viewStudents(Connection con) throws Exception {
        String sql = "SELECT * FROM students";
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(sql);

        System.out.println("\nID\tName\tMarks");
        System.out.println("---------------------------");

        while (rs.next()) {
            System.out.println(
                    rs.getInt("id") + "\t" +
                            rs.getString("name") + "\t" +
                            rs.getInt("marks")
            );
        }
    }

    // UPDATE
    public static void updateStudent(Connection con, Scanner sc) throws Exception {
        System.out.print("Enter ID to Update: ");
        int id = sc.nextInt();
        sc.nextLine();

        System.out.print("Enter New Name: ");
        String name = sc.nextLine();

        System.out.print("Enter New Marks: ");
        int marks = sc.nextInt();

        String sql = "UPDATE students SET name=?, marks=? WHERE id=?";
        PreparedStatement pst = con.prepareStatement(sql);

        pst.setString(1, name);
        pst.setInt(2, marks);
        pst.setInt(3, id);

        pst.executeUpdate();
        System.out.println("Student Updated Successfully!");
    }

    // DELETE
    public static void deleteStudent(Connection con, Scanner sc) throws Exception {
        System.out.print("Enter ID to Delete: ");
        int id = sc.nextInt();

        String sql = "DELETE FROM students WHERE id=?";
        PreparedStatement pst = con.prepareStatement(sql);

        pst.setInt(1, id);

        pst.executeUpdate();
        System.out.println("Student Deleted Successfully!");
    }
}
