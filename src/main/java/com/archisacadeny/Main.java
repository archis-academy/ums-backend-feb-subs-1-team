package com.archisacadeny;

import com.archisacadeny.config.DataBaseConnectorConfig;
import com.archisacadeny.course.CourseRepository;
import com.archisacadeny.course.CourseService;
import com.archisacadeny.instructor.InstructorRepository;
import com.archisacadeny.instructor.InstructorService;
import com.archisacadeny.student.CourseStudentMapper;
import com.archisacadeny.student.Student;
import com.archisacadeny.student.StudentRepository;
import com.archisacadeny.student.StudentService;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Scanner;

public class Main {

    private static CourseRepository courseRepository = new CourseRepository();
    private static CourseStudentMapper courseStudentMapper = new CourseStudentMapper();
    private static CourseService courseService = new CourseService(courseRepository, courseStudentMapper);
    private static InstructorService instructorService = new InstructorService(new InstructorRepository(), courseRepository);
    private static StudentService studentService = new StudentService(new StudentRepository());


    public static void main(String[] args) throws ParseException {

        DataBaseConnectorConfig.setConnection();

        Scanner scanner = new Scanner(System.in);

        studentService.createStudentTable();
        instructorService.createInstructorTable();
        courseService.createCourseTable();
        CourseStudentMapper.createCourseStudentTable();

        mainMenu(scanner, studentService, courseService, instructorService);
    }

    private static void mainMenu(Scanner scanner, StudentService studentService, CourseService courseService, InstructorService instructorService) {
        System.out.println("Welcome to our University Management Panel!");

        int choice;
        do {
            System.out.println("<---------------------------------->");
            System.out.println("1. Student Operations");
            System.out.println("2. Instructor Operations");
            System.out.println("3. Course Operations");
            System.out.println("4. Exit");
            System.out.println("<---------------------------------->");
            System.out.print("Your choice: ");

            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    studentMenu(scanner, studentService);
                    break;
                case 2:
                    instructorMenu(scanner, instructorService);
                    break;
                case 3:
                    courseMenu(scanner, courseService);
                    break;
                case 4:
                    System.out.println("Exit the panel...");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice!");
            }
        } while (true);
    }

    private static void studentMenu(Scanner scanner, StudentService studentService) {

        System.out.println("Student Operations!");
        System.out.println("<------------------------------------------>");

        boolean backToMenu = false;

        while (!backToMenu) {
            System.out.println("1. Student Registration");
            System.out.println("2. Student Deletion");
            System.out.println("3. Create and print student success report");
            System.out.println("4. Course recommendation for student");
            System.out.println("0. Main menu");
            System.out.print("Your choice: ");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    Student student = new Student();

                    System.out.println("Enter the student's name: ");
                    scanner.nextLine();
                    String studentFullName = scanner.nextLine();
                    student.setFullName(studentFullName);

                    System.out.println("Enter the student's mail: ");
                    String studentMail = scanner.nextLine();
                    student.setEmail(studentMail);

                    System.out.println("Enter the student's gender: ");
                    String studentGender = scanner.nextLine();
                    student.setGender(studentGender);

                    System.out.println("Enter the student's identityNo: ");
                    String studentIdentity = scanner.nextLine();
                    student.setIdentityNo(studentIdentity);

                    Timestamp enrollmentDate = new Timestamp(System.currentTimeMillis());
                    student.setEnrollmentDate(enrollmentDate);
                    student.setTotalCreditCount(10);
                    student.setYearOfStudy(enrollmentDate.getYear());

                    studentService.createStudent(student);
                    break;
                case 2:
                    System.out.println("Enter the ID of the student to be deleted: ");
                    int deletionStudentId = scanner.nextInt();
                    studentService.deleteStudent(deletionStudentId);
                    break;
                case 3:
                    System.out.println("Enter the ID of the student for whom the report will be generated: ");
                    int studentIdForReport = scanner.nextInt();
                    studentService.printStudentAchievementReport(studentIdForReport);
                    break;
                case 4:
                    System.out.println("Enter the ID of the student to be recommended course: ");
                    int studentIdForRecommendedCourse = scanner.nextInt();
                    studentService.listRecommendedCoursesForStudent(studentIdForRecommendedCourse);
                    break;
                case 0:
                    System.out.println("Returning to the main menu...");
                    backToMenu = true;
                    break;
                default:
                    System.out.println("Wrong choice!");
                    break;
            }
        }
    }

    private static void instructorMenu(Scanner scanner, InstructorService instructorService) {

        System.out.println("Instructor Operations!");
        System.out.println("<------------------------------------------>");

        boolean backToMenu = false;

        while (!backToMenu) {
            System.out.println("1. Instructor Registration");
            System.out.println("2. See all our instructors:");
            System.out.println("3. Create and print student success report");
            System.out.println("4. Course recommendation for student");
            System.out.println("0. Main menu");
            System.out.print("Your choice: ");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:

                    break;
                case 2:

                    break;
                case 3:

                    break;
                case 4:

                    break;
                case 0:
                    System.out.println("Returning to the main menu...");
                    backToMenu = true;
                    break;
                default:
                    System.out.println("Wrong choice!");
                    break;
            }
        }
    }

    private static void courseMenu(Scanner scanner, CourseService courseService) {

        System.out.println("Course Operations!");
        System.out.println("<------------------------------------------>");

        boolean backToMenu = false;

        while (!backToMenu) {
            System.out.println("1. Course Registration");
            System.out.println("2. See all our courses:");
            System.out.println("3. ");
            System.out.println("4. ");
            System.out.println("0. Main menu");
            System.out.print("Your choice: ");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:

                    break;
                case 2:

                    break;
                case 3:

                    break;
                case 4:

                    break;
                case 0:
                    System.out.println("Returning to the main menu...");
                    backToMenu = true;
                    break;
                default:
                    System.out.println("Wrong choice!");
                    break;
            }
        }

    }
}
