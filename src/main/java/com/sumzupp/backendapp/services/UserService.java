package com.sumzupp.backendapp.services;

import com.sumzupp.backendapp.beans.*;
import com.sumzupp.backendapp.entities.*;
import com.sumzupp.backendapp.enums.UserTypeEnum;
import com.sumzupp.backendapp.repositories.UserRepository;
import com.sumzupp.backendapp.utils.Constants;
import com.sumzupp.backendapp.utils.FileUtils;
import com.sumzupp.backendapp.utils.ZipUtils;
import org.apache.http.util.TextUtils;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static com.sumzupp.backendapp.utils.FileUtils.*;

/**
 * Created by akash.mercer on 21-Jun-17.
 */

@Service
public class UserService {
    private static final String TAG = "UserService : ";

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserTypeService userTypeService;

    @Autowired
    private StandardDivisionService standardDivisionService;

    @Autowired
    private TeacherInstituteService teacherInstituteService;

    @Autowired
    private TeacherSubjectService teacherSubjectService;

    @Autowired
    private BadgeService badgeService;

    @Autowired
    private StudentRankingService studentRankingService;

    @Autowired
    private SubjectService subjectService;

    @Autowired
    private MailService mailService;

    @Autowired
    private EntityManager entityManager;

    private static Logger debugLogger = Logger.getLogger("debugLogs");

    private static Logger errorLogger = Logger.getLogger("errorLogs");

    public User findById(String id) {
        return userRepository.findOne(id);
    }

    public void save(User user) {
        userRepository.save(user);
    }

    public List<User> findAllTeachersAndInstituteForInstituteByTeacher(User user) {
        User retrievedUser = userRepository.findOne(user.getId());
        List<User> teacherList = new ArrayList<>();
        if (retrievedUser == null) {
            errorLogger.error(TAG + "Error in finding User with username : " + user.getId());
            return null;
        }
        if(user.getUserType().getType() == 2) {
            TeacherInstitute teacherInstitute = teacherInstituteService.findByTeacher(user);
            User institute = teacherInstitute.getInstitute();
            List<TeacherInstitute> teacherInstitutes = teacherInstituteService.findByInstitute(institute);
            teacherList = teacherInstitutes.stream().map((ti) -> ti.getTeacher()).collect(Collectors.toList());
            teacherList.add(institute);
        } else if(user.getUserType().getType() == 4) {
            List<TeacherInstitute> teacherInstitutes = teacherInstituteService.findByInstitute(user);
            teacherList = teacherInstitutes.stream().map((ti) -> ti.getTeacher()).collect(Collectors.toList());
            teacherList.add(user);
        }
        return teacherList;
    }

    public StatusBean isRegisteredUsername(String username) {
        StatusBean statusBean = new StatusBean();

        User retrievedUser = userRepository.findFirstByUsername(username);

        if (retrievedUser == null) {
            statusBean.setStatus(1);
        } else {
            errorLogger.error(TAG + "User with username : " + username + " already exists.");

            statusBean.setStatus(0);
            statusBean.setMessage(Constants.USERNAME_ALREADY_EXISTS);
        }

        return statusBean;
    }

