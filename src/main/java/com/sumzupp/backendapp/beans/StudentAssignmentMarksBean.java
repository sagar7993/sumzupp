package com.sumzupp.backendapp.beans;

/**
 * Created by akash.mercer on 30-Nov-17.
 */
public class StudentAssignmentMarksBean {

    private String studentId;

    private String assignmentId;

    private Integer correct;

    public StudentAssignmentMarksBean() {

    }

    public StudentAssignmentMarksBean(String studentId, String assignmentId, Integer correct) {
        this.studentId = studentId;
        this.assignmentId = assignmentId;
        this.correct = correct;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getAssignmentId() {
        return assignmentId;
    }

    public void setAssignmentId(String assignmentId) {
        this.assignmentId = assignmentId;
    }

    public Integer getCorrect() {
        return correct;
    }

    public void setCorrect(Integer correct) {
        this.correct = correct;
    }
}
