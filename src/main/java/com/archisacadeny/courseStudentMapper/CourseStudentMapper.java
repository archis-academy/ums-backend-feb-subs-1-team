package com.archisacadeny.courseStudentMapper;

import com.archisacadeny.instructor.Instructor;
import com.archisacadeny.student.Student;

public class CourseStudentMapper {
    private long id;
    private long courseId;
    private long studentId;

    public CourseStudentMapper(long id, long courseId, long studentId) {
        this.id = id;
        this.courseId = courseId;
        this.studentId = studentId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCourseId() {
        return courseId;
    }

    public void setCourseId(long courseId) {
        this.courseId = courseId;
    }

    public long getStudentId() {
        return studentId;
    }

    public void setStudentId(long studentId) {
        this.studentId = studentId;
    }
}
