package com.sumzupp.backendapp.beans;

/**
 * Created by akash.mercer on 22-Mar-18.
 */
public class EventDetailsBean extends StatusBean {

    private EventBean eventBean = new EventBean();

    public EventDetailsBean() {

    }

    public EventBean getEventBean() {
        return eventBean;
    }

    public void setEventBean(EventBean eventBean) {
        this.eventBean = eventBean;
    }
}
