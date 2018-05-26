package com.sumzupp.backendapp.beans;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by akash.mercer on 15-Oct-17.
 */
public class QuestionDateBean {

    private String date;

    private List<QuestionBean> questionBeans = new ArrayList<>();

    public QuestionDateBean() {

    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<QuestionBean> getQuestionBeans() {
        return questionBeans;
    }

    public void setQuestionBeans(List<QuestionBean> questionBeans) {
        this.questionBeans = questionBeans;
    }
}
