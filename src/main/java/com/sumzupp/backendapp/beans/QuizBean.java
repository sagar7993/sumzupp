package com.sumzupp.backendapp.beans;

/**
 * Created by akash.mercer on 11-Jul-17.
 */

public class QuizBean extends StatusBean {

    private String termsAndConditions;

    private Boolean started;

    private Boolean ended;

    private Long startTime;

    public QuizBean() {

    }

    public String getTermsAndConditions() {
        return termsAndConditions;
    }

    public void setTermsAndConditions(String termsAndConditions) {
        this.termsAndConditions = termsAndConditions;
    }

    public Boolean getStarted() {
        return started;
    }

    public void setStarted(Boolean started) {
        this.started = started;
    }

    public Boolean getEnded() {
        return ended;
    }

    public void setEnded(Boolean ended) {
        this.ended = ended;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }
}
