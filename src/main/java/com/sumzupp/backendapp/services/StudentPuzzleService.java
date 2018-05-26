package com.sumzupp.backendapp.services;

import com.sumzupp.backendapp.repositories.StudentPuzzleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by akash.mercer on 05-Jul-17.
 */

@Service
public class StudentPuzzleService {
    private static final String TAG = "StudentPuzzleService : ";

    @Autowired
    private StudentPuzzleRepository studentPuzzleRepository;

    private static Logger debugLogger = LoggerFactory.getLogger("debugLogs");

    private static Logger errorLogger = LoggerFactory.getLogger("errorLogs");

}
