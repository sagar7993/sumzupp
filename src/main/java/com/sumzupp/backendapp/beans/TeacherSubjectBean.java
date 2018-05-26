package com.sumzupp.backendapp.beans;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by akash.mercer on 16-Nov-17.
 */
public class TeacherSubjectBean {

    private String teacherId;

    private String subjectId;

    private List<String> standardDivisionIds = new ArrayList<>();

    public TeacherSubjectBean() {

    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public List<String> getStandardDivisionIds() {
        return standardDivisionIds;
    }

    public void setStandardDivisionIds(List<String> standardDivisionIds) {
        this.standardDivisionIds = standardDivisionIds;
    }
}
