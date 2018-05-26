package com.sumzupp.backendapp.repositories;

import com.sumzupp.backendapp.beans.SubjectBean;
import com.sumzupp.backendapp.entities.Subject;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by akash.mercer on 05-Jul-17.
 */

@Repository
public interface SubjectRepository extends CrudRepository<Subject, String> {

    @Query(value = "select count(*) from Subject")
    Integer getCount();

    Subject findByType(Integer type);

    @Query(value = "select new com.sumzupp.backendapp.beans.SubjectBean(s.id, s.name, s.type, s.active, s.global) from Subject s where s.active = true")
    List<SubjectBean> findAllActiveSubjectBean();

    @Query(value = "select new com.sumzupp.backendapp.beans.SubjectBean(s.id, s.name, s.type, s.active, s.global) from Subject s")
    List<SubjectBean> findAllSubjectBean();

}
