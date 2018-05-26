package com.sumzupp.backendapp.controllers;

import com.sumzupp.backendapp.beans.PostBean;
import com.sumzupp.backendapp.beans.PostVoteBean;
import com.sumzupp.backendapp.beans.StatusBean;
import com.sumzupp.backendapp.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by akash.mercer on 04-Jun-17.
 */

@RestController
@RequestMapping(value = "/post")
public class PostController {

    @Autowired
    private PostService postService;

    @RequestMapping(value = "/save", method = RequestMethod.POST, consumes = "application/json")
    public @ResponseBody StatusBean save(@RequestBody PostBean postBean) {
        return postService.save(postBean);
    }

    @RequestMapping(value = "/vote", method = RequestMethod.POST, consumes = "application/json")
    public @ResponseBody StatusBean vote(@RequestBody PostVoteBean postVoteBean) {
        return postService.vote(postVoteBean);
    }
}
