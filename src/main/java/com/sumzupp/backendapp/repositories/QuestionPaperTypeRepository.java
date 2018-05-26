package com.sumzupp.backendapp.repositories;

import com.sumzupp.backendapp.entities.QuestionPaperType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by akash.mercer on 28-Oct-17.
 */

@Repository
public interface QuestionPaperTypeRepository extends CrudRepository<QuestionPaperType, String> {

    @Query(value = "select count(*) from QuestionPaperType")
    Integer getCount();

    QuestionPaperType findByType(Integer type);
}
