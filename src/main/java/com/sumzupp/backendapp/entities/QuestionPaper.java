package com.sumzupp.backendapp.entities;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by akash.mercer on 28-Oct-17.
 */

@Entity
@Table(name = "question_paper")
public class QuestionPaper {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(length = 60)
    private String id;

    @Column(nullable = false)
    private String questionPaperName;

    @Column(length = 1500)
    private String questionPaperUrl;

    @CreationTimestamp
    private Date createdAt;

    @UpdateTimestamp
    private Date updatedAt;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "question_paper_type", nullable = false)
    private QuestionPaperType questionPaperType;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "subject_chapter", nullable = false)
    private SubjectChapter subjectChapter;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "institute", nullable = false)
    private User institute;

    public QuestionPaper() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public QuestionPaperType getQuestionPaperType() {
        return questionPaperType;
    }

    public void setQuestionPaperType(QuestionPaperType questionPaperType) {
        this.questionPaperType = questionPaperType;
    }

    public SubjectChapter getSubjectChapter() {
        return subjectChapter;
    }

    public void setSubjectChapter(SubjectChapter subjectChapter) {
        this.subjectChapter = subjectChapter;
    }

    public User getInstitute() {
        return institute;
    }

    public void setInstitute(User institute) {
        this.institute = institute;
    }
}
