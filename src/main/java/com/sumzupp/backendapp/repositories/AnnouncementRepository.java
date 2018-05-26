package com.sumzupp.backendapp.repositories;

import com.sumzupp.backendapp.entities.Announcement;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by akash.mercer on 04-Jun-17.
 */

@Repository
public interface AnnouncementRepository extends CrudRepository<Announcement, String> {

}
