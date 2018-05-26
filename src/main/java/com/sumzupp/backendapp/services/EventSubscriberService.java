package com.sumzupp.backendapp.services;

import com.sumzupp.backendapp.beans.EventSubscriberBean;
import com.sumzupp.backendapp.beans.EventSubscribersBean;
import com.sumzupp.backendapp.beans.StatusBean;
import com.sumzupp.backendapp.entities.Board;
import com.sumzupp.backendapp.entities.Event;
import com.sumzupp.backendapp.entities.EventSubscriber;
import com.sumzupp.backendapp.entities.UserType;
import com.sumzupp.backendapp.repositories.EventSubscriberRepository;
import com.sumzupp.backendapp.utils.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by akash.mercer on 22-Mar-18.
 */

@Service
public class EventSubscriberService {
    private static final String TAG = "EventSubscriberService : ";

    @Autowired
    private EventSubscriberRepository eventSubscriberRepository;

    @Autowired
    private EventService eventService;

    @Autowired
    private UserTypeService userTypeService;

    @Autowired
    private BoardService boardService;

    private static Logger debugLogger = LoggerFactory.getLogger("debugLogs");

    private static Logger errorLogger = LoggerFactory.getLogger("errorLogs");

    public StatusBean save(String eventId, EventSubscriberBean eventSubscriberBean) {
        StatusBean statusBean = new StatusBean();

        Event retrievedEvent = eventService.findById(eventId);

        if (retrievedEvent == null) {
            errorLogger.error(TAG + "Error in finding Event with id : " + eventId);

            statusBean.setStatus(0);
            statusBean.setMessage(Constants.SOMETHING_WENT_WRONG);

            return statusBean;
        }

        UserType retrievedUserType = userTypeService.findByType(eventSubscriberBean.getUserType());

        if (retrievedUserType == null) {
            errorLogger.error(TAG + "Error in finding UserType with type : " + eventSubscriberBean.getUserType());

            statusBean.setStatus(0);
            statusBean.setMessage(Constants.SOMETHING_WENT_WRONG);

            return statusBean;
        }

        Board retrievedBoard = boardService.findByType(eventSubscriberBean.getBoard());

        if (retrievedBoard == null) {
            errorLogger.error(TAG + "Error in finding Board with type : " + eventSubscriberBean.getBoard());

            statusBean.setStatus(0);
            statusBean.setMessage(Constants.SOMETHING_WENT_WRONG);

            return statusBean;
        }

        EventSubscriber eventSubscriber = new EventSubscriber(eventSubscriberBean, retrievedEvent, retrievedUserType, retrievedBoard);

        try {
            eventSubscriberRepository.save(eventSubscriber);

            statusBean.setStatus(1);
        } catch (Exception e) {
            errorLogger.error(TAG + "Error in saving EventSubscriber with error : " + e.getMessage());

            statusBean.setStatus(0);
            statusBean.setMessage(Constants.SOMETHING_WENT_WRONG);
        }

        return statusBean;
    }

    public EventSubscribersBean findByEvent(String eventId) {
        EventSubscribersBean eventSubscribersBean = new EventSubscribersBean();

        Event retrievedEvent = eventService.findById(eventId);

        if (retrievedEvent == null) {
            errorLogger.error(TAG + "Error in finding Event with id : " + eventId);

            eventSubscribersBean.setStatus(0);
            eventSubscribersBean.setMessage(Constants.SOMETHING_WENT_WRONG);

            return eventSubscribersBean;
        }

        eventSubscribersBean.setEventSubscriberBeans(eventSubscriberRepository.findByEvent(eventId));

        eventSubscribersBean.setStatus(1);
        return eventSubscribersBean;
    }
}
