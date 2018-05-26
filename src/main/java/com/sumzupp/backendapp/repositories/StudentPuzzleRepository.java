package com.sumzupp.backendapp.repositories;

import com.sumzupp.backendapp.entities.StudentPuzzle;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by akash.mercer on 05-Jul-17.
 */

@Repository
public interface StudentPuzzleRepository extends CrudRepository<StudentPuzzle, String> {

}
