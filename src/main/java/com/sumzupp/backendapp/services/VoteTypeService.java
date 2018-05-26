package com.sumzupp.backendapp.services;

import com.sumzupp.backendapp.entities.VoteType;
import com.sumzupp.backendapp.repositories.VoteTypeRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by akash.mercer on 05-Jun-17.
 */

@Service
public class VoteTypeService {
    private static final String TAG = "VoteTypeService : ";

    @Autowired
    private VoteTypeRepository voteTypeRepository;

    private static Logger debugLogger = Logger.getLogger("debugLogs");

    private static Logger errorLogger = Logger.getLogger("errorLogs");

    public Integer getCount() {
        return voteTypeRepository.getCount();
    }

    public VoteType findByType(Integer type) {
        return voteTypeRepository.findByType(type);
    }
}
