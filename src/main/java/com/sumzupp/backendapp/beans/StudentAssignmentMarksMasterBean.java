package com.sumzupp.backendapp.beans;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by akash.mercer on 03-Dec-17.
 */
public class StudentAssignmentMarksMasterBean {

    private String studentId;

    private String studentName;

    private List<StudentAssignmentMarksBean> studentAssignmentMarksBeans = new ArrayList<>();

    public StudentAssignmentMarksMasterBean() {

    }

    public StudentAssignmentMarksMasterBean(String studentId, String studentName) {
        this.studentId = studentId;
        this.studentName = studentName;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public List<StudentAssignmentMarksBean> getStudentAssignmentMarksBeans() {
        return studentAssignmentMarksBeans;
    }

    public void setStudentAssignmentMarksBeans(List<StudentAssignmentMarksBean> studentAssignmentMarksBeans) {
        this.studentAssignmentMarksBeans = studentAssignmentMarksBeans;
    }
}