    @Transactional(rollbackFor = {Exception.class})
    public LoginResultBean signUp(SignUpBean signUpBean) throws Exception {
        LoginResultBean loginResultBean = new LoginResultBean();

        User user = new User(signUpBean);

        UserType retrievedUserType = userTypeService.findByType(signUpBean.getUserType());

        if (retrievedUserType == null) {
            errorLogger.error(TAG + "Error in finding UserType with type : " + signUpBean.getUserType());

            loginResultBean.setStatus(0);
            loginResultBean.setMessage(Constants.SOMETHING_WENT_WRONG);

            return loginResultBean;
        }

        user.setUserType(retrievedUserType);

        user.setAdmin(false);

        try {
            user = userRepository.save(user);

            loginResultBean.setStatus(1);
        } catch (Exception e) {
            errorLogger.error(TAG + "Error in saving User with error : " + e.getMessage());

            loginResultBean.setStatus(0);
            loginResultBean.setMessage(Constants.SOMETHING_WENT_WRONG);

            throw e;
        }

        loginResultBean.populateData(user, null);

        if (signUpBean.getUserType() == UserTypeEnum.STUDENT.getType()) {
            if (!StringUtils.isEmpty(signUpBean.getStandardDivisionId())) {
                StandardDivision retrievedStandardDivision = standardDivisionService.findById(signUpBean.getStandardDivisionId());

                if (retrievedStandardDivision == null) {
                    errorLogger.error(TAG + "Error in finding Standard with id : " + signUpBean.getStandardDivisionId());

                    loginResultBean.setStatus(0);
                    loginResultBean.setMessage(Constants.SOMETHING_WENT_WRONG);

                    throw new Exception(Constants.SOMETHING_WENT_WRONG);
                }

                user.setStandardDivision(retrievedStandardDivision);

                try {
                    entityManager.merge(user);

                    loginResultBean.setStatus(1);
                } catch (Exception e) {
                    errorLogger.error(TAG + "Error in saving StudentStandard with error : " + e.getMessage());

                    loginResultBean.setStatus(0);
                    loginResultBean.setMessage(Constants.SOMETHING_WENT_WRONG);

                    throw e;
                }
            }

            StudentRanking studentRanking = new StudentRanking();
            studentRanking.setUser(user);
            studentRanking.setBadge(badgeService.findByType(1));

            try {
                studentRankingService.save(studentRanking);
            } catch (Exception e) {
                errorLogger.error(TAG + "Error in saving StudentRanking with error : " + e.getMessage());

                loginResultBean.setStatus(0);
                loginResultBean.setMessage(Constants.SOMETHING_WENT_WRONG);

                throw e;
            }
        } else if (signUpBean.getUserType() == UserTypeEnum.TEACHER.getType()) {
            if (!StringUtils.isEmpty(signUpBean.getInstituteId())) {
                TeacherInstitute teacherInstitute = new TeacherInstitute();
                teacherInstitute.setTeacher(user);

                User retrievedInstitute = userRepository.findOne(signUpBean.getInstituteId());

                if (retrievedInstitute == null) {
                    errorLogger.error(TAG + "Error in finding Institute with id : " + signUpBean.getInstituteId());

                    loginResultBean.setStatus(0);
                    loginResultBean.setMessage(Constants.SOMETHING_WENT_WRONG);

                    throw new Exception(Constants.SOMETHING_WENT_WRONG);
                }

                teacherInstitute.setInstitute(retrievedInstitute);

                try {
                    teacherInstituteService.save(teacherInstitute);

                    loginResultBean.setStatus(1);
                } catch (Exception e) {
                    errorLogger.error(TAG + "Error in saving TeacherInstitute with error : " + e.getMessage());

                    loginResultBean.setStatus(0);
                    loginResultBean.setMessage(Constants.SOMETHING_WENT_WRONG);

                    throw e;
                }

                if (!CollectionUtils.isEmpty(signUpBean.getTeacherSubjectBeans())) {
                    teacherSubjectService.saveTeacherSubjects(user, signUpBean.getTeacherSubjectBeans());

                    loginResultBean.setStatus(1);
                } else {
                    errorLogger.error(TAG + "Missing teacherSubjectBeans for Teacher with name : " + signUpBean.getName());

                    loginResultBean.setStatus(0);
                    loginResultBean.setMessage(Constants.MISSING_TEACHER_SUBJECT_BEANS );

                    throw new Exception(Constants.MISSING_TEACHER_SUBJECT_BEANS);
                }
            } else {
                errorLogger.error(TAG + "Missing instituteId for Teacher with name : " + signUpBean.getName());

                loginResultBean.setStatus(0);
                loginResultBean.setMessage(Constants.MISSING_INSTITUTE_ID);

                throw new Exception(Constants.MISSING_INSTITUTE_ID);
            }
        } else if (signUpBean.getUserType() == UserTypeEnum.INSTITUTE.getType()) {
            user.setStatus(2);
            userRepository.save(user);

            if (!CollectionUtils.isEmpty(signUpBean.getStandardBeans())) {
                try {
                    standardDivisionService.saveStandardDivisions(user, signUpBean.getStandardBeans());
                } catch (Exception e) {
                    errorLogger.error(TAG + "Error in saving Standards for Institute with id : " + user.getId() + " with error : " + e.getMessage());

                    loginResultBean.setStatus(0);
                    loginResultBean.setMessage(Constants.SOMETHING_WENT_WRONG);

                    throw e;
                }
            } else {
                errorLogger.error(TAG + "Missing standardBeans for Institute with name : " + signUpBean.getName());

                loginResultBean.setStatus(0);
                loginResultBean.setMessage(Constants.MISSING_STANDARDS);

                throw new Exception(Constants.MISSING_STANDARDS);
            }
        }

        return loginResultBean;
    }

    public LoginResultBean webTeacherLogin(LoginBean loginBean) {
        LoginResultBean loginResultBean = new LoginResultBean();

        // FIXME: 14-Jul-17 Remove below Jugaad and fix the code
        List<UserType> teacherOrInstituteOrAdminUserTypes = userTypeService.findTeacherOrInstituteOrAdminTypes();

        User retrievedUser = userRepository.findByEmailOrUsernameAndPasswordAndUserType(loginBean.getEmail(), loginBean.getPassword(), teacherOrInstituteOrAdminUserTypes);

        if (retrievedUser == null) {
            errorLogger.error(TAG + "Error in finding Teacher with email : " + loginBean.getEmail());
            loginResultBean.setStatus(0);
            loginResultBean.setMessage(Constants.USER_NOT_REGISTERED);

            return loginResultBean;
        }

        TeacherInstitute retrievedTeacherInstitute = teacherInstituteService.findByTeacher(retrievedUser);

        loginResultBean.setStatus(1);

        loginResultBean.populateData(retrievedUser, retrievedTeacherInstitute);

        return loginResultBean;
    }

