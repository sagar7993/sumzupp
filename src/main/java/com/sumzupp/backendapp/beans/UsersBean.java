package com.sumzupp.backendapp.beans;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by akash.mercer on 16-Nov-17.
 */
public class UsersBean extends StatusBean {

    private List<UserBean> userBeans = new ArrayList<>();

    public UsersBean() {

    }

    public List<UserBean> getUserBeans() {
        return userBeans;
    }

    public void setUserBeans(List<UserBean> userBeans) {
        this.userBeans = userBeans;
    }
}
