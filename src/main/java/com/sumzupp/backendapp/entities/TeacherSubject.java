package com.sumzupp.backendapp.entities;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by akash.mercer on 18-Jun-17.
 */

@Entity
@Table(name = "teacher_subject")
public class TeacherSubject {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(length = 60)
    private String id;

    @Column(length = 20)
    private String qualification;

    @CreationTimestamp
    private Date createdAt;

    @UpdateTimestamp
    private Date updatedAt;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "teacher", nullable = false)
    private User teacher;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "subject", nullable = false)
    private Subject subject;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "standard_division", nullable = false)
    private StandardDivision standardDivision;

    public TeacherSubject() {

    }

    public TeacherSubject(User teacher, Subject subject, StandardDivision standardDivision) {
        this.teacher = teacher;
        this.subject = subject;
        this.standardDivision = standardDivision;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
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

    public User getTeacher() {
        return teacher;
    }

    public void setTeacher(User teacher) {
        this.teacher = teacher;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public StandardDivision getStandardDivision() {
        return standardDivision;
    }

    public void setStandardDivision(StandardDivision standardDivision) {
        this.standardDivision = standardDivision;
    }

    @Override
    public String toString() {
        return "TeacherSubject{" +
                "id='" + id + '\'' +
                ", qualification='" + qualification + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", teacher=" + teacher +
                ", subject=" + subject +
                ", standardDivision=" + standardDivision +
                '}';
    }
}
