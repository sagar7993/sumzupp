package com.sumzupp.backendapp.controllers;

import com.sumzupp.backendapp.beans.HomeBean;
import com.sumzupp.backendapp.services.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

/**
 * Created by akash.mercer on 20-Jan-18.
 */

@RestController
@RequestMapping(value = "/home")
public class HomeController {

    @Autowired
    private HomeService homeService;

    @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
    public @ResponseBody HomeBean getHome(@PathVariable("userId") String userId, @PageableDefault(size = 20, sort = {"createdAt"}, direction = Sort.Direction.DESC) Pageable pageable) {
        return homeService.getHome(userId, pageable);
    }
}

