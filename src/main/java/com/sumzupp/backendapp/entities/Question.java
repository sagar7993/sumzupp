package com.sumzupp.backendapp.entities;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by akash.mercer on 07-Jul-17.
 */

@Entity
@Table(name = "question")
public class Question {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(length = 60)
    private String id;

    @Column(nullable = false)
    private String questionText;

    @Column(length = 1500)
    private String questionImageUrls;

    @Column(nullable = false, columnDefinition = "int(11) default '0'")
    private Integer marks = 0;

    @Column(nullable = false, columnDefinition = "tinyint(1) default 0")
    private Boolean formula = false;

    @Column(nullable = false, columnDefinition = "tinyint(1) default 0")
    private Boolean global = false;

    @CreationTimestamp
    private Date createdAt;

    @UpdateTimestamp
    private Date updatedAt;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "question_type", nullable = false)
    private QuestionType questionType;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "subject_chapter", nullable = false)
    private SubjectChapter subjectChapter;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "global_standard")
    private Standard standard;

    @Transient
    private List<SolutionOption> solutionOptions = new ArrayList<>();

    @Transient
    private List<String> questionImageFilePaths = new ArrayList<>();

    public Question() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public Boolean getGlobal() {
        return global;
    }

    public void setGlobal(Boolean global) {
        this.global = global;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public QuestionType getQuestionType() {
        return questionType;
    }

    public void setQuestionType(QuestionType questionType) {
        this.questionType = questionType;
    }

    public SubjectChapter getSubjectChapter() {
        return subjectChapter;
    }

    public void setSubjectChapter(SubjectChapter subjectChapter) {
        this.subjectChapter = subjectChapter;
    }

    public Standard getStandard() {
        return standard;
    }

    public void setStandard(Standard standard) {
        this.standard = standard;
    }

    public List<SolutionOption> getSolutionOptions() {
        return solutionOptions;
    }

    public void setSolutionOptions(List<SolutionOption> solutionOptions) {
        this.solutionOptions = solutionOptions;
    }

    public List<String> getQuestionImageFilePaths() {
        return questionImageFilePaths;
    }

    public void setQuestionImageFilePaths(List<String> questionImageFilePaths) {
        this.questionImageFilePaths = questionImageFilePaths;
    }

    @Override
    public String toString() {
        return "Question{" +
                "id='" + id + '\'' +
                ", questionText='" + questionText + '\'' +
                ", questionImageUrls='" + questionImageUrls + '\'' +
                ", marks=" + marks +
                ", formula=" + formula +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", questionType=" + questionType +
                ", subjectChapter=" + subjectChapter +
                ", solutionOptions=" + solutionOptions +
                '}';
    }
}
