package com.sumzupp.backendapp.repositories;

import com.sumzupp.backendapp.entities.TeacherInstitute;
import com.sumzupp.backendapp.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by akash.mercer on 04-Jun-17.
 */

@Repository
public interface TeacherInstituteRepository extends CrudRepository<TeacherInstitute, String> {

    TeacherInstitute findByTeacher(User teacher);

    List<TeacherInstitute> findByInstitute(User institute);

}
