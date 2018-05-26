package com.sumzupp.backendapp.beans;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by akash.mercer on 28-Jul-17.
 */
public class StandardsBean extends StatusBean {

    private List<StandardBean> standardBeans = new ArrayList<>();

    public StandardsBean() {

    }

    public List<StandardBean> getStandardBeans() {
        return standardBeans;
    }

    public void setStandardBeans(List<StandardBean> standardBeans) {
        this.standardBeans = standardBeans;
    }
}
