package com.archisacadeny.student;

public class Student {
    private long id;
    private String fullName;
    private String gender;
    private long courseID;
    private String identityNo;
    private String unvEnrollmentDate;
    private long yearsOfStudy;
    private int totalCreditCount;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
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
