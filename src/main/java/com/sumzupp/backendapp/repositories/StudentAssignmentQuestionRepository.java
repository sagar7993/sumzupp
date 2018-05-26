package com.sumzupp.backendapp.repositories;

import com.sumzupp.backendapp.beans.StudentAssignmentQuestionBean;
import com.sumzupp.backendapp.entities.Assignment;
import com.sumzupp.backendapp.entities.StudentAssignmentQuestion;
import com.sumzupp.backendapp.entities.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by akash.mercer on 06-Aug-17.
 */

@Repository
public interface StudentAssignmentQuestionRepository extends CrudRepository<StudentAssignmentQuestion, String> {

    @Query(value = "select new com.sumzupp.backendapp.beans.StudentAssignmentQuestionBean(s.assignmentQuestion.question.id, s.assignmentQuestion.question.questionText, s.assignmentQuestion.question.formula, s.attempted, s.correct) from StudentAssignmentQuestion s where s.assignmentQuestion.assignment = :assignment and s.student.standardDivision.institute = :institute and s.student.admin = false")
    List<StudentAssignmentQuestionBean> findByInstituteAndAssignment(@Param("institute") User institute, @Param("assignment") Assignment assignment);

    @Query(value = "select s from StudentAssignmentQuestion s where s.assignmentQuestion.assignment = :assignment and s.student.standardDivision.institute = :institute")
    List<StudentAssignmentQuestion> findByAssignmentAndInstitute(@Param("assignment") Assignment assignment, @Param("institute") User institute);

    @Transactional
    @Modifying
    @Query(value = "update StudentAssignmentQuestion s set s.student = :primaryStudent where s.student = :secondaryStudent")
    void updateStudentAssignmentQuestions(@Param("primaryStudent") User primaryStudent, @Param("secondaryStudent") User secondaryStudent);
}
