package com.sumzupp.backendapp.services;

import com.google.gson.Gson;
import com.sumzupp.backendapp.beans.*;
import com.sumzupp.backendapp.entities.*;
import com.sumzupp.backendapp.enums.UserTypeEnum;
import com.sumzupp.backendapp.repositories.AssignmentRepository;
import com.sumzupp.backendapp.utils.Constants;
import com.sumzupp.backendapp.utils.NotificationManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by akash.mercer on 04-Jun-17.
 */

@Service
public class AssignmentService {
    private static final String TAG = "AssignmentService : ";

    @Autowired
    private AssignmentRepository assignmentRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private AssignmentStandardDivisionService assignmentStandardDivisionService;

    @Autowired
    private AssignmentQuestionService assignmentQuestionService;

    @Autowired
    private SubjectChapterService subjectChapterService;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private StandardService standardService;

    @Autowired
    private StandardDivisionService standardDivisionService;

    @Autowired
    private StudentAssignmentService studentAssignmentService;

    @Autowired
    private SolutionOptionService solutionOptionService;

    @Autowired
    private StudentAssignmentQuestionService studentAssignmentQuestionService;

    @Autowired
    private TeacherInstituteService teacherInstituteService;

    @Autowired
    private TeacherSubjectService teacherSubjectService;

    @Autowired
    private EntityManager entityManager;

    private static Logger debugLogger = LoggerFactory.getLogger("debugLogs");

    private static Logger errorLogger = LoggerFactory.getLogger("errorLogs");

    public void saveAll(List<Assignment> assignments) throws Exception {
        assignmentRepository.save(assignments);
    }

    public Assignment findById(String assignmentId) {
        return assignmentRepository.findOne(assignmentId);
    }

    public AssignmentsListBean getAllGlobalAssignments() {
        AssignmentsListBean assignmentsListBean = new AssignmentsListBean(0, Constants.SOMETHING_WENT_WRONG);
        List<AssignmentBean> assignmentBeanList = assignmentRepository.getAllGlobalAssignments();
        assignmentBeanList = removeDuplicateAssignments(assignmentBeanList);
        assignmentsListBean.setAssignmentsList(assignmentBeanList);
        if(assignmentsListBean.getAssignmentsList().size() > 0) {
            assignmentsListBean.setStatus(1);
        }
        return assignmentsListBean;
    }

    public AssignmentsListBean getAllSuperGlobalAssignments() {
        AssignmentsListBean assignmentsListBean = new AssignmentsListBean(0, Constants.SOMETHING_WENT_WRONG);
        List<AssignmentBean> assignmentBeanList = assignmentRepository.getAllSuperGlobalAssignments();
        assignmentBeanList = removeDuplicateAssignments(assignmentBeanList);
        assignmentsListBean.setAssignmentsList(assignmentBeanList);
        if(assignmentsListBean.getAssignmentsList().size() > 0) {
            assignmentsListBean.setStatus(1);
        }
        return assignmentsListBean;
    }

    public AssignmentsListBean getAllStandardListGlobalAssignments(AssignmentBean assignmentBean) {
        AssignmentsListBean assignmentsListBean = new AssignmentsListBean(0, Constants.SOMETHING_WENT_WRONG);
        List<String> standardIds = assignmentBean.getStandardIds();
        List<AssignmentBean> assignmentBeanList = assignmentRepository.getAllStandardListGlobalAssignments(standardIds);
        assignmentBeanList = removeDuplicateAssignments(assignmentBeanList);
        assignmentsListBean.setAssignmentsList(assignmentBeanList);
        if(assignmentsListBean.getAssignmentsList().size() > 0) {
            assignmentsListBean.setStatus(1);
        }
        return assignmentsListBean;
    }

    public AssignmentsListBean getAllStandardGlobalAssignments(String standardId) {
        AssignmentsListBean assignmentsListBean = new AssignmentsListBean(0, Constants.SOMETHING_WENT_WRONG);
        List<AssignmentBean> assignmentBeanList = assignmentRepository.getAllStandardGlobalAssignments(standardId);
        assignmentBeanList = removeDuplicateAssignments(assignmentBeanList);
        assignmentsListBean.setAssignmentsList(assignmentBeanList);
        if(assignmentsListBean.getAssignmentsList().size() > 0) {
            assignmentsListBean.setStatus(1);
        }
        return assignmentsListBean;
    }

    public AssignmentsListBean getAllInstituteGlobalAssignments(String instituteId) {
        AssignmentsListBean assignmentsListBean = new AssignmentsListBean(0, Constants.SOMETHING_WENT_WRONG);
        List<AssignmentBean> assignmentBeanList = assignmentRepository.getAllInstituteGlobalAssignments(instituteId);
        assignmentBeanList = removeDuplicateAssignments(assignmentBeanList);
        assignmentsListBean.setAssignmentsList(assignmentBeanList);
        if(assignmentsListBean.getAssignmentsList().size() > 0) {
            assignmentsListBean.setStatus(1);
        }
        return assignmentsListBean;
    }

    public QuizBean getQuizTermsAndConditions(String assignmentId) {
        QuizBean quizBean = new QuizBean();

        Assignment retrievedAssignment = assignmentRepository.findOne(assignmentId);

        if (retrievedAssignment == null) {
            errorLogger.error(TAG + "Error in finding Assignment with id : " + assignmentId);

            quizBean.setStatus(0);
            quizBean.setMessage(Constants.SOMETHING_WENT_WRONG);
            return quizBean;
        }

        quizBean.setStartTime(retrievedAssignment.getStartDate());

        // TODO: 4/4/18 Remove below null check after full quiz feature implementation
        if (retrievedAssignment.getStartDate() != null) {
            quizBean.setStarted(System.currentTimeMillis() >= retrievedAssignment.getStartDate());
        }

        quizBean.setEnded(System.currentTimeMillis() >= retrievedAssignment.getDeadlineDate());

        quizBean.setTermsAndConditions("Terms & Conditions :\n"
                + "1) No food and drinks allowed.\n"
                + "2) Prize money will be rigged.");

        quizBean.setStatus(1);
        return quizBean;
    }

