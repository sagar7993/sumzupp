package com.sumzupp.backendapp.beans;

/**
 * Created by akash.mercer on 09-Sep-17.
 */
public class BoardBean {

    private String boardId;

    private String boardName;

    public BoardBean() {

    }

    public BoardBean(String boardId, String boardName) {
        this.boardId = boardId;
        this.boardName = boardName;
    }

    public String getBoardId() {
        return boardId;
    }

    public void setBoardId(String boardId) {
        this.boardId = boardId;
    }

    public String getBoardName() {
        return boardName;
    }

    public void setBoardName(String boardName) {
        this.boardName = boardName;
    }
}
