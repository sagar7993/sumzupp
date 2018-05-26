package com.sumzupp.backendapp.repositories;

import com.sumzupp.backendapp.beans.SubjectChapterBean;
import com.sumzupp.backendapp.entities.Board;
import com.sumzupp.backendapp.entities.Standard;
import com.sumzupp.backendapp.entities.SubjectChapter;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by akash.mercer on 09-Jul-17.
 */

@Repository
public interface SubjectChapterRepository extends CrudRepository<SubjectChapter, String> {

    @Query(value = "select s from SubjectChapter s where s.id in :subjectChapterIdList")
    List<SubjectChapter> findByIds(@Param("subjectChapterIdList") List<String> subjectChapterIdList);

    @Query(value = "select new com.sumzupp.backendapp.beans.SubjectChapterBean(s.id, s.subjectChapterName, s.subjectChapterNumber, s.subject.id, s.board.id, s.locked) from SubjectChapter s where s.subject.id in :subjectIds and s.board = :board and s.standard = :standard order by s.subjectChapterNumber asc")
    List<SubjectChapterBean> findBySubjectIdsAndBoardAndStandard(@Param("subjectIds") List<String> subjectIds, @Param("board") Board board, @Param("standard") Standard standard);

    @Query(value = "select new com.sumzupp.backendapp.beans.SubjectChapterBean(s.id, s.subjectChapterName, s.subjectChapterNumber, s.subject.id, s.board.id, s.locked) from SubjectChapter s where s.subject.id in :subjectIds and s.standard = :standard order by s.subjectChapterNumber asc")
    List<SubjectChapterBean> findBySubjectIdsAndStandard(@Param("subjectIds") List<String> subjectIds, @Param("standard") Standard standard);

}
