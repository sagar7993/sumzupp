package com.sumzupp.backendapp.controllers;

import com.sumzupp.backendapp.beans.LoginBean;
import com.sumzupp.backendapp.beans.LoginResultBean;
import com.sumzupp.backendapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by akash.mercer on 08-Jun-17.
 */

@RestController
@RequestMapping(value = "/teacher")
public class TeacherController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/webTeacherLogin", method = RequestMethod.POST, consumes = "application/json")
    public @ResponseBody LoginResultBean webTeacherLogin(@RequestBody LoginBean loginBean){
        return userService.webTeacherLogin(loginBean);
    }

}
