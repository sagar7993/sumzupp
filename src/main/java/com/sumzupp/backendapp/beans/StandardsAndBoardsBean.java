package com.sumzupp.backendapp.beans;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by akash.mercer on 09-Sep-17.
 */

public class StandardsAndBoardsBean extends StatusBean {

    private List<StandardBean> standardBeans = new ArrayList<>();

    private List<BoardBean> boardBeans = new ArrayList<>();

    public StandardsAndBoardsBean() {

    }

    public List<StandardBean> getStandardBeans() {
        return standardBeans;
    }

    public void setStandardBeans(List<StandardBean> standardBeans) {
        this.standardBeans = standardBeans;
    }

    public List<BoardBean> getBoardBeans() {
        return boardBeans;
    }

    public void setBoardBeans(List<BoardBean> boardBeans) {
        this.boardBeans = boardBeans;
    }
}
