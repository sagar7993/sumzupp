package com.sumzupp.backendapp.controllers;

import com.sumzupp.backendapp.services.StoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by akash.mercer on 08-Jun-17.
 */

@RestController
@RequestMapping(value = "/story")
public class StoryContoller {

    @Autowired
    private StoryService storyService;

}
