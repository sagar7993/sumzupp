package com.sumzupp.backendapp.controllers;

import com.sumzupp.backendapp.beans.QuestionPapersBean;
import com.sumzupp.backendapp.services.QuestionPaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by akash.mercer on 28-Oct-17.
 */

@RestController
@RequestMapping(value = "/questionPaper")
public class QuestionPaperController {

    @Autowired
    private QuestionPaperService questionPaperService;

    @RequestMapping(value = "/findByUserAndSubjectChapter/{userId}/{subjectChapterId}", method = RequestMethod.GET)
    public @ResponseBody QuestionPapersBean findByUserAndSubjectChapter(@PathVariable("userId") String userId, @PathVariable("subjectChapterId") String subjectChapterId) {
        return questionPaperService.findByUserAndSubjectChapter(userId, subjectChapterId);
    }
}