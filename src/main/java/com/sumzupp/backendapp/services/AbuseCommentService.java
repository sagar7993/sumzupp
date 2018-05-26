package com.sumzupp.backendapp.services;

import com.sumzupp.backendapp.repositories.AbuseCommentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by akash.mercer on 05-Jun-17.
 */

@Service
public class AbuseCommentService {
    private static final String TAG = "AbuseCommentService : ";

    @Autowired
    private AbuseCommentRepository abuseCommentRepository;

    private static Logger debugLogger = LoggerFactory.getLogger("debugLogs");

    private static Logger errorLogger = LoggerFactory.getLogger("errorLogs");

}
