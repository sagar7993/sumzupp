package com.sumzupp.backendapp.beans;

/**
 * Created by akash.mercer on 19-Nov-17.
 */

public class StudentInstituteBean {

    private String userId;

    private String rollNumber;

    private String standardDivision;

    private String instituteName;

    public StudentInstituteBean() {

    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRollNumber() {
        return rollNumber;
    }

    public void setRollNumber(String rollNumber) {
        this.rollNumber = rollNumber;
    }

    public String getStandardDivision() {
        return standardDivision;
    }

    public void setStandardDivision(String standardDivision) {
        this.standardDivision = standardDivision;
    }

    public String getInstituteName() {
        return instituteName;
    }

    public void setInstituteName(String instituteName) {
        this.instituteName = instituteName;
    }
}
