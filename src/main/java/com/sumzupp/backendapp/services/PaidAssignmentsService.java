package com.sumzupp.backendapp.services;

import com.sumzupp.backendapp.beans.AssignmentBean;
import com.sumzupp.backendapp.entities.SubjectChapter;
import com.sumzupp.backendapp.entities.User;
import com.sumzupp.backendapp.repositories.PaidAssignmentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by akash.mercer on 26-Dec-17.
 */

@Service
public class PaidAssignmentsService {

    @Autowired
    private PaidAssignmentsRepository paidAssignmentsRepository;

    public List<AssignmentBean> findByStudentAndSubjectChapter(User student, SubjectChapter subjectChapter) {
        return paidAssignmentsRepository.findByStudentAndSubjectChapter(student, subjectChapter);
    }
}
