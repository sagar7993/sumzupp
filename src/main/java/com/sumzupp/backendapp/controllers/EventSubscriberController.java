package com.sumzupp.backendapp.controllers;

import com.sumzupp.backendapp.beans.EventSubscriberBean;
import com.sumzupp.backendapp.beans.EventSubscribersBean;
import com.sumzupp.backendapp.beans.StatusBean;
import com.sumzupp.backendapp.services.EventSubscriberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by akash.mercer on 22-Mar-18.
 */

@RestController
@RequestMapping(value = "/eventSubscriber")
public class EventSubscriberController {

    @Autowired
    private EventSubscriberService eventSubscriberService;

    @RequestMapping(value = "/save/{eventId}", method = RequestMethod.POST, consumes = "application/json")
    public @ResponseBody StatusBean save(@PathVariable("eventId") String eventId, @RequestBody EventSubscriberBean eventSubscriberBean) {
        return eventSubscriberService.save(eventId, eventSubscriberBean);
    }

    @RequestMapping(value = "/findByEvent/{eventId}", method = RequestMethod.GET)
    public @ResponseBody EventSubscribersBean findByEvent(@PathVariable("eventId") String eventId) {
        return eventSubscriberService.findByEvent(eventId);
    }
}
