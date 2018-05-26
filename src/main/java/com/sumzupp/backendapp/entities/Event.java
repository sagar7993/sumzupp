package com.sumzupp.backendapp.entities;

import com.sumzupp.backendapp.beans.EventBean;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by akash.mercer on 22-Mar-18.
 */

@Entity
@Table(name = "event")
public class Event {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(length = 60)
    private String id;

    @Column(nullable = false, length = 500)
    private String title;

    @Column(nullable = false, length = 10000)
    private String content;

    @Column(nullable = false)
    private Long eventDate;

    @Column
    private String heroImageUrl;

    @Column
    private String eventImageUrls;

    @Column(nullable = false, columnDefinition = "tinyint(1) default 1")
    private Boolean active = true;

    @CreationTimestamp
    private Date createdAt;

    @UpdateTimestamp
    private Date updatedAt;

    public Event() {

    }

    public Event(EventBean eventBean) {
        this.title = eventBean.getTitle();
        this.content = eventBean.getContent();
        this.eventDate = eventBean.getEventDate();
        this.heroImageUrl = eventBean.getHeroImageUrl();
        this.eventImageUrls = eventBean.getEventImageUrls();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getEventDate() {
        return eventDate;
    }

    public void setEventDate(Long eventDate) {
        this.eventDate = eventDate;
    }

    public String getHeroImageUrl() {
        return heroImageUrl;
    }

    public void setHeroImageUrl(String heroImageUrl) {
        this.heroImageUrl = heroImageUrl;
    }

    public String getEventImageUrls() {
        return eventImageUrls;
    }

    public void setEventImageUrls(String eventImageUrls) {
        this.eventImageUrls = eventImageUrls;
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
}
