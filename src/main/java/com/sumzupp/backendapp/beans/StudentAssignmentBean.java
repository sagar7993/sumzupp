package com.sumzupp.backendapp.beans;

import com.sumzupp.backendapp.entities.StudentAssignment;

/**
 * Created by akash.mercer on 11-Jul-17.
 */
public class StudentAssignmentBean {

    private String studentAssignmentId;

    private String assignmentId;

    private String assignmentTitle;

    private String studentSolution;

    private Integer marks = 0;

    private Integer attempted = 0;

    private Integer correct = 0;

    public StudentAssignmentBean() {

    }

    public StudentAssignmentBean(String studentAssignmentId, String assignmentId, String assignmentTitle, String studentSolution, Integer marks, Integer attempted, Integer correct) {
        this.studentAssignmentId = studentAssignmentId;
        this.assignmentId = assignmentId;
        this.assignmentTitle = assignmentTitle;
        this.studentSolution = studentSolution;
        this.marks = marks;
        this.attempted = attempted;
        this.correct = correct;
    }

    public StudentAssignmentBean(StudentAssignment studentAssignment) {
        studentAssignmentId = studentAssignment.getId();
        assignmentId = studentAssignment.getAssignment().getId();
        assignmentTitle = studentAssignment.getAssignment().getId();
        marks = studentAssignment.getAssignment().getMarks();
        attempted = studentAssignment.getAttempted();
        correct = studentAssignment.getCorrect();
    }

    public String getStudentAssignmentId() {
        return studentAssignmentId;
    }

    public void setStudentAssignmentId(String studentAssignmentId) {
        this.studentAssignmentId = studentAssignmentId;
    }

    public String getAssignmentId() {
        return assignmentId;
    }

    public void setAssignmentId(String assignmentId) {
        this.assignmentId = assignmentId;
    }

    public String getAssignmentTitle() {
        return assignmentTitle;
    }

    public void setAssignmentTitle(String assignmentTitle) {
        this.assignmentTitle = assignmentTitle;
    }

    public String getStudentSolution() {
        return studentSolution;
    }

    public void setStudentSolution(String studentSolution) {
        this.studentSolution = studentSolution;
    }

    public Integer getMarks() {
        return marks;
    }

    public void setMarks(Integer marks) {
        this.marks = marks;
    }

    public Integer getAttempted() {
        return attempted;
    }

    public void setAttempted(Integer attempted) {
        this.attempted = attempted;
    }

    public Integer getCorrect() {
        return correct;
    }

    public void setCorrect(Integer correct) {
        this.correct = correct;
    }
}
