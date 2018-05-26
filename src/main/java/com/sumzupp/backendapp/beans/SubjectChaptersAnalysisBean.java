package com.sumzupp.backendapp.beans;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by akash.mercer on 30-Nov-17.
 */
public class SubjectChaptersAnalysisBean extends StatusBean {

    private String instituteName;

    private String subjectName;

    private Integer standard;

    private List<AssignmentMarksMasterBean> assignmentMarksMasterBeans = new ArrayList<>();

    private List<StudentAssignmentMarksMasterBean> studentAssignmentMarksMasterBeans = new ArrayList<>();

    public SubjectChaptersAnalysisBean() {

    }

    public String getInstituteName() {
        return instituteName;
    }

    public void setInstituteName(String instituteName) {
        this.instituteName = instituteName;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public Integer getStandard() {
        return standard;
    }

    public void setStandard(Integer standard) {
        this.standard = standard;
    }

    public List<AssignmentMarksMasterBean> getAssignmentMarksMasterBeans() {
        return assignmentMarksMasterBeans;
    }

    public void setAssignmentMarksMasterBeans(List<AssignmentMarksMasterBean> assignmentMarksMasterBeans) {
        this.assignmentMarksMasterBeans = assignmentMarksMasterBeans;
    }

    public List<StudentAssignmentMarksMasterBean> getStudentAssignmentMarksMasterBeans() {
        return studentAssignmentMarksMasterBeans;
    }

    public void setStudentAssignmentMarksMasterBeans(List<StudentAssignmentMarksMasterBean> studentAssignmentMarksMasterBeans) {
        this.studentAssignmentMarksMasterBeans = studentAssignmentMarksMasterBeans;
    }
}
