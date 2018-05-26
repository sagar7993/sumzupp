package com.sumzupp.backendapp.beans;

/**
 * Created by akash.mercer on 18-Jun-17.
 */
public class AbusePostBean {

    private String postId;

    private String userId;

    public AbusePostBean() {

    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

}
