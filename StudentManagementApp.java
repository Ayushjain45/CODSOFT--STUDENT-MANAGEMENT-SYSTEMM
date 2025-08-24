

import java.io.*;
import java.util.*;


class Student implements Serializable {
    private int rollNumber;
    private String name;
    private String grade;

    public Student(int rollNumber, String name, String grade) {
        this.rollNumber = rollNumber;
        this.name = name;
        this.grade = grade;
    }

    public int getRollNumber() {
        return rollNumber;
    }

    public void setName(String name) {
        if (name != null && !name.trim().isEmpty()) {
            this.name = name;
        }
    }

    public void setGrade(String grade) {
        if (grade != null && !grade.trim().isEmpty()) {
            this.grade = grade;
        }
    }

    @Override
    public String toString() {
        return "Roll: " + rollNumber + " | Name: " + name + " | Grade: " + grade;
    }
}


class StudentManagementSystem {
    private List<Student> students;
    
    private final String FILE_NAME = "AyushJain_2337185_students.dat";

    public StudentManagementSystem() {
        students = new ArrayList<>();
        loadFromFile();
    }

    
    public void addStudent(Student s) {
        students.add(s);
        saveToFile();
    }

    
    public boolean removeStudent(int roll) {
        Iterator<Student> it = students.iterator();
        while (it.hasNext()) {
            if (it.next().getRollNumber() == roll) {
                it.remove();
                saveToFile();
                return true;
            }
        }
        return false;
    }

    
    public Student searchStudent(int roll) {
        for (Student s : students) {
            if (s.getRollNumber() == roll) {
                return s;
            }
        }
        return null;
    }

    
    public void displayStudents() {
        if (students.isEmpty()) {
            System.out.println("No students found.");
        } else {
            students.forEach(System.out::println);
        }
    }

    
    private void saveToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(students);
        } catch (IOException e) {
            System.out.println("Error saving data: " + e.getMessage());
        }
    }

    
    @SuppressWarnings("unchecked")
    private void loadFromFile() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            students = (List<Student>) ois.readObject();
        } catch (Exception e) {
            students = new ArrayList<>();
        }
    }
}


public class StudentManagementApp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        StudentManagementSystem sms = new StudentManagementSystem();

        System.out.println("==============================================");
        System.out.println("   Welcome to Student Management System");
        System.out.println("   Developed by: Ayush Jain");
        System.out.println("   Roll No: 2337185");
        System.out.println("==============================================");

        while (true) {
            System.out.println("\n--- Menu ---");
            System.out.println("1. Add Student");
            System.out.println("2. Remove Student");
            System.out.println("3. Search Student");
            System.out.println("4. Display All Students");
            System.out.println("5. Edit Student");
            System.out.println("6. Exit");
            System.out.print("Enter choice: ");

            int choice;
            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (Exception e) {
                System.out.println("Invalid input! Enter a number.");
                continue;
            }

            switch (choice) {
                case 1:
                    System.out.print("Enter Roll No: ");
                    int roll = Integer.parseInt(sc.nextLine());
                    System.out.print("Enter Name: ");
                    String name = sc.nextLine();
                    System.out.print("Enter Grade: ");
                    String grade = sc.nextLine();

                    if (name.isEmpty() || grade.isEmpty()) {
                        System.out.println("Invalid input! Fields cannot be empty.");
                        break;
                    }
                    sms.addStudent(new Student(roll, name, grade));
                    System.out.println("Student added successfully!");
                    break;

                case 2:
                    System.out.print("Enter Roll No to remove: ");
                    int r = Integer.parseInt(sc.nextLine());
                    if (sms.removeStudent(r))
                        System.out.println("Student removed.");
                    else
                        System.out.println("Student not found!");
                    break;

                case 3:
                    System.out.print("Enter Roll No to search: ");
                    int sr = Integer.parseInt(sc.nextLine());
                    Student found = sms.searchStudent(sr);
                    if (found != null)
                        System.out.println("Found: " + found);
                    else
                        System.out.println("Student not found.");
                    break;

                case 4:
                    sms.displayStudents();
                    break;

                case 5:
                    System.out.print("Enter Roll No to edit: ");
                    int er = Integer.parseInt(sc.nextLine());
                    Student st = sms.searchStudent(er);
                    if (st != null) {
                        System.out.print("Enter New Name (leave blank to keep): ");
                        String newName = sc.nextLine();
                        if (!newName.isEmpty()) st.setName(newName);

                        System.out.print("Enter New Grade (leave blank to keep): ");
                        String newGrade = sc.nextLine();
                        if (!newGrade.isEmpty()) st.setGrade(newGrade);

                        System.out.println("Student updated successfully!");
                    } else {
                        System.out.println("Student not found.");
                    }
                    break;

                case 6:
                    System.out.println("Exiting program... Goodbye from Ayush Jain (Roll No: 2337185)!");
                    sc.close();
                    return;

                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }
}