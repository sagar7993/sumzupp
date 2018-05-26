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
@Table(name = "announcement")
public class Announcement {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(length = 60)
    private String id;

    @Column(nullable = false)
    private String announcementText;

    @Column(nullable = false)
    private Long announcementDate;

    @Column(nullable = false, columnDefinition = "tinyint(1) default 0")
    private Boolean standardGlobal = false;

    @Column(nullable = false, columnDefinition = "tinyint(1) default 0")
    private Boolean studentGlobal = false;

    @Column(nullable = false, columnDefinition = "tinyint(1) default 0")
    private Boolean teacherGlobal = false;

    @Column(nullable = false, columnDefinition = "tinyint(1) default 0")
    private Boolean parentGlobal = false;

    @Column(nullable = false, columnDefinition = "tinyint(1) default 0")
    private Boolean global = false;

    @Column(nullable = false, columnDefinition = "tinyint(1) default 1")
    private Boolean active = true;

    @CreationTimestamp
    private Date createdAt;

    @UpdateTimestamp
    private Date updatedAt;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user", nullable = false)
    private User user;

    public Announcement() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAnnouncementText() {
        return announcementText;
    }

    public void setAnnouncementText(String announcementText) {
        this.announcementText = announcementText;
    }

    public Long getAnnouncementDate() {
        return announcementDate;
    }

    public void setAnnouncementDate(Long announcementDate) {
        this.announcementDate = announcementDate;
    }

    public Boolean getStandardGlobal() {
        return standardGlobal;
    }

    public void setStandardGlobal(Boolean standardGlobal) {
        this.standardGlobal = standardGlobal;
    }

    public Boolean getStudentGlobal() {
        return studentGlobal;
    }

    public void setStudentGlobal(Boolean studentGlobal) {
        this.studentGlobal = studentGlobal;
    }

    public Boolean getTeacherGlobal() {
        return teacherGlobal;
    }

    public void setTeacherGlobal(Boolean teacherGlobal) {
        this.teacherGlobal = teacherGlobal;
    }

    public Boolean getParentGlobal() {
        return parentGlobal;
    }

    public void setParentGlobal(Boolean parentGlobal) {
        this.parentGlobal = parentGlobal;
    }

    public Boolean getGlobal() {
        return global;
    }

    public void setGlobal(Boolean global) {
        this.global = global;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
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
}
