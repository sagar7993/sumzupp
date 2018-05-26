package com.sumzupp.backendapp.repositories;

import com.sumzupp.backendapp.entities.Puzzle;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by akash.mercer on 08-Jun-17.
 */

@Repository
public interface PuzzleRepository extends CrudRepository<Puzzle, String> {

}
