package com.sumzupp.backendapp.beans;

/**
 * Created by akash.mercer on 22-Jun-17.
 */
public class PostVoteBean {

    private String userId;

    private String postId;

    private Integer voteType;

    public PostVoteBean() {

    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public Integer getVoteType() {
        return voteType;
    }

    public void setVoteType(Integer voteType) {
        this.voteType = voteType;
    }
}
