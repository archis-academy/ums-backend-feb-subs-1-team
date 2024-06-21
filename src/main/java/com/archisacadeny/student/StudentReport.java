package com.archisacadeny.student;
import java.util.List;

public class StudentReport {
    private long studentId;
    private String fullName;
    private double gpa;
    private List<CourseGrade> courseGrades;

    public StudentReport(long studentId, String fullName, double gpa, List<CourseGrade> courseGrades) {
        this.studentId = studentId;
        this.fullName = fullName;
        this.gpa = gpa;
        this.courseGrades = courseGrades;
    }

    public long getStudentId() { return studentId; }
    public void setStudentId(long studentId) { this.studentId = studentId; }
    public String getFullName() { return fullName; }
    public void setStudentName(String fullName) { this.fullName = fullName; }
    public double getGpa() { return gpa; }
    public void setGpa(double gpa) { this.gpa = gpa; }
    public List<CourseGrade> getCourseGrades() { return courseGrades; }
    public void setCourseGrades(List<CourseGrade> courseGrades) { this.courseGrades = courseGrades; }

}
