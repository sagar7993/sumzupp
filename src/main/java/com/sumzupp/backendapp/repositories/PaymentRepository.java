package com.sumzupp.backendapp.repositories;

import com.sumzupp.backendapp.entities.Payment;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by akash.mercer on 02-Jan-18.
 */

@Repository
public interface PaymentRepository extends CrudRepository<Payment, String> {

    @Query(value = "select count(*) from Payment p where p.user.id = :userId")
    Integer getCountByUser(@Param("userId") String userId);

}
