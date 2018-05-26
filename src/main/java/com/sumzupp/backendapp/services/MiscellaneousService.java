package com.sumzupp.backendapp.services;

import com.google.gson.Gson;
import com.sumzupp.backendapp.beans.*;
import com.sumzupp.backendapp.entities.*;
import com.sumzupp.backendapp.enums.UserTypeEnum;
import com.sumzupp.backendapp.utils.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by akash.mercer on 06-Aug-17.
 */

@Service
public class MiscellaneousService {
    private static final String TAG = "MiscellaneousService : ";

    @Autowired
    private AssignmentService assignmentService;

    @Autowired
    private AssignmentStandardDivisionService assignmentStandardDivisionService;

    @Autowired
    private UserService userService;

    @Autowired
    private StudentAssignmentService studentAssignmentService;

    @Autowired
    private AssignmentQuestionService assignmentQuestionService;

    @Autowired
    private SolutionOptionService solutionOptionService;

    @Autowired
    private StudentAssignmentQuestionService studentAssignmentQuestionService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private PostService postService;

    private static Logger debugLogger = LoggerFactory.getLogger("debugLogs");

    private static Logger errorLogger = LoggerFactory.getLogger("errorLogs");

    @Deprecated
    public StatusBean populateAssignmentTotal() {
        StatusBean statusBean = new StatusBean();

        List<AssignmentStandardDivision> assignmentStandardDivisions = assignmentStandardDivisionService.fetchAll();

        Map<String, List<StandardDivision>> standardDivisionMap = new HashMap<>();

        Map<String, Assignment> assignmentMap = new HashMap<>();

        for (AssignmentStandardDivision assignmentStandardDivision : assignmentStandardDivisions) {
            String key = assignmentStandardDivision.getAssignment().getId();

            if (standardDivisionMap.containsKey(key)) {
                List<StandardDivision> retrievedAssignmentStandardDivisions = standardDivisionMap.get(key);
                retrievedAssignmentStandardDivisions.add(assignmentStandardDivision.getStandardDivision());
            } else {
                List<StandardDivision> newAssignmentStandardDivisions = new ArrayList<>();
                newAssignmentStandardDivisions.add(assignmentStandardDivision.getStandardDivision());
                standardDivisionMap.put(key, newAssignmentStandardDivisions);

                assignmentMap.put(key, assignmentStandardDivision.getAssignment());
            }
        }

        List<Assignment> assignments = new ArrayList<>();

        for (Map.Entry<String, Assignment> assignmentEntry : assignmentMap.entrySet()) {
            String key = assignmentEntry.getKey();

            List<StandardDivision> standardDivisions = standardDivisionMap.get(key);

            Integer studentCount = userService.getStudentCountByStandardDivisions(standardDivisions);

            assignmentEntry.getValue().setTotal(studentCount);

            assignments.add(assignmentEntry.getValue());
        }

        try {
            assignmentService.saveAll(assignments);

            statusBean.setStatus(1);
        } catch (Exception e) {
            errorLogger.error(TAG + "Error in updating Assignment total field with error : " + e.getMessage());

            statusBean.setStatus(0);
            statusBean.setMessage(Constants.SOMETHING_WENT_WRONG);

            return statusBean;
        }

        return statusBean;
    }

    @Deprecated
    public StatusBean populateStudentAssignmentDates() {
        StatusBean statusBean = new StatusBean();

        List<StudentAssignment> studentAssignments = studentAssignmentService.fetchAll();

        for (StudentAssignment studentAssignment : studentAssignments) {
            studentAssignment.setStartDate(studentAssignment.getCreatedAt().getTime() - (studentAssignment.getAssignment().getMarks() * 3 * 60 * 1000));
            studentAssignment.setSubmitDate(studentAssignment.getCreatedAt().getTime());
        }

        try {
            studentAssignmentService.saveAll(studentAssignments);

            statusBean.setStatus(1);
        } catch (Exception e) {
            errorLogger.error(TAG + "Error in updating StudentAssignment total field with error : " + e.getMessage());

            statusBean.setStatus(0);
            statusBean.setMessage(Constants.SOMETHING_WENT_WRONG);

            return statusBean;
        }

        return statusBean;
    }

