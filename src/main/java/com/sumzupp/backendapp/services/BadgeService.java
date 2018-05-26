package com.sumzupp.backendapp.services;

import com.sumzupp.backendapp.entities.Badge;
import com.sumzupp.backendapp.repositories.BadgeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by akash.mercer on 08-Jun-17.
 */

@Service
public class BadgeService {
    private static final String TAG = "BadgeService : ";

    @Autowired
    private BadgeRepository badgeRepository;

    private static Logger debugLogger = LoggerFactory.getLogger("debugLogs");

    private static Logger errorLogger = LoggerFactory.getLogger("errorLogs");

    public Integer getCount() {
        return badgeRepository.getCount();
    }

    public Badge findByType(Integer type) {
        return badgeRepository.findByType(type);
    }
}
