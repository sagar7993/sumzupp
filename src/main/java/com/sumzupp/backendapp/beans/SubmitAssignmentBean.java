package com.sumzupp.backendapp.beans;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by akash.mercer on 08-Jul-17.
 */

public class SubmitAssignmentBean {

    private String studentAssignmentId;

    private List<QuestionSolutionBean> questionSolutionBeans = new ArrayList<>();

    public SubmitAssignmentBean() {

    }

    public String getStudentAssignmentId() {
        return studentAssignmentId;
    }

    public void setStudentAssignmentId(String studentAssignmentId) {
        this.studentAssignmentId = studentAssignmentId;
    }

    public List<QuestionSolutionBean> getQuestionSolutionBeans() {
        return questionSolutionBeans;
    }

    public void setQuestionSolutionBeans(List<QuestionSolutionBean> questionSolutionBeans) {
        this.questionSolutionBeans = questionSolutionBeans;
    }
}
