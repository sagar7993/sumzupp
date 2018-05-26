package com.sumzupp.backendapp.repositories;

import com.sumzupp.backendapp.beans.EventBean;
import com.sumzupp.backendapp.entities.Event;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by akash.mercer on 22-Mar-18.
 */

@Repository
public interface EventRepository extends CrudRepository<Event, String> {

    @Query(value = "select new com.sumzupp.backendapp.beans.EventBean(e.title, e.content, e.eventDate, e.heroImageUrl, e.eventImageUrls) from Event e where e.id = :eventId")
    EventBean findById(@Param("eventId") String eventId);
}