    @Transactional
    public LoginResultBean login(LoginBean loginBean) {
        LoginResultBean loginResultBean = new LoginResultBean();

        User retrievedUser = userRepository.findFirstByUsername(loginBean.getUsername());

        if (retrievedUser == null) {
            errorLogger.error(TAG + "Error in finding User with username : " + loginBean.getUsername());

            loginResultBean.setStatus(0);
            loginResultBean.setMessage(Constants.USER_NOT_REGISTERED);

            return loginResultBean;
        }

        retrievedUser = userRepository.findFirstByUsernameAndPassword(loginBean.getUsername(), loginBean.getPassword());

        if (retrievedUser == null) {
            errorLogger.error(TAG + "Error in finding User with username : " + loginBean.getUsername());

            loginResultBean.setStatus(0);
            loginResultBean.setMessage(Constants.WRONG_CREDENTIALS);

            return loginResultBean;
        }

        retrievedUser.setAccountType(loginBean.getAccountType());
        retrievedUser.setDeviceType(loginBean.getDeviceType());
        retrievedUser.setAppVersion(loginBean.getAppVersion());
        retrievedUser.setFcmToken(loginBean.getFcmToken());

        User updatedUser;

        try {
            updatedUser = entityManager.merge(retrievedUser);

            loginResultBean.setStatus(1);
        } catch (Exception e) {
            errorLogger.error(TAG + "Error in updating User with id : " + loginBean.getContactNumber() + " with error : " + e.getMessage());

            loginResultBean.setStatus(0);
            loginResultBean.setMessage(Constants.SOMETHING_WENT_WRONG);

            return loginResultBean;
        }

        if (UserTypeEnum.STUDENT.getType() == retrievedUser.getUserType().getType()) {
            loginResultBean.populateData(updatedUser, null);
        } else {
            TeacherInstitute retrievedTeacherInstitute = teacherInstituteService.findByTeacher(retrievedUser);

            loginResultBean.populateData(updatedUser, retrievedTeacherInstitute);
        }

        return loginResultBean;
    }

    public StatusBean updateStandardDivision(String userId, UpdateStandardDivisionBean updateStandardDivisionBean) {
        StatusBean statusBean = new StatusBean();

        User retrievedUser = userRepository.findOne(userId);

        if (retrievedUser == null) {
            errorLogger.error(TAG + "Error in finding User with id : " + userId);

            statusBean.setStatus(0);
            statusBean.setMessage(Constants.SOMETHING_WENT_WRONG);

            return statusBean;
        }

        StandardDivision retrievedStandardDivision = standardDivisionService.findByStandardAndBoardAndInstitute(updateStandardDivisionBean.getStandardId(), updateStandardDivisionBean.getBoardId());

        if (retrievedStandardDivision == null) {
            errorLogger.error(TAG + "Error in finding StandardDivision with standardId : " + updateStandardDivisionBean.getStandardId() + " and boardId : " + updateStandardDivisionBean.getBoardId() + " combination.");

            statusBean.setStatus(0);
            statusBean.setMessage(Constants.SOMETHING_WENT_WRONG);

            return statusBean;
        }

        try {
            retrievedUser.setStatus(2);
            retrievedUser.setStandardDivision(retrievedStandardDivision);

            userRepository.save(retrievedUser);

            statusBean.setStatus(1);
        } catch (Exception e) {
            errorLogger.error(TAG + "Error in updating StandardDivision of User with id : " + userId + " with error : " + e.getMessage());

            statusBean.setStatus(0);
            statusBean.setMessage(Constants.SOMETHING_WENT_WRONG);

            return statusBean;
        }

        return statusBean;
    }

    @Transactional
    public StatusBean resetCredentials(CredentialBean credentialBean) {
        StatusBean statusBean = new StatusBean();

        User retrievedUser = userRepository.findOne(credentialBean.getUserId());

        if (retrievedUser == null) {
            errorLogger.error(TAG + "Error in finding User with id : " + credentialBean.getUserId());

            statusBean.setStatus(0);
            statusBean.setMessage(Constants.SOMETHING_WENT_WRONG);

            return statusBean;
        }

        retrievedUser.setUsername(credentialBean.getUsername());
        retrievedUser.setPassword(credentialBean.getPassword());
        retrievedUser.setStatus(2);

        try {
            userRepository.save(retrievedUser);

            statusBean.setStatus(1);
        } catch (Exception e) {
            errorLogger.error(TAG + "Error in updating credentials of User with id : " + credentialBean.getUserId() + " with error : " + e.getMessage());

            statusBean.setStatus(0);
            statusBean.setMessage(Constants.SOMETHING_WENT_WRONG);

            return statusBean;
        }

        return statusBean;
    }

