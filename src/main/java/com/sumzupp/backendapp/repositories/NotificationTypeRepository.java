package com.sumzupp.backendapp.repositories;

import com.sumzupp.backendapp.entities.NotificationType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by akash.mercer on 09-Aug-17.
 */

@Repository
public interface NotificationTypeRepository extends CrudRepository<NotificationType, String> {

    @Query(value = "select count(*) from NotificationType")
    Integer getCount();

    NotificationType findByType(Integer type);
}
