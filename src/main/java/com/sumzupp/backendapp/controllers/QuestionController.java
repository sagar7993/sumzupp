package com.sumzupp.backendapp.controllers;

import com.sumzupp.backendapp.beans.GetQuestionBankBean;
import com.sumzupp.backendapp.beans.QuestionBean;
import com.sumzupp.backendapp.beans.QuestionDateBean;
import com.sumzupp.backendapp.beans.StatusBean;
import com.sumzupp.backendapp.services.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by akash.mercer on 10-Jul-17.
 */

@RestController
@RequestMapping(value = "/question")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @RequestMapping(value = "/findByQuestionId/{questionId}", method = RequestMethod.GET)
    public @ResponseBody QuestionBean findByQuestionId(@PathVariable("questionId") String questionId) {
        return questionService.findByQuestionId(questionId);
    }

    @RequestMapping(value = "/getQuestionsByBoardAndStandardAndSubjectChapter", method = RequestMethod.POST, consumes = "application/json")
    public @ResponseBody List<QuestionBean> getQuestionsByBoardAndStandardAndSubjectChapter(@RequestBody GetQuestionBankBean getQuestionBankBean) {
        return questionService.getQuestionsByBoardAndStandardAndSubjectChapter(getQuestionBankBean);
    }

    @RequestMapping(value = "/getQuestionsByStandardAndSubjectChapter", method = RequestMethod.POST, consumes = "application/json")
    public @ResponseBody List<QuestionDateBean> getQuestionsByStandardAndSubjectChapter(@RequestBody GetQuestionBankBean getQuestionBankBean) {
        return questionService.getQuestionsByStandardAndSubjectChapter(getQuestionBankBean);
    }

    @RequestMapping(value = "/editQuestion", method = RequestMethod.POST, consumes = "application/json")
    public @ResponseBody StatusBean editQuestion(@RequestBody QuestionBean questionBean) {
        return questionService.editQuestion(questionBean);
    }

}
