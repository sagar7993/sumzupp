package com.sumzupp.backendapp.controllers;

import com.sumzupp.backendapp.beans.EventBean;
import com.sumzupp.backendapp.beans.EventDetailsBean;
import com.sumzupp.backendapp.beans.StatusBean;
import com.sumzupp.backendapp.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by akash.mercer on 22-Mar-18.
 */

@RestController
@RequestMapping(value = "/event")
public class EventController {

    @Autowired
    private EventService eventService;

    @RequestMapping(value = "/save", method = RequestMethod.POST, consumes = "application/json")
    public @ResponseBody StatusBean save(@RequestBody EventBean eventBean) {
        return eventService.save(eventBean);
    }

    @RequestMapping(value = "/findById/{eventId}", method = RequestMethod.GET)
    public @ResponseBody EventDetailsBean findEventById(@PathVariable("eventId") String eventId) {
        return eventService.findEventById(eventId);
    }
}
