package com.sumzupp.backendapp.beans;

import com.sumzupp.backendapp.entities.Subject;

import java.util.ArrayList;
import java.util.List;

public class SubjectBean {

    private String subjectId;

    private String name;

    private String subjectImageUrl;

    private Integer type;

    private Boolean active;

    private Boolean global;

    private List<SubjectChapterBean> subjectChapterBeans = new ArrayList<>();

    public SubjectBean() {

    }

    public SubjectBean(String subjectId, String name, Integer type) {
        this.subjectId = subjectId;
        this.name = name;
        this.type = type;
    }

    public SubjectBean(String subjectId, String name, Integer type, Boolean active) {
        this.subjectId = subjectId;
        this.name = name;
        this.type = type;
        this.active = active;
    }

    public SubjectBean(String subjectId, String name, Integer type, Boolean active, Boolean global) {
        this.subjectId = subjectId;
        this.name = name;
        this.type = type;
        this.active = active;
        this.global = global;
    }

    public SubjectBean(Subject subject) {
        this.subjectId = subject.getId();
        this.name = subject.getName();
        this.type = subject.getType();
        this.active = subject.getActive();
        this.global = subject.getGlobal();
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubjectImageUrl() {
        return subjectImageUrl;
    }

    public void setSubjectImageUrl(String subjectImageUrl) {
        this.subjectImageUrl = subjectImageUrl;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public List<SubjectChapterBean> getSubjectChapterBeans() {
        return subjectChapterBeans;
    }

    public void setSubjectChapterBeans(List<SubjectChapterBean> subjectChapterBeans) {
        this.subjectChapterBeans = subjectChapterBeans;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Boolean getGlobal() {
        return global;
    }

    public void setGlobal(Boolean global) {
        this.global = global;
    }

}