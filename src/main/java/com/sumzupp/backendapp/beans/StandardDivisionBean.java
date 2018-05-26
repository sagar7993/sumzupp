package com.sumzupp.backendapp.beans;

import com.sumzupp.backendapp.entities.StandardDivision;

/**
 * Created by akash.mercer on 07-Jul-17.
 */
public class StandardDivisionBean {

    private String standardDivisionId;

    private Integer standardName;

    private String divisionName;

    public StandardDivisionBean() {

    }

    public StandardDivisionBean(StandardDivision standardDivision) {
        standardName = standardDivision.getStandard().getStandardName();
        divisionName = standardDivision.getDivisionName();
    }

    public String getStandardDivisionId() {
        return standardDivisionId;
    }

    public void setStandardDivisionId(String standardDivisionId) {
        this.standardDivisionId = standardDivisionId;
    }

    public Integer getStandardName() {
        return standardName;
    }

    public void setStandardName(Integer standardName) {
        this.standardName = standardName;
    }

    public String getDivisionName() {
        return divisionName;
    }

    public void setDivisionName(String divisionName) {
        this.divisionName = divisionName;
    }
}
