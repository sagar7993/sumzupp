package com.sumzupp.backendapp.repositories;

import com.sumzupp.backendapp.entities.VoteType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by akash.mercer on 05-Jun-17.
 */

@Repository
public interface VoteTypeRepository extends CrudRepository<VoteType, String> {

    @Query(value = "select count(*) from VoteType")
    Integer getCount();

    VoteType findByType(Integer type);

}
