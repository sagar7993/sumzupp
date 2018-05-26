package com.sumzupp.backendapp.entities;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by akash.mercer on 04-Jun-17.
 */

@Entity
@Table(name = "assignment")
public class Assignment {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(length = 60)
    private String id;

    @Column(nullable = false)
    private String assignmentTitle;

    @Column
    private Long startDate;

    @Column(nullable = false)
    private Long deadlineDate;

    @Column(nullable = false, columnDefinition = "int(11) default '0'")
    private Integer total = 0;

    @Column(nullable = false, columnDefinition = "int(11) default '0'")
    private Integer marks = 0;

    @Column(nullable = false, columnDefinition = "tinyint(1) default 1")
    private Boolean active = true;

    @Column(nullable = false, columnDefinition = "tinyint(1) default 0")
    private Boolean global = false;

    @Column(nullable = false, columnDefinition = "tinyint(1) default 0")
    private Boolean locked = false;

    @CreationTimestamp
    private Date createdAt;

    @UpdateTimestamp
    private Date updatedAt;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "teacher", nullable = false)
    private User teacher;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "subject_chapter", nullable = false)
    private SubjectChapter subjectChapter;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "institute_global")
    private User instituteGlobal;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "standard_global")
    private Standard standardGlobal;

    public Assignment() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAssignmentTitle() {
        return assignmentTitle;
    }

    public void setAssignmentTitle(String assignmentTitle) {
        this.assignmentTitle = assignmentTitle;
    }

    public Long getStartDate() {
        return startDate;
    }

    public void setStartDate(Long startDate) {
        this.startDate = startDate;
    }

    public Long getDeadlineDate() {
        return deadlineDate;
    }

    public void setDeadlineDate(Long deadlineDate) {
        this.deadlineDate = deadlineDate;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getMarks() {
        return marks;
    }

    public void setMarks(Integer marks) {
        this.marks = marks;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Boolean getGlobal() {
        return global;
    }

    public void setGlobal(Boolean global) {
        this.global = global;
    }

    public Boolean getLocked() {
        return locked;
    }

    public void setLocked(Boolean locked) {
        this.locked = locked;
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

    public SubjectChapter getSubjectChapter() {
        return subjectChapter;
    }

    public void setSubjectChapter(SubjectChapter subjectChapter) {
        this.subjectChapter = subjectChapter;
    }

    public User getInstituteGlobal() {
        return instituteGlobal;
    }

    public void setInstituteGlobal(User instituteGlobal) {
        this.instituteGlobal = instituteGlobal;
    }

    public Standard getStandardGlobal() {
        return standardGlobal;
    }

    public void setStandardGlobal(Standard standardGlobal) {
        this.standardGlobal = standardGlobal;
    }

    @Override
    public String toString() {
        return "Assignment{" +
                "id='" + id + '\'' +
                ", assignmentTitle='" + assignmentTitle + '\'' +
                ", deadlineDate=" + deadlineDate +
                ", total=" + total +
                ", marks=" + marks +
                ", active=" + active +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", teacher=" + teacher +
                ", subjectChapter=" + subjectChapter +
                '}';
    }
}
