package com.sumzupp.backendapp.beans;

/**
 * Created by akash.mercer on 28-Oct-17.
 */
public class QuestionPaperBean {

    private String questionPaperName;

    private String questionPaperUrl;

    private Integer questionPaperType;

    private String subjectChapterId;

    private String instituteId;

    private Boolean downloadable;

    public QuestionPaperBean() {

    }

    public QuestionPaperBean(String questionPaperName, String questionPaperUrl, Boolean downloadable) {
        this.questionPaperName = questionPaperName;
        this.questionPaperUrl = questionPaperUrl;
        this.downloadable = downloadable;
    }

    public String getQuestionPaperName() {
        return questionPaperName;
    }

    public void setQuestionPaperName(String questionPaperName) {
        this.questionPaperName = questionPaperName;
    }

    public String getQuestionPaperUrl() {
        return questionPaperUrl;
    }

    public void setQuestionPaperUrl(String questionPaperUrl) {
        this.questionPaperUrl = questionPaperUrl;
    }

    public Integer getQuestionPaperType() {
        return questionPaperType;
    }

    public void setQuestionPaperType(Integer questionPaperType) {
        this.questionPaperType = questionPaperType;
    }

    public String getSubjectChapterId() {
        return subjectChapterId;
    }

    public void setSubjectChapterId(String subjectChapterId) {
        this.subjectChapterId = subjectChapterId;
    }

    public String getInstituteId() {
        return instituteId;
    }

    public void setInstituteId(String instituteId) {
        this.instituteId = instituteId;
    }

    public Boolean getDownloadable() {
        return downloadable;
    }

    public void setDownloadable(Boolean downloadable) {
        this.downloadable = downloadable;
    }
}
