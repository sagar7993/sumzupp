package com.sumzupp.backendapp.controllers;

import com.sumzupp.backendapp.entities.StandardDivision;
import com.sumzupp.backendapp.services.StandardDivisionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by akash.mercer on 04-Jun-17.
 */

@RestController
@RequestMapping(value = "/standardDivision")
public class StandardDivisionController {

    @Autowired
    private StandardDivisionService standardDivisionService;

    @RequestMapping(value = "/findByInstitute/{instituteId}", method = RequestMethod.GET)
    public @ResponseBody List<StandardDivision> findByInstitute(@PathVariable("instituteId") String instituteId) {
        return standardDivisionService.findByInstitute(instituteId);
    }

    @RequestMapping(value = "/findByTeacher/{teacherId}", method = RequestMethod.GET)
    public @ResponseBody List<StandardDivision> findByTeacher(@PathVariable("teacherId") String teacherId) {
        return standardDivisionService.findByTeacher(teacherId);
    }

}
