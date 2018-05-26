package com.sumzupp.backendapp.controllers;

import com.sumzupp.backendapp.beans.AddChapterBean;
import com.sumzupp.backendapp.beans.StatusBean;
import com.sumzupp.backendapp.beans.SubjectChapterBean;
import com.sumzupp.backendapp.beans.SubjectChaptersBean;
import com.sumzupp.backendapp.services.SubjectChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Sagar Jain on 7/23/2017.
 */

@RestController
@RequestMapping(value = "/subjectChapter")
public class SubjectChapterController {

    @Autowired SubjectChapterService subjectChapterService;

    @RequestMapping(value = "/findBySubject/{subjectId}/{studentId}", method = RequestMethod.GET)
    public @ResponseBody SubjectChaptersBean findBySubject(@PathVariable("subjectId") String subjectId, @PathVariable("studentId") String studentId) {
        return subjectChapterService.findBySubject(subjectId, studentId);
    }

    @RequestMapping(value = "/findBySubjectAndBoardAndStandard/{subjectId}/{boardId}/{standardId}", method = RequestMethod.GET)
    public @ResponseBody List<SubjectChapterBean> findBySubjectAndBoardAndStandard(@PathVariable("subjectId") String subjectId, @PathVariable("boardId") String boardId, @PathVariable("standardId") String standardId){
        return subjectChapterService.findBySubjectAndBoardAndStandard(subjectId, boardId, standardId);
    }

    @RequestMapping(value = "/findBySubjectAndStandard/{subjectId}/{standardId}", method = RequestMethod.GET)
    public @ResponseBody List<SubjectChapterBean> findBySubjectAndStandard(@PathVariable("subjectId") String subjectId, @PathVariable("standardId") String standardId){
        return subjectChapterService.findBySubjectAndStandard(subjectId, standardId);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST, consumes = "application/json")
    public @ResponseBody StatusBean save(@RequestBody AddChapterBean addChapterBean){
        return subjectChapterService.save(addChapterBean);
    }

}
