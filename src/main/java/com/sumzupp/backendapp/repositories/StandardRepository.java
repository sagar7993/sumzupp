package com.sumzupp.backendapp.repositories;

import com.sumzupp.backendapp.beans.StandardBean;
import com.sumzupp.backendapp.entities.Standard;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by akash.mercer on 04-Jun-17.
 */

@Repository
public interface StandardRepository extends CrudRepository<Standard, String> {

    @Query(value = "select count(*) from Standard")
    Integer getCount();

    Standard findByStandardName(Integer standardName);

    @Query(value = "select new com.sumzupp.backendapp.beans.StandardBean(s.id, s.standardName) from Standard s where s.active = true")
    List<StandardBean> fetchAllActiveStandardBeans();
}
