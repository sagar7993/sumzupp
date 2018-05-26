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
@Table(name = "puzzle_solution_option")
public class PuzzleSolutionOption {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(length = 60)
    private String id;

    @Column(nullable = false)
    private String puzzleSolutionOptionText;

    @Column(length = 1500)
    private String puzzleSolutionOptionImageUrls;

    @Column(nullable = false, columnDefinition = "tinyint(1) default 0")
    private Boolean solution = false;

    @CreationTimestamp
    private Date createdAt;

    @UpdateTimestamp
    private Date updatedAt;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "puzzle", nullable = false)
    private Puzzle puzzle;

    public PuzzleSolutionOption() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPuzzleSolutionOptionText() {
        return puzzleSolutionOptionText;
    }

    public void setPuzzleSolutionOptionText(String puzzleSolutionOptionText) {
        this.puzzleSolutionOptionText = puzzleSolutionOptionText;
    }

    public String getPuzzleSolutionOptionImageUrls() {
        return puzzleSolutionOptionImageUrls;
    }

    public void setPuzzleSolutionOptionImageUrls(String puzzleSolutionOptionImageUrls) {
        this.puzzleSolutionOptionImageUrls = puzzleSolutionOptionImageUrls;
    }

    public Boolean getSolution() {
        return solution;
    }

    public void setSolution(Boolean solution) {
        this.solution = solution;
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

    public Puzzle getPuzzle() {
        return puzzle;
    }

    public void setPuzzle(Puzzle puzzle) {
        this.puzzle = puzzle;
    }
}
