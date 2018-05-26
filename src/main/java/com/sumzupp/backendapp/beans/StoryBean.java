package com.sumzupp.backendapp.beans;

/**
 * Created by akash.mercer on 18-Jun-17.
 */
public class StoryBean {

    private String storyId;

    private String storyText;

    private String storyImageUrl;

    public StoryBean() {

    }

    public String getStoryId() {
        return storyId;
    }

    public void setStoryId(String storyId) {
        this.storyId = storyId;
    }

    public String getStoryText() {
        return storyText;
    }

    public void setStoryText(String storyText) {
        this.storyText = storyText;
    }

    public String getStoryImageUrl() {
        return storyImageUrl;
    }

    public void setStoryImageUrl(String storyImageUrl) {
        this.storyImageUrl = storyImageUrl;
    }
}
