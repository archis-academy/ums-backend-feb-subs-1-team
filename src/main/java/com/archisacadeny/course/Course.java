package com.archisacadeny.course;

import com.archisacadeny.config.DataBaseConfig;

public class Course {
    private long courseId;
    private String courseTitle;
    private int    creditHours;
    private float   gradeScale;
    private String  department;
    private long instructorId;

    public long getCourseId() {
        return courseId;
    }

    public void setCourseId(long courseId) {
        this.courseId = courseId;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public int getCreditHours() {
        return creditHours;
    }

    public void setCreditHours(int creditHours) {
        this.creditHours = creditHours;
    }

    public float getGradeScale() {
        return gradeScale;
    }

    public void setGradeScale(float gradeScale) {
        this.gradeScale = gradeScale;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public long getInstructorId() {
        return instructorId;
    }

    public void setInstructorId(long instructorId) {
        this.instructorId = instructorId;
    }
}
