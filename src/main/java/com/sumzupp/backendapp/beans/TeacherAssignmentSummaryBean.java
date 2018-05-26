package com.sumzupp.backendapp.beans;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by akash.mercer on 09-Aug-17.
 */
public class TeacherAssignmentSummaryBean extends StatusBean {

    List<QuestionSolutionSummaryBean> questionSolutionSummaryBeans = new ArrayList<>();

    public TeacherAssignmentSummaryBean() {

    }

    public List<QuestionSolutionSummaryBean> getQuestionSolutionSummaryBeans() {
        return questionSolutionSummaryBeans;
    }

    public void setQuestionSolutionSummaryBeans(List<QuestionSolutionSummaryBean> questionSolutionSummaryBeans) {
        this.questionSolutionSummaryBeans = questionSolutionSummaryBeans;
    }
}
