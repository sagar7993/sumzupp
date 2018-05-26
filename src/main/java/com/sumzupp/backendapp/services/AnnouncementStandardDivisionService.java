package com.sumzupp.backendapp.services;

import com.sumzupp.backendapp.repositories.AnnouncementStandardDivisionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by akash.mercer on 05-Jun-17.
 */

@Service
public class AnnouncementStandardDivisionService {
    private static final String TAG = "AnnouncementStandardService : ";

    @Autowired
    private AnnouncementStandardDivisionRepository announcementStandardDivisionRepository;

    private static Logger debugLogger = LoggerFactory.getLogger("debugLogs");

    private static Logger errorLogger = LoggerFactory.getLogger("errorLogs");

}
