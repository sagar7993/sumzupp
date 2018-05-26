package com.sumzupp.backendapp.repositories;

import com.sumzupp.backendapp.entities.QuestionType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by akash.mercer on 05-Jul-17.
 */

@Repository
public interface QuestionTypeRepository extends CrudRepository<QuestionType, String> {

    @Query(value = "select count(*) from QuestionType")
    Integer getCount();

}
