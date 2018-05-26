package com.sumzupp.backendapp.repositories;

import com.sumzupp.backendapp.entities.Admin;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by akash.mercer on 08-Jun-17.
 */

@Repository
public interface AdminRepository extends CrudRepository<Admin, String> {

    @Query(value = "select count(*) from Admin")
    Integer getCount();

    @Transactional
    @Query(value = "SELECT a FROM Admin a WHERE a.email = :email AND a.password = :password")
    Admin login(@Param("email") String email, @Param("password") String password);

}
