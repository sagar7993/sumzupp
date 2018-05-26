package com.sumzupp.backendapp.controllers;

import com.sumzupp.backendapp.beans.LeaderboardBean;
import com.sumzupp.backendapp.services.StudentRankingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

/**
 * Created by akash.mercer on 05-Nov-17.
 */

@RestController
@RequestMapping("/studentRanking")
public class StudentRankingController {

    @Autowired
    private StudentRankingService studentRankingService;

    @RequestMapping(value = "/getLeaderboard/{userId}/{subjectId}", method = RequestMethod.GET)
    public @ResponseBody LeaderboardBean getLeaderboard(@PathVariable("userId") String userId, @PathVariable("subjectId") String subjectId, @PageableDefault(sort = {"points"}, direction = Sort.Direction.DESC) Pageable pageable) {
        return studentRankingService.getLeaderboard(userId, subjectId, pageable);
    }
}
