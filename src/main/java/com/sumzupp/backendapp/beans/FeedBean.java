package com.sumzupp.backendapp.beans;

import org.springframework.data.domain.Page;

/**
 * Created by akash.mercer on 21-Jun-17.
 */
public class FeedBean extends StatusBean {

    private Page<PostBean> postPage;

    private UserBean userBean;

    private AppConfigBean appConfigBean;

    private Boolean selectInstitute = false;

    public FeedBean() {

    }

    public Page<PostBean> getPostPage() {
        return postPage;
    }

    public void setPostPage(Page<PostBean> postPage) {
        this.postPage = postPage;
    }

    public UserBean getUserBean() {
        return userBean;
    }

    public void setUserBean(UserBean userBean) {
        this.userBean = userBean;
    }

    public AppConfigBean getAppConfigBean() {
        return appConfigBean;
    }

    public void setAppConfigBean(AppConfigBean appConfigBean) {
        this.appConfigBean = appConfigBean;
    }

    public Boolean getSelectInstitute() {
        return selectInstitute;
    }

    public void setSelectInstitute(Boolean selectInstitute) {
        this.selectInstitute = selectInstitute;
    }
}
