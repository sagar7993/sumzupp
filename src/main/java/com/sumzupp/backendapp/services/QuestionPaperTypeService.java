package com.sumzupp.backendapp.services;

import com.sumzupp.backendapp.entities.QuestionPaperType;
import com.sumzupp.backendapp.repositories.QuestionPaperTypeRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by akash.mercer on 28-Oct-17.
 */

@Service
public class QuestionPaperTypeService {
    private static final String TAG = "QuestionPaperTypeService : ";

    @Autowired
    private QuestionPaperTypeRepository questionPaperTypeRepository;

    private static Logger debugLogger = Logger.getLogger("debugLogs");

    private static Logger errorLogger = Logger.getLogger("errorLogs");

    public Integer getCount() {
        return questionPaperTypeRepository.getCount();
    }

    public QuestionPaperType findByType(Integer type) {
        return questionPaperTypeRepository.findByType(type);
    }

}
