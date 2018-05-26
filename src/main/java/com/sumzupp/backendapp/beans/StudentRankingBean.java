package com.sumzupp.backendapp.beans;

/**
 * Created by akash.mercer on 18-Jun-17.
 */
public class StudentRankingBean {

    private Integer assignmentMarks = 0;

    private Integer puzzlePoints = 0;

    private BadgeBean badgeBean;

    public StudentRankingBean() {

    }

    public Integer getAssignmentMarks() {
        return assignmentMarks;
    }

    public void setAssignmentMarks(Integer assignmentMarks) {
        this.assignmentMarks = assignmentMarks;
    }

    public Integer getPuzzlePoints() {
        return puzzlePoints;
    }

    public void setPuzzlePoints(Integer puzzlePoints) {
        this.puzzlePoints = puzzlePoints;
    }

    public BadgeBean getBadgeBean() {
        return badgeBean;
    }

    public void setBadgeBean(BadgeBean badgeBean) {
        this.badgeBean = badgeBean;
    }
}
