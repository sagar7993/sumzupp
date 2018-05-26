package com.sumzupp.backendapp.beans;

/**
 * Created by akash.mercer on 22-Mar-18.
 */
public class EventBean {

    private String title;

    private String content;

    private Long eventDate;

    private String heroImageUrl;

    private String eventImageUrls;

    public EventBean() {

    }

    public EventBean(String title, String content, Long eventDate, String heroImageUrl, String eventImageUrls) {
        this.title = title;
        this.content = content;
        this.eventDate = eventDate;
        this.heroImageUrl = heroImageUrl;
        this.eventImageUrls = eventImageUrls;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getEventDate() {
        return eventDate;
    }

    public void setEventDate(Long eventDate) {
        this.eventDate = eventDate;
    }

    public String getHeroImageUrl() {
        return heroImageUrl;
    }

    public void setHeroImageUrl(String heroImageUrl) {
        this.heroImageUrl = heroImageUrl;
    }

    public String getEventImageUrls() {
        return eventImageUrls;
    }

    public void setEventImageUrls(String eventImageUrls) {
        this.eventImageUrls = eventImageUrls;
    }
}
