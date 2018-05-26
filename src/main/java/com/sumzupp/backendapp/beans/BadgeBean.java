package com.sumzupp.backendapp.beans;

/**
 * Created by akash.mercer on 18-Jun-17.
 */
public class BadgeBean {

    private String name;

    private Integer type;

    private String badgeImageUrl;

    public BadgeBean() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getBadgeImageUrl() {
        return badgeImageUrl;
    }

    public void setBadgeImageUrl(String badgeImageUrl) {
        this.badgeImageUrl = badgeImageUrl;
    }
}
