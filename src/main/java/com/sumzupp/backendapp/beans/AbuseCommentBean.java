package com.sumzupp.backendapp.beans;

/**
 * Created by akash.mercer on 18-Jun-17.
 */
public class AbuseCommentBean {

    private String commentId;

    private String userId;

    public AbuseCommentBean() {

    }

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
