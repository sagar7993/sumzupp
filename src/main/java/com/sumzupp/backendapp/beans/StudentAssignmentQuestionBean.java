package com.sumzupp.backendapp.beans;

/**
 * Created by akash.mercer on 11-Aug-17.
 */
public class StudentAssignmentQuestionBean {

    private String questionId;

    private String questionText;

    private Boolean formula;

    private Boolean attempted = false;

    private Boolean correct = false;

    public StudentAssignmentQuestionBean() {

    }

    public StudentAssignmentQuestionBean(String questionId, String questionText, Boolean formula, Boolean attempted, Boolean correct) {
        this.questionId = questionId;
        this.questionText = questionText;
        this.formula = formula;
        this.attempted = attempted;
        this.correct = correct;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public Boolean getFormula() {
        return formula;
    }

    public void setFormula(Boolean formula) {
        this.formula = formula;
    }

    public Boolean getAttempted() {
        return attempted;
    }

    public void setAttempted(Boolean attempted) {
        this.attempted = attempted;
    }

    public Boolean getCorrect() {
        return correct;
    }

    public void setCorrect(Boolean correct) {
        this.correct = correct;
    }
}
