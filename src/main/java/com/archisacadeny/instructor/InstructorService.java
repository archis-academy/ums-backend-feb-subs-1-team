package com.archisacadeny.instructor;

import com.archisacadeny.course.Course;

import java.util.List;

public class InstructorService {
    private final InstructorRepository instructorRepository;

    public InstructorService(InstructorRepository instructorRepository) {
        this.instructorRepository = instructorRepository;
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
        List<Course> courses = CourseRepository.getCoursesByInstructorId(instructorId); //burada hata alıyorum repository yazan yerde

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
