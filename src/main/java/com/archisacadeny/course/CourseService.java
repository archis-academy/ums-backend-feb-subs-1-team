package com.archisacadeny.course;


import com.archisacadeny.instructor.Instructor;

import java.util.ArrayList;
import java.util.List;

public class CourseService {
    private final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public Course createCourse(Course course) {
        return courseRepository.save(course);
    }

    public List<Course> getStudentEnrolledCourses(int studentId){
        int[] courseIds = courseRepository.getStudentEnrolledCourses(studentId);
        ArrayList<Course> courses = new ArrayList<>();
        for(int i = 0;i< courseIds.length;i++){
            courses.add(courseRepository.getCourseById(courseIds[i]));
        }
        return courses;
    }

}





