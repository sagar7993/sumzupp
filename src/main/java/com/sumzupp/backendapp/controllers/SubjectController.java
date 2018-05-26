package com.sumzupp.backendapp.controllers;

import com.sumzupp.backendapp.beans.SubjectsBean;
import com.sumzupp.backendapp.services.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by akash.mercer on 08-Jun-17.
 */

@RestController
@RequestMapping(value = "/subject")
public class SubjectController {

    @Autowired
    private SubjectService subjectService;

    @RequestMapping(value = "/findByTeacher/{teacherId}", method = RequestMethod.GET)
    public @ResponseBody SubjectsBean findByTeacher(@PathVariable("teacherId") String teacherId){
        return subjectService.findByTeacher(teacherId);
    }

    @RequestMapping(value = "/findByStudent/{studentId}", method = RequestMethod.GET)
    public @ResponseBody SubjectsBean findByStudent(@PathVariable("studentId") String studentId) {
        return subjectService.findByStudent(studentId, null);
    }

    @RequestMapping(value = "/findAll", method = RequestMethod.GET)
    public @ResponseBody SubjectsBean findAllSubjectBean(){
        return subjectService.findAllSubjectBean();
    }

}
