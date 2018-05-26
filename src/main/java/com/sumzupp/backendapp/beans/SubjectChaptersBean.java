package com.sumzupp.backendapp.beans;

import java.util.List;

/**
 * Created by akash.mercer on 21-Jan-18.
 */

public class SubjectChaptersBean extends StatusBean {

    private List<SubjectChapterBean> subjectChapterBeans;

    private Integer amount = 995;

    private String note = "This payment will allow you to have unlimited access to assignments, which are specially curated by Sumzupp experts.";

    public SubjectChaptersBean() {

    }

    public List<SubjectChapterBean> getSubjectChapterBeans() {
        return subjectChapterBeans;
    }

    public void setSubjectChapterBeans(List<SubjectChapterBean> subjectChapterBeans) {
        this.subjectChapterBeans = subjectChapterBeans;
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
