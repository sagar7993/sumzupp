package com.sumzupp.backendapp.repositories;

import com.sumzupp.backendapp.beans.AssignmentBean;
import com.sumzupp.backendapp.entities.PaidAssignments;
import com.sumzupp.backendapp.entities.SubjectChapter;
import com.sumzupp.backendapp.entities.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by akash.mercer on 26-Dec-17.
 */

@Repository
public interface PaidAssignmentsRepository extends CrudRepository<PaidAssignments, String> {

    @Query(value = "select new com.sumzupp.backendapp.beans.AssignmentBean(p.assignment.id, p.assignment.assignmentTitle, p.assignment.deadlineDate, p.assignment.marks, p.assignment.subjectChapter.subject.name, p.assignment.subjectChapter.subjectChapterName, p.assignment.teacher.id, p.assignment.global, p.assignment.locked) from PaidAssignments p where p.student = :student and p.assignment.subjectChapter = :subjectChapter order by p.assignment.deadlineDate desc")
    List<AssignmentBean> findByStudentAndSubjectChapter(@Param("student") User student, @Param("subjectChapter") SubjectChapter subjectChapter);

}
