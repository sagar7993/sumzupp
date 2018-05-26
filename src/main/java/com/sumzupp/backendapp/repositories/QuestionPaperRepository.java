package com.sumzupp.backendapp.repositories;

import com.sumzupp.backendapp.beans.QuestionPaperBean;
import com.sumzupp.backendapp.entities.QuestionPaper;
import com.sumzupp.backendapp.entities.SubjectChapter;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by akash.mercer on 28-Oct-17.
 */

@Repository
public interface QuestionPaperRepository extends CrudRepository<QuestionPaper, String> {

    @Query(value = "select new com.sumzupp.backendapp.beans.QuestionPaperBean(q.questionPaperName, q.questionPaperUrl, q.questionPaperType.type = 1) from QuestionPaper q where q.subjectChapter = :subjectChapter and q.institute.id in :instituteIds order by q.createdAt desc")
    List<QuestionPaperBean> findBySubjectChapterAndInstitutes(@Param("subjectChapter") SubjectChapter subjectChapter, @Param("instituteIds") List<String> instituteIds);
}
