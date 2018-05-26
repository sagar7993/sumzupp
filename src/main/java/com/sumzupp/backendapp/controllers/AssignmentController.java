package com.sumzupp.backendapp.controllers;

import com.sumzupp.backendapp.beans.*;
import com.sumzupp.backendapp.services.AssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

/**
 * Created by akash.mercer on 04-Jun-17.
 */

@RestController
@RequestMapping(value = "/assignment")
public class AssignmentController {

    @Autowired
    private AssignmentService assignmentService;

    @RequestMapping(value = "/findByUser/{userId}", method = RequestMethod.GET)
    public @ResponseBody AssignmentsListBean findByUser(@PathVariable("userId") String userId) {
        return assignmentService.findByUser(userId);
    }

    @RequestMapping(value = "/findByTeacher/{teacherId}", method = RequestMethod.GET)
    public @ResponseBody AssignmentsListBean findByTeacher(@PathVariable("teacherId") String teacherId) {
        return assignmentService.findByTeacher(teacherId);
    }

    @RequestMapping(value = "/findByAssignment/{assignmentId}", method = RequestMethod.GET)
    public @ResponseBody AssignmentBean findByAssignment(@PathVariable("assignmentId") String assignmentId) {
        return assignmentService.findByAssignment(assignmentId);
    }

    @RequestMapping(value = "/findByUserAndSubjectChapter/{userId}/{subjectChapterId}", method = RequestMethod.GET)
    public @ResponseBody AssignmentsBean findByUserAndSubjectChapter(@PathVariable("userId") String userId, @PathVariable("subjectChapterId") String subjectChapterId, @PageableDefault(size = 20, sort = {"createdAt"}, direction = Sort.Direction.DESC) Pageable pageable) {
        return assignmentService.findByUserAndSubjectChapter(userId, subjectChapterId, pageable);
    }

    @RequestMapping(value = "/findById/{userId}/{assignmentId}", method = RequestMethod.GET)
    public @ResponseBody AssignmentDetailsBean findById(@PathVariable("userId") String userId, @PathVariable("assignmentId") String assignmentId) {
        return assignmentService.findById(userId, assignmentId);
    }

    @RequestMapping(value = "/getSubjectChaptersAnalysis", method = RequestMethod.GET)
    public @ResponseBody SubjectChaptersAnalysisBean getSubjectChaptersAnalysis(@Param("subjectChapterIds") String subjectChapterIds, @Param("instituteId") String instituteId, @PageableDefault(size = 2, sort = {"attempted"}, direction = Sort.Direction.DESC) Pageable pageable) {
        return assignmentService.getSubjectChaptersAnalysis(subjectChapterIds, instituteId, pageable);
    }

    @RequestMapping(value = "/submit", method = RequestMethod.POST, consumes = "application/json")
    public @ResponseBody StatusBean submit(@RequestBody SubmitAssignmentBean submitAssignmentBean) {
        return assignmentService.submit(submitAssignmentBean);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST, consumes = "application/json")
    public @ResponseBody StatusBean save(@RequestBody AssignmentBean assignmentBean) {
        return assignmentService.save(assignmentBean);
    }

    @RequestMapping(value = "/getAssignmentSummary/{userId}/{assignmentId}", method = RequestMethod.GET)
    public @ResponseBody TeacherAssignmentSummaryBean getAssignmentSummary(@PathVariable("userId") String userId, @PathVariable("assignmentId") String assignmentId) {
        return assignmentService.getAssignmentSummary(userId, assignmentId);
    }

    @RequestMapping(value = "/getAllGlobalAssignments", method = RequestMethod.GET)
    public @ResponseBody AssignmentsListBean getAllGlobalAssignments() {
        return assignmentService.getAllGlobalAssignments();
    }

    @RequestMapping(value = "/getAllSuperGlobalAssignments", method = RequestMethod.GET)
    public @ResponseBody AssignmentsListBean getAllSuperGlobalAssignments() {
        return assignmentService.getAllSuperGlobalAssignments();
    }

    @RequestMapping(value = "/getAllStandardListGlobalAssignments", method = RequestMethod.POST, consumes = "application/json")
    public @ResponseBody AssignmentsListBean getAllStandardListGlobalAssignments(@RequestBody AssignmentBean assignmentBean) {
        return assignmentService.getAllStandardListGlobalAssignments(assignmentBean);
    }

    @RequestMapping(value = "/getAllStandardGlobalAssignments", method = RequestMethod.GET)
    public @ResponseBody AssignmentsListBean getAllStandardGlobalAssignments(@PathVariable("standardId") String standardId) {
        return assignmentService.getAllStandardGlobalAssignments(standardId);
    }

    @RequestMapping(value = "/getAllInstituteGlobalAssignments/{instituteId}", method = RequestMethod.GET)
    public @ResponseBody AssignmentsListBean getAllInstituteGlobalAssignments(@PathVariable("instituteId") String instituteId) {
        return assignmentService.getAllInstituteGlobalAssignments(instituteId);
    }

    @RequestMapping(value = "/getQuizTermsAndConditions/{assignmentId}", method = RequestMethod.GET)
    public @ResponseBody QuizBean getQuizTermsAndConditions(@PathVariable("assignmentId") String assignmentId) {
        return assignmentService.getQuizTermsAndConditions(assignmentId);
    }

}
