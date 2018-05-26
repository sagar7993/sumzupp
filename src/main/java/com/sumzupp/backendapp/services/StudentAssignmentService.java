package com.sumzupp.backendapp.services;

import com.sumzupp.backendapp.beans.*;
import com.sumzupp.backendapp.entities.Assignment;
import com.sumzupp.backendapp.entities.StudentAssignment;
import com.sumzupp.backendapp.entities.SubjectChapter;
import com.sumzupp.backendapp.entities.User;
import com.sumzupp.backendapp.repositories.StudentAssignmentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by akash.mercer on 05-Jul-17.
 */

@Service
public class StudentAssignmentService {
    private static final String TAG = "StudentAssignmentService : ";

    @Autowired
    private StudentAssignmentRepository studentAssignmentRepository;

    @Autowired
    private AssignmentQuestionService assignmentQuestionService;

    private static Logger debugLogger = LoggerFactory.getLogger("debugLogs");

    private static Logger errorLogger = LoggerFactory.getLogger("errorLogs");

    public void save(StudentAssignment studentAssignment) {
        studentAssignmentRepository.save(studentAssignment);
    }

    public void saveAll(List<StudentAssignment> studentAssignments) throws Exception {
        studentAssignmentRepository.save(studentAssignments);
    }

    public List<StudentAssignment> fetchAll() {
        return (List<StudentAssignment>) studentAssignmentRepository.findAll();
    }

    public StudentAssignment findById(String id) {
        return studentAssignmentRepository.findOne(id);
    }

    public List<StudentAssignmentBean> findByStudent(User student) {
        return studentAssignmentRepository.findByStudent(student);
    }

    public StudentAssignmentSummaryBean findByStudentAndAssignment(String studentId, String assignmentId) {
        StudentAssignmentSummaryBean studentAssignmentSummaryBean = new StudentAssignmentSummaryBean();

        studentAssignmentSummaryBean.setStudentAssignmentBean(studentAssignmentRepository.findByStudentAndAssignment(studentId, assignmentId));
        studentAssignmentSummaryBean.setStatus(1);

        return studentAssignmentSummaryBean;
    }

    public StudentAssignmentBean findByStudentAndAssignment(User student, Assignment assignment) {
        return studentAssignmentRepository.findByStudentAndAssignment(student, assignment);
    }

    public List<StudentRankingDetailsBean> findByAssignmentIds(List<String> assignmentIds, Pageable pageable) {
        return studentAssignmentRepository.findByAssignmentIds(assignmentIds, pageable);
    }

    public void updateStudentAssignments(User primaryUser, User secondaryUser) {
        studentAssignmentRepository.updateStudentAssignments(primaryUser, secondaryUser);
    }

    public List<AssignmentMarksBean> getAssignmentMarksSummary(SubjectChapter subjectChapter, User retrievedInstitute, Pageable pageable) {
        List<AssignmentMarksBean> assignmentMarksBeans = studentAssignmentRepository.getAssignmentMarksSummary(subjectChapter, retrievedInstitute, pageable);

        for (AssignmentMarksBean assignmentMarksBean : assignmentMarksBeans) {
            assignmentMarksBean.setTotalMarks(assignmentQuestionService.getQuestionCountByAssignment(assignmentMarksBean.getAssignmentId()));
        }

        return assignmentMarksBeans;
    }

    public List<StudentAssignmentMarksBean> getStudentAssignmentMarks(List<String> assignmentIds, List<String> studentIds) {
        return studentAssignmentRepository.getStudentAssignmentMarks(assignmentIds, studentIds);
    }
}
