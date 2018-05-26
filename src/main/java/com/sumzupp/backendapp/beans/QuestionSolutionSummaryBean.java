package com.sumzupp.backendapp.beans;

/**
 * Created by akash.mercer on 09-Aug-17.
 */
public class QuestionSolutionSummaryBean {

    private String questionText;

    private Integer total;

    private Boolean formula;

    private Integer attempted;

    private Integer correct;

    public QuestionSolutionSummaryBean() {

    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Boolean getFormula() {
        return formula;
    }

    public void setFormula(Boolean formula) {
        this.formula = formula;
    }

    public Integer getAttempted() {
        return attempted;
    }

    public void setAttempted(Integer attempted) {
        this.attempted = attempted;
    }

    public Integer getCorrect() {
        return correct;
    }

    public void setCorrect(Integer correct) {
        this.correct = correct;
    }
}
