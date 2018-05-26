package com.sumzupp.backendapp.services;

import com.sumzupp.backendapp.beans.LeaderboardBean;
import com.sumzupp.backendapp.entities.StudentRanking;
import com.sumzupp.backendapp.entities.Subject;
import com.sumzupp.backendapp.entities.User;
import com.sumzupp.backendapp.repositories.StudentRankingRepository;
import com.sumzupp.backendapp.utils.Constants;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by akash.mercer on 08-Jun-17.
 */

@Service
public class StudentRankingService {
    private static final String TAG = "StudentRankingService : ";

    @Autowired
    private StudentRankingRepository studentRankingRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private SubjectService subjectService;

    @Autowired
    private AssignmentService assignmentService;

    @Autowired
    private StudentAssignmentService studentAssignmentService;

    private static Logger debugLogger = Logger.getLogger("debugLogs");

    private static Logger errorLogger = Logger.getLogger("errorLogs");

    public void save(StudentRanking studentRanking) {
        studentRankingRepository.save(studentRanking);
    }

    public LeaderboardBean getLeaderboard(String userId, String subjectId, Pageable pageable) {
        LeaderboardBean leaderboardBean = new LeaderboardBean();

        User retrievedUser = userService.findById(userId);

        if (retrievedUser == null) {
            errorLogger.error(TAG + "Error in finding User with id : " + userId);

            leaderboardBean.setStatus(0);
            leaderboardBean.setMessage(Constants.SOMETHING_WENT_WRONG);

            return leaderboardBean;
        }

        Subject retrievedSubject = subjectService.findById(subjectId);

        if (retrievedSubject == null) {
            errorLogger.error(TAG + "Error in finding Subject with id : " + subjectId);

            leaderboardBean.setStatus(0);
            leaderboardBean.setMessage(Constants.SOMETHING_WENT_WRONG);

            return leaderboardBean;
        }

        List<String> assignmentIds = new ArrayList<>();

        List<String> globalAssignmentIds = assignmentService.findAssignmentIdsByGlobalAndSubject(retrievedSubject);

        List<String> standardGlobalAssignmentIds = assignmentService.findAssignmentIdsByStandardGlobalAndSubject(retrievedUser.getStandardDivision().getStandard(), retrievedSubject);

        if (!CollectionUtils.isEmpty(globalAssignmentIds)) {
            assignmentIds.addAll(globalAssignmentIds);
        }

        if (!CollectionUtils.isEmpty(standardGlobalAssignmentIds)) {
            assignmentIds.addAll(standardGlobalAssignmentIds);
        }

        if (!CollectionUtils.isEmpty(assignmentIds)) {
            leaderboardBean.setStudentRankingDetailsBeans(studentAssignmentService.findByAssignmentIds(assignmentIds, pageable));

            Collections.sort(leaderboardBean.getStudentRankingDetailsBeans(), (lhs, rhs) -> rhs.getPoints().compareTo(lhs.getPoints()));
        }

        leaderboardBean.setStatus(1);
        return leaderboardBean;
    }
}
