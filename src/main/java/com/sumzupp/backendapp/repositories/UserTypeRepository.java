package com.sumzupp.backendapp.repositories;

import com.sumzupp.backendapp.entities.UserType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by akash.mercer on 21-Jun-17.
 */

@Repository
public interface UserTypeRepository extends CrudRepository<UserType, String> {

    @Query(value = "select count(*) from UserType")
    Integer getCount();

    UserType findByType(Integer type);

    @Query(value = "select u from UserType u where u.type = '2' or u.type = '4' or u.type = '5'")
    List<UserType> findTeacherOrInstituteOrAdminTypes();

}
