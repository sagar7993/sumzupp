package com.sumzupp.backendapp.repositories;

import com.sumzupp.backendapp.entities.PaymentType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by akash.mercer on 02-Jan-18.
 */

@Repository
public interface PaymentTypeRepository extends CrudRepository<PaymentType, String> {

    @Query(value = "select count(*) from PaymentType")
    Integer getCount();

    PaymentType findByType(Integer type);
}
