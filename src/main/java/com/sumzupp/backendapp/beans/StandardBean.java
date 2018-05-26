package com.sumzupp.backendapp.beans;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by akash.mercer on 18-Jun-17.
 */
public class StandardBean {

    private String standardId;

    private Integer standardName;

    private List<String> divisionNames = new ArrayList<>();

    private List<String> boardIds = new ArrayList<>();

    public StandardBean() {

    }

    public StandardBean(String standardId, Integer standardName) {
        this.standardId = standardId;
        this.standardName = standardName;
    }

    public String getStandardId() {
        return standardId;
    }

    public void setStandardId(String standardId) {
        this.standardId = standardId;
    }

    public Integer getStandardName() {
        return standardName;
    }

    public void setStandardName(Integer standardName) {
        this.standardName = standardName;
    }

    public List<String> getDivisionNames() {
        return divisionNames;
    }

    public void setDivisionNames(List<String> divisionNames) {
        this.divisionNames = divisionNames;
    }

    public List<String> getBoardIds() {
        return boardIds;
    }

    public void setBoardIds(List<String> boardIds) {
        this.boardIds = boardIds;
    }
}
