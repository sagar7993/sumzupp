package com.sumzupp.backendapp.beans;

import com.sumzupp.backendapp.entities.Post;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by akash.mercer on 18-Jun-17.
 */
public class PostBean {

    private String postId;

    private String postText;

    private String postImageUrl;

    private Integer commentCount;

    private Integer upvoteCount;

    private Integer downvoteCount;

    private Long date;

    private String userId;

    private String name;

    private String profilePicUrl;

    private List<String> postTags = new ArrayList<>();

    private List<Integer> tagTypes = new ArrayList<>();

    private List<VoteBean> voteBeans = new ArrayList<>();

    public PostBean() {

    }

    public PostBean(String postId, String postText, String postImageUrl, Integer commentCount, Integer upvoteCount, Integer downvoteCount, Date createdAt, String userId, String name, String profilePicUrl) {
        this.postId = postId;
        this.postText = postText;
        this.postImageUrl = postImageUrl;
        this.commentCount = commentCount;
        this.upvoteCount = upvoteCount;
        this.downvoteCount = downvoteCount;
        this.date = createdAt.getTime();
        this.userId = userId;
        this.name = name;
        this.profilePicUrl = profilePicUrl;
    }

    public PostBean(Post post) {
        this.postId = post.getId();
        this.postText = post.getPostText();
        this.postImageUrl = post.getPostImageUrl();
        this.upvoteCount = post.getUpvoteCount();
        this.downvoteCount = post.getDownvoteCount();
        this.userId = post.getUser().getId();
        this.name = post.getUser().getName();
        this.profilePicUrl = post.getUser().getProfilePicUrl();
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getPostText() {
        return postText;
    }

    public void setPostText(String postText) {
        this.postText = postText;
    }

    public String getPostImageUrl() {
        return postImageUrl;
    }

    public void setPostImageUrl(String postImageUrl) {
        this.postImageUrl = postImageUrl;
    }

    public Integer getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }

    public Integer getUpvoteCount() {
        return upvoteCount;
    }

    public void setUpvoteCount(Integer upvoteCount) {
        this.upvoteCount = upvoteCount;
    }

    public Integer getDownvoteCount() {
        return downvoteCount;
    }

    public void setDownvoteCount(Integer downvoteCount) {
        this.downvoteCount = downvoteCount;
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

    public List<String> getPostTags() {
        return postTags;
    }

    public void setPostTags(List<String> postTags) {
        this.postTags = postTags;
    }

    public List<Integer> getTagTypes() {
        return tagTypes;
    }

    public void setTagTypes(List<Integer> tagTypes) {
        this.tagTypes = tagTypes;
    }

    public List<VoteBean> getVoteBeans() {
        return voteBeans;
    }

    public void setVoteBeans(List<VoteBean> voteBeans) {
        this.voteBeans = voteBeans;
    }
}
