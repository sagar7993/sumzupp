package com.sumzupp.backendapp.repositories;

import com.sumzupp.backendapp.beans.QuestionBean;
import com.sumzupp.backendapp.entities.Assignment;
import com.sumzupp.backendapp.entities.AssignmentQuestion;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by akash.mercer on 05-Jul-17.
 */

@Repository
public interface AssignmentQuestionRepository extends CrudRepository<AssignmentQuestion, String> {

    @Query(value = "select new com.sumzupp.backendapp.beans.QuestionBean(a.question.id, a.question.questionText, a.question.questionImageUrls, a.question.marks, a.question.formula, a.question.questionType.type) from AssignmentQuestion a where a.assignment.id = :assignmentId")
    List<QuestionBean> findByAssignment(@Param("assignmentId") String assignmentId);

    List<AssignmentQuestion> findByAssignment(Assignment assignment);

    @Query(value = "select count(*) from AssignmentQuestion a where a.assignment.id = :assignmentId")
    Integer getQuestionCountByAssignment(@Param("assignmentId") String assignmentId);

}