    public AssignmentsListBean findByUser(String userId) {
        AssignmentsListBean assignmentsListBean = new AssignmentsListBean();
        User retrievedUser = userService.findById(userId);
        if (retrievedUser == null) {
            errorLogger.error(TAG + "Error in finding User with id : " + userId);
            assignmentsListBean.setStatus(0);
            assignmentsListBean.setMessage(Constants.SOMETHING_WENT_WRONG);
            return assignmentsListBean;
        }
        List<AssignmentBean> assignmentBeanList = assignmentRepository.findByTeacher(retrievedUser);
        for(AssignmentBean assignmentBean : assignmentBeanList) {
            List<String> standardDivisionNames = new ArrayList<>();
            List<AssignmentStandardDivision> assignmentStandardDivisionList = assignmentStandardDivisionService.findByAssignmentId(assignmentBean.getAssignmentId());
            for(AssignmentStandardDivision assignmentStandardDivision : assignmentStandardDivisionList) {
                standardDivisionNames.add(assignmentStandardDivision.getStandardDivision().getStandard().getStandardName() + " " + assignmentStandardDivision.getStandardDivision().getDivisionName());
            }
            assignmentBean.setStandardDivisionNames(standardDivisionNames);
        }
        assignmentsListBean.setAssignmentsList(assignmentBeanList);
        assignmentsListBean.setStatus(1);
        return assignmentsListBean;
    }

    private List<Standard> removeDuplicatesFromStandardList(List<Standard> list) {
        HashSet<Object> seen = new HashSet<>();
        list.removeIf(item->!seen.add(item.getId()));
        return list;
    }

    private List<AssignmentBean> removeDuplicateAssignments(List<AssignmentBean> assignmentList) {
        HashSet<Object> seen = new HashSet<>();
        List<String> standardDivisionNames = new ArrayList<>();
        assignmentList.removeIf(assignment->!seen.add(assignment.getAssignmentId()));
        for(AssignmentBean assignmentBean : assignmentList) {
            Assignment assignment = assignmentRepository.findAssignmentById(assignmentBean.getAssignmentId());
            if(assignment.getStandardGlobal() != null) {
                assignmentBean.setStandardGlobal(assignment.getStandardGlobal());
                standardDivisionNames.add(assignment.getStandardGlobal().getStandardName() + "th\tStandard");
            } else if(assignment.getInstituteGlobal() != null) {
                assignmentBean.setInstituteGlobal(assignment.getInstituteGlobal());
                standardDivisionNames.add(assignment.getInstituteGlobal().getName());
            } else if(assignment.getGlobal()) {
                standardDivisionNames.add("All");
            } else {
                List<AssignmentStandardDivision> assignmentStandardDivisionList = assignmentStandardDivisionService.findByAssignmentId(assignmentBean.getAssignmentId());
                for(AssignmentStandardDivision assignmentStandardDivision : assignmentStandardDivisionList) {
                    standardDivisionNames.add(assignmentStandardDivision.getStandardDivision().getStandard().getStandardName() + " " + assignmentStandardDivision.getStandardDivision().getDivisionName());
                }
            }
            assignmentBean.setStandardDivisionNames(standardDivisionNames);
            standardDivisionNames = new ArrayList<>();
        }
        return assignmentList;
    }

    private List<AssignmentBean> getGlobalAsignmentsForTeacher(User teacher) {
        List<AssignmentBean> globalAssignments = new ArrayList<>();
        TeacherInstitute teacherInstitute = teacherInstituteService.findByTeacher(teacher);
        List<TeacherSubject> teacherSubjectList = teacherSubjectService.findTeacherSubjectByTeacher(teacher);
        List<AssignmentBean> standardGlobalAssignments = new ArrayList<>();
        List<AssignmentBean> instituteGlobalAssignments = new ArrayList<>();
        List<AssignmentBean> superGlobalAssignments = assignmentRepository.getAllSuperGlobalAssignments();
        globalAssignments.addAll(superGlobalAssignments);
        if(teacher.getUserType().getType() == 2) {
            if(teacherInstitute == null || CollectionUtils.isEmpty(teacherSubjectList)) {
                return null;
            }
            List<String> standardIdList = teacherSubjectList.stream().map((teacherSubject) -> teacherSubject.getStandardDivision().getStandard().getId()).collect(Collectors.toList());
            standardGlobalAssignments = assignmentRepository.getAllStandardListGlobalAssignments(standardIdList);
            instituteGlobalAssignments = assignmentRepository.getAllInstituteGlobalAssignments(teacherInstitute.getInstitute().getId());
        } else if(teacher.getUserType().getType() == 4) {
            List<StandardDivision> standardDivisionList = standardDivisionService.findByInstitute(teacher.getId());
            if(CollectionUtils.isEmpty(standardDivisionList)) {
                return null;
            }
            List<String> standardIdList = standardDivisionList.stream().map((standardDivision) -> standardDivision.getStandard().getId()).collect(Collectors.toList());
            standardGlobalAssignments = assignmentRepository.getAllStandardListGlobalAssignments(standardIdList);
            instituteGlobalAssignments = assignmentRepository.getAllInstituteGlobalAssignments(teacher.getId());
        }
        globalAssignments.addAll(standardGlobalAssignments);
        globalAssignments.addAll(instituteGlobalAssignments);
        globalAssignments = removeDuplicateAssignments(globalAssignments);
        return globalAssignments;
    }

