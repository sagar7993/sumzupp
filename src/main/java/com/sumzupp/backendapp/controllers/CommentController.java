package com.sumzupp.backendapp.controllers;

import com.sumzupp.backendapp.beans.CommentBean;
import com.sumzupp.backendapp.beans.CommentsBean;
import com.sumzupp.backendapp.beans.DataSaveResultBean;
import com.sumzupp.backendapp.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

/**
 * Created by akash.mercer on 04-Jun-17.
 */

@RestController
@RequestMapping(value = "/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @RequestMapping(value = "/save", method = RequestMethod.POST, consumes = "application/json")
    public @ResponseBody DataSaveResultBean save(@RequestBody CommentBean commentBean) {
        return commentService.save(commentBean);
    }

    @RequestMapping(value = "/findByPost/{postId}", method = RequestMethod.GET)
    public @ResponseBody CommentsBean findByPost(@PathVariable("postId") String postId, @PageableDefault(size = 20, sort = {"createdAt"}, direction = Sort.Direction.ASC) Pageable pageable) {
        return commentService.findByPost(postId, pageable);
    }

}
