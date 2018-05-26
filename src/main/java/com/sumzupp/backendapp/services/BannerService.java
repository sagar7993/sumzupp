package com.sumzupp.backendapp.services;

import com.sumzupp.backendapp.beans.BannerBean;
import com.sumzupp.backendapp.repositories.BannerRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BannerService {
    private static final String TAG = "BannerService : ";

    @Autowired
    private BannerRepository bannerRepository;

    private static Logger debugLogger = Logger.getLogger("debugLogs");

    private static Logger errorLogger = Logger.getLogger("errorLogs");

    public List<BannerBean> fetchActiveBanners(){
        return bannerRepository.fetchActiveBanners();
    }

}