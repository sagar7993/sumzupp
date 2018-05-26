package com.sumzupp.backendapp.beans;

import com.sumzupp.backendapp.entities.Assignment;
import com.sumzupp.backendapp.entities.Standard;
import com.sumzupp.backendapp.entities.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by akash.mercer on 18-Jun-17.
 */
public class AssignmentBean extends StatusBean {

    private String assignmentId;

    private String assignmentTitle;

    private Long deadlineDate;

    private Integer marks;

    private Boolean active;

    private String subject;

    private String teacherId;

    private String subjectChapterId;

    private String subjectChapterName;

    private String standardId;

    private String standardName;

    private List<String> standardIds = new ArrayList<>();

    private List<String> standardDivisionIds = new ArrayList<>();

    private List<String> standardDivisionNames = new ArrayList<>();

    private List<String> questionIds = new ArrayList<>();

    private List<String> assignmentTags = new ArrayList<>();

    private List<String> assignmentTagIds = new ArrayList<>();

    private List<QuestionBean> questionBeans = new ArrayList<>();

    private StudentAssignmentBean studentAssignmentBean;

    private Boolean solved = false;

    private Boolean expired = false;

    private Boolean global = false;

    private Boolean locked = false;

    private Standard standardGlobal;

    private User instituteGlobal;

    public AssignmentBean() {

    }

    public AssignmentBean(String assignmentId, String assignmentTitle, Long deadlineDate, Integer marks, String subject, String subjectChapterName, String teacherId, Boolean locked) {
        this.assignmentId = assignmentId;
        this.assignmentTitle = assignmentTitle;
        this.deadlineDate = deadlineDate;
        this.marks = marks;
        this.subject = subject;
        this.subjectChapterName = subjectChapterName;
        this.teacherId = teacherId;
        this.locked = locked;
    }

    public AssignmentBean(String assignmentId, String assignmentTitle, Long deadlineDate, Integer marks, String subject, String subjectChapterName, String teacherId, Boolean global, Boolean locked) {
        this.assignmentId = assignmentId;
        this.assignmentTitle = assignmentTitle;
        this.deadlineDate = deadlineDate;
        this.marks = marks;
        this.subject = subject;
        this.subjectChapterName = subjectChapterName;
        this.teacherId = teacherId;
        this.global = global;
        this.locked = locked;
    }

    public AssignmentBean(String assignmentId, String assignmentTitle, Long deadlineDate, Integer marks, String subject, String subjectChapterName, String teacherId, Boolean global, Boolean locked, Standard standardGlobal, User instituteGlobal) {
        this.assignmentId = assignmentId;
        this.assignmentTitle = assignmentTitle;
        this.deadlineDate = deadlineDate;
        this.marks = marks;
        this.subject = subject;
        this.subjectChapterName = subjectChapterName;
        this.teacherId = teacherId;
        this.global = global;
        this.standardGlobal = standardGlobal;
        this.instituteGlobal = instituteGlobal;
        this.locked = locked;
    }

    public AssignmentBean(String assignmentId, String assignmentTitle, Long deadlineDate, Integer marks, String subject, String teacherId, Boolean locked) {
        this.assignmentId = assignmentId;
        this.assignmentTitle = assignmentTitle;
        this.deadlineDate = deadlineDate;
        this.marks = marks;
        this.subject = subject;
        this.teacherId = teacherId;
        this.locked = locked;
    }

    public AssignmentBean(Assignment assignment) {
        assignmentId = assignment.getId();
        assignmentTitle = assignment.getAssignmentTitle();
        deadlineDate = assignment.getDeadlineDate();
        marks = assignment.getMarks();
        subject = assignment.getSubjectChapter().getSubject().getName();
        locked = assignment.getLocked();
    }

    public String getAssignmentId() {
        return assignmentId;
    }

    public void setAssignmentId(String assignmentId) {
        this.assignmentId = assignmentId;
    }

    public String getAssignmentTitle() {
        return assignmentTitle;
    }

    public void setAssignmentTitle(String assignmentTitle) {
        this.assignmentTitle = assignmentTitle;
    }

    public Long getDeadlineDate() {
        return deadlineDate;
    }

    public void setDeadlineDate(Long deadlineDate) {
        this.deadlineDate = deadlineDate;
    }

    public Integer getMarks() {
        return marks;
    }

    public void setMarks(Integer marks) {
        this.marks = marks;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
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

    public String getStandardId() {
        return standardId;
    }

    public void setStandardId(String standardId) {
        this.standardId = standardId;
    }

    public String getStandardName() {
        return standardName;
    }

    public void setStandardName(String standardName) {
        this.standardName = standardName;
    }

    public List<String> getStandardIds() {
        return standardIds;
    }

    public void setStandardIds(List<String> standardIds) {
        this.standardIds = standardIds;
    }

    public List<String> getStandardDivisionIds() {
        return standardDivisionIds;
    }

    public void setStandardDivisionIds(List<String> standardDivisionIds) {
        this.standardDivisionIds = standardDivisionIds;
    }

    public List<String> getStandardDivisionNames() {
        return standardDivisionNames;
    }

    public void setStandardDivisionNames(List<String> standardDivisionNames) {
        this.standardDivisionNames = standardDivisionNames;
    }

    public List<String> getQuestionIds() {
        return questionIds;
    }

    public void setQuestionIds(List<String> questionIds) {
        this.questionIds = questionIds;
    }

    public List<String> getAssignmentTags() {
        return assignmentTags;
    }

    public void setAssignmentTags(List<String> assignmentTags) {
        this.assignmentTags = assignmentTags;
    }

    public List<String> getAssignmentTagIds() {
        return assignmentTagIds;
    }

    public void setAssignmentTagIds(List<String> assignmentTagIds) {
        this.assignmentTagIds = assignmentTagIds;
    }

    public List<QuestionBean> getQuestionBeans() {
        return questionBeans;
    }

    public void setQuestionBeans(List<QuestionBean> questionBeans) {
        this.questionBeans = questionBeans;
    }

    public StudentAssignmentBean getStudentAssignmentBean() {
        return studentAssignmentBean;
    }

    public void setStudentAssignmentBean(StudentAssignmentBean studentAssignmentBean) {
        this.studentAssignmentBean = studentAssignmentBean;
    }

    public Boolean getSolved() {
        return solved;
    }

    public void setSolved(Boolean solved) {
        this.solved = solved;
    }

    public Boolean getExpired() {
        return expired;
    }

    public void setExpired(Boolean expired) {
        this.expired = expired;
    }

    public Boolean getGlobal() {
        return global;
    }

    public void setGlobal(Boolean global) {
        this.global = global;
    }

    public Boolean getLocked() {
        return locked;
    }

    public void setLocked(Boolean locked) {
        this.locked = locked;
    }

    public Standard getStandardGlobal() {
        return standardGlobal;
    }

    public void setStandardGlobal(Standard standardGlobal) {
        this.standardGlobal = standardGlobal;
    }

    public User getInstituteGlobal() {
        return instituteGlobal;
    }

    public void setInstituteGlobal(User instituteGlobal) {
        this.instituteGlobal = instituteGlobal;
    }

}
