package com.sumzupp.backendapp.beans;

import java.util.Date;

/**
 * Created by akash.mercer on 18-Jun-17.
 */
public class CommentBean {

    private String commentId;

    private String commentText;

    private String postId;

    private Long date;

    private String userId;

    private String name;

    private String profilePicUrl;

    public CommentBean() {

    }

    public CommentBean(String commentId, String commentText, Date createdAt, String userId, String name, String profilePicUrl) {
        this.commentId = commentId;
        this.commentText = commentText;
        this.date = createdAt.getTime();
        this.userId = userId;
        this.name = name;
        this.profilePicUrl = profilePicUrl;
    }

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfilePicUrl() {
        return profilePicUrl;
    }

    public void setProfilePicUrl(String profilePicUrl) {
        this.profilePicUrl = profilePicUrl;
    }
}
