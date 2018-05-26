package com.sumzupp.backendapp.controllers;

import com.sumzupp.backendapp.services.FollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by akash.mercer on 21-Jun-17.
 */

@RestController
@RequestMapping(value = "/follow")
public class FollowController {

    @Autowired
    private FollowService followService;

}