    public UsersBean getUsers(String userTypes, String instituteId) {
        UsersBean usersBean = new UsersBean();

        List<Integer> userTypeList = new ArrayList<>();

        User retrievedUser;

        if (!TextUtils.isEmpty(instituteId)) {
            retrievedUser = userRepository.findOne(instituteId);

            if (retrievedUser == null) {
                errorLogger.error(TAG + "Error in finding Institute with id : " + instituteId);

                usersBean.setStatus(0);
                usersBean.setMessage(Constants.SOMETHING_WENT_WRONG);

                return usersBean;
            }
        } else {
            retrievedUser = null;
        }

        if (!TextUtils.isEmpty(userTypes)) {
            String[] userTypeArray = userTypes.split("_");

            for (String useTypeString : userTypeArray) {
                userTypeList.add(Integer.parseInt(useTypeString));
            }
        } else {
            errorLogger.error(TAG + "Missing userTypes for getUsers()");

            usersBean.setStatus(0);
            usersBean.setMessage(Constants.MISSING_USER_TYPES);

            return usersBean;
        }

        if ((userTypeList.contains(UserTypeEnum.STUDENT.getType()) || userTypeList.contains(UserTypeEnum.TEACHER.getType())) && TextUtils.isEmpty(instituteId)) {
            errorLogger.error(TAG + "Missing instituteId for getUsers() for Teacher and Student types.");

            usersBean.setStatus(0);
            usersBean.setMessage(Constants.MISSING_INSTITUTE_ID);

            return usersBean;
        }

        if (!TextUtils.isEmpty(instituteId)) {
            usersBean.getUserBeans().addAll(userRepository.getStudentsByInstitute(instituteId));
            usersBean.getUserBeans().addAll(teacherInstituteService.findTeacherByInstitute(retrievedUser));
        } else {
            usersBean.setUserBeans(userRepository.fetchInstitutes());
        }

        usersBean.setStatus(1);
        return usersBean;
    }

    @Transactional
    public StatusBean updateNotificationToken(NotificationTokenBean notificationTokenBean) {
        User retrievedUser = userRepository.findOne(notificationTokenBean.getUserId());

        StatusBean statusBean = new StatusBean();

        if(retrievedUser == null) {
            errorLogger.error(TAG + "Tried to update notification token of non existing User : " + notificationTokenBean.getUserId());

            statusBean.setStatus(0);
            statusBean.setMessage(Constants.SOMETHING_WENT_WRONG);
            return statusBean;
        }

        retrievedUser.setFcmToken(notificationTokenBean.getFcmToken());

        try {
            if (Constants.DEVICE_TYPE_ANDROID.equals(retrievedUser.getDeviceType())){
                entityManager.merge(retrievedUser);
            }

            statusBean.setStatus(1);
            return statusBean;
        } catch (Exception e) {
            errorLogger.error(TAG + "FCM Android Token update failed for User : " + retrievedUser.getId());

            statusBean.setStatus(0);
            statusBean.setMessage(Constants.SOMETHING_WENT_WRONG);
            return statusBean;
        }
    }

    public List<String> fetchFcmTokensByStandardDivisions(List<StandardDivision> standardDivisions) {
        return userRepository.fetchFcmTokensByStandardDivisions(standardDivisions);
    }

