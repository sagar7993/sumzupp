package com.sumzupp.backendapp.controllers;

import com.sumzupp.backendapp.beans.*;
import com.sumzupp.backendapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by akash.mercer on 04-Jun-17.
 */

@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Deprecated
    @RequestMapping(value = "/isRegisteredUsername", method = RequestMethod.GET)
    public @ResponseBody StatusBean isRegisteredUsername(@RequestParam("username") String username) {
        return userService.isRegisteredUsername(username);
    }

    @RequestMapping(value = "/isRegisteredUsername", method = RequestMethod.POST, consumes = "application/json")
    public @ResponseBody StatusBean isRegisteredUsername(@RequestBody UsernameBean usernameBean) {
        return userService.isRegisteredUsername(usernameBean.getUsername());
    }

    @RequestMapping(value = "/getUsers", method = RequestMethod.GET)
    public @ResponseBody UsersBean getUsers(@RequestParam("userTypes") String userTypes, @RequestParam(value = "instituteId", required = false) String instituteId) {
        return userService.getUsers(userTypes, instituteId);
    }

    @RequestMapping(value = "/signUp", method = RequestMethod.POST, consumes = "application/json")
    public @ResponseBody LoginResultBean signUp(@RequestBody SignUpBean signUpBean) throws Exception {
        return userService.signUp(signUpBean);
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST, consumes = "application/json")
    public @ResponseBody LoginResultBean login(@RequestBody LoginBean loginBean) {
        return userService.login(loginBean);
    }

    @RequestMapping(value = "/fetchInstituteNames", method = RequestMethod.GET)
    public @ResponseBody InstitutesNamesBean fetchInstituteNames() {
        return userService.fetchInstituteNames();
    }

    @RequestMapping(value = "/selectInstitute", method = RequestMethod.POST, consumes = "application/json")
    public @ResponseBody StatusBean selectInstitute(@RequestBody StudentInstituteBean studentInstituteBean) {
        return userService.selectInstitute(studentInstituteBean);
    }

    @RequestMapping(value = "/updateStandardDivision/{userId}", method = RequestMethod.POST, consumes = "application/json")
    public @ResponseBody StatusBean updateStandardDivision(@PathVariable("userId") String userId, @RequestBody UpdateStandardDivisionBean updateStandardDivisionBean) {
        return userService.updateStandardDivision(userId, updateStandardDivisionBean);
    }

    @RequestMapping(value = "/resetCredentials", method = RequestMethod.POST, consumes = "application/json")
    public @ResponseBody StatusBean resetCredentials(@RequestBody CredentialBean credentialBean) {
        return userService.resetCredentials(credentialBean);
    }

    @RequestMapping(value = "/updateNotificationToken", method = RequestMethod.POST, consumes = "application/json")
    public @ResponseBody StatusBean updateNotificationToken(@RequestBody NotificationTokenBean notificationTokenBean) {
        return userService.updateNotificationToken(notificationTokenBean);
    }

}
