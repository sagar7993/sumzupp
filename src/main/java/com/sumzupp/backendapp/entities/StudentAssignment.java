package com.sumzupp.backendapp.entities;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by akash.mercer on 05-Jul-17.
 */

@Entity
@Table(name = "student_assignment")
public class StudentAssignment {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(length = 60)
    private String id;

    @Column(length = 3000)
    private String studentSolution;

    @Column(nullable = false, columnDefinition = "int(11) default '0'")
    private Integer marks = 0;

    @Column(nullable = false, columnDefinition = "int(11) default '0'")
    private Integer attempted = 0;

    @Column(nullable = false, columnDefinition = "int(11) default '0'")
    private Integer correct = 0;

    @Column(nullable = false)
    private Long startDate;

    @Column
    private Long submitDate;

    @CreationTimestamp
    private Date createdAt;

    @UpdateTimestamp
    private Date updatedAt;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "student", nullable = false)
    private User student;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "assignment", nullable = false)
    private Assignment assignment;

    @Transient
    private List<AssignmentQuestion> assignmentQuestions = new ArrayList<>();

    public StudentAssignment() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStudentSolution() {
        return studentSolution;
    }

    public void setStudentSolution(String studentSolution) {
        this.studentSolution = studentSolution;
    }

    public Integer getMarks() {
        return marks;
    }

    public void setMarks(Integer marks) {
        this.marks = marks;
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

    public Long getStartDate() {
        return startDate;
    }

    public void setStartDate(Long startDate) {
        this.startDate = startDate;
    }

    public Long getSubmitDate() {
        return submitDate;
    }

    public void setSubmitDate(Long submitDate) {
        this.submitDate = submitDate;
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

    public Assignment getAssignment() {
        return assignment;
    }

    public void setAssignment(Assignment assignment) {
        this.assignment = assignment;
    }

    public List<AssignmentQuestion> getAssignmentQuestions() {
        return assignmentQuestions;
    }

    public void setAssignmentQuestions(List<AssignmentQuestion> assignmentQuestions) {
        this.assignmentQuestions = assignmentQuestions;
    }

    @Override
    public String toString() {
        return "StudentAssignment{" +
                "id='" + id + '\'' +
                ", studentSolution='" + studentSolution + '\'' +
                ", marks=" + marks +
                ", attempted=" + attempted +
                ", correct=" + correct +
                ", startDate=" + startDate +
                ", submitDate=" + submitDate +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", student=" + student +
                ", assignment=" + assignment +
                ", assignmentQuestions=" + assignmentQuestions +
                '}';
    }
}