    public StatusBean createHtmlWelcomeFile(List<User> users, String instituteId, Set<String> standardDivisionSet) throws Exception {
        String osName = System.getProperty("os.name");

        List<String> fileNames = new ArrayList<>();

        File instituteRootDirectory = new File(osName.startsWith("Windows") ? Constants.WINDOWS_STUDENT_WELCOME_PATH : Constants.UBUNTU_STUDENT_WELCOME_PATH + File.separator + instituteId);

        if (!instituteRootDirectory.exists()) {
            instituteRootDirectory.mkdirs();
        }

        for (String standardDivision : standardDivisionSet) {
            File standardDivisionDirectory = new File(instituteRootDirectory.getAbsolutePath() + File.separator + standardDivision);

            if (!standardDivisionDirectory.exists()) {
                standardDivisionDirectory.mkdirs();
            }
        }

        for (User user: users) {
            String htmlContent = "<html>\n" +
                "\t<body>\n" +
                "\t\t<h2 style=\"text-align:center;margin-top:1rem;\">Dear STUDENT_NAME</h2>\n" +
                "\t\t<div style=\"text-align:justify;\">\n" +
                "\t\t\tGreetings from <b>Sumzupp</b>!\n" +
                "\t\t\t<br/><br/>\n" +
                "\t\t\t<b>Sumzupp</b> is an academic solution for testing a student's aptitude using technology. It is a unique K12 education system which aids schools, teachers, and parents in understanding a child's performance and analyzing data in real time. Sumzupp has a set of thoroughly curated concept based questions built by a team of experts. By attempting the tests on Sumzupp, every child gets a holistic practice session across different concepts at their own pace of understanding. Also, the assignments are assessed online without adding to the daily functionality of the school.\n" +
                "\t\t\t<br/><br/>\n" +
                "\t\t\tWith a mobile application for students and teachers, <b>Sumzupp makes learning extremely easy and interactive.</b>\n" +
                "\t\t\t<br/><br/>\n" +
                "\t\t\tYour generated credentials are:\n" +
                "\t\t\t<br/>\n" +
                "\t\t\t<b>Username</b> : STUDENT_USERNAME\n" +
                "\t\t\t<br/>\n" +
                "\t\t\t<b>Password</b> : STUDENT_PASSWORD\n" +
                "\t\t\t<br/><br/>\n" +
                "\t\t\tYou can change the username and password through our mobile app.\n" +
                "\t\t\t<br/><br/>\n" +
                "\t\t\tFollow the steps and start using Sumzupp:\n" +
                "\t\t\t<ol>\n" +
                "\t\t\t\t<li>Download the \"SUMZUPP\" application on your android phone from play store.</li>\n" +
                "\t\t\t\t<li>Enter your username and password.</li>\n" +
                "\t\t\t\t<li>It will prompt you to change your username and password. Do remember your new username and password that you have entered.</li>\n" +
                "\t\t\t\t<li>Login and start exploring the application.</li>\n" +
                "\t\t\t</ol>\n" +
                "\t\t\tThank you,\n" +
                "\t\t\t<br/>\n" +
                "\t\t\tTeam Sumzupp\n" +
                "\t\t</div>\n" +
                "\t</body>\n" +
                "</html>";

            htmlContent = htmlContent.replaceAll("STUDENT_NAME", user.getName());
            htmlContent = htmlContent.replaceAll("STUDENT_USERNAME", user.getUsername());
            htmlContent = htmlContent.replaceAll("STUDENT_PASSWORD", user.getPassword());

            String fileName = user.getRollNumber();

            File serverFile = new File(instituteRootDirectory.getAbsolutePath() + File.separator + user.getStandardDivision().getStandard().getStandardName() + "-" + user.getStandardDivision().getDivisionName() + File.separator + fileName + ".html");

            BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));

            byte[] bytes = htmlContent.getBytes();

            stream.write(bytes);
            stream.close();

