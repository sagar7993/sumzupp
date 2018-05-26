package com.sumzupp.backendapp.beans;

/**
 * Created by akash.mercer on 03-Dec-17.
 */
public class AssignmentMarksBean {

    private String assignmentId;

    private String assignmentName;

    private Integer highestMarks;

    private Double averageMarks;

    private Long attempted;

    private Integer totalMarks;

    public AssignmentMarksBean() {

    }

    public AssignmentMarksBean(String assignmentId, String assignmentName, Integer highestMarks, Double averageMarks, Long attempted) {
        this.assignmentId = assignmentId;
        this.assignmentName = assignmentName;
        this.highestMarks = highestMarks;
        this.averageMarks = averageMarks;
        this.attempted = attempted;
    }

    public String getAssignmentId() {
        return assignmentId;
    }

    public void setAssignmentId(String assignmentId) {
        this.assignmentId = assignmentId;
    }

    public String getAssignmentName() {
        return assignmentName;
    }

    public void setAssignmentName(String assignmentName) {
        this.assignmentName = assignmentName;
    }

    public Integer getHighestMarks() {
        return highestMarks;
    }

    public void setHighestMarks(Integer highestMarks) {
        this.highestMarks = highestMarks;
    }

    public Double getAverageMarks() {
        return averageMarks;
    }

    public void setAverageMarks(Double averageMarks) {
        this.averageMarks = averageMarks;
    }

    public Long getAttempted() {
        return attempted;
    }

    public void setAttempted(Long attempted) {
        this.attempted = attempted;
    }

    public Integer getTotalMarks() {
        return totalMarks;
    }

    public void setTotalMarks(Integer totalMarks) {
        this.totalMarks = totalMarks;
    }
}
