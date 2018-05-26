package com.sumzupp.backendapp.beans;

/**
 * Created by akash.mercer on 19-Jul-17.
 */
public class SubjectChapterBean {

    private String subjectChapterId;

    private String subjectChapterName;

    private Integer subjectChapterNumber;

    private String subjectId;

    private String boardId;

    private Boolean locked;

    private Boolean available;

    public SubjectChapterBean() {

    }

    public SubjectChapterBean(String subjectChapterId, String subjectChapterName, Integer subjectChapterNumber, String subjectId, String boardId, Boolean locked) {
        this.subjectChapterId = subjectChapterId;
        this.subjectChapterName = subjectChapterName;
        this.subjectChapterNumber = subjectChapterNumber;
        this.subjectId = subjectId;
        this.boardId = boardId;
        this.locked = locked;
        this.available = true;
    }

    public SubjectChapterBean(String subjectChapterName) {
        this.subjectChapterName = subjectChapterName;
        this.available = false;
        this.locked = true;
    }

    public String getSubjectChapterId() {
        return subjectChapterId;
    }

    public void setSubjectChapterId(String subjectChapterId) {
        this.subjectChapterId = subjectChapterId;
    }

    public String getSubjectChapterName() {
        return subjectChapterName;
    }

    public void setSubjectChapterName(String subjectChapterName) {
        this.subjectChapterName = subjectChapterName;
    }

    public Integer getSubjectChapterNumber() {
        return subjectChapterNumber;
    }

    public void setSubjectChapterNumber(Integer subjectChapterNumber) {
        this.subjectChapterNumber = subjectChapterNumber;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getBoardId() {
        return boardId;
    }

    public void setBoardId(String boardId) {
        this.boardId = boardId;
    }

    public Boolean getLocked() {
        return locked;
    }

    public void setLocked(Boolean locked) {
        this.locked = locked;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SubjectChapterBean)) return false;

        SubjectChapterBean that = (SubjectChapterBean) o;

        return getSubjectChapterId().equals(that.getSubjectChapterId());
    }

    @Override
    public int hashCode() {
        return getSubjectChapterId().hashCode();
    }
}
