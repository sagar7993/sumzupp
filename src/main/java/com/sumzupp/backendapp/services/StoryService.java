package com.sumzupp.backendapp.services;

import com.sumzupp.backendapp.repositories.StoryRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by akash.mercer on 08-Jun-17.
 */

@Service
public class StoryService {
    private static final String TAG = "StoryService : ";

    @Autowired
    private StoryRepository storyRepository;

    private static Logger debugLogger = Logger.getLogger("debugLogs");

    private static Logger errorLogger = Logger.getLogger("errorLogs");

}
