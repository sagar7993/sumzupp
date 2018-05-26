package com.sumzupp.backendapp.beans;

/**
 * Created by akash.mercer on 12-Jul-17.
 */
public class StudentAssignmentSummaryBean extends StatusBean {

    private StudentAssignmentBean studentAssignmentBean;

    public StudentAssignmentSummaryBean() {

    }

    public StudentAssignmentBean getStudentAssignmentBean() {
        return studentAssignmentBean;
    }

    public void setStudentAssignmentBean(StudentAssignmentBean studentAssignmentBean) {
        this.studentAssignmentBean = studentAssignmentBean;
    }
}
