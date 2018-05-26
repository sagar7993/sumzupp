package com.sumzupp.backendapp.repositories;

import com.sumzupp.backendapp.entities.Story;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by akash.mercer on 08-Jun-17.
 */

@Repository
public interface StoryRepository extends CrudRepository<Story, String> {

}
