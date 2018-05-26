package com.sumzupp.backendapp.entities;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by akash.mercer on 07-Jul-17.
 */

@Entity
@Table(name = "standard_division")
public class StandardDivision {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(length = 60)
    private String id;

    @Column(nullable = false, length = 20)
    private String divisionName;

    @Column(nullable = false, columnDefinition = "tinyint(1) default 1")
    private Boolean active = true;

    @CreationTimestamp
    private Date createdAt;

    @UpdateTimestamp
    private Date updatedAt;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "institute", nullable = false)
    private User institute;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "standard", nullable = false)
    private Standard standard;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "board", nullable = false)
    private Board board;

    public StandardDivision() {

    }

    public StandardDivision(String divisionName, User institute, Standard standard, Board board) {
        this.divisionName = divisionName;
        this.institute = institute;
        this.standard = standard;
        this.board = board;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDivisionName() {
        return divisionName;
    }

    public void setDivisionName(String divisionName) {
        this.divisionName = divisionName;
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

    public User getInstitute() {
        return institute;
    }

    public void setInstitute(User institute) {
        this.institute = institute;
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
        return "StandardDivision{" +
                "id='" + id + '\'' +
                ", divisionName='" + divisionName + '\'' +
                ", active=" + active +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", institute=" + institute +
                ", standard=" + standard +
                ", board=" + board +
                '}';
    }
}