    public AssignmentsListBean findByTeacher(String teacherId) {
        AssignmentsListBean assignmentsListBean = new AssignmentsListBean();
        User retrievedTeacher = userService.findById(teacherId);
        if (retrievedTeacher == null) {
            errorLogger.error(TAG + "Error in finding Teacher with id : " + teacherId);
            assignmentsListBean.setStatus(0);
            assignmentsListBean.setMessage(Constants.SOMETHING_WENT_WRONG);
            return assignmentsListBean;
        }
        List<User> teachersOrInstituteForInstituteByTeacher = userService.findAllTeachersAndInstituteForInstituteByTeacher(retrievedTeacher);
        List<AssignmentBean> assignmentBeanList = assignmentRepository.findByTeacherList(teachersOrInstituteForInstituteByTeacher);
        List<AssignmentBean> teacherGlobalAssignments = getGlobalAsignmentsForTeacher(retrievedTeacher);
        if(teacherGlobalAssignments == null) {
            assignmentsListBean.setStatus(1);
            if(retrievedTeacher.getUserType().getType() == 2) {
                assignmentsListBean.setMessage("Teacher is mapped to institute or does not belong to any standard / divisions");
            } else {
                assignmentsListBean.setMessage("No standard / divisions found for this user");
            }
            return assignmentsListBean;
        }
        assignmentBeanList.addAll(teacherGlobalAssignments);
        assignmentBeanList = removeDuplicateAssignments(assignmentBeanList);
        assignmentsListBean.setAssignmentsList(assignmentBeanList);
        assignmentsListBean.setStatus(1);
        return assignmentsListBean;
    }

    public AssignmentBean findByAssignment(String assignmentId) {
        AssignmentBean retrievedAssignment = assignmentRepository.findById(assignmentId);
        if (retrievedAssignment == null) {
            errorLogger.error(TAG + "Error in finding Assignment with id : " + assignmentId);
            retrievedAssignment.setStatus(0);
            retrievedAssignment.setMessage(Constants.SOMETHING_WENT_WRONG);
            return retrievedAssignment;
        }
        List<QuestionBean> questionBeanList = assignmentQuestionService.findByAssignment(retrievedAssignment.getAssignmentId());
        if(CollectionUtils.isEmpty(questionBeanList)) {
            errorLogger.error(TAG + "Error in finding QuestionBeans for assignment with id : " + assignmentId);
            retrievedAssignment.setStatus(0);
            retrievedAssignment.setMessage(Constants.SOMETHING_WENT_WRONG);
            return retrievedAssignment;
        }
        retrievedAssignment.setQuestionBeans(questionBeanList);
        List<String> standardDivisionNames = new ArrayList<>();
        List<AssignmentStandardDivision> assignmentStandardDivisionList = assignmentStandardDivisionService.findByAssignmentId(retrievedAssignment.getAssignmentId());
        for(AssignmentStandardDivision assignmentStandardDivision : assignmentStandardDivisionList) {
            standardDivisionNames.add(assignmentStandardDivision.getStandardDivision().getStandard().getStandardName() + " " + assignmentStandardDivision.getStandardDivision().getDivisionName());
        }
        retrievedAssignment.setStandardDivisionNames(standardDivisionNames);
        retrievedAssignment.setStatus(1);
        return retrievedAssignment;
    }

