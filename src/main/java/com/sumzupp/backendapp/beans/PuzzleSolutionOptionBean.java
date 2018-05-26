package com.sumzupp.backendapp.beans;

/**
 * Created by akash.mercer on 18-Jun-17.
 */
public class PuzzleSolutionOptionBean {

    private String puzzleSolutionOptionId;

    private String puzzleSolutionOptionText;

    private String puzzleSolutionOptionImageUrls;

    private Boolean solution;

    public PuzzleSolutionOptionBean() {

    }

    public String getPuzzleSolutionOptionId() {
        return puzzleSolutionOptionId;
    }

    public void setPuzzleSolutionOptionId(String puzzleSolutionOptionId) {
        this.puzzleSolutionOptionId = puzzleSolutionOptionId;
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
}
