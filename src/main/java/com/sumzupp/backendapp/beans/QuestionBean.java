package com.sumzupp.backendapp.beans;

import com.sumzupp.backendapp.utils.DateOperations;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by akash.mercer on 05-Jul-17.
 */
public class QuestionBean {

    private String questionId;

    private String questionText;

    private String questionImageUrls;

    private Integer marks;

    private Boolean formula;

    private Integer questionType;

    private List<SolutionOptionBean> solutionOptionBeans = new ArrayList<>();

    private Boolean solved = false;

    private String date;

    public QuestionBean() {

    }

    public QuestionBean(String questionId, String questionText, String questionImageUrls, Integer marks, Boolean formula, Integer questionType) {
        this.questionId = questionId;
        this.questionText = questionText;
        this.questionImageUrls = questionImageUrls;
        this.marks = marks;
        this.formula = formula;
        this.questionType = questionType;
    }

    public QuestionBean(String questionId, String questionText, String questionImageUrls, Integer marks, Boolean formula, Integer questionType, Date createdAt) {
        this.questionId = questionId;
        this.questionText = questionText;
        this.questionImageUrls = questionImageUrls;
        this.marks = marks;
        this.formula = formula;
        this.questionType = questionType;
        this.date = DateOperations.getIstDateString(createdAt);
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

    public String getQuestionImageUrls() {
        return questionImageUrls;
    }

    public void setQuestionImageUrls(String questionImageUrls) {
        this.questionImageUrls = questionImageUrls;
    }

    public Integer getMarks() {
        return marks;
    }

    public void setMarks(Integer marks) {
        this.marks = marks;
    }

    public Boolean getFormula() {
        return formula;
    }

    public void setFormula(Boolean formula) {
        this.formula = formula;
    }

    public Integer getQuestionType() {
        return questionType;
    }

    public void setQuestionType(Integer questionType) {
        this.questionType = questionType;
    }

    public List<SolutionOptionBean> getSolutionOptionBeans() {
        return solutionOptionBeans;
    }

    public void setSolutionOptionBeans(List<SolutionOptionBean> solutionOptionBeans) {
        this.solutionOptionBeans = solutionOptionBeans;
    }

    public Boolean getSolved() {
        return solved;
    }

    public void setSolved(Boolean solved) {
        this.solved = solved;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
