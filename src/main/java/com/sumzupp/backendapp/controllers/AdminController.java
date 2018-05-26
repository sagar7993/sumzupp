package com.sumzupp.backendapp.controllers;

import com.sumzupp.backendapp.beans.AdminLoginStatusBean;
import com.sumzupp.backendapp.beans.LoginBean;
import com.sumzupp.backendapp.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by akash.mercer on 08-Jun-17.
 */

@RestController
@RequestMapping(value = "/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @RequestMapping(value = "/login", method = RequestMethod.POST, consumes = "application/json")
    public @ResponseBody AdminLoginStatusBean login(@RequestBody LoginBean loginBean){
        return adminService.login(loginBean);
    }

}
