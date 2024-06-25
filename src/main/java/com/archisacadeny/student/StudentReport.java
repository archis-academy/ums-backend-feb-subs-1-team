package com.archisacadeny.student;

public class StudentReport {
    private long studentId;
    private String fullName;
    private double gpa;

    public StudentReport(long studentId, String fullName, double gpa) {
        this.studentId = studentId;
        this.fullName = fullName;
        this.gpa = gpa;
    }

    public long getStudentId() { return studentId; }
    public void setStudentId(long studentId) { this.studentId = studentId; }
    public String getFullName() { return fullName; }
    public void setStudentName(String fullName) { this.fullName = fullName; }
    public double getGpa() { return gpa; }
    public void setGpa(double gpa) { this.gpa = gpa; }

}
