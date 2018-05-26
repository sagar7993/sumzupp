package com.sumzupp.backendapp.controllers;

import com.sumzupp.backendapp.beans.StandardsAndBoardsBean;
import com.sumzupp.backendapp.services.StandardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by akash.mercer on 04-Jun-17.
 */

@RestController
@RequestMapping(value = "/standard")
public class StandardController {

    @Autowired
    private StandardService standardService;

    @RequestMapping(value = "/fetchStandardsAndBoards", method = RequestMethod.GET)
    public @ResponseBody StandardsAndBoardsBean fetchStandardsAndBoards() {
        return standardService.fetchStandardsAndBoards();
    }
}