    public AssignmentsBean findByUserAndSubjectChapter(String userId, String subjectChapterId, Pageable pageable) {
        AssignmentsBean assignmentsBean = new AssignmentsBean();

        User retrievedUser = userService.findById(userId);

        if (retrievedUser == null) {
            errorLogger.error(TAG + "Error in finding User with id : " + userId);

            assignmentsBean.setStatus(0);
            assignmentsBean.setMessage(Constants.SOMETHING_WENT_WRONG);

            return assignmentsBean;
        }

        SubjectChapter retrievedSubjectChapter = subjectChapterService.findById(subjectChapterId);

        if (retrievedSubjectChapter == null) {
            errorLogger.error(TAG + "Error in finding SubjectChapter with id : " + subjectChapterId);

            assignmentsBean.setStatus(0);
            assignmentsBean.setMessage(Constants.SOMETHING_WENT_WRONG);

            return assignmentsBean;
        }

        Page<AssignmentBean> assignmentBeanPage;

        AssignmentsPage assignmentsPage = new AssignmentsPage();

        if (UserTypeEnum.STUDENT.getType() == retrievedUser.getUserType().getType()) {
            List<AssignmentBean> globalAssignmentBeans = assignmentRepository.findByGlobalAndSubjectChapter(retrievedSubjectChapter);

            List<AssignmentBean> standardGlobalAssignmentBeans = assignmentRepository.findByStandardGlobalAndSubjectChapter(retrievedUser.getStandardDivision().getStandard(), retrievedSubjectChapter);

            List<AssignmentBean> instituteGlobalAssignmentBeans = assignmentRepository.findByInstituteGlobalAndSubjectChapter(retrievedUser.getStandardDivision().getInstitute(), retrievedSubjectChapter);

            if (!CollectionUtils.isEmpty(globalAssignmentBeans)) {
                assignmentsPage.getContent().addAll(globalAssignmentBeans);
            }

            if (!CollectionUtils.isEmpty(standardGlobalAssignmentBeans)) {
                assignmentsPage.getContent().addAll(standardGlobalAssignmentBeans);
            }

            if (!CollectionUtils.isEmpty(instituteGlobalAssignmentBeans)) {
                assignmentsPage.getContent().addAll(instituteGlobalAssignmentBeans);
            }

            // FIXME: 23-Sep-17 Fix below query to maintain default page functionality
            assignmentBeanPage = assignmentStandardDivisionService.findByStandardDivisionAndSubjectChapter(retrievedUser.getStandardDivision(), retrievedSubjectChapter, pageable);

            assignmentsPage.getContent().addAll(assignmentBeanPage.getContent());

            List<StudentAssignmentBean> studentAssignmentBeans = studentAssignmentService.findByStudent(retrievedUser);

            Map<String, StudentAssignmentBean> studentAssignmentBeanMap = new HashMap<>();

            for (StudentAssignmentBean studentAssignmentBean : studentAssignmentBeans) {
                studentAssignmentBeanMap.put(studentAssignmentBean.getAssignmentId(), studentAssignmentBean);
            }

            for (AssignmentBean assignmentBean : assignmentsPage.getContent()) {
                String key = assignmentBean.getAssignmentId();

                if (studentAssignmentBeanMap.containsKey(key)) {
                    assignmentBean.setStudentAssignmentBean(studentAssignmentBeanMap.get(key));

                    if (StringUtils.isEmpty(studentAssignmentBeanMap.get(key).getStudentSolution())) {
                        assignmentBean.setSolved(false);

                        if (System.currentTimeMillis() >= assignmentBean.getDeadlineDate()) {
                            assignmentBean.setExpired(true);
                        }
                    } else {
                        assignmentBean.setSolved(true);
                    }

                    continue;
                } else {
                    if (System.currentTimeMillis() >= assignmentBean.getDeadlineDate()) {
                        assignmentBean.setExpired(true);
                    }
                }
            }
        } else {
            TeacherInstitute retrievedTeacherInstitute = teacherInstituteService.findByTeacher(retrievedUser);

            if (retrievedTeacherInstitute == null) {
                errorLogger.error(TAG + "Error in finding TeacherInstitute with teacherId : " + userId);

                assignmentsBean.setStatus(0);
                assignmentsBean.setMessage(Constants.SOMETHING_WENT_WRONG);

                return assignmentsBean;
            }

            List<AssignmentBean> globalAssignmentBeans = assignmentRepository.findByGlobalAndSubjectChapter(retrievedSubjectChapter);

            List<AssignmentBean> standardGlobalAssignmentBeans = assignmentRepository.findByStandardGlobalAndSubjectChapter(retrievedSubjectChapter.getStandard(), retrievedSubjectChapter);

            List<AssignmentBean> instituteGlobalAssignmentBeans = assignmentRepository.findByInstituteGlobalAndSubjectChapter(retrievedTeacherInstitute.getInstitute(), retrievedSubjectChapter);

            if (!CollectionUtils.isEmpty(globalAssignmentBeans)) {
                assignmentsPage.getContent().addAll(globalAssignmentBeans);
            }

            if (!CollectionUtils.isEmpty(standardGlobalAssignmentBeans)) {
                assignmentsPage.getContent().addAll(standardGlobalAssignmentBeans);
            }

            if (!CollectionUtils.isEmpty(instituteGlobalAssignmentBeans)) {
                assignmentsPage.getContent().addAll(instituteGlobalAssignmentBeans);
            }

            assignmentBeanPage = assignmentStandardDivisionService.findByInstituteAndSubjectChapter(retrievedTeacherInstitute.getInstitute(), retrievedSubjectChapter, pageable);

            assignmentsPage.getContent().addAll(assignmentBeanPage.getContent());
        }

        assignmentsBean.setAssignmentsPage(assignmentsPage);

        assignmentsBean.setStatus(1);
        return assignmentsBean;
    }

//    public List<String> findByUserAndBoard(String userId, String boardId) {
//        List<String> assignmentIds = new ArrayList<>();
//
//        assignmentIds.addAll(assignmentRepository.findByGlobalAndSubjectChapter(retrievedSubjectChapter));
//
//        return assignmentIds;
//    }

