package com.sumzupp.backendapp.controllers;

import com.sumzupp.backendapp.beans.FeedBean;
import com.sumzupp.backendapp.services.FeedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

/**
 * Created by akash.mercer on 21-Jun-17.
 */

@RestController
@RequestMapping(value = "/feed")
public class FeedController {

    @Autowired
    private FeedService feedService;

    @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
    public @ResponseBody FeedBean getFeed(@PathVariable("userId") String userId, @PageableDefault(size = 20, sort = {"createdAt"}, direction = Sort.Direction.DESC) Pageable pageable) {
        return feedService.getFeed(userId, pageable);
    }
}
