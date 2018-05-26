package com.sumzupp.backendapp.beans;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by akash.mercer on 19-Jul-17.
 */
public class SubjectsBean extends StatusBean {

    private List<SubjectBean> subjectBeans = new ArrayList<>();

    private Integer amount = 995;

    private String note = "This payment will allow you to have unlimited access to assignments, which are specially curated by Sumzupp experts.";

    public SubjectsBean() {

    }

    public List<SubjectBean> getSubjectBeans() {
        return subjectBeans;
    }

    public void setSubjectBeans(List<SubjectBean> subjectBeans) {
        this.subjectBeans = subjectBeans;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