    public AssignmentDetailsBean findById(String userId, String assignmentId) {
        AssignmentDetailsBean assignmentDetailsBean = new AssignmentDetailsBean();

        User retrievedUser = userService.findById(userId);

        if (retrievedUser == null) {
            errorLogger.error(TAG + "Error in finding User with id : " + userId);

            assignmentDetailsBean.setStatus(0);
            assignmentDetailsBean.setMessage(Constants.SOMETHING_WENT_WRONG);

            return assignmentDetailsBean;
        }

        Assignment retrievedAssignment = assignmentRepository.findOne(assignmentId);

        if (retrievedAssignment == null) {
            errorLogger.error(TAG + "Error in finding Assignment with id : " + assignmentId);

            assignmentDetailsBean.setStatus(0);
            assignmentDetailsBean.setMessage(Constants.SOMETHING_WENT_WRONG);

            return assignmentDetailsBean;
        }

        AssignmentBean assignmentBean = new AssignmentBean(retrievedAssignment);
        assignmentBean.setQuestionBeans(assignmentQuestionService.findByAssignment(retrievedAssignment.getId()));

        if (UserTypeEnum.STUDENT.getType() == retrievedUser.getUserType().getType()) {
            assignmentBean.setStudentAssignmentBean(studentAssignmentService.findByStudentAndAssignment(retrievedUser, retrievedAssignment));

            if (assignmentBean.getStudentAssignmentBean() != null) {
                if (!StringUtils.isEmpty(assignmentBean.getStudentAssignmentBean().getStudentSolution()) || System.currentTimeMillis() >= assignmentBean.getDeadlineDate()) {
                    if (System.currentTimeMillis() >= assignmentBean.getDeadlineDate()) {
                        assignmentBean.setExpired(true);
                    }

                    if (!StringUtils.isEmpty(assignmentBean.getStudentAssignmentBean().getStudentSolution())) {
                        assignmentBean.setSolved(true);
                    }

                    SubmitAssignmentBean submitAssignmentBean = new Gson().fromJson(assignmentBean.getStudentAssignmentBean().getStudentSolution(), SubmitAssignmentBean.class);

                    if (submitAssignmentBean != null) {
                        Map<String, String> studentSolutionMap = new HashMap<>();

                        for (QuestionSolutionBean questionSolutionBean : submitAssignmentBean.getQuestionSolutionBeans()) {
                            studentSolutionMap.put(questionSolutionBean.getQuestionId(), questionSolutionBean.getSolutionOptionId());
                        }

                        for (QuestionBean questionBean : assignmentBean.getQuestionBeans()) {
                            for (SolutionOptionBean solutionOptionBean : questionBean.getSolutionOptionBeans()) {
                                if (solutionOptionBean.getSolutionOptionId().equals(studentSolutionMap.get(questionBean.getQuestionId()))) {
                                    solutionOptionBean.setSelectedSolution(true);
                                    questionBean.setSolved(true);

                                    break;
                                }
                            }
                        }
                    }
                } else {
                    assignmentBean.setSolved(false);
                }
            } else {
                StudentAssignment studentAssignment = new StudentAssignment();
                studentAssignment.setStartDate(System.currentTimeMillis());
                studentAssignment.setStudent(retrievedUser);
                studentAssignment.setAssignment(retrievedAssignment);

                try {
                    studentAssignmentService.save(studentAssignment);

                    assignmentBean.setStudentAssignmentBean(new StudentAssignmentBean(studentAssignment));
                } catch (Exception e) {
                    errorLogger.error(TAG + "Error in creating StudentAssignment with error : " + e.getMessage());

                    assignmentDetailsBean.setStatus(0);
                    assignmentDetailsBean.setMessage(Constants.SOMETHING_WENT_WRONG);

                    return assignmentDetailsBean;
                }

                if (System.currentTimeMillis() >= assignmentBean.getDeadlineDate()) {
                    assignmentBean.setExpired(true);
                }
            }
        }

        assignmentDetailsBean.setAssignmentBean(assignmentBean);

        assignmentDetailsBean.setStatus(1);
        return assignmentDetailsBean;
    }

    @Transactional
    public StatusBean submit(SubmitAssignmentBean submitAssignmentBean) {
        StatusBean statusBean = new StatusBean();

        StudentAssignment retrievedStudentAssignment = studentAssignmentService.findById(submitAssignmentBean.getStudentAssignmentId());

        if (retrievedStudentAssignment == null) {
            errorLogger.error(TAG + "Error in finding StudentAssignment with id : " + submitAssignmentBean.getStudentAssignmentId());

            statusBean.setStatus(0);
            statusBean.setMessage(Constants.SOMETHING_WENT_WRONG);

            return statusBean;
        }

        List<AssignmentQuestion> assignmentQuestions = assignmentQuestionService.findByAssignment(retrievedStudentAssignment.getAssignment());

        List<StudentAssignmentQuestion> studentAssignmentQuestions = new ArrayList<>();

        for (AssignmentQuestion assignmentQuestion : assignmentQuestions) {
            StudentAssignmentQuestion studentAssignmentQuestion = new StudentAssignmentQuestion();
            studentAssignmentQuestion.setAssignmentQuestion(assignmentQuestion);
            studentAssignmentQuestion.setStudent(retrievedStudentAssignment.getStudent());

            studentAssignmentQuestions.add(studentAssignmentQuestion);
        }

        retrievedStudentAssignment.setAttempted(submitAssignmentBean.getQuestionSolutionBeans().size());

        int correct = 0;

        if (!CollectionUtils.isEmpty(submitAssignmentBean.getQuestionSolutionBeans())) {
            List<String> questionIds = new ArrayList<>();

            for (QuestionSolutionBean questionSolutionBean : submitAssignmentBean.getQuestionSolutionBeans()) {
                questionIds.add(questionSolutionBean.getQuestionId());
            }

            List<SolutionOptionBean> solutionOptionBeans = solutionOptionService.findByQuestionIdsWithSolution(questionIds);

            Map<String, StudentAssignmentQuestion> studentAssignmentQuestionMap = new HashMap<>();

            for (StudentAssignmentQuestion studentAssignmentQuestion : studentAssignmentQuestions) {
                studentAssignmentQuestionMap.put(studentAssignmentQuestion.getAssignmentQuestion().getQuestion().getId(), studentAssignmentQuestion);
            }

            Map<String, SolutionOptionBean> solutionOptionBeanMap = new HashMap<>();

            for (SolutionOptionBean solutionOptionBean : solutionOptionBeans) {
                solutionOptionBeanMap.put(solutionOptionBean.getQuestionId(), solutionOptionBean);
            }

            int marks = 0;

            for (QuestionSolutionBean questionSolutionBean : submitAssignmentBean.getQuestionSolutionBeans()) {
                String key = questionSolutionBean.getQuestionId();

                StudentAssignmentQuestion studentAssignmentQuestion = studentAssignmentQuestionMap.get(key);
                studentAssignmentQuestion.setAttempted(true);

                if (solutionOptionBeanMap.get(key).getSolutionOptionId().equals(questionSolutionBean.getSolutionOptionId())) {
                    studentAssignmentQuestion.setCorrect(true);

                    marks += solutionOptionBeanMap.get(key).getMarks();
                    correct++;
                }
            }

            retrievedStudentAssignment.setMarks(marks);
            retrievedStudentAssignment.setCorrect(correct);
        } else {
            retrievedStudentAssignment.setMarks(0);
            retrievedStudentAssignment.setCorrect(0);
        }

        retrievedStudentAssignment.setStudentSolution(new Gson().toJson(submitAssignmentBean));
        retrievedStudentAssignment.setSubmitDate(System.currentTimeMillis());

        if (retrievedStudentAssignment.getAssignment().getSubjectChapter().getSubject().getType() == 5) {
            try {
                retrievedStudentAssignment.getStudent().setQuizScore(retrievedStudentAssignment.getStudent().getQuizScore() + correct);

                entityManager.merge(retrievedStudentAssignment.getStudent());
            } catch (Exception e) {
                errorLogger.error(TAG + "Error in Updating Student Quiz Score for StudentAssignment with id : " + submitAssignmentBean.getStudentAssignmentId() + " with error : " + e.getMessage());

                statusBean.setStatus(0);
                statusBean.setMessage(Constants.SOMETHING_WENT_WRONG);

                return statusBean;
            }
        }

        try {
            studentAssignmentQuestionService.saveAll(studentAssignmentQuestions);

            try {
                entityManager.merge(retrievedStudentAssignment);
            } catch (Exception e) {
                errorLogger.error(TAG + "Error in updating StudentAssignment with id : " + submitAssignmentBean.getStudentAssignmentId() + " with error : " + e.getMessage());

                statusBean.setStatus(0);
                statusBean.setMessage(Constants.SOMETHING_WENT_WRONG);

                return statusBean;
            }
        } catch (Exception e) {
            errorLogger.error(TAG + "Error in saving StudentAssignmentQuestions for StudentAssignment with id : " + submitAssignmentBean.getStudentAssignmentId() + " with error : " + e.getMessage());

            statusBean.setStatus(0);
            statusBean.setMessage(Constants.SOMETHING_WENT_WRONG);

            return statusBean;
        }

        statusBean.setStatus(1);
        return statusBean;
    }