            fileNames.add(serverFile.getAbsolutePath());
        }

        return createPdfWelcomeFileAndDeleteHtmlFile(fileNames, instituteRootDirectory.getAbsolutePath());
    }

    public StatusBean createPdfWelcomeFileAndDeleteHtmlFile(List<String> fileNames, String rootPath) throws Exception {
        StatusBean statusBean = new StatusBean(0, Constants.SOMETHING_WENT_WRONG);

        String osName = System.getProperty("os.name");

        int counter = 0;

        for(String fileName : fileNames) {
            String fileNameWithoutExtension = fileName.substring(0, fileName.length() - 5);

            File pdfFile = new File(fileNameWithoutExtension + ".pdf");

            String urlPath = fileNameWithoutExtension + ".html";

            String wxpath = osName.startsWith("Windows") ? Constants.WINDOWS_WKHTML_PATH : Constants.UBUNTU_WKHTML_PATH;

            String command = wxpath + " " + urlPath + " " + pdfFile.getPath();

            Process process = Runtime.getRuntime().exec(command);

            if (process.waitFor() == 0) {
                File htmlFile = new File(urlPath);

                if (htmlFile.delete()) {
                    counter++;
                }
            }
        }

        if(counter == fileNames.size()) {
            try {
                createZipFileFromPdfs(rootPath);

                statusBean.setStatus(1);
            } catch (Exception e) {
                throw e;
            }
        } else {
            throw new Exception("Could not generate welcome PDF's or delete existing files for all students");
        }

        return statusBean;
    }

    public void createZipFileFromPdfs(String rootPath) throws IOException {
        try {
            ZipUtils.makeZipFile(rootPath);
        } catch (Exception e) {
            e.printStackTrace();

            throw e;
        }

        String[] toMail = new String[] {"khanvilkarninad1191@gmail.com ", "mihir0792@gmail.com", "akash.mercer@gmail.com", "akshayprakashkadam@gmail.com"};

        mailService.sendFileToMultiplePeople(toMail, rootPath + ".zip");
    }

    public Integer getStudentCountByStandardDivisions(List<StandardDivision> standardDivisions) {
        return userRepository.getStudentCountByStandardDivisions(standardDivisions);
    }

    public Integer getStudentCountByInstitute(User institute) {
        return userRepository.getStudentCountByInstitute(institute);
    }

    public InstitutesNamesBean fetchInstituteNames() {
        InstitutesNamesBean institutesNamesBean = new InstitutesNamesBean();

        institutesNamesBean.setInstituteNames(userRepository.fetchInstituteNames());

        institutesNamesBean.setStatus(1);
        return institutesNamesBean;
    }

    public StatusBean selectInstitute(StudentInstituteBean studentInstituteBean) {
        StatusBean statusBean = new StatusBean();

        User retrievedStudent = userRepository.findOne(studentInstituteBean.getUserId());

        if (retrievedStudent == null) {
            errorLogger.error(TAG + "Error in finding User with id : " + studentInstituteBean.getUserId());

            statusBean.setStatus(0);
            statusBean.setMessage(Constants.SOMETHING_WENT_WRONG);
            return statusBean;
        }

        String content = "<html>" +
                "<h3>Studnet Id : " + retrievedStudent.getId() + "</h3>" +
                "<h3>Studnet Name : " + retrievedStudent.getName() + "</h3>" +
                "<h3>Roll Number : " + studentInstituteBean.getRollNumber() + "</h3>" +
                "<h3>Standard division : " + studentInstituteBean.getStandardDivision() + "</h3>" +
                "<h3>Institute Name : " + studentInstituteBean.getInstituteName() + "</h3>" +
                "</html>";

        try {
            mailService.sendTextMail(Constants.FROM_EMAIL, "Institute Changed", content);
        } catch (Exception e) {
            errorLogger.error(TAG + "Error in sending select Institute mail for User with id : " + studentInstituteBean.getUserId());
            statusBean.setStatus(0);
            statusBean.setMessage(Constants.SOMETHING_WENT_WRONG);
            return statusBean;
        }

        statusBean.setStatus(1);
        return statusBean;
    }

    @Transactional(rollbackFor = {Exception.class})
    public StatusBean readStudentsDataSheet(String fileName) throws Exception {
        String osName = System.getProperty("os.name");

        String path = osName.startsWith("Windows") ? Constants.WINDOWS_STUDENT_DATA_FILE_PATH : Constants.UBUNTU_STUDENT_DATA_FILE_PATH;

        StatusBean statusBean = new StatusBean();

        String[] fileNameParts = fileName.split("_");

        User retrievedInstitute = userRepository.findOne(fileNameParts[0]);

        if (retrievedInstitute == null) {
            errorLogger.error(TAG + "Error in finding Institute with id : " + fileNameParts[0] + " in file : " + fileName);
            statusBean.setStatus(0);
            statusBean.setMessage("Error in finding Institute with id : " + fileNameParts[0] + " in file : " + fileName);
            return statusBean;
        }

        File file = new File(path + File.separator + fileName);

        File newFile = new File(path + File.separator + "new_" + fileName);

        XSSFWorkbook xssfWorkbook;

        try {
            xssfWorkbook = FileUtils.getWorkbookFromFile(file);
        } catch (Exception e) {
            errorLogger.error(TAG + "Error in opening Workbook file : " + file.getAbsolutePath());
            statusBean.setStatus(0);
            statusBean.setMessage("Error in opening Workbook file : " + file.getAbsolutePath());
            return statusBean;
        }

        XSSFSheet xssfSheet = FileUtils.getSheetFromWorkBook(xssfWorkbook);

        if (xssfSheet == null) {
            errorLogger.error(TAG + "No Sheet found in Workbook file : " + file.getAbsolutePath());
            statusBean.setStatus(0);
            statusBean.setMessage("No Sheet found in Workbook file : " + file.getAbsolutePath());
            return statusBean;
        }

        int lastRowIndex = xssfSheet.getLastRowNum();

        List<User> users = new ArrayList<>();

        List<StandardDivision> standardDivisions = standardDivisionService.findByInstitute(retrievedInstitute.getId());

        Map<String, StandardDivision> standardDivisionMap = new HashMap<>();

        for (StandardDivision standardDivision : standardDivisions) {
            standardDivisionMap.put(standardDivision.getStandard().getStandardName() + "-" + standardDivision.getDivisionName(), standardDivision);
        }

        for (int i = 1; i <= lastRowIndex; i++) {
            XSSFRow xssfRow = xssfSheet.getRow(i);
            User user = new User();

            if (xssfRow.getCell(1) == null) {
                break;
            }

            if (xssfRow.getCell(0) != null) {
                xssfRow.getCell(0).setCellType(CellType.STRING);

                user.setRollNumber(xssfRow.getCell(0).getStringCellValue());
            }

            user.setName(xssfRow.getCell(1).getStringCellValue());
            user.setAccountType(Constants.ACCOUNT_TYPE_EMAIL);
            user.setDeviceType(Constants.DEVICE_TYPE_ANDROID);
            user.setAppVersion("1.0");
            user.setUserType(userTypeService.findByType(1));
            user.setStandardDivision(standardDivisionMap.get(xssfRow.getCell(4).getStringCellValue()));
            user.setStatus(1);
            users.add(user);
        }

        try {
            userRepository.save(users);

            try {
                createHtmlWelcomeFile(users, retrievedInstitute.getId(), standardDivisionMap.keySet());
            } catch (Exception e) {
                errorLogger.error(TAG + "Error in creating welcomeHtmlFiles with error : " + e.getMessage());
                statusBean.setStatus(0);
                statusBean.setMessage("Error in creating welcomeHtmlFiles with error : " + e.getMessage());
                throw e;
            }
        } catch (Exception e) {
            errorLogger.error(TAG + "Error in saving Users with error : " + e.getMessage());
            statusBean.setStatus(0);
            statusBean.setMessage(TAG + "Error in saving Users with error : " + e.getMessage());
            throw e;
        }

        try {
            writeToStudentsDataSheet(xssfSheet, users);
        } catch (Exception e) {
            errorLogger.error(TAG + "Error in writing content to worksheet : " + file.getAbsolutePath());
            statusBean.setStatus(0);
            statusBean.setMessage("Error in writing content to worksheet : " + file.getAbsolutePath());
        }

        try {
            writeToWorkbook(file, newFile, xssfWorkbook);
        } catch (Exception e) {
            errorLogger.error(TAG + "Error in writing content to workbook : " + file.getAbsolutePath());
            statusBean.setStatus(0);
            statusBean.setMessage("Error in writing content to workbook : " + file.getAbsolutePath());
        }

        try {
            if (xssfWorkbook != null) {
                xssfWorkbook.close();
            }
        } catch (IOException e) {
            errorLogger.error(TAG + "Error in closing workbook file : " + file.getAbsolutePath());
        }

        statusBean.setStatus(1);
        return statusBean;
    }

    private void writeToStudentsDataSheet(XSSFSheet xssfSheet, List<User> users) throws Exception {
        initializeStudentDataSheetHeader(xssfSheet);

        for (int i = 0; i < users.size(); i++) {
            XSSFRow xssfRow = xssfSheet.getRow(i + 1);

            XSSFCell usernameXssfCell = xssfRow.createCell(5);
            usernameXssfCell.setCellValue(users.get(i).getUsername());

            XSSFCell passwordXssfCell = xssfRow.createCell(6);
            passwordXssfCell.setCellValue(users.get(i).getPassword());
        }
    }

    @Transactional(rollbackFor = {Exception.class})
    public StatusBean readTeachersDataSheet(String fileName) throws Exception {
        String osName = System.getProperty("os.name");
        String path = osName.startsWith("Windows") ? Constants.WINDOWS_TEACHER_DATA_FILE_PATH : Constants.UBUNTU_TEACHER_DATA_FILE_PATH;
        StatusBean statusBean = new StatusBean();

        String[] fileNameParts = fileName.split("_");

        User retrievedInstitute = userRepository.findOne(fileNameParts[0]);

        if (retrievedInstitute == null) {
            errorLogger.error(TAG + "Error in finding Institute with id : " + fileNameParts[0] + " in file : " + fileName);
            statusBean.setStatus(0);
            statusBean.setMessage(Constants.SOMETHING_WENT_WRONG);
            return statusBean;
        }

        File file = new File(path + File.separator + fileName);
        File newFile = new File(path + File.separator + "new_" + fileName);
        XSSFWorkbook xssfWorkbook;

        try {
            xssfWorkbook = FileUtils.getWorkbookFromFile(file);
        } catch (Exception e) {
            errorLogger.error(TAG + "Error in opening Workbook file : " + file.getAbsolutePath());
            statusBean.setStatus(0);
            statusBean.setMessage(Constants.SOMETHING_WENT_WRONG);
            return statusBean;
        }

        XSSFSheet xssfSheet = FileUtils.getSheetFromWorkBook(xssfWorkbook);

        if (xssfSheet == null) {
            errorLogger.error(TAG + "No Sheet found in Workbook file : " + file.getAbsolutePath());
            statusBean.setStatus(0);
            statusBean.setMessage(Constants.SOMETHING_WENT_WRONG);
            return statusBean;
        }

        int lastRowIndex = xssfSheet.getLastRowNum();

        List<User> users = new ArrayList<>();

        List<TeacherInstitute> teacherInstitutes = new ArrayList<>();

        List<TeacherSubject> teacherSubjects = new ArrayList<>();

        List<StandardDivision> standardDivisions = standardDivisionService.findByInstitute(retrievedInstitute.getId());

        List<Subject> subjects = subjectService.fetchAll();

        Map<String, StandardDivision> standardDivisionMap = new HashMap<>();

        for (StandardDivision standardDivision : standardDivisions) {
            standardDivisionMap.put(standardDivision.getStandard().getStandardName() + "-" + standardDivision.getDivisionName() + "-" + standardDivision.getInstitute().getId(), standardDivision);
        }

        Map<String, Subject> subjectMap = new HashMap<>();

        for (Subject subject : subjects) {
            subjectMap.put(subject.getName(), subject);
        }

        for (int i = 1; i <= lastRowIndex; i++) {
            XSSFRow xssfRow = xssfSheet.getRow(i);
            User user = new User();

            if (xssfRow.getCell(1) == null) {
                break;
            }

            user.setName(xssfRow.getCell(1).getStringCellValue());
            user.setAccountType(Constants.ACCOUNT_TYPE_EMAIL);
            user.setDeviceType(Constants.DEVICE_TYPE_ANDROID);
            user.setAppVersion("1.0");
            user.setUserType(userTypeService.findByType(2));
            user.setStatus(1);
            users.add(user);

            TeacherInstitute teacherInstitute = new TeacherInstitute();
            teacherInstitute.setTeacher(user);
            teacherInstitute.setInstitute(retrievedInstitute);

            teacherInstitutes.add(teacherInstitute);

            teacherSubjects.addAll(buildTeacherSubjects(user, xssfRow.getCell(2).getStringCellValue(), subjectMap, standardDivisionMap, retrievedInstitute.getId()));
        }

        try {
            userRepository.save(users);

            try {
                teacherInstituteService.saveAll(teacherInstitutes);
            } catch (Exception e) {
                errorLogger.error(TAG + "Error in saving TeacherInstitutes with error : " + e.getMessage());
                statusBean.setStatus(0);
                statusBean.setMessage(Constants.SOMETHING_WENT_WRONG);
                throw e;
            }

            try {
                teacherSubjectService.saveAll(teacherSubjects);
            } catch (Exception e) {
                errorLogger.error(TAG + "Error in saving TeacherSubjects with error : " + e.getMessage());
                statusBean.setStatus(0);
                statusBean.setMessage(Constants.SOMETHING_WENT_WRONG);
                throw e;
            }
        } catch (Exception e) {
            errorLogger.error(TAG + "Error in saving Teachers with error : " + e.getMessage());
            statusBean.setStatus(0);
            statusBean.setMessage(Constants.SOMETHING_WENT_WRONG);
            throw e;
        }

        try {
            writeToTeachersDataSheet(xssfSheet, users);
        } catch (Exception e) {
            errorLogger.error(TAG + "Error in writing content to worksheet : " + file.getAbsolutePath());
            statusBean.setStatus(0);
            statusBean.setMessage(Constants.SOMETHING_WENT_WRONG);
        }

        try {
            writeToWorkbook(file, newFile, xssfWorkbook);
        } catch (Exception e) {
            errorLogger.error(TAG + "Error in writing content to workbook : " + file.getAbsolutePath());
            statusBean.setStatus(0);
            statusBean.setMessage(Constants.SOMETHING_WENT_WRONG);
        }

        try {
            if (xssfWorkbook != null) {
                xssfWorkbook.close();
            }
        } catch (IOException e) {
            errorLogger.error(TAG + "Error in closing workbook file : " + file.getAbsolutePath());
        }

        statusBean.setStatus(1);
        return statusBean;
    }

    private List<TeacherSubject> buildTeacherSubjects(User teacher, String teacherSubjectsString, Map<String, Subject> subjectMap, Map<String, StandardDivision> standardDivisionMap, String instituteId) {
        List<TeacherSubject> teacherSubjects = new ArrayList<>();

        if (teacherSubjectsString.contains(",")) {
            String[] teacherSubjectArray = teacherSubjectsString.split(",");

            for (String teacherSubjectString : teacherSubjectArray) {
                TeacherSubject teacherSubject = buildTeacherSubject(teacher, teacherSubjectString, subjectMap, standardDivisionMap, instituteId);
                teacherSubjects.add(teacherSubject);
            }
        } else {
            TeacherSubject teacherSubject = buildTeacherSubject(teacher, teacherSubjectsString, subjectMap, standardDivisionMap, instituteId);
            teacherSubjects.add(teacherSubject);
        }

        return teacherSubjects;
    }

    private TeacherSubject buildTeacherSubject(User teacher, String teacherSubjectString, Map<String, Subject> subjectMap, Map<String, StandardDivision> standardDivisionMap, String instituteId) {
        TeacherSubject teacherSubject = new TeacherSubject();
        teacherSubject.setTeacher(teacher);

        int subjectSeparatorIndex = teacherSubjectString.lastIndexOf("-");
        teacherSubject.setSubject(subjectMap.get(teacherSubjectString.substring(subjectSeparatorIndex + 1)));
        teacherSubject.setStandardDivision(standardDivisionMap.get(teacherSubjectString.substring(0, subjectSeparatorIndex) + "-" + instituteId));

        return teacherSubject;
    }

    private void writeToTeachersDataSheet(XSSFSheet xssfSheet, List<User> users) throws Exception {
        initializeTeacherDataSheetHeader(xssfSheet);

        for (int i = 0; i < users.size(); i++) {
            XSSFRow xssfRow = xssfSheet.getRow(i + 1);

            XSSFCell usernameXssfCell = xssfRow.createCell(3);
            usernameXssfCell.setCellValue(users.get(i).getUsername());

            XSSFCell passwordXssfCell = xssfRow.createCell(4);
            passwordXssfCell.setCellValue(users.get(i).getPassword());
        }
    }

    public List<StudentAssignmentMarksMasterBean> findByInstituteAndStandard(User institute, Standard standard) {
        return userRepository.findByInstituteAndStandard(institute, standard);
    }
}
