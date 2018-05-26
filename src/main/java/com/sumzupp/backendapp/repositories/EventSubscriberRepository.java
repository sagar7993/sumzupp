package com.sumzupp.backendapp.repositories;

import com.sumzupp.backendapp.beans.EventSubscriberBean;
import com.sumzupp.backendapp.entities.EventSubscriber;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by akash.mercer on 22-Mar-18.
 */

@Repository
public interface EventSubscriberRepository extends CrudRepository<EventSubscriber, String> {

    @Query(value = "select new com.sumzupp.backendapp.beans.EventSubscriberBean(e.name, e.contactNumber, e.email, e.userType.type, e.board.type) from EventSubscriber e where e.event.id = :eventId")
    List<EventSubscriberBean> findByEvent(@Param("eventId") String eventId);

}
