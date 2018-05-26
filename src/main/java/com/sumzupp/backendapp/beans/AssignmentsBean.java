package com.sumzupp.backendapp.beans;

/**
 * Created by akash.mercer on 06-Jul-17.
 */

public class AssignmentsBean extends StatusBean {

    private AssignmentsPage assignmentsPage;

    public AssignmentsBean() {

    }

    public AssignmentsPage getAssignmentsPage() {
        return assignmentsPage;
    }

    public void setAssignmentsPage(AssignmentsPage assignmentsPage) {
        this.assignmentsPage = assignmentsPage;
    }
}