    @Deprecated
    @Transactional(rollbackFor = {Exception.class})
    public StatusBean populateStudentAssignmentQuestions() throws Exception {
        StatusBean statusBean = new StatusBean();

        List<AssignmentQuestion> assignmentQuestions = assignmentQuestionService.fetchAll();

        Map<String, List<AssignmentQuestion>> assignmentQuestionMap = new HashMap<>();

        List<String> questionIds = new ArrayList<>();

        for (AssignmentQuestion assignmentQuestion : assignmentQuestions) {
            String key = assignmentQuestion.getAssignment().getId();

            if (assignmentQuestionMap.containsKey(key)) {
                List<AssignmentQuestion> retrievedAssignmentQuestions = assignmentQuestionMap.get(key);
                retrievedAssignmentQuestions.add(assignmentQuestion);
            } else {
                List<AssignmentQuestion> newAssignmentQuestions = new ArrayList<>();
                newAssignmentQuestions.add(assignmentQuestion);
                assignmentQuestionMap.put(key, newAssignmentQuestions);
            }

            questionIds.add(assignmentQuestion.getQuestion().getId());
        }

        List<SolutionOptionBean> solutionOptionBeans = solutionOptionService.findByQuestionIdsWithSolution(questionIds);

        Map<String, String> questionSolutionMap = new HashMap<>();

        for (SolutionOptionBean solutionOptionBean : solutionOptionBeans) {
            questionSolutionMap.put(solutionOptionBean.getQuestionId(), solutionOptionBean.getSolutionOptionId());
        }

        for (Map.Entry<String, List<AssignmentQuestion>> assignmentQuestionEntry : assignmentQuestionMap.entrySet()) {
            for (AssignmentQuestion assignmentQuestion : assignmentQuestionEntry.getValue()) {
                assignmentQuestion.setSolutionId(questionSolutionMap.get(assignmentQuestion.getQuestion().getId()));
            }
        }

        List<StudentAssignment> studentAssignments = studentAssignmentService.fetchAll();

        List<StudentAssignmentQuestion> studentAssignmentQuestions = new ArrayList<>();

        for (StudentAssignment studentAssignment : studentAssignments) {
            SubmitAssignmentBean submitAssignmentBean = new Gson().fromJson(studentAssignment.getStudentSolution(), SubmitAssignmentBean.class);

            Map<String, String> studentQuestionSolutionMap = new HashMap<>();

            if (submitAssignmentBean != null && !CollectionUtils.isEmpty(submitAssignmentBean.getQuestionSolutionBeans())) {
                for (QuestionSolutionBean questionSolutionBean : submitAssignmentBean.getQuestionSolutionBeans()) {
                    studentQuestionSolutionMap.put(questionSolutionBean.getQuestionId(), questionSolutionBean.getSolutionOptionId());
                }
            }

            for (AssignmentQuestion assignmentQuestion : assignmentQuestionMap.get(studentAssignment.getAssignment().getId())) {
                StudentAssignmentQuestion studentAssignmentQuestion = new StudentAssignmentQuestion();

                if (submitAssignmentBean != null && !CollectionUtils.isEmpty(submitAssignmentBean.getQuestionSolutionBeans())) {
                    studentAssignmentQuestion.setAttempted(studentQuestionSolutionMap.containsKey(assignmentQuestion.getQuestion().getId()));

                    if (studentAssignmentQuestion.getAttempted()) {
                        studentAssignmentQuestion.setCorrect(assignmentQuestion.getSolutionId().equals(studentQuestionSolutionMap.get(assignmentQuestion.getQuestion().getId())));
                    }
                } else {
                    studentAssignmentQuestion.setAttempted(false);
                    studentAssignmentQuestion.setCorrect(false);
                }

                studentAssignmentQuestion.setStudent(studentAssignment.getStudent());
                studentAssignmentQuestion.setAssignmentQuestion(assignmentQuestion);

                studentAssignmentQuestions.add(studentAssignmentQuestion);
            }
        }

        try {
            studentAssignmentQuestionService.saveAll(studentAssignmentQuestions);

            statusBean.setStatus(1);
        } catch (Exception e) {
            errorLogger.error(TAG + "Error in saving StudentAssignmentQuestions with error : " + e.getMessage());

            statusBean.setStatus(0);
            statusBean.setMessage(Constants.SOMETHING_WENT_WRONG);

            throw e;
        }

        return statusBean;
    }

