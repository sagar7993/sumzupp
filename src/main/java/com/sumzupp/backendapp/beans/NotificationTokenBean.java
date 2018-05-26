package com.sumzupp.backendapp.beans;

/**
 * Created by akash.mercer on 01-Oct-16.
 */
public class NotificationTokenBean {

    private String userId;

    private String fcmToken;

    public NotificationTokenBean(){

    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFcmToken() {
        return fcmToken;
    }

    public void setFcmToken(String fcmToken) {
        this.fcmToken = fcmToken;
    }
}
