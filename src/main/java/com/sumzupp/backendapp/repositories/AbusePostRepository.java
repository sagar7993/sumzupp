package com.sumzupp.backendapp.repositories;

import com.sumzupp.backendapp.entities.AbusePost;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by akash.mercer on 05-Jun-17.
 */

@Repository
public interface AbusePostRepository extends CrudRepository<AbusePost, String> {

}
