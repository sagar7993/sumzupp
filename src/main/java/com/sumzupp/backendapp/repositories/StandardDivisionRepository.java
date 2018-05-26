package com.sumzupp.backendapp.repositories;

import com.sumzupp.backendapp.beans.StandardBean;
import com.sumzupp.backendapp.entities.Standard;
import com.sumzupp.backendapp.entities.StandardDivision;
import com.sumzupp.backendapp.entities.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by akash.mercer on 07-Jul-17.
 */

@Repository
public interface StandardDivisionRepository extends CrudRepository<StandardDivision, String> {

    @Query(value = "select s from StandardDivision s where s.id in :standardDivisionIds")
    List<StandardDivision> findByIds(@Param("standardDivisionIds") List<String> standardDivisionIds);

    List<StandardDivision> findByStandard(Standard standard);

    List<StandardDivision> findByInstitute(User institute);

    List<StandardDivision> findByInstituteAndStandard(User institute, Standard standard);

    @Query(value = "select new com.sumzupp.backendapp.beans.StandardBean(s.standard.id, s.standard.standardName) from StandardDivision s where s.institute.id = :instituteId")
    List<StandardBean> findStandardBeansByInstitute(@Param("instituteId") String instituteId);

    @Query(value = "select s from StandardDivision s where s.standard.id = :standardId and s.board.id = :boardId and s.institute.id = :instituteId")
    StandardDivision findByStandardAndBoardAndInstitute(@Param("standardId") String standardId, @Param("boardId") String boardId, @Param("instituteId") String instituteId);
}
