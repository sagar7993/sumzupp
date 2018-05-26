package com.sumzupp.backendapp.services;

import com.sumzupp.backendapp.beans.EventBean;
import com.sumzupp.backendapp.beans.EventDetailsBean;
import com.sumzupp.backendapp.beans.StatusBean;
import com.sumzupp.backendapp.entities.Event;
import com.sumzupp.backendapp.repositories.EventRepository;
import com.sumzupp.backendapp.utils.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Created by akash.mercer on 22-Mar-18.
 */

@Service
public class EventService {
    private static final String TAG = "EventService : ";

    @Autowired
    private EventRepository eventRepository;

    private static Logger debugLogger = LoggerFactory.getLogger("debugLogs");

    private static Logger errorLogger = LoggerFactory.getLogger("errorLogs");

    public StatusBean save(EventBean eventBean) {
        StatusBean statusBean = new StatusBean();

        Event event = new Event(eventBean);

        try {
            eventRepository.save(event);

            statusBean.setStatus(1);
        } catch (Exception e) {
            errorLogger.error(TAG + "Error in saving Event with error : " + e.getMessage());

            statusBean.setStatus(0);
            statusBean.setMessage(Constants.SOMETHING_WENT_WRONG);
        }

        return statusBean;
    }

    public Event findById(String eventId) {
        return eventRepository.findOne(eventId);
    }

    public EventDetailsBean findEventById(@PathVariable("eventId") String eventId) {
        EventDetailsBean eventDetailsBean = new EventDetailsBean();

        Event retrievedEvent = eventRepository.findOne(eventId);

        if (retrievedEvent == null) {
            errorLogger.error(TAG + "Error in finding Event with id : " + eventId);

            eventDetailsBean.setStatus(0);
            eventDetailsBean.setMessage(Constants.SOMETHING_WENT_WRONG);

            return eventDetailsBean;
        }

        eventDetailsBean.setEventBean(eventRepository.findById(eventId));

        eventDetailsBean.setStatus(1);
        return eventDetailsBean;
    }
}
