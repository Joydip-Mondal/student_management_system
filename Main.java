import java.io.*;
import java.util.*;

class Student implements Serializable {
    private static final long serialVersionUID = 1L;
    String id, name;
    int age;
    
    Student(String id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public String toString() {
        return "ID: " + id + ", Name: " + name + ", Age: " + age;
    }
}

class StudentManagement {
    private static final String FILE_NAME = "students.dat";
    private List<Student> students = new ArrayList<>();

    StudentManagement() {
        loadStudents();
    }

    void addStudent(String id, String name, int age) {
        students.add(new Student(id, name, age));
        saveStudents();
    }

    void viewStudents() {
        if (students.isEmpty()) {
            System.out.println("No students found.");
            return;
        }
        students.forEach(System.out::println);
    }

    void searchStudent(String id) {
        for (Student s : students) {
            if (s.id.equals(id)) {
                System.out.println(s);
                return;
            }
        }
        System.out.println("Student not found.");
    }

    void deleteStudent(String id) {
        students.removeIf(s -> s.id.equals(id));
        saveStudents();
    }

    private void saveStudents() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            out.writeObject(students);
        } catch (IOException e) {
            System.out.println("Error saving students.");
        }
    }

    private void loadStudents() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            students = (List<Student>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            students = new ArrayList<>();
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        StudentManagement sm = new StudentManagement();
        
        while (true) {
            System.out.println("1. Add Student\n2. View Students\n3. Search Student\n4. Delete Student\n5. Exit");
            System.out.print("Choose an option: ");
            int choice = sc.nextInt();
            sc.nextLine();
            
            switch (choice) {
                case 1:
                    System.out.print("Enter ID: ");
                    String id = sc.nextLine();
                    System.out.print("Enter Name: ");
                    String name = sc.nextLine();
                    System.out.print("Enter Age: ");
                    int age = sc.nextInt();
                    sm.addStudent(id, name, age);
                    break;
                case 2:
                    sm.viewStudents();
                    break;
                case 3:
                    System.out.print("Enter ID to search: ");
                    String searchId = sc.nextLine();
                    sm.searchStudent(searchId);
                    break;
                case 4:
                    System.out.print("Enter ID to delete: ");
                    String deleteId = sc.nextLine();
                    sm.deleteStudent(deleteId);
                    break;
                case 5:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
}
