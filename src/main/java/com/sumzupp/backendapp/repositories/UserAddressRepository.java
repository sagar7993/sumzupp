package com.sumzupp.backendapp.repositories;

import com.sumzupp.backendapp.entities.UserAddress;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by akash.mercer on 05-Jul-17.
 */

@Repository
public interface UserAddressRepository extends CrudRepository<UserAddress, String> {

}
