package com.sumzupp.backendapp.beans;

public class BannerBean {

    private String id;

    private String title;

    private String description;

    private String bannerImageUrl;

    private String deepLink;

    private Integer bannerType;

    public BannerBean(){

    }

    public BannerBean(String id, String title, String description, String bannerImageUrl, String deepLink, Integer bannerType) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.bannerImageUrl = bannerImageUrl;
        this.deepLink = deepLink;
        this.bannerType = bannerType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBannerImageUrl() {
        return bannerImageUrl;
    }

    public void setBannerImageUrl(String bannerImageUrl) {
        this.bannerImageUrl = bannerImageUrl;
    }

    public String getDeepLink() {
        return deepLink;
    }

    public void setDeepLink(String deepLink) {
        this.deepLink = deepLink;
    }

    public Integer getBannerType() {
        return bannerType;
    }

    public void setBannerType(Integer bannerType) {
        this.bannerType = bannerType;
    }

}