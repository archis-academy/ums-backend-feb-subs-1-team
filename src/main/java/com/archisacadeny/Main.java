package com.archisacadeny;
import com.archisacadeny.config.DataBaseConfig;
import com.archisacadeny.config.DataBaseConnectorConfig;
import com.archisacadeny.course.Course;
//import com.archisacadeny.course.CourseRepository;
import com.archisacadeny.instructor.Instructor;
import com.archisacadeny.instructor.InstructorRepository;
import com.archisacadeny.instructor.InstructorService;
import com.archisacadeny.person.Person;
import com.archisacadeny.person.PersonRepository;
import com.archisacadeny.person.PersonService;
import java.util.List;

public class Main {
    public static void main(String[] args) {

            DataBaseConnectorConfig.getConnection();

            InstructorRepository instructorRepository = new InstructorRepository();
            InstructorService instructorService = new InstructorService(instructorRepository);

            Instructor instructor = new Instructor();
            instructor.setFullName("Nehir Çakılcı");
            instructor.setId(1);
            instructor.setNumber("123456789");
            instructor.setEmail("nehir@gmail.com");
            instructor.setPassword("12345");

            Instructor newInstructor = new Instructor();
            newInstructor.setId(2);
            newInstructor.setFullName("example");
            newInstructor.setNumber("123456789");
            newInstructor.setEmail("example@gmail.com");
            newInstructor.setPassword("12345");

           instructorRepository.save(instructor);
           instructorService.createInstructor(newInstructor);

            List<Instructor> instructors = instructorService.listAllInstructors();
            for (Instructor instructorr : instructors) {
                System.out.println("Instructor Name: " + instructorr.getFullName());
                System.out.println("Instructor Number: " + instructorr.getNumber());
                System.out.println("Instructor Email: " + instructorr.getEmail());
                System.out.println("Courses: instructorr.getCourses()");
                System.out.println();
            }




    }
}
