package com.sumzupp.backendapp.services;

import com.sumzupp.backendapp.entities.UserType;
import com.sumzupp.backendapp.repositories.UserTypeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by akash.mercer on 21-Jun-17.
 */

@Service
public class UserTypeService {
    private static final String TAG = "UserTypeService : ";

    @Autowired
    private UserTypeRepository userTypeRepository;

    private static Logger debugLogger = LoggerFactory.getLogger("debugLogs");

    private static Logger errorLogger = LoggerFactory.getLogger("errorLogs");

    public Integer getCount() {
        return userTypeRepository.getCount();
    }

    public UserType findByType(Integer type) {
        return userTypeRepository.findByType(type);
    }

    public List<UserType> findTeacherOrInstituteOrAdminTypes() {
        return userTypeRepository.findTeacherOrInstituteOrAdminTypes();
    }
}
