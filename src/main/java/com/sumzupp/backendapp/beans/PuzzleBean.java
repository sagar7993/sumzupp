package com.sumzupp.backendapp.beans;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by akash.mercer on 18-Jun-17.
 */
public class PuzzleBean {

    private String puzzleId;

    private String puzzleText;

    private String puzzleImageUrls;

    private Integer points;

    private List<PuzzleSolutionOptionBean> puzzleSolutionOptionBeans = new ArrayList<>();

    public PuzzleBean() {

    }

    public String getPuzzleId() {
        return puzzleId;
    }

    public void setPuzzleId(String puzzleId) {
        this.puzzleId = puzzleId;
    }

    public String getPuzzleText() {
        return puzzleText;
    }

    public void setPuzzleText(String puzzleText) {
        this.puzzleText = puzzleText;
    }

    public String getPuzzleImageUrls() {
        return puzzleImageUrls;
    }

    public void setPuzzleImageUrls(String puzzleImageUrls) {
        this.puzzleImageUrls = puzzleImageUrls;
    }

    public List<PuzzleSolutionOptionBean> getPuzzleSolutionOptionBeans() {
        return puzzleSolutionOptionBeans;
    }

    public void setPuzzleSolutionOptionBeans(List<PuzzleSolutionOptionBean> puzzleSolutionOptionBeans) {
        this.puzzleSolutionOptionBeans = puzzleSolutionOptionBeans;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }
}
