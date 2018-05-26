package com.sumzupp.backendapp.services;

import com.sumzupp.backendapp.beans.StandardsAndBoardsBean;
import com.sumzupp.backendapp.entities.Standard;
import com.sumzupp.backendapp.repositories.StandardRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by akash.mercer on 04-Jun-17.
 */

@Service
public class StandardService {
    private static final String TAG = "StandardService : ";

    @Autowired
    private StandardRepository standardRepository;

    @Autowired
    private StandardDivisionService standardDivisionService;

    @Autowired
    private BoardService boardService;

    private static Logger debugLogger = Logger.getLogger("debugLogs");

    private static Logger errorLogger = Logger.getLogger("errorLogs");

    public Integer getCount() {
        return standardRepository.getCount();
    }

    public Standard findById(String id) {
        return standardRepository.findOne(id);
    }

    public Standard findByStandardName(Integer standardName) {
        return standardRepository.findByStandardName(standardName);
    }

    public StandardsAndBoardsBean fetchStandardsAndBoards() {
        StandardsAndBoardsBean standardsAndBoardsBean = new StandardsAndBoardsBean();

        // TODO: 10-Sep-17 Below Institute Id is hard-coded on purpose
        standardsAndBoardsBean.setStandardBeans(standardDivisionService.findStandardDivisionsByInstitute("7"));

        standardsAndBoardsBean.setBoardBeans(boardService.fetchAllActiveBoardBeans());

        standardsAndBoardsBean.setStatus(1);
        return standardsAndBoardsBean;
    }
    
}
