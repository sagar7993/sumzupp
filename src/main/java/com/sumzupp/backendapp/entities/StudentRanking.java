package com.sumzupp.backendapp.entities;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by akash.mercer on 08-Jun-17.
 */

@Entity
@Table(name = "student_ranking")
public class StudentRanking {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(length = 60)
    private String id;

    @Column(nullable = false, columnDefinition = "int(11) default '0'")
    private Integer assignmentMarks = 0;

    @Column(nullable = false, columnDefinition = "int(11) default '0'")
    private Integer puzzlePoints = 0;

    @CreationTimestamp
    private Date createdAt;

    @UpdateTimestamp
    private Date updatedAt;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user", nullable = false)
    private User user;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "badge", nullable = false)
    private Badge badge;

    public StudentRanking() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getAssignmentMarks() {
        return assignmentMarks;
    }

    public void setAssignmentMarks(Integer assignmentMarks) {
        this.assignmentMarks = assignmentMarks;
    }

    public Integer getPuzzlePoints() {
        return puzzlePoints;
    }

    public void setPuzzlePoints(Integer puzzlePoints) {
        this.puzzlePoints = puzzlePoints;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Badge getBadge() {
        return badge;
    }

    public void setBadge(Badge badge) {
        this.badge = badge;
    }
}
