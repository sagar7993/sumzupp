package com.sumzupp.backendapp.beans;

/**
 * Created by akash.mercer on 09-Sep-17.
 */

public class UpdateStandardDivisionBean {

    private String standardId;

    private String boardId;

    public UpdateStandardDivisionBean() {

    }

    public String getStandardId() {
        return standardId;
    }

    public void setStandardId(String standardId) {
        this.standardId = standardId;
    }

    public String getBoardId() {
        return boardId;
    }

    public void setBoardId(String boardId) {
        this.boardId = boardId;
    }
}
