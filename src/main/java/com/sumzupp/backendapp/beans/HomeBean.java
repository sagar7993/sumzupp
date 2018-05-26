package com.sumzupp.backendapp.beans;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by akash.mercer on 20-Jan-18.
 */
public class HomeBean extends StatusBean {

    private UserBean userBean;

    private List<BannerBean> bannerBeans = new ArrayList<>();

    private AppConfigBean appConfigBean;

    private Boolean selectInstitute = false;

    public HomeBean() {

    }

    public UserBean getUserBean() {
        return userBean;
    }

    public void setUserBean(UserBean userBean) {
        this.userBean = userBean;
    }

    public List<BannerBean> getBannerBeans() {
        return bannerBeans;
    }

    public void setBannerBeans(List<BannerBean> bannerBeans) {
        this.bannerBeans = bannerBeans;
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
