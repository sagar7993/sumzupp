package com.sumzupp.backendapp.beans;

import com.sumzupp.backendapp.entities.AppConfig;

/**
 * Created by akash.mercer on 23-Jul-17.
 */
public class AppConfigBean {

    private Integer androidUserAppVersion;

    private Integer androidTeacherAppVersion;

    // TODO: 25-Sep-17 Remove below field after 31st October 2017
    private Integer androidTeacherappVersion;

    private String iosUserAppVersion;

    private String updateMessage;

    private String appMessage;

    public AppConfigBean() {

    }

    public AppConfigBean(AppConfig appConfig) {
        androidUserAppVersion = appConfig.getAndroidUserAppVersion();
        androidTeacherAppVersion = appConfig.getAndroidTeacherAppVersion();
        androidTeacherappVersion = appConfig.getAndroidTeacherAppVersion();
        iosUserAppVersion = appConfig.getIosUserAppVersion();
        updateMessage = appConfig.getUpdateMessage();
        appMessage = appConfig.getAppMessage();
    }

    public Integer getAndroidUserAppVersion() {
        return androidUserAppVersion;
    }

    public void setAndroidUserAppVersion(Integer androidUserAppVersion) {
        this.androidUserAppVersion = androidUserAppVersion;
    }

    public Integer getAndroidTeacherAppVersion() {
        return androidTeacherAppVersion;
    }

    public void setAndroidTeacherAppVersion(Integer androidTeacherAppVersion) {
        this.androidTeacherAppVersion = androidTeacherAppVersion;
    }

    public Integer getAndroidTeacherappVersion() {
        return androidTeacherappVersion;
    }

    public void setAndroidTeacherappVersion(Integer androidTeacherappVersion) {
        this.androidTeacherappVersion = androidTeacherappVersion;
    }

    public String getIosUserAppVersion() {
        return iosUserAppVersion;
    }

    public void setIosUserAppVersion(String iosUserAppVersion) {
        this.iosUserAppVersion = iosUserAppVersion;
    }

    public String getUpdateMessage() {
        return updateMessage;
    }

    public void setUpdateMessage(String updateMessage) {
        this.updateMessage = updateMessage;
    }

    public String getAppMessage() {
        return appMessage;
    }

    public void setAppMessage(String appMessage) {
        this.appMessage = appMessage;
    }
}
