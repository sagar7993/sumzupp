package com.sumzupp.backendapp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

/**
 * Created by akash.mercer on 07-Jul-17.
 */

@Service
@Transactional
public class DatabaseInitializeService implements ApplicationListener<ContextRefreshedEvent> {
    private static final String TAG = "DatabaseInitializeService : ";

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private AdminService adminService;

    @Autowired
    private AppConfigService appConfigService;

    @Autowired
    private BadgeService badgeService;

    @Autowired
    private BannerTypeService bannerTypeService;

    @Autowired
    private BoardService boardService;

    @Autowired
    private NotificationTypeService notificationTypeService;

    @Autowired
    private PaymentTypeService paymentTypeService;

    @Autowired
    private QuestionPaperTypeService questionPaperTypeService;

    @Autowired
    private QuestionTypeService questionTypeService;

    @Autowired
    private StandardService standardService;

    @Autowired
    private SubjectService subjectService;

    @Autowired
    private TagTypeService tagTypeService;

    @Autowired
    private UserTypeService userTypeService;

    @Autowired
    private VoteTypeService voteTypeService;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        entityManager.createNativeQuery("SET FOREIGN_KEY_CHECKS = 0;").executeUpdate();

        int adminCount = adminService.getCount();

        if(adminCount == 0) {
            entityManager.createNativeQuery("INSERT INTO admin (id, created_at, admin_name, active, email, contact_number, password, updated_at) VALUES " +
                    "('1', '2017-07-09 00:00:00', 'Akshay Kadam', TRUE, 'hello@sumzupp.com', '9987870743', 'Milano', '2017-07-09 00:00:00');").executeUpdate();
        }

        int appConfigCount = appConfigService.getCount();

        if(appConfigCount == 0) {
            entityManager.createNativeQuery("INSERT INTO app_config (id, created_at, android_user_app_version, android_teacher_app_version, ios_user_app_version, update_message, app_message, updated_at) VALUES " +
                    "('1', '2017-07-09 00:00:00', 1, 1, '1.0', '', '', '2017-07-09 00:00:00');").executeUpdate();
        }

        int badgeCount = badgeService.getCount();

        if(badgeCount == 0) {
            entityManager.createNativeQuery("INSERT INTO badge (id, created_at, name, type, badge_image_url, updated_at) VALUES " +
                    "('1', '2017-07-09 00:00:00', 'Basic', 1, '', '2017-07-09 00:00:00')," +
                    "('2', '2017-07-09 00:00:00', 'Bronze', 2, '', '2017-07-09 00:00:00')," +
                    "('3', '2017-07-09 00:00:00', 'Silver', 3, '', '2017-07-09 00:00:00')," +
                    "('4', '2017-07-09 00:00:00', 'Gold', 4, '', '2017-07-09 00:00:00')," +
                    "('5', '2017-07-09 00:00:00', 'Platinum', 5, '', '2017-07-09 00:00:00');").executeUpdate();
        }

        int bannerTypeCount = bannerTypeService.getCount();

        if(bannerTypeCount == 0) {
            entityManager.createNativeQuery("INSERT INTO banner_type (id, created_at, name, type, updated_at) VALUES " +
                    "('1', '2017-07-09 00:00:00', 'Quiz', 1, '2017-07-09 00:00:00')," +
                    "('2', '2017-07-09 00:00:00', 'Webinar', 2, '2017-07-09 00:00:00');").executeUpdate();
        }

        int boardCount = boardService.getCount();

        if(boardCount == 0) {
            entityManager.createNativeQuery("INSERT INTO board (id, created_at, name, type, updated_at) VALUES " +
                    "('1', '2017-07-09 00:00:00', 'SSC', 1, '2017-07-09 00:00:00')," +
                    "('2', '2017-07-09 00:00:00', 'CBSE', 2, '2017-07-09 00:00:00')," +
                    "('3', '2017-07-09 00:00:00', 'ICSE', 3, '2017-07-09 00:00:00');").executeUpdate();
        }

        int notificationTypeCount = notificationTypeService.getCount();

        if(notificationTypeCount == 0) {
            entityManager.createNativeQuery("INSERT INTO notification_type (id, created_at, name, type, updated_at) VALUES " +
                    "('1', '2017-07-09 00:00:00', 'Assignment Published', 1, '2017-07-09 00:00:00');").executeUpdate();
        }

        int paymentTypeCount = paymentTypeService.getCount();

        if(paymentTypeCount == 0) {
            entityManager.createNativeQuery("INSERT INTO payment_type (id, created_at, name, type, updated_at) VALUES " +
                    "('1', '2018-01-02 00:00:00', 'Others', 1, '2018-01-02 00:00:00');").executeUpdate();
        }

        int questionPaperTypeCount = questionPaperTypeService.getCount();

        if(questionPaperTypeCount == 0) {
            entityManager.createNativeQuery("INSERT INTO question_paper_type (id, created_at, name, type, updated_at) VALUES " +
                    "('1', '2017-10-28 00:00:00', 'Free', 1, '2017-10-28 00:00:00')," +
                    "('2', '2017-10-28 00:00:00', 'Paid', 2, '2017-10-28 00:00:00');").executeUpdate();
        }

