import java.io.*;
import java.util.*;

public class CRUDFile {

    static final String FILE_NAME = "students.txt";

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n===== FILE CRUD MENU =====");
            System.out.println("1. Insert");
            System.out.println("2. Read All");
            System.out.println("3. Update");
            System.out.println("4. Delete");
            System.out.println("5. Exit");
            System.out.print("Enter choice: ");

            int choice = sc.nextInt();

            if (choice == 1) insertStudent(sc);
            else if (choice == 2) readStudents();
            else if (choice == 3) updateStudent(sc);
            else if (choice == 4) deleteStudent(sc);
            else break;
        }
    }

    // Create / Insert
    public static void insertStudent(Scanner sc) throws Exception {
        System.out.print("Enter ID: ");
        int id = sc.nextInt();
        System.out.print("Enter Name: ");
        String name = sc.next();
        System.out.print("Enter Marks: ");
        int marks = sc.nextInt();

        BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME, true));
        bw.write(id + "," + name + "," + marks);
        bw.newLine();
        bw.close();

        System.out.println("Record inserted!");
    }

    // Read
    public static void readStudents() throws Exception {
        BufferedReader br = new BufferedReader(new FileReader(FILE_NAME));

        String line;
        System.out.println("\nID\tName\tMarks");
        while ((line = br.readLine()) != null) {
            String[] data = line.split(",");
            System.out.println(data[0] + "\t" + data[1] + "\t" + data[2]);
        }
        br.close();
    }

    // Update
    public static void updateStudent(Scanner sc) throws Exception {
        System.out.print("Enter ID to update: ");
        int id = sc.nextInt();

        File input = new File(FILE_NAME);
        File temp = new File("temp.txt");

        BufferedReader br = new BufferedReader(new FileReader(input));
        BufferedWriter bw = new BufferedWriter(new FileWriter(temp));

        String line;
        boolean found = false;

        while ((line = br.readLine()) != null) {
            String[] data = line.split(",");
            if (Integer.parseInt(data[0]) == id) {
                System.out.print("Enter new marks: ");
                int newMarks = sc.nextInt();
                bw.write(id + "," + data[1] + "," + newMarks);
                found = true;
            } else {
                bw.write(line);
            }
            bw.newLine();
        }

        br.close();
        bw.close();

        input.delete();
        temp.renameTo(input);

        if (found)
            System.out.println("Record updated!");
        else
            System.out.println("ID not found.");
    }

    // Delete
    public static void deleteStudent(Scanner sc) throws Exception {
        System.out.print("Enter ID to delete: ");
        int id = sc.nextInt();

        File input = new File(FILE_NAME);
        File temp = new File("temp.txt");

        BufferedReader br = new BufferedReader(new FileReader(input));
        BufferedWriter bw = new BufferedWriter(new FileWriter(temp));

        String line;
        boolean found = false;

        while ((line = br.readLine()) != null) {
            String[] data = line.split(",");
            if (Integer.parseInt(data[0]) == id) {
                found = true; // skip writing this record → delete
            } else {
                bw.write(line);
                bw.newLine();
            }
        }

        br.close();
        bw.close();

        input.delete();
        temp.renameTo(input);

        if (found)
            System.out.println("Record deleted!");
        else
            System.out.println("ID not found.");
    }
}
