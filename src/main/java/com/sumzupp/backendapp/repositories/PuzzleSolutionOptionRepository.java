package com.sumzupp.backendapp.repositories;

import com.sumzupp.backendapp.entities.PuzzleSolutionOption;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by akash.mercer on 18-Jun-17.
 */

@Repository
public interface PuzzleSolutionOptionRepository extends CrudRepository<PuzzleSolutionOption, String> {

}
