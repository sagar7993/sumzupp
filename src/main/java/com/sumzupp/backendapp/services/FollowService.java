package com.sumzupp.backendapp.services;

import com.sumzupp.backendapp.repositories.FollowRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by akash.mercer on 18-Jun-17.
 */

@Service
public class FollowService {
    private static final String TAG = "FollowService : ";

    @Autowired
    private FollowRepository followRepository;

    private static Logger debugLogger = Logger.getLogger("debugLogs");

    private static Logger errorLogger = Logger.getLogger("errorLogs");

}
