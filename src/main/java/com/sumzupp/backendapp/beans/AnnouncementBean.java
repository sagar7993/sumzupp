package com.sumzupp.backendapp.beans;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by akash.mercer on 18-Jun-17.
 */
public class AnnouncementBean {

    private String announcementId;

    private String announcementText;

    private Long announcementDate;

    private Boolean standardGlobal;

    private Boolean studentGlobal;

    private Boolean teacherGlobal;

    private Boolean parentGlobal;

    private Boolean global;

    private String userId;

    private List<String> standardDivisionIds = new ArrayList<>();

    public AnnouncementBean() {

    }

    public String getAnnouncementId() {
        return announcementId;
    }

    public void setAnnouncementId(String announcementId) {
        this.announcementId = announcementId;
    }

    public String getAnnouncementText() {
        return announcementText;
    }

    public void setAnnouncementText(String announcementText) {
        this.announcementText = announcementText;
    }

    public Long getAnnouncementDate() {
        return announcementDate;
    }

    public void setAnnouncementDate(Long announcementDate) {
        this.announcementDate = announcementDate;
    }

    public Boolean getStandardGlobal() {
        return standardGlobal;
    }

    public void setStandardGlobal(Boolean standardGlobal) {
        this.standardGlobal = standardGlobal;
    }

    public Boolean getStudentGlobal() {
        return studentGlobal;
    }

    public void setStudentGlobal(Boolean studentGlobal) {
        this.studentGlobal = studentGlobal;
    }

    public Boolean getTeacherGlobal() {
        return teacherGlobal;
    }

    public void setTeacherGlobal(Boolean teacherGlobal) {
        this.teacherGlobal = teacherGlobal;
    }

    public Boolean getParentGlobal() {
        return parentGlobal;
    }

    public void setParentGlobal(Boolean parentGlobal) {
        this.parentGlobal = parentGlobal;
    }

    public Boolean getGlobal() {
        return global;
    }

    public void setGlobal(Boolean global) {
        this.global = global;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<String> getStandardDivisionIds() {
        return standardDivisionIds;
    }

    public void setStandardDivisionIds(List<String> standardDivisionIds) {
        this.standardDivisionIds = standardDivisionIds;
    }
}
