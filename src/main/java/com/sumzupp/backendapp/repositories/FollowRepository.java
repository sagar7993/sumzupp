package com.sumzupp.backendapp.repositories;

import com.sumzupp.backendapp.entities.Follow;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by akash.mercer on 18-Jun-17.
 */

@Repository
public interface FollowRepository extends CrudRepository<Follow, String> {

}
