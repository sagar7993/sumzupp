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
@Table(name = "assignment_standard_division")
public class AssignmentStandardDivision {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(length = 60)
    private String id;

    @CreationTimestamp
    private Date createdAt;

    @UpdateTimestamp
    private Date updatedAt;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "assignment", nullable = false)
    private Assignment assignment;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "standard_division", nullable = false)
    private StandardDivision standardDivision;

    public AssignmentStandardDivision() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public Assignment getAssignment() {
        return assignment;
    }

    public void setAssignment(Assignment assignment) {
        this.assignment = assignment;
    }

    public StandardDivision getStandardDivision() {
        return standardDivision;
    }

    public void setStandardDivision(StandardDivision standardDivision) {
        this.standardDivision = standardDivision;
    }

    @Override
    public String toString() {
        return "AssignmentStandardDivision{" +
                "id='" + id + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", assignment=" + assignment +
                ", standardDivision=" + standardDivision +
                '}';
    }
}
