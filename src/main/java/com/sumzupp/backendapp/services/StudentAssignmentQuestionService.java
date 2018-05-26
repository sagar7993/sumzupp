package com.sumzupp.backendapp.services;

import com.sumzupp.backendapp.beans.QuestionSolutionSummaryBean;
import com.sumzupp.backendapp.beans.StudentAssignmentQuestionBean;
import com.sumzupp.backendapp.entities.Assignment;
import com.sumzupp.backendapp.entities.StudentAssignmentQuestion;
import com.sumzupp.backendapp.entities.User;
import com.sumzupp.backendapp.repositories.StudentAssignmentQuestionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by akash.mercer on 06-Aug-17.
 */

@Service
public class StudentAssignmentQuestionService {
    private static final String TAG = "StudentAssignmentQuestionService : ";

    @Autowired
    private StudentAssignmentQuestionRepository studentAssignmentQuestionRepository;

    @Autowired
    private StandardDivisionService standardDivisionService;

    @Autowired
    private UserService userService;

    private static Logger debugLogger = LoggerFactory.getLogger("debugLogs");

    private static Logger errorLogger = LoggerFactory.getLogger("errorLogs");

    public void saveAll(List<StudentAssignmentQuestion> studentAssignmentQuestions) throws Exception {
        studentAssignmentQuestionRepository.save(studentAssignmentQuestions);
    }

    public List<QuestionSolutionSummaryBean> getAssignmentSummary(User institute, Assignment assignment) {
        List<StudentAssignmentQuestionBean> studentAssignmentQuestionBeans = studentAssignmentQuestionRepository.findByInstituteAndAssignment(institute, assignment);

        Map<String, QuestionSolutionSummaryBean> questionSolutionSummaryBeanMap = new HashMap<>();

        List<QuestionSolutionSummaryBean> questionSolutionSummaryBeans = new ArrayList<>();

        for (StudentAssignmentQuestionBean studentAssignmentQuestionBean : studentAssignmentQuestionBeans) {
            String key = studentAssignmentQuestionBean.getQuestionId();

            if (questionSolutionSummaryBeanMap.containsKey(key)) {
                QuestionSolutionSummaryBean questionSolutionSummaryBean = questionSolutionSummaryBeanMap.get(key);
                questionSolutionSummaryBean.setAttempted(questionSolutionSummaryBean.getAttempted() + (studentAssignmentQuestionBean.getAttempted() ? 1 : 0));
                questionSolutionSummaryBean.setCorrect(questionSolutionSummaryBean.getCorrect() + (studentAssignmentQuestionBean.getCorrect() ? 1 : 0));
            } else {
                QuestionSolutionSummaryBean questionSolutionSummaryBean = new QuestionSolutionSummaryBean();
                questionSolutionSummaryBean.setQuestionText(studentAssignmentQuestionBean.getQuestionText());
                questionSolutionSummaryBean.setFormula(studentAssignmentQuestionBean.getFormula());
                questionSolutionSummaryBean.setTotal(getAssignmentTotal(institute, assignment));
                questionSolutionSummaryBean.setAttempted(studentAssignmentQuestionBean.getAttempted() ? 1 : 0);
                questionSolutionSummaryBean.setCorrect(studentAssignmentQuestionBean.getCorrect() ? 1 : 0);

                questionSolutionSummaryBeanMap.put(key, questionSolutionSummaryBean);

                questionSolutionSummaryBeans.add(questionSolutionSummaryBean);
            }
        }

        return questionSolutionSummaryBeans;
    }

    public Integer getAssignmentTotal(User institute, Assignment assignment) {
        if (assignment.getStandardGlobal() != null) {
            return userService.getStudentCountByStandardDivisions(standardDivisionService.findByInstituteAndStandard(institute, assignment.getStandardGlobal()));
        } else if (assignment.getInstituteGlobal() != null) {
            return userService.getStudentCountByInstitute(assignment.getInstituteGlobal());
        } else if (assignment.getGlobal()) {
            return userService.getStudentCountByInstitute(institute);
        } else {
            return assignment.getTotal();
        }
    }

    public List<StudentAssignmentQuestion> findByAssignmentAndInstitute(Assignment assignment, User institute) {
        return studentAssignmentQuestionRepository.findByAssignmentAndInstitute(assignment, institute);
    }

    public void updateStudentAssignmentQuestions(User primaryUser, User secondaryUser) {
        studentAssignmentQuestionRepository.updateStudentAssignmentQuestions(primaryUser, secondaryUser);
    }

}
