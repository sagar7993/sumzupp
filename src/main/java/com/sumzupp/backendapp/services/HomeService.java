package com.sumzupp.backendapp.services;

import com.sumzupp.backendapp.beans.HomeBean;
import com.sumzupp.backendapp.beans.UserBean;
import com.sumzupp.backendapp.entities.User;
import com.sumzupp.backendapp.utils.Constants;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Created by akash.mercer on 20-Jan-18.
 */

@Service
public class HomeService {
    private static final String TAG = "HomeService : ";

    @Autowired
    private UserService userService;

    @Autowired
    private BannerService bannerService;

    @Autowired
    private PostService postService;

    @Autowired
    private AppConfigService appConfigService;

    private static Logger debugLogger = Logger.getLogger("debugLogs");

    private static Logger errorLogger = Logger.getLogger("errorLogs");

    public HomeBean getHome(String userId, Pageable pageable) {
        HomeBean homeBean = new HomeBean();

        User retrievedUser = userService.findById(userId);

        if (retrievedUser == null) {
            errorLogger.error(TAG + "Error in finding User with id : " + userId);

            homeBean.setStatus(0);
            homeBean.setMessage(Constants.SOMETHING_WENT_WRONG);

            return homeBean;
        }

        homeBean.setUserBean(new UserBean(retrievedUser, null));

        if (retrievedUser.getStandardDivision().getInstitute().getId().equals("7")) {
            homeBean.setSelectInstitute(true);
        }

        homeBean.setBannerBeans(bannerService.fetchActiveBanners());

        homeBean.setAppConfigBean(appConfigService.getAppConfig());

        homeBean.setStatus(1);
        return homeBean;
    }
}
