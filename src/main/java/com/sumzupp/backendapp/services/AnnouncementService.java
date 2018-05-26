package com.sumzupp.backendapp.services;

import com.sumzupp.backendapp.repositories.AnnouncementRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by akash.mercer on 04-Jun-17.
 */

@Service
public class AnnouncementService {
    private static final String TAG = "AnnouncementService : ";

    @Autowired
    private AnnouncementRepository announcementRepository;

    private static Logger debugLogger = LoggerFactory.getLogger("debugLogs");

    private static Logger errorLogger = LoggerFactory.getLogger("errorLogs");
    
}
