package com.sumzupp.backendapp.repositories;

import com.sumzupp.backendapp.beans.SolutionOptionBean;
import com.sumzupp.backendapp.entities.Question;
import com.sumzupp.backendapp.entities.SolutionOption;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by akash.mercer on 05-Jul-17.
 */

@Repository
public interface SolutionOptionRepository extends CrudRepository<SolutionOption, String> {

    @Query(value = "select new com.sumzupp.backendapp.beans.SolutionOptionBean(s.id, s.solutionOptionText, s.solutionOptionImageUrls, s.solution, s.formula, s.question.id, s.question.marks) from SolutionOption s where s.question.id in :questionIds")
    List<SolutionOptionBean> findByQuestionIds(@Param("questionIds") List<String> questionIds);

    @Query(value = "select new com.sumzupp.backendapp.beans.SolutionOptionBean(s.id, s.solutionOptionText, s.solutionOptionImageUrls, s.solution, s.formula, s.question.id, s.question.marks) from SolutionOption s where s.question.id in :questionIds and s.solution = true")
    List<SolutionOptionBean> findByQuestionIdsWithSolution(@Param("questionIds") List<String> questionIds);

    @Query(value = "select s from SolutionOption s where s.question = :question")
    List<SolutionOption> findByQuestion(@Param("question") Question question);

    @Query(value = "select new com.sumzupp.backendapp.beans.SolutionOptionBean(s.id, s.solutionOptionText, s.solutionOptionImageUrls, s.solution, s.formula, s.question.id, s.question.marks) from SolutionOption s where s.question.id = :questionId")
    List<SolutionOptionBean> findByQuestionId(@Param("questionId") String questionId);
}
