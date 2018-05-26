package com.sumzupp.backendapp.entities;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by akash.mercer on 09-Jul-17.
 */

@Entity
@Table(name = "subject_chapter")
public class SubjectChapter {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(length = 60)
    private String id;

    @Column(nullable = false, length = 80)
    private String subjectChapterName;

    @Column(nullable = false, columnDefinition = "int(11) default '1'")
    private Integer subjectChapterNumber = 1;

    @Column(nullable = false, columnDefinition = "tinyint(1) default 1")
    private Boolean active = true;

    @Column(nullable = false, columnDefinition = "tinyint(1) default 1")
    private Boolean locked = true;

    @CreationTimestamp
    private Date createdAt;

    @UpdateTimestamp
    private Date updatedAt;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "subject", nullable = false)
    private Subject subject;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "standard")
    private Standard standard;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "board")
    private Board board;

    public SubjectChapter() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSubjectChapterName() {
        return subjectChapterName;
    }

    public void setSubjectChapterName(String subjectChapterName) {
        this.subjectChapterName = subjectChapterName;
    }

    public Integer getSubjectChapterNumber() {
        return subjectChapterNumber;
    }

    public void setSubjectChapterNumber(Integer subjectChapterNumber) {
        this.subjectChapterNumber = subjectChapterNumber;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
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

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public Standard getStandard() {
        return standard;
    }

    public void setStandard(Standard standard) {
        this.standard = standard;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    @Override
    public String toString() {
        return "SubjectChapter{" +
                "id='" + id + '\'' +
                ", subjectChapterName='" + subjectChapterName + '\'' +
                ", subjectChapterNumber=" + subjectChapterNumber +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", subject=" + subject +
                ", standard=" + standard +
                ", board=" + board +
                '}';
    }
}