    @Transactional(rollbackFor = {Exception.class})
    public StatusBean save(AssignmentBean assignmentBean) {
        StatusBean statusBean = new StatusBean(); int marks = 0;
        List<StandardDivision> standardDivisions = new ArrayList<>();
        User retrievedTeacher = userService.findById(assignmentBean.getTeacherId());
        if (retrievedTeacher == null) {
            errorLogger.error(TAG + "Error in finding Teacher with id : " + assignmentBean.getTeacherId());
            statusBean.setStatus(0);
            statusBean.setMessage(Constants.SOMETHING_WENT_WRONG);
            return statusBean;
        }
        Assignment assignment = new Assignment();
        SubjectChapter retrievedSubjectChapter = subjectChapterService.findById(assignmentBean.getSubjectChapterId());
        if (retrievedSubjectChapter == null) {
            errorLogger.error(TAG + "Error in finding SubjectChapter with id : " + assignmentBean.getSubjectChapterId());
            statusBean.setStatus(0);
            statusBean.setMessage(Constants.SOMETHING_WENT_WRONG);
            return statusBean;
        }
        assignment.setSubjectChapter(retrievedSubjectChapter);
        if(assignmentBean.getGlobal()) {
            Standard retrievedStandard = standardService.findById(assignmentBean.getStandardId());

            if (retrievedStandard == null) {
                errorLogger.error(TAG + "Error in finding Standard with id : " + assignmentBean.getStandardId());
                statusBean.setStatus(0);
                statusBean.setMessage(Constants.SOMETHING_WENT_WRONG);
                return statusBean;
            }

            assignment.setStandardGlobal(retrievedStandard);

            standardDivisions = standardDivisionService.findByStandard(retrievedStandard);
        } else {
            standardDivisions = standardDivisionService.findByIds(assignmentBean.getStandardDivisionIds());
            if (CollectionUtils.isEmpty(standardDivisions)) {
                errorLogger.error(TAG + "Error in finding StandardDivisions with ids : " + assignmentBean.getStandardDivisionIds());
                statusBean.setStatus(0);
                statusBean.setMessage(Constants.SOMETHING_WENT_WRONG);
                return statusBean;
            }
            Integer studentCount = userService.getStudentCountByStandardDivisions(standardDivisions);
            assignment.setTotal(studentCount);
        }
        assignment.setAssignmentTitle(assignmentBean.getAssignmentTitle());
        assignment.setDeadlineDate(assignmentBean.getDeadlineDate());
        assignment.setMarks(assignmentBean.getMarks());
        assignment.setActive(true);
        assignment.setTeacher(retrievedTeacher);
        List<Question> questions = questionService.findByIds(assignmentBean.getQuestionIds());
        if (CollectionUtils.isEmpty(questions)) {
            errorLogger.error(TAG + "Error in finding Questions with ids : " + assignmentBean.getQuestionIds());
            statusBean.setStatus(0);
            statusBean.setMessage(Constants.SOMETHING_WENT_WRONG);
            return statusBean;
        }
        List<AssignmentQuestion> assignmentQuestions = new ArrayList<>();
        for (Question question : questions) {
            marks += question.getMarks();
            AssignmentQuestion assignmentQuestion = new AssignmentQuestion();
            assignmentQuestion.setAssignment(assignment);
            assignmentQuestion.setQuestion(question);
            assignmentQuestions.add(assignmentQuestion);
        }
        assignment.setMarks(marks);
        try {
            assignmentRepository.save(assignment);
            try {
                assignmentQuestionService.saveAll(assignmentQuestions);
            } catch (Exception e) {
                errorLogger.error(TAG + "Error in saving AssignmentQuestions with error : " + e.getMessage());
                statusBean.setStatus(0);
                statusBean.setMessage(Constants.SOMETHING_WENT_WRONG);
                throw e;
            }
            if(!assignmentBean.getGlobal()) {
                try {
                    List<AssignmentStandardDivision> assignmentStandardDivisions = new ArrayList<>();
                    for (StandardDivision standardDivision : standardDivisions) {
                        AssignmentStandardDivision assignmentStandardDivision = new AssignmentStandardDivision();
                        assignmentStandardDivision.setAssignment(assignment);
                        assignmentStandardDivision.setStandardDivision(standardDivision);
                        assignmentStandardDivisions.add(assignmentStandardDivision);
                    }
                    assignmentStandardDivisionService.saveAll(assignmentStandardDivisions);
                } catch (Exception e) {
                    errorLogger.error(TAG + "Error in saving AssignmentStandardDivisions with error : " + e.getMessage());
                    statusBean.setStatus(0);
                    statusBean.setMessage(Constants.SOMETHING_WENT_WRONG);
                    throw e;
                }
            }
        } catch (Exception e) {
            errorLogger.error(TAG + "Error in saving Assignment with error : " + e.getMessage());
            statusBean.setStatus(0);
            statusBean.setMessage(Constants.SOMETHING_WENT_WRONG);
            throw e;
        }

        //Send Notifications to Students
        if (!CollectionUtils.isEmpty(standardDivisions)) {
            try {
                sendAssignmentPublishedNotification(assignment, standardDivisions);
            } catch (Exception e) {
                errorLogger.error(TAG + "Error in sending assignment notifications : " + e.getMessage());
            }
        }

        statusBean.setStatus(1);
        return statusBean;
    }