    @Transactional(rollbackFor = {Exception.class})
    public StatusBean linkUserAccounts(List<UserAccountLinkBean> userAccountLinkBeans) {
        StatusBean statusBean = new StatusBean();

        for (UserAccountLinkBean userAccountLinkBean : userAccountLinkBeans) {
            User retrievedPrimaryUser = userService.findById(userAccountLinkBean.getPrimaryUserId());

            User retrievedSecondaryUser = userService.findById(userAccountLinkBean.getSecondaryUserId());

            if (retrievedPrimaryUser == null) {
                errorLogger.error(TAG + "Error in finding User with id : " + userAccountLinkBean.getPrimaryUserId());

                statusBean.setStatus(0);
                statusBean.setMessage("Error in finding User with id : " + userAccountLinkBean.getPrimaryUserId());

                return statusBean;
            }

            if (retrievedSecondaryUser == null) {
                errorLogger.error(TAG + "Error in finding User with id : " + userAccountLinkBean.getSecondaryUserId());

                statusBean.setStatus(0);
                statusBean.setMessage("Error in finding User with id : " + userAccountLinkBean.getSecondaryUserId());

                return statusBean;
            }

            if (!retrievedPrimaryUser.getUserType().getType().equals(retrievedSecondaryUser.getUserType().getType())) {
                errorLogger.error(TAG + "UserType mismatch for Primary User : " + userAccountLinkBean.getPrimaryUserId() + " & secondary User : " + userAccountLinkBean.getSecondaryUserId());

                statusBean.setStatus(0);
                statusBean.setMessage("UserType mismatch for Primary User : " + userAccountLinkBean.getPrimaryUserId() + " & secondary User : " + userAccountLinkBean.getSecondaryUserId());

                return statusBean;
            }

            retrievedPrimaryUser.setStandardDivision(retrievedSecondaryUser.getStandardDivision());

            userService.save(retrievedPrimaryUser);

            //Update Posts
            try {
                postService.updateUserPosts(retrievedPrimaryUser, retrievedSecondaryUser);
            } catch (Exception e) {
                errorLogger.error(TAG + "Error in linking Posts for User with Primary account : " + userAccountLinkBean.getPrimaryUserId() + " with error : " + e.getMessage());

                statusBean.setStatus(0);
                statusBean.setMessage("Error in linking Posts for User with Primary account : " + userAccountLinkBean.getPrimaryUserId() + " with error : " + e.getMessage());

                throw e;
            }

            //Update Comments
            try {
                commentService.updateUserComments(retrievedPrimaryUser, retrievedSecondaryUser);
            } catch (Exception e) {
                errorLogger.error(TAG + "Error in linking Comments for User with Primary account : " + userAccountLinkBean.getPrimaryUserId() + " with error : " + e.getMessage());

                statusBean.setStatus(0);
                statusBean.setMessage("Error in linking Comments for User with Primary account : " + userAccountLinkBean.getPrimaryUserId() + " with error : " + e.getMessage());

                throw e;
            }

            if (retrievedPrimaryUser.getUserType().getType().equals(UserTypeEnum.STUDENT.getType())) {
                //Update StudentAssignment
                try {
                    studentAssignmentService.updateStudentAssignments(retrievedPrimaryUser, retrievedSecondaryUser);
                } catch (Exception e) {
                    errorLogger.error(TAG + "Error in linking StudentAssignments for User with Primary account : " + userAccountLinkBean.getPrimaryUserId() + " with error : " + e.getMessage());

                    statusBean.setStatus(0);
                    statusBean.setMessage("Error in linking StudentAssignments for User with Primary account : " + userAccountLinkBean.getPrimaryUserId() + " with error : " + e.getMessage());

                    throw e;
                }

                //Update StudentAssignmentQuestion
                try {
                    studentAssignmentQuestionService.updateStudentAssignmentQuestions(retrievedPrimaryUser, retrievedSecondaryUser);
                } catch (Exception e) {
                    errorLogger.error(TAG + "Error in linking StudentAssignmentQuestions for User with Primary account : " + userAccountLinkBean.getPrimaryUserId() + " with error : " + e.getMessage());

                    statusBean.setStatus(0);
                    statusBean.setMessage("Error in linking StudentAssignmentQuestions for User with Primary account : " + userAccountLinkBean.getPrimaryUserId() + " with error : " + e.getMessage());

                    throw e;
                }
            }
        }

        statusBean.setStatus(1);
        return statusBean;
    }
}
