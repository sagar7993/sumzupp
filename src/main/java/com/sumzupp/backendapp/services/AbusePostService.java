package com.sumzupp.backendapp.services;

import com.sumzupp.backendapp.repositories.AbusePostRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by akash.mercer on 05-Jun-17.
 */

@Service
public class AbusePostService {
    private static final String TAG = "AbusePostService : ";

    @Autowired
    private AbusePostRepository abusePostRepository;

    private static Logger debugLogger = LoggerFactory.getLogger("debugLogs");

    private static Logger errorLogger = LoggerFactory.getLogger("errorLogs");

}
