package com.sumzupp.backendapp.beans;

/**
 * Created by akash.mercer on 18-Jun-17.
 */
public class FollowBean {

    private String followerId;

    private String followingId;

    public FollowBean() {

    }

    public String getFollowerId() {
        return followerId;
    }

    public void setFollowerId(String followerId) {
        this.followerId = followerId;
    }

    public String getFollowingId() {
        return followingId;
    }

    public void setFollowingId(String followingId) {
        this.followingId = followingId;
    }
}
