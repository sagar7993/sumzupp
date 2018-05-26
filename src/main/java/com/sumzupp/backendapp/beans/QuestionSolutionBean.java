package com.sumzupp.backendapp.beans;

/**
 * Created by akash.mercer on 11-Jul-17.
 */

public class QuestionSolutionBean {

    private String questionId;

    private String solutionOptionId;

    public QuestionSolutionBean() {

    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getSolutionOptionId() {
        return solutionOptionId;
    }

    public void setSolutionOptionId(String solutionOptionId) {
        this.solutionOptionId = solutionOptionId;
    }
}