        int questionTypeCount = questionTypeService.getCount();

        if(questionTypeCount == 0) {
            entityManager.createNativeQuery("INSERT INTO question_type (id, created_at, name, type, updated_at) VALUES " +
                    "('1', '2017-07-09 00:00:00', 'Choose One', 1, '2017-07-09 00:00:00')," +
                    "('2', '2017-07-09 00:00:00', 'Fill in the Blank', 2, '2017-07-09 00:00:00')," +
                    "('3', '2017-07-09 00:00:00', '', 3, '2017-07-09 00:00:00')," +
                    "('4', '2017-07-09 00:00:00', 'True or False', 4, '2017-07-09 00:00:00')," +
                    "('5', '2017-07-09 00:00:00', 'Support with Reasons', 5, '2017-07-09 00:00:00')," +
                    "('6', '2017-07-09 00:00:00', 'State the kind of Sentences', 6, '2017-07-09 00:00:00');").executeUpdate();
        }

        int standardCount = standardService.getCount();

        if(standardCount == 0) {
            entityManager.createNativeQuery("INSERT INTO standard (id, created_at, standard_name, active, updated_at) VALUES " +
                    "('1', '2017-07-09 00:00:00', 1, FALSE, '2017-07-09 00:00:00')," +
                    "('2', '2017-07-09 00:00:00', 2, FALSE, '2017-07-09 00:00:00')," +
                    "('3', '2017-07-09 00:00:00', 3, FALSE, '2017-07-09 00:00:00')," +
                    "('4', '2017-07-09 00:00:00', 4, FALSE, '2017-07-09 00:00:00')," +
                    "('5', '2017-07-09 00:00:00', 5, FALSE, '2017-07-09 00:00:00')," +
                    "('6', '2017-07-09 00:00:00', 6, FALSE, '2017-07-09 00:00:00')," +
                    "('7', '2017-07-09 00:00:00', 7, FALSE, '2017-07-09 00:00:00')," +
                    "('8', '2017-07-09 00:00:00', 8, TRUE, '2017-07-09 00:00:00')," +
                    "('9', '2017-07-09 00:00:00', 9, TRUE, '2017-07-09 00:00:00')," +
                    "('10', '2017-07-09 00:00:00', 10, FALSE, '2017-07-09 00:00:00')," +
                    "('11', '2017-07-09 00:00:00', 11, FALSE, '2017-07-09 00:00:00')," +
                    "('12', '2017-07-09 00:00:00', 12, FALSE, '2017-07-09 00:00:00');").executeUpdate();
        }

        int subjectCount = subjectService.getCount();

        if(subjectCount == 0) {
            entityManager.createNativeQuery("INSERT INTO subject (id, created_at, name, type, updated_at) VALUES " +
                    "('1', '2017-07-09 00:00:00', 'Science', 1, '2017-07-09 00:00:00')," +
                    "('2', '2017-07-09 00:00:00', 'Maths', 2, '2017-07-09 00:00:00')," +
                    "('3', '2017-07-09 00:00:00', 'English', 3, '2017-07-09 00:00:00');").executeUpdate();
        }

        int tagTypeCount = tagTypeService.getCount();

        if(tagTypeCount == 0) {

        }

        int userTypeCount = userTypeService.getCount();

        if(userTypeCount == 0) {
            entityManager.createNativeQuery("INSERT INTO user_type (id, created_at, name, type, updated_at) VALUES " +
                    "('1', '2017-07-09 00:00:00', 'Student', 1, '2017-07-09 00:00:00')," +
                    "('2', '2017-07-09 00:00:00', 'Teacher', 2, '2017-07-09 00:00:00')," +
                    "('3', '2017-07-09 00:00:00', 'Parent', 3, '2017-07-09 00:00:00')," +
                    "('4', '2017-07-09 00:00:00', 'Institute', 4, '2017-07-09 00:00:00')," +
                    "('5', '2017-07-09 00:00:00', 'Super Admin', 5, '2017-07-09 00:00:00');").executeUpdate();
        }

        int voteTypeCount = voteTypeService.getCount();

        if(voteTypeCount == 0) {
            entityManager.createNativeQuery("INSERT INTO vote_type (id, created_at, name, type, updated_at) VALUES " +
                    "('1', '2017-07-09 00:00:00', 'Upvote', 1, '2017-07-09 00:00:00')," +
                    "('2', '2017-07-09 00:00:00', 'Downvote', 2, '2017-07-09 00:00:00')," +
                    "('3', '2017-07-09 00:00:00', 'Like', 3, '2017-07-09 00:00:00')," +
                    "('4', '2017-07-09 00:00:00', 'Dislike', 4, '2017-07-09 00:00:00');").executeUpdate();
        }

        entityManager.createNativeQuery("SET FOREIGN_KEY_CHECKS = 1;").executeUpdate();
    }
}
