package com.sumzupp.backendapp.services;

import com.sumzupp.backendapp.entities.QuestionType;
import com.sumzupp.backendapp.repositories.QuestionTypeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by akash.mercer on 05-Jul-17.
 */

@Service
public class QuestionTypeService {
    private static final String TAG = "QuestionTypeService : ";

    @Autowired
    private QuestionTypeRepository questionTypeRepository;

    private static Logger debugLogger = LoggerFactory.getLogger("debugLogs");

    private static Logger errorLogger = LoggerFactory.getLogger("errorLogs");

    public Integer getCount() {
        return questionTypeRepository.getCount();
    }

    public List<QuestionType> fetchAll() {
        return (List<QuestionType>) questionTypeRepository.findAll();
    }
}
