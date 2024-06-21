package com.archisacadeny.student;

public class CourseGrade {
    private long courseId;
    private String courseName;
    private int grade;

    public CourseGrade(long courseId, String courseName, int grade) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.grade = grade;
    }

    public long getCourseId() {
        return courseId;
    }

    public void setCourseId(long courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }
}
