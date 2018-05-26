package com.sumzupp.backendapp.controllers;

import com.sumzupp.backendapp.beans.StatusBean;
import com.sumzupp.backendapp.beans.UserAccountLinkBean;
import com.sumzupp.backendapp.services.MiscellaneousService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by akash.mercer on 06-Aug-17.
 */

@RestController
@RequestMapping(value = "/miscellaneous")
public class MiscellaneousController {

    @Autowired
    private MiscellaneousService miscellaneousService;

    @Deprecated
    @RequestMapping(value = "/populateAssignmentTotal", method = RequestMethod.GET)
    public @ResponseBody StatusBean populateAssignmentTotal() {
        return miscellaneousService.populateAssignmentTotal();
    }

    @Deprecated
    @RequestMapping(value = "/populateStudentAssignmentDates", method = RequestMethod.GET)
    public @ResponseBody StatusBean populateStudentAssignmentDates() {
        return miscellaneousService.populateStudentAssignmentDates();
    }

    @Deprecated
    @RequestMapping(value = "/populateStudentAssignmentQuestions", method = RequestMethod.GET)
    public @ResponseBody StatusBean populateStudentAssignmentQuestions() throws Exception {
        return miscellaneousService.populateStudentAssignmentQuestions();
    }

    @RequestMapping(value = "/linkUserAccounts", method = RequestMethod.POST, consumes = "application/json")
    public @ResponseBody StatusBean linkUserAccounts(@RequestBody List<UserAccountLinkBean> userAccountLinkBeans) {
        return miscellaneousService.linkUserAccounts(userAccountLinkBeans);
    }
}