    @Deprecated
    public StandardsBean getStandardsFromAssignmentsByTeacher(String teacherId) {
        StandardsBean standardsBean = new StandardsBean();

        User retrievedTeacher = userService.findById(teacherId);

        if (retrievedTeacher == null) {
            errorLogger.error(TAG + "Error in finding Teacher with id : " + teacherId);

            standardsBean.setStatus(0);
            standardsBean.setMessage(Constants.SOMETHING_WENT_WRONG);

            return standardsBean;
        }

        standardsBean.setStandardBeans(assignmentStandardDivisionService.getStandardsFromAssignmentsByTeacher(retrievedTeacher));
        standardsBean.setStatus(1);

        return standardsBean;
    }

    public List<SubjectChapterBean> findSubjectChaptersFromAssignmentsByTeacher(User teacher) {
        return assignmentRepository.findSubjectChaptersFromAssignmentsByTeacher(teacher);
    }

    public TeacherAssignmentSummaryBean getAssignmentSummary(String userId, String assignmentId) {
        TeacherAssignmentSummaryBean teacherAssignmentSummaryBean = new TeacherAssignmentSummaryBean();

        User retrievedTeacher = userService.findById(userId);

        if (retrievedTeacher == null) {
            errorLogger.error(TAG + "Error in finding Teacher with id : " + userId);

            teacherAssignmentSummaryBean.setStatus(0);
            teacherAssignmentSummaryBean.setMessage(Constants.SOMETHING_WENT_WRONG);

            return teacherAssignmentSummaryBean;
        }

        TeacherInstitute retrievedTeacherInstitute = teacherInstituteService.findByTeacher(retrievedTeacher);

        if (retrievedTeacherInstitute == null) {
            errorLogger.error(TAG + "Error in finding TeacherInstitute with teacherId : " + userId);

            teacherAssignmentSummaryBean.setStatus(0);
            teacherAssignmentSummaryBean.setMessage(Constants.SOMETHING_WENT_WRONG);

            return teacherAssignmentSummaryBean;
        }

        Assignment retrievedAssignment = assignmentRepository.findOne(assignmentId);

        if (retrievedAssignment == null) {
            errorLogger.error(TAG + "Error in finding Assignment with id : " + assignmentId);

            teacherAssignmentSummaryBean.setStatus(0);
            teacherAssignmentSummaryBean.setMessage(Constants.SOMETHING_WENT_WRONG);

            return teacherAssignmentSummaryBean;
        }

        teacherAssignmentSummaryBean.setQuestionSolutionSummaryBeans(studentAssignmentQuestionService.getAssignmentSummary(retrievedTeacherInstitute.getInstitute(), retrievedAssignment));

        teacherAssignmentSummaryBean.setStatus(1);
        return teacherAssignmentSummaryBean;
    }

    private void sendAssignmentPublishedNotification(Assignment assignment, List<StandardDivision> standardDivisions) {
        NotificationBean notificationBean = new NotificationBean();
        notificationBean.setTitle("New assignment published!");
        notificationBean.setShortDescription(assignment.getAssignmentTitle() + " published for " + assignment.getSubjectChapter().getSubjectChapterName());
        notificationBean.setLongDescription("Hey there, we thought to give you heads up about the new assignment titled \"" + assignment.getAssignmentTitle() + "\" published for " + assignment.getSubjectChapter().getSubjectChapterName() + " chapter.");
        notificationBean.setPromoUrl(assignment.getSubjectChapter().getId());
        notificationBean.setNotificationType(1);

        List<String> receivers = userService.fetchFcmTokensByStandardDivisions(standardDivisions);

        NotificationManager.sendBroadcastNotification(notificationBean, receivers);
    }

