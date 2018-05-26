package com.sumzupp.backendapp.entities;

import com.sumzupp.backendapp.beans.EventSubscriberBean;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by akash.mercer on 22-Mar-18.
 */

@Entity
@Table(name = "event_subscriber")
public class EventSubscriber {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(length = 60)
    private String id;

    @Column(nullable = false, length = 40)
    private String name;

    @Column(length = 10)
    private String contactNumber;

    @Column(columnDefinition = "VARCHAR(255) CHARACTER SET utf8 COLLATE utf8_bin")
    private String email;

    @Column(nullable = false, columnDefinition = "tinyint(1) default 1")
    private Boolean active = true;

    @CreationTimestamp
    private Date createdAt;

    @UpdateTimestamp
    private Date updatedAt;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "event", nullable = false)
    private Event event;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_type", nullable = false)
    private UserType userType;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "board", nullable = false)
    private Board board;

    public EventSubscriber() {

    }

    public EventSubscriber(EventSubscriberBean eventSubscriberBean, Event event, UserType userType, Board board) {
        this.name = eventSubscriberBean.getName();
        this.contactNumber = eventSubscriberBean.getContactNumber();
        this.email = eventSubscriberBean.getEmail();
        this.event = event;
        this.userType = userType;
        this.board = board;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }
}
