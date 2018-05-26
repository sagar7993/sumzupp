package com.sumzupp.backendapp.repositories;

import com.sumzupp.backendapp.beans.QuestionBean;
import com.sumzupp.backendapp.entities.Question;
import com.sumzupp.backendapp.entities.Standard;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by akash.mercer on 07-Jul-17.
 */

@Repository
public interface QuestionRepository extends CrudRepository<Question, String> {

    @Query(value = "select new com.sumzupp.backendapp.beans.QuestionBean(q.id, q.questionText, q.questionImageUrls, q.marks, q.formula, q.questionType.type) from Question q where q.id = :questionId")
    QuestionBean findByQuestionId(@Param("questionId") String questionId);

    @Query(value = "select q from Question q where q.id in :questionIds")
    List<Question> findByIds(@Param("questionIds") List<String> questionIds);

    @Query(value = "select new com.sumzupp.backendapp.beans.QuestionBean(q.id, q.questionText, q.questionImageUrls, q.marks, q.formula, q.questionType.type) from Question q where q.subjectChapter.board.id = :boardId and q.subjectChapter.standard.id = :standardId and q.subjectChapter.id = :subjectChapterId")
    List<QuestionBean> findByBoardAndStandardAndSubjectChapter(@Param("boardId") String boardId, @Param("standardId") String standardId, @Param("subjectChapterId") String subjectChapterId);

    @Query(value = "select new com.sumzupp.backendapp.beans.QuestionBean(q.id, q.questionText, q.questionImageUrls, q.marks, q.formula, q.questionType.type, q.createdAt) from Question q where q.subjectChapter.standard.id = :standardId and q.subjectChapter.id = :subjectChapterId order by q.createdAt desc")
    List<QuestionBean> findByStandardAndSubjectChapter(@Param("standardId") String standardId, @Param("subjectChapterId") String subjectChapterId);

    @Query(value = "select new com.sumzupp.backendapp.beans.QuestionBean(q.id, q.questionText, q.questionImageUrls, q.marks, q.formula, q.questionType.type) from Question q where q.standard = :standard and q.global = true")
    List<QuestionBean> findGlobalQuestionsByStandard(@Param("standard") Standard standard);

}
