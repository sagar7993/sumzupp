package com.sumzupp.backendapp.beans;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by akash.mercer on 29-Oct-17.
 */
public class QuestionPapersBean extends StatusBean {

    private List<QuestionPaperBean> questionPaperBeans = new ArrayList<>();

    public QuestionPapersBean() {

    }

    public List<QuestionPaperBean> getQuestionPaperBeans() {
        return questionPaperBeans;
    }

    public void setQuestionPaperBeans(List<QuestionPaperBean> questionPaperBeans) {
        this.questionPaperBeans = questionPaperBeans;
    }
}
