package com.archisacadeny;

import com.archisacadeny.config.DataBaseConfig;
import com.archisacadeny.config.DataBaseConnectorConfig;
import com.archisacadeny.course.Course;
import com.archisacadeny.course.CourseRepository;
import com.archisacadeny.course.CourseService;
import com.archisacadeny.instructor.Instructor;
import com.archisacadeny.instructor.InstructorRepository;
import com.archisacadeny.instructor.InstructorService;
import com.archisacadeny.person.Person;
import com.archisacadeny.person.PersonRepository;
import com.archisacadeny.person.PersonService;
import com.archisacadeny.student.CourseStudentMapper;
import com.archisacadeny.student.Student;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws ParseException {

        DataBaseConnectorConfig.setConnection();
        CourseService s = new CourseService(new CourseRepository());
//        Course course = new Course();
//        Instructor i = new Instructor();
//        i.setId(1);
//        course.setCourseName("Bootstrap");
//        course.setCourseNumber("105");
//        course.setInstructor(i);
//        course.setDepartment("CSS");
//        course.setCredits(5);
//        course.setMaxStudents(5);
//        course.setEnrolledStudents(new ArrayList<>());
////                Course course = new Course();
////                course.setId(2);
//        s.update(course.getCourseNumber(),course);
//          s.getTotalCreditAmount(1);
//            s.calculateLetterGradeForStudent(1,1);
//        s.getCoursesByInstructorId(1);
//        s.getStudentCountForCourse(3);
//        s.getAllCourses();
//        s.listPopularCourses(1);
//        s.generateCourseReport(4);
//        s.calculateInstructorCoursesAttendanceRate(1);
    }
}
