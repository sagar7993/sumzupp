package com.sumzupp.backendapp.beans;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by akash.mercer on 30-Nov-17.
 */
public class AssignmentMarksMasterBean {

    private String subjectChapterName;

    private Integer subjectChapterNumber;

    private List<AssignmentMarksBean> assignmentMarksBeans = new ArrayList<>();

    public AssignmentMarksMasterBean() {

    }

    public String getSubjectChapterName() {
        return subjectChapterName;
    }

    public void setSubjectChapterName(String subjectChapterName) {
        this.subjectChapterName = subjectChapterName;
    }

    public Integer getSubjectChapterNumber() {
        return subjectChapterNumber;
    }

    public void setSubjectChapterNumber(Integer subjectChapterNumber) {
        this.subjectChapterNumber = subjectChapterNumber;
    }

    public List<AssignmentMarksBean> getAssignmentMarksBeans() {
        return assignmentMarksBeans;
    }

    public void setAssignmentMarksBeans(List<AssignmentMarksBean> assignmentMarksBeans) {
        this.assignmentMarksBeans = assignmentMarksBeans;
    }
}
