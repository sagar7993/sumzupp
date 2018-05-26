package com.sumzupp.backendapp.beans;

/**
 * Created by akash.mercer on 06-Jul-17.
 */

public class AssignmentDetailsBean extends StatusBean {

    private AssignmentBean assignmentBean;

    public AssignmentDetailsBean() {

    }

    public AssignmentBean getAssignmentBean() {
        return assignmentBean;
    }

    public void setAssignmentBean(AssignmentBean assignmentBean) {
        this.assignmentBean = assignmentBean;
    }
}
