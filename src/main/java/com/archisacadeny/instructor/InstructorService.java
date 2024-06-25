package com.archisacadeny.instructor;

import com.archisacadeny.course.Course;
import com.archisacadeny.course.CourseRepository;

import java.util.List;

public class InstructorService {
    private final InstructorRepository instructorRepository;
    private final CourseRepository courseRepository;

    public InstructorService(InstructorRepository instructorRepository, CourseRepository courseRepository) {
        this.instructorRepository = instructorRepository;
        this.courseRepository = courseRepository;
    }

    public Instructor createInstructor(Instructor instructor) {
        if(instructor== null){
            throw new RuntimeException("instructor is null");
        }
        return instructorRepository.save(instructor);
    }

    public List<Instructor> listAllInstructors() {
        return instructorRepository.listAllInstructors();
    }

    public Instructor createInstructor(long id, String instructorNumber, String fullName, String email, String password){

        Instructor instructor = new Instructor();
        instructor.setId(id);
        instructor.setNumber(instructorNumber);
        instructor.setFullName(fullName);
        instructor.setEmail(email);
        instructor.setPassword(password);

        return instructorRepository.save(instructor);
    }

    public void viewInstructorTaughtCourses(long instructorId) {
        Instructor instructor = null ;
        List<Course> courses = courseRepository.getCoursesByInstructorId(instructorId); //burada hata alıyorum repository yazan yerde

        for (Course course : courses) {
            System.out.println("Course Name: " + course.getCourseName());
            System.out.println("Course Number: " + course.getCourseNumber());
            System.out.println("Credits: " + course.getCredits());
            System.out.println("Department: " + course.getDepartment());
            System.out.println("Max Students: " + course.getMaxStudents());
            System.out.println("Instructor: " + course.getInstructor().getFullName());
            System.out.println();
        }

    }
}
