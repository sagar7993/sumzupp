package com.sumzupp.backendapp.beans;

/**
 * Created by akash.mercer on 08-Nov-17.
 */
public class StudentRankingDetailsBean {

    private String userId;

    private String studentName;

    private String instituteName;

    private Long points = 0L;

    public StudentRankingDetailsBean() {

    }

    public StudentRankingDetailsBean(String userId, String studentName, String instituteName, Long points) {
        this.userId = userId;
        this.studentName = studentName;
        this.instituteName = instituteName;
        this.points = points;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getInstituteName() {
        return instituteName;
    }

    public void setInstituteName(String instituteName) {
        this.instituteName = instituteName;
    }

    public Long getPoints() {
        return points;
    }

    public void setPoints(Long points) {
        this.points = points;
    }
}
