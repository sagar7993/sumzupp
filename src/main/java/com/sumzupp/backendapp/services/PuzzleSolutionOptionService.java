package com.sumzupp.backendapp.services;

import com.sumzupp.backendapp.repositories.PuzzleSolutionOptionRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by akash.mercer on 18-Jun-17.
 */

@Service
public class PuzzleSolutionOptionService {
    private static final String TAG = "PuzzleSolutionOptionService : ";

    @Autowired
    private PuzzleSolutionOptionRepository puzzleSolutionOptionRepository;

    private static Logger debugLogger = Logger.getLogger("debugLogs");

    private static Logger errorLogger = Logger.getLogger("errorLogs");

}
