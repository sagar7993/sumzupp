package com.sumzupp.backendapp.services;

import com.sumzupp.backendapp.beans.FeedBean;
import com.sumzupp.backendapp.beans.UserBean;
import com.sumzupp.backendapp.entities.TeacherInstitute;
import com.sumzupp.backendapp.entities.User;
import com.sumzupp.backendapp.enums.UserTypeEnum;
import com.sumzupp.backendapp.utils.Constants;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Created by akash.mercer on 21-Jun-17.
 */

@Service
public class FeedService {
    private static final String TAG = "FeedService : ";

    @Autowired
    private UserService userService;

    @Autowired
    private TeacherInstituteService teacherInstituteService;

    @Autowired
    private PostService postService;

    @Autowired
    private AppConfigService appConfigService;

    private static Logger debugLogger = Logger.getLogger("debugLogs");

    private static Logger errorLogger = Logger.getLogger("errorLogs");

    public FeedBean getFeed(String userId, Pageable pageable) {
        FeedBean feedBean = new FeedBean();

        User retrievedUser = userService.findById(userId);

        if (retrievedUser == null) {
            errorLogger.error(TAG + "Error in finding User with id : " + userId);

            feedBean.setStatus(0);
            feedBean.setMessage(Constants.SOMETHING_WENT_WRONG);

            return feedBean;
        }

        feedBean.setPostPage(postService.fetchAll(pageable));

        if (UserTypeEnum.STUDENT.getType() == retrievedUser.getUserType().getType()) {
            feedBean.setUserBean(new UserBean(retrievedUser, null));

            if (retrievedUser.getStandardDivision().getInstitute().getId().equals("7")) {
                feedBean.setSelectInstitute(true);
            }
        } else {
            TeacherInstitute retrievedTeacherInstitute = teacherInstituteService.findByTeacher(retrievedUser);

            feedBean.setUserBean(new UserBean(retrievedUser, retrievedTeacherInstitute.getInstitute()));
        }

        feedBean.setAppConfigBean(appConfigService.getAppConfig());

        feedBean.setStatus(1);
        return feedBean;
    }
}