    public List<SubjectChapterBean> getSubjectChaptersForStandard(User institute, Standard standard) {
        return assignmentRepository.getSubjectChaptersForStandard(institute, standard);
    }

    public List<String> findAssignmentIdsByStandardGlobalAndSubject(Standard standard, Subject subject) {
        return assignmentRepository.findAssignmentIdsByStandardGlobalAndSubject(standard, subject);
    }

    public List<String> findAssignmentIdsByGlobalAndSubject(Subject subject) {
        return assignmentRepository.findAssignmentIdsByGlobalAndSubject(subject);
    }

    public SubjectChaptersAnalysisBean getSubjectChaptersAnalysis(String subjectChapterIds, String instituteId, Pageable pageable) {
        SubjectChaptersAnalysisBean subjectChaptersAnalysisBean = new SubjectChaptersAnalysisBean();

        User retrievedInstitute = userService.findById(instituteId);

        if (retrievedInstitute == null) {
            errorLogger.error(TAG + "Error in finding Institute with id : " + instituteId);

            subjectChaptersAnalysisBean.setStatus(0);
            subjectChaptersAnalysisBean.setMessage(Constants.SOMETHING_WENT_WRONG);

            return subjectChaptersAnalysisBean;
        }

        String[] subjectChapterIdArray = subjectChapterIds.split("_");

        List<String> subjectChapterIdList = new ArrayList<>();

        for (String subjectChapterId : subjectChapterIdArray) {
            if (!StringUtils.isEmpty(subjectChapterId)) {
                subjectChapterIdList.add(subjectChapterId);
            }
        }

        List<SubjectChapter> retrievedSubjectChapters = subjectChapterService.findByIds(subjectChapterIdList);

        if (CollectionUtils.isEmpty(retrievedSubjectChapters)) {
            errorLogger.error(TAG + "Error in finding SubjectChapters with ids : " + subjectChapterIds);

            subjectChaptersAnalysisBean.setStatus(0);
            subjectChaptersAnalysisBean.setMessage(Constants.SOMETHING_WENT_WRONG);

            return subjectChaptersAnalysisBean;
        }

        subjectChaptersAnalysisBean.setSubjectName(retrievedSubjectChapters.get(0).getSubject().getName());

        subjectChaptersAnalysisBean.setStandard(retrievedSubjectChapters.get(0).getStandard().getStandardName());

        subjectChaptersAnalysisBean.setInstituteName(retrievedInstitute.getName());

        List<String> assignmentIds = new ArrayList<>();

        for (SubjectChapter subjectChapter : retrievedSubjectChapters) {
            AssignmentMarksMasterBean assignmentMarksMasterBean = new AssignmentMarksMasterBean();
            assignmentMarksMasterBean.setSubjectChapterName(subjectChapter.getSubjectChapterName());
            assignmentMarksMasterBean.setSubjectChapterNumber(subjectChapter.getSubjectChapterNumber());

            assignmentMarksMasterBean.setAssignmentMarksBeans(studentAssignmentService.getAssignmentMarksSummary(subjectChapter, retrievedInstitute, pageable));

            for (AssignmentMarksBean assignmentMarksBean : assignmentMarksMasterBean.getAssignmentMarksBeans()) {
                assignmentIds.add(assignmentMarksBean.getAssignmentId());
            }

            subjectChaptersAnalysisBean.getAssignmentMarksMasterBeans().add(assignmentMarksMasterBean);
        }

        subjectChaptersAnalysisBean.setStudentAssignmentMarksMasterBeans(userService.findByInstituteAndStandard(retrievedInstitute, retrievedSubjectChapters.get(0).getStandard()));

        List<String> studentIds = new ArrayList<>();

        for (StudentAssignmentMarksMasterBean studentAssignmentMarksMasterBean : subjectChaptersAnalysisBean.getStudentAssignmentMarksMasterBeans()) {
            studentIds.add(studentAssignmentMarksMasterBean.getStudentId());
        }

        List<StudentAssignmentMarksBean> studentAssignmentMarksBeans = studentAssignmentService.getStudentAssignmentMarks(assignmentIds, studentIds);

        Map<String, StudentAssignmentMarksBean> studentAssignmentMarksBeanMap = new HashMap<>();

        for (StudentAssignmentMarksBean studentAssignmentMarksBean : studentAssignmentMarksBeans) {
            studentAssignmentMarksBeanMap.put(studentAssignmentMarksBean.getStudentId() + studentAssignmentMarksBean.getAssignmentId(), studentAssignmentMarksBean);
        }

        for (StudentAssignmentMarksMasterBean studentAssignmentMarksMasterBean : subjectChaptersAnalysisBean.getStudentAssignmentMarksMasterBeans()) {
            for (AssignmentMarksMasterBean assignmentMarksMasterBean : subjectChaptersAnalysisBean.getAssignmentMarksMasterBeans()) {
                for (AssignmentMarksBean assignmentMarksBean : assignmentMarksMasterBean.getAssignmentMarksBeans()) {
                    String key = studentAssignmentMarksMasterBean.getStudentId() + assignmentMarksBean.getAssignmentId();

                    if (studentAssignmentMarksBeanMap.containsKey(key)) {
                        studentAssignmentMarksMasterBean.getStudentAssignmentMarksBeans().add(studentAssignmentMarksBeanMap.get(key));
                    } else {
                        studentAssignmentMarksMasterBean.getStudentAssignmentMarksBeans().add(null);
                    }
                }
            }
        }

        subjectChaptersAnalysisBean.setStatus(1);
        return subjectChaptersAnalysisBean;
    }
}
