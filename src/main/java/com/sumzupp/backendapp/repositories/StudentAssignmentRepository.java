package com.sumzupp.backendapp.repositories;

import com.sumzupp.backendapp.beans.AssignmentMarksBean;
import com.sumzupp.backendapp.beans.StudentAssignmentBean;
import com.sumzupp.backendapp.beans.StudentAssignmentMarksBean;
import com.sumzupp.backendapp.beans.StudentRankingDetailsBean;
import com.sumzupp.backendapp.entities.Assignment;
import com.sumzupp.backendapp.entities.StudentAssignment;
import com.sumzupp.backendapp.entities.SubjectChapter;
import com.sumzupp.backendapp.entities.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by akash.mercer on 05-Jul-17.
 */

@Repository
public interface StudentAssignmentRepository extends CrudRepository<StudentAssignment, String> {

    @Query(value = "select new com.sumzupp.backendapp.beans.StudentAssignmentBean(s.id, s.assignment.id, s.assignment.assignmentTitle, s.studentSolution, s.marks, s.attempted, s.correct) from StudentAssignment s where s.student = :student")
    List<StudentAssignmentBean> findByStudent(@Param("student") User student);

    @Query(value = "select new com.sumzupp.backendapp.beans.StudentAssignmentBean(s.id, s.assignment.id, s.assignment.assignmentTitle, s.studentSolution, s.marks, s.attempted, s.correct) from StudentAssignment s where s.student.id = :studentId and s.assignment.id = :assignmentId")
    StudentAssignmentBean findByStudentAndAssignment(@Param("studentId") String studentId, @Param("assignmentId") String assignmentId);

    @Query(value = "select new com.sumzupp.backendapp.beans.StudentAssignmentBean(s.id, s.assignment.id, s.assignment.assignmentTitle, s.studentSolution, s.marks, s.attempted, s.correct) from StudentAssignment s where s.student = :student and s.assignment = :assignment")
    StudentAssignmentBean findByStudentAndAssignment(@Param("student") User student, @Param("assignment") Assignment assignment);

    @Query(value = "select new com.sumzupp.backendapp.beans.StudentRankingDetailsBean(s.student.id, s.student.name, s.student.standardDivision.institute.name, sum(s.marks) as points) from StudentAssignment s where s.assignment.id in :assignmentIds group by s.student")
    List<StudentRankingDetailsBean> findByAssignmentIds(@Param("assignmentIds") List<String> assignmentIds, Pageable pageable);

    @Query(value = "select new com.sumzupp.backendapp.beans.AssignmentMarksBean(s.assignment.id, s.assignment.assignmentTitle, max(s.marks), avg(s.marks), count(*)) from StudentAssignment s where s.student.standardDivision.institute = :institute and s.assignment.subjectChapter = :subjectChapter and s.studentSolution is not null group by s.assignment")
    List<AssignmentMarksBean> getAssignmentMarksSummary(@Param("subjectChapter") SubjectChapter subjectChapter, @Param("institute") User institute, Pageable pageable);

    @Query("select new com.sumzupp.backendapp.beans.StudentAssignmentMarksBean(s.student.id, s.assignment.id, s.marks) from StudentAssignment s where s.assignment.id in :assignmentIds and s.student.id in :studentIds and s.studentSolution is not null")
    List<StudentAssignmentMarksBean> getStudentAssignmentMarks(@Param("assignmentIds") List<String> assignmentIds, @Param("studentIds") List<String> studentIds);

    @Transactional
    @Modifying
    @Query(value = "update StudentAssignment s set s.student = :primaryStudent where s.student = :secondaryStudent")
    void updateStudentAssignments(@Param("primaryStudent") User primaryStudent, @Param("secondaryStudent") User secondaryStudent);
}
