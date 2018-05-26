package com.sumzupp.backendapp.beans;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by akash.mercer on 22-Mar-18.
 */
public class EventSubscribersBean extends StatusBean {

    private List<EventSubscriberBean> eventSubscriberBeans = new ArrayList<>();

    public EventSubscribersBean() {

    }

    public List<EventSubscriberBean> getEventSubscriberBeans() {
        return eventSubscriberBeans;
    }

    public void setEventSubscriberBeans(List<EventSubscriberBean> eventSubscriberBeans) {
        this.eventSubscriberBeans = eventSubscriberBeans;
    }
}
