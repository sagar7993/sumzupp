package com.sumzupp.backendapp.beans;

/**
 * Created by akash.mercer on 22-Nov-17.
 */
public class UserAccountLinkBean {

    private String primaryUserId;

    private String secondaryUserId;

    public UserAccountLinkBean() {

    }

    public String getPrimaryUserId() {
        return primaryUserId;
    }

    public void setPrimaryUserId(String primaryUserId) {
        this.primaryUserId = primaryUserId;
    }

    public String getSecondaryUserId() {
        return secondaryUserId;
    }

    public void setSecondaryUserId(String secondaryUserId) {
        this.secondaryUserId = secondaryUserId;
    }
}
