package com.sumzupp.backendapp.repositories;

import com.sumzupp.backendapp.beans.StudentAssignmentMarksMasterBean;
import com.sumzupp.backendapp.beans.UserBean;
import com.sumzupp.backendapp.entities.Standard;
import com.sumzupp.backendapp.entities.StandardDivision;
import com.sumzupp.backendapp.entities.User;
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
public interface UserRepository extends CrudRepository<User, String> {

    User findFirstByContactNumber(String contactNumber);

    User findFirstByEmail(String email);

    User findFirstByUsername(String username);

    User findFirstByUsernameAndPassword(String username, String password);

    User findByEmailAndPasswordAndUserType(String email, String password, UserType userType);

    @Query(value = "select new com.sumzupp.backendapp.beans.UserBean(u) from User u where u.userType.type = 4")
    List<UserBean> fetchInstitutes();

    @Query(value = "select distinct new com.sumzupp.backendapp.beans.UserBean(u) from User u where u.standardDivision.institute.id = :instituteId")
    List<UserBean> getStudentsByInstitute(@Param("instituteId") String instituteId);

    @Query(value = "select u from User u where (u.email = :email or u.username = :email) and u.password = :password and u.userType in :userTypes")
    User findByEmailOrUsernameAndPasswordAndUserType(@Param("email") String email, @Param("password") String password, @Param("userTypes") List<UserType> userTypes);

    @Query(value = "select count(*) from User u where u.userType.type = 1 and u.standardDivision in :standardDivisions and u.admin = false")
    Integer getStudentCountByStandardDivisions(@Param("standardDivisions") List<StandardDivision> standardDivisions);

    @Query(value = "select u.fcmToken from User u where u.standardDivision in :standardDivisions")
    List<String> fetchFcmTokensByStandardDivisions(@Param("standardDivisions") List<StandardDivision> standardDivisions);

    @Query(value = "select u.name from User u where u.userType.type = 4")
    List<String> fetchInstituteNames();

    @Query(value = "select count(*) from User u where u.userType.type = 1 and u.standardDivision.institute = :institute and u.admin = false")
    Integer getStudentCountByInstitute(@Param("institute") User institute);

    @Query(value = "select new com.sumzupp.backendapp.beans.StudentAssignmentMarksMasterBean(u.id, u.name) from User u where u.standardDivision.institute = :institute and  u.standardDivision.standard = :standard")
    List<StudentAssignmentMarksMasterBean> findByInstituteAndStandard(@Param("institute") User institute, @Param("standard") Standard standard);
}
