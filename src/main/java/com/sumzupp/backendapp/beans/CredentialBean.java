package com.sumzupp.backendapp.beans;

/**
 * Created by akash.mercer on 20-Jul-17.
 */
public class CredentialBean {

    private String username;

    private String password;

    private String userId;

    public CredentialBean() {

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
