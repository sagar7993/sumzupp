package com.sumzupp.backendapp.repositories;

import com.sumzupp.backendapp.beans.AssignmentBean;
import com.sumzupp.backendapp.beans.SubjectChapterBean;
import com.sumzupp.backendapp.entities.*;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by akash.mercer on 04-Jun-17.
 */

@Repository
public interface AssignmentRepository extends CrudRepository<Assignment, String> {

    @Query(value = "select new com.sumzupp.backendapp.beans.AssignmentBean(a.id, a.assignmentTitle, a.deadlineDate, a.marks, a.subjectChapter.subject.name, a.subjectChapter.subjectChapterName, a.teacher.id, a.locked) from Assignment a where a.id = :id")
    AssignmentBean findById(@Param("id") String id);

    @Query(value = "select new com.sumzupp.backendapp.beans.AssignmentBean(a.id, a.assignmentTitle, a.deadlineDate, a.marks, a.subjectChapter.subject.name, a.subjectChapter.subjectChapterName, a.teacher.id, a.global, a.locked) from Assignment a where a.standardGlobal = :standard and a.subjectChapter = :subjectChapter order by a.deadlineDate desc")
    List<AssignmentBean> findByStandardGlobalAndSubjectChapter(@Param("standard") Standard standard, @Param("subjectChapter") SubjectChapter subjectChapter);

    @Query(value = "select new com.sumzupp.backendapp.beans.AssignmentBean(a.id, a.assignmentTitle, a.deadlineDate, a.marks, a.subjectChapter.subject.name, a.subjectChapter.subjectChapterName, a.teacher.id, a.global, a.locked) from Assignment a where a.instituteGlobal = :institute and a.subjectChapter = :subjectChapter order by a.deadlineDate desc")
    List<AssignmentBean> findByInstituteGlobalAndSubjectChapter(@Param("institute") User institute, @Param("subjectChapter") SubjectChapter subjectChapter);

    @Query(value = "select new com.sumzupp.backendapp.beans.AssignmentBean(a.id, a.assignmentTitle, a.deadlineDate, a.marks, a.subjectChapter.subject.name, a.subjectChapter.subjectChapterName, a.teacher.id, a.global, a.locked) from Assignment a where a.global = true and a.subjectChapter = :subjectChapter order by a.deadlineDate desc")
    List<AssignmentBean> findByGlobalAndSubjectChapter(@Param("subjectChapter") SubjectChapter subjectChapter);

    @Query(value = "select a.id from Assignment a where a.standardGlobal = :standard and a.subjectChapter.subject = :subject")
    List<String> findAssignmentIdsByStandardGlobalAndSubject(@Param("standard") Standard standard, @Param("subject") Subject subject);

    @Query(value = "select a.id from Assignment a where a.global = true and a.subjectChapter.subject = :subject")
    List<String> findAssignmentIdsByGlobalAndSubject(@Param("subject") Subject subject);

    @Query(value = "select a from Assignment a where a.id = :id")
    Assignment findAssignmentById(@Param("id") String id);

    @Query(value = "select new com.sumzupp.backendapp.beans.AssignmentBean(a.id, a.assignmentTitle, a.deadlineDate, a.marks, a.subjectChapter.subject.name, a.subjectChapter.subjectChapterName, a.teacher.id, a.locked) from Assignment a where a.teacher = :teacher")
    List<AssignmentBean> findByTeacher(@Param("teacher") User teacher);

    @Query(value = "select new com.sumzupp.backendapp.beans.AssignmentBean(a.id, a.assignmentTitle, a.deadlineDate, a.marks, a.subjectChapter.subject.name, a.subjectChapter.subjectChapterName, a.teacher.id, a.locked) from Assignment a where a.teacher in :teacherList")
    List<AssignmentBean> findByTeacherList(@Param("teacherList") List<User> teacherList);

    @Query(value = "select new com.sumzupp.backendapp.beans.AssignmentBean(a.id, a.assignmentTitle, a.deadlineDate, a.marks, a.subjectChapter.subject.name, a.subjectChapter.subjectChapterName, a.teacher.id, a.locked) from Assignment a where a.subjectChapter.standard in :standardList")
    List<AssignmentBean> findByStandardList(@Param("standardList") List<Standard> standardList);

    @Query(value = "select new com.sumzupp.backendapp.beans.AssignmentBean(a.id, a.assignmentTitle, a.deadlineDate, a.marks, a.subjectChapter.subject.name, a.subjectChapter.subjectChapterName, a.teacher.id, a.locked) from Assignment a where a.global = true or a.standardGlobal is not null or a.instituteGlobal is not null")
    List<AssignmentBean> getAllGlobalAssignments();

    @Query(value = "select new com.sumzupp.backendapp.beans.AssignmentBean(a.id, a.assignmentTitle, a.deadlineDate, a.marks, a.subjectChapter.subject.name, a.subjectChapter.subjectChapterName, a.teacher.id, a.locked) from Assignment a where a.global = true")
    List<AssignmentBean> getAllSuperGlobalAssignments();

    @Query(value = "select new com.sumzupp.backendapp.beans.AssignmentBean(a.id, a.assignmentTitle, a.deadlineDate, a.marks, a.subjectChapter.subject.name, a.subjectChapter.subjectChapterName, a.teacher.id, a.locked) from Assignment a where a.standardGlobal.id in :standardIds")
    List<AssignmentBean> getAllStandardListGlobalAssignments(@Param("standardIds") List<String> standardIds);

    @Query(value = "select new com.sumzupp.backendapp.beans.AssignmentBean(a.id, a.assignmentTitle, a.deadlineDate, a.marks, a.subjectChapter.subject.name, a.subjectChapter.subjectChapterName, a.teacher.id, a.locked) from Assignment a where a.standardGlobal.id = :standardId")
    List<AssignmentBean> getAllStandardGlobalAssignments(@Param("standardId") String standardId);

    @Query(value = "select new com.sumzupp.backendapp.beans.AssignmentBean(a.id, a.assignmentTitle, a.deadlineDate, a.marks, a.subjectChapter.subject.name, a.subjectChapter.subjectChapterName, a.teacher.id, a.locked) from Assignment a where a.instituteGlobal.id = :instituteId")
    List<AssignmentBean> getAllInstituteGlobalAssignments(@Param("instituteId") String instituteId);

    @Query(value = "select new com.sumzupp.backendapp.beans.SubjectChapterBean(a.subjectChapter.id, a.subjectChapter.subjectChapterName, a.subjectChapter.subjectChapterNumber, a.subjectChapter.subject.id, a.subjectChapter.board.id, a.subjectChapter.locked) from Assignment a where a.teacher = :teacher group by a.subjectChapter")
    List<SubjectChapterBean> findSubjectChaptersFromAssignmentsByTeacher(@Param("teacher") User teacher);

    @Query(value = "select new com.sumzupp.backendapp.beans.SubjectChapterBean(a.subjectChapter.id, a.subjectChapter.subjectChapterName, a.subjectChapter.subjectChapterNumber, a.subjectChapter.subject.id, a.subjectChapter.board.id, a.subjectChapter.locked) from Assignment a where a.global = true or a.standardGlobal = :standard or a.instituteGlobal = :institute group by a.subjectChapter order by a.subjectChapter.subjectChapterName asc")
    List<SubjectChapterBean> getSubjectChaptersForStandard(@Param("institute") User institute, @Param("standard") Standard standard);

}
