package com.sumzupp.backendapp.services;

import com.sumzupp.backendapp.entities.BannerType;
import com.sumzupp.backendapp.repositories.BannerTypeRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by akash.mercer on 02-Jan-18.
 */

@Service
public class BannerTypeService {

    @Autowired
    private BannerTypeRepository bannerTypeRepository;

    private static Logger debugLogger = Logger.getLogger("debugLogs");

    private static Logger errorLogger = Logger.getLogger("errorLogs");

    public Integer getCount() {
        return bannerTypeRepository.getCount();
    }

    public BannerType findByType(Integer type) {
        return bannerTypeRepository.findByType(type);
    }
}
