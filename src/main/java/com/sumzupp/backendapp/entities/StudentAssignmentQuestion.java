package com.sumzupp.backendapp.entities;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by akash.mercer on 06-Aug-17.
 */

@Entity
@Table(name = "student_assignment_question")
public class StudentAssignmentQuestion {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(length = 60)
    private String id;

    @Column(nullable = false, columnDefinition = "tinyint(1) default 1")
    private Boolean attempted = false;

    @Column(nullable = false, columnDefinition = "tinyint(1) default 1")
    private Boolean correct = false;

    @CreationTimestamp
    private Date createdAt;

    @UpdateTimestamp
    private Date updatedAt;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "student", nullable = false)
    private User student;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "assignment_question", nullable = false)
    private AssignmentQuestion assignmentQuestion;

    public StudentAssignmentQuestion() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public User getStudent() {
        return student;
    }

    public void setStudent(User student) {
        this.student = student;
    }

    public AssignmentQuestion getAssignmentQuestion() {
        return assignmentQuestion;
    }

    public void setAssignmentQuestion(AssignmentQuestion assignmentQuestion) {
        this.assignmentQuestion = assignmentQuestion;
    }

    @Override
    public String toString() {
        return "StudentAssignmentQuestion{" +
                "id='" + id + '\'' +
                ", attempted=" + attempted +
                ", correct=" + correct +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", student=" + student +
                ", assignmentQuestion=" + assignmentQuestion +
                '}';
    }
}
