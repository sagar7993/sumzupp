package com.sumzupp.backendapp.entities;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by akash.mercer on 05-Jul-17.
 */

@Entity
@Table(name = "solution_option")
public class SolutionOption {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(length = 60)
    private String id;

    @Column(nullable = false)
    private String solutionOptionText;

    @Column(length = 1500)
    private String solutionOptionImageUrls;

    @Column(nullable = false, columnDefinition = "tinyint(1) default 0")
    private Boolean solution = false;

    @Column(nullable = false, columnDefinition = "tinyint(1) default 0")
    private Boolean formula = false;

    @CreationTimestamp
    private Date createdAt;

    @UpdateTimestamp
    private Date updatedAt;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "question", nullable = false)
    private Question question;

    @Transient
    private String solutionOptionImageFilePath;

    public SolutionOption() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSolutionOptionText() {
        return solutionOptionText;
    }

    public void setSolutionOptionText(String solutionOptionText) {
        this.solutionOptionText = solutionOptionText;
    }

    public String getSolutionOptionImageUrls() {
        return solutionOptionImageUrls;
    }

    public void setSolutionOptionImageUrls(String solutionOptionImageUrls) {
        this.solutionOptionImageUrls = solutionOptionImageUrls;
    }

    public Boolean getSolution() {
        return solution;
    }

    public void setSolution(Boolean solution) {
        this.solution = solution;
    }

    public Boolean getFormula() {
        return formula;
    }

    public void setFormula(Boolean formula) {
        this.formula = formula;
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

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public String getSolutionOptionImageFilePath() {
        return solutionOptionImageFilePath;
    }

    public void setSolutionOptionImageFilePath(String solutionOptionImageFilePath) {
        this.solutionOptionImageFilePath = solutionOptionImageFilePath;
    }

    @Override
    public String toString() {
        return "SolutionOption{" +
                "id='" + id + '\'' +
                ", solutionOptionText='" + solutionOptionText + '\'' +
                ", solutionOptionImageUrls='" + solutionOptionImageUrls + '\'' +
                ", solution=" + solution +
                ", formula=" + formula +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", question=" + question +
                '}';
    }
}
