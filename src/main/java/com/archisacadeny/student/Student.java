package com.archisacadeny.student;

public class Student {
    private long studentID;
    private String firstName;
    private String secondName;
    private String gender;
    private long courseID;
    private String identityNo;
    private String unvEnrollmentDate;
    private long yearsOfStudy;
    private int totalCreditCount;

    public long getStudentID() {
        return studentID;
    }

    public void setStudentID(long studentID) {
        this.studentID = studentID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public long getCourseID() {
        return courseID;
    }

    public void setCourseID(long courseID) {
        this.courseID = courseID;
    }

    public String getIdentityNo() {
        return identityNo;
    }

    public void setIdentityNo(String identityNo) {
        this.identityNo = identityNo;
    }

    public String getUnvEnrollmentDate() {
        return unvEnrollmentDate;
    }

    public void setUnvEnrollmentDate(String unvEnrollmentDate) {
        this.unvEnrollmentDate = unvEnrollmentDate;
    }

    public long getYearsOfStudy() {
        return yearsOfStudy;
    }

    public void setYearsOfStudy(long yearsOfStudy) {
        this.yearsOfStudy = yearsOfStudy;
    }

    public int getTotalCreditCount() {
        return totalCreditCount;
    }

    public void setTotalCreditCount(int totalCreditCount) {
        this.totalCreditCount = totalCreditCount;
    }
}
