package com.sumzupp.backendapp.utils;

/**
 * Created by akash.mercer on 21-Jun-17.
 */
public class Constants {

    public static final String MIX_PANEL_API_TOKEN = "bb442cafc3a156fce5ba084bcd1872da";

    static final String FCM_KEY = "AIzaSyBGc9SHQ8tzwqv2XPHAT4wcDjjWylAxOUo";

    //AWS S3 credentials
    public static final String AWS_ACCESS_KEY_ID = "AKIAIWVA3NZQBYTW743A";
    public static final String AWS_SECRET_ACCESS_KEY = "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";

    //Response URL's
    public static final String LOCALHOST_URL = "http://localhost:8000/#/";
    public static final String DEV_URL = "http://dev.sumzupp.com/admin/#/";
    public static final String PROD_URL = "http://prod.sumzupp.com/admin/#/";
    public static final String LOCALHOST_BASE_URL = "http://localhost/";
    public static final String DEV_BASE_URL = "http://dev.sumzupp.com/";
    public static final String PROD_BASE_URL = "http://prod.sumzupp.com/";

    public static final String AWS_QUESTION_BANK_IMAGES_BUCKET_URL= "https://s3.ap-south-1.amazonaws.com/question-bank-images/";
    public static final String AWS_QUESTION_PAPERS_BUCKET_URL= "https://s3.ap-south-1.amazonaws.com/question-papers/";

    //File Path directories
    public static final String DATABASE_DUMP_PATH = "/home/ubuntu/dump/";
    public static final String UBUNTU_WKHTML_PATH = "/home/ubuntu/wkhtmltox/bin/wkhtmltopdf";
    public static final String WINDOWS_WKHTML_PATH = "\\Work\\SumzuppFiles\\wkhtmltopdf\\bin\\wkhtmltopdf";
    public static final String UBUNTU_STUDENT_DATA_FILE_PATH = "/home/ubuntu/student_data/";
    public static final String WINDOWS_STUDENT_DATA_FILE_PATH = "\\Work\\SumzuppFiles\\student_data\\";
    public static final String UBUNTU_TEACHER_DATA_FILE_PATH = "/home/ubuntu/teacher_data/";
    public static final String WINDOWS_TEACHER_DATA_FILE_PATH = "\\Work\\SumzuppFiles\\teacher_data\\";
    public static final String UBUNTU_QUESTION_SHEET_FILE_PATH = "/home/ubuntu/question_sheets/";
    public static final String WINDOWS_QUESTION_SHEET_FILE_PATH = "\\Work\\SumzuppFiles\\question_sheets\\";
    public static final String UBUNTU_QUESTION_PAPER_FILE_PATH = "/home/ubuntu/question_papers/";
    public static final String WINDOWS_QUESTION_PAPER_FILE_PATH = "\\Work\\SumzuppFiles\\question_papers\\";
    public static final String UBUNTU_STUDENT_WELCOME_PATH = "/home/ubuntu/students/welcome/";
    public static final String WINDOWS_STUDENT_WELCOME_PATH = "\\Work\\SumzuppFiles\\students\\welcome\\";
    public static final String UBUNTU_PDF_FILE_PATH = "/var/www/html/pdf/assignment/";
    public static final String WINDOWS_PDF_FILE_PATH = "C:\\wamp64\\www\\pdf\\assignment\\";
    public static final String UBUNTU_ASSIGNMENT_IMAGE_FILE_PATH = "/home/ubuntu/assignment/image/";
    public static final String WINDOWS_ASSIGNMENT_IMAGE_FILE_PATH = "\\Work\\SumzuppFiles\\assignment\\image\\";
    public static final String UBUNTU_ASSIGNMENT_TEMPLATE_PATH = "/home/ubuntu/templates/assignment/";
    public static final String WINDOWS_ASSIGNMENT_TEMPLATE_PATH = "\\Work\\SumzuppFiles\\templates\\assignment\\";
    public static final String UBUNTU_ASSIGNMENT_SAVE_PATH = "/var/www/html/pdf/assignment/";
    public static final String WINDOWS_ASSIGNMENT_SAVE_PATH = "\\Work\\SumzuppFiles\\assignment\\";

    //Success Messages
    public static final String POST_SAVED = "Comment has been successfully saved.";
    public static final String UPVOTED = "Upvoted";
    public static final String DOWNVOTED = "Downvoted";
    public static final String LIKED = "Liked";
    public static final String UNLIKED = "Unliked";
    public static final String VOTED = "Voted";
    public static final String COMMENT_SAVED = "Comment has been successfully saved.";
    public static final String STUDENT_DATA_SHEET_SAVED = "Student data sheet successfully saved.";
    public static final String TEACHER_DATA_SHEET_SAVED = "Teacher data sheet successfully saved.";
    public static final String QUESTION_SHEET_SAVED = "Question sheet successfully saved.";
    public static final String QUESTION_PAPER_SAVED = "Question paper successfully saved.";
    public static final String SIGN_UP_SUCCESSFUL  = "Signup successful";
    public static final String LOGIN_SUCCESSFUL  = "Login successful";

    //Failure Messages
    public static final String SOMETHING_WENT_WRONG = "Something went wrong. Please try again later.";
    public static final String USERNAME_ALREADY_EXISTS = "User with entered username already exists. Please choose different username.";
    public static final String CONTACT_NUMBER_ALREADY_EXISTS = "User with entered contact number already exists. Please login to continue.";
    public static final String EMAIL_ALREADY_EXISTS = "User with entered email already exists. Please login to continue.";
    public static final String WRONG_CREDENTIALS = "Username and password combination do not match. Please contact your school administration for your account details.";
    public static final String USER_NOT_REGISTERED = "User with entered username and password does not exist. Please contact your school administration for your account details.";
    public static final String FILE_UPLOAD_FAILED = "File upload failed";
    public static final String EMAIL_DOES_NOT_EXIST = "Your email is not updated with Sumzupp. Please contact your school administration for updating your email.";
    public static final String INVALID_ADMIN_CREDENTIALS = "Incorrect Email / Password combination";
    public static final String MISSING_STANDARDS = "standardBeans is mandatory field.";
    public static final String MISSING_INSTITUTE_ID = "instituteId is mandatory field.";
    public static final String MISSING_TEACHER_SUBJECT_BEANS = "teacherSubjectBeans is mandatory field.";
    public static final String MISSING_USER_TYPES = "userTypes is mandatory field.";
    public static final String PAYMENT_ALREADY_PROCESSED = "Payment has already been processed.";

    //Miscellaneous
    public static final String ACCOUNT_TYPE_EMAIL = "Email";
    public static final String DEVICE_TYPE_ANDROID = "Android";
    public static final String DEVICE_TYPE_IOS = "iOS";

    public static final int SERVER_DATE_CHANGE_SECOND = 18*60*60 + 30*60;

    //Mails
    public static final String FROM_EMAIL = "contact@sumzupp.com";
    public static final String REPLY_TO_EMAIL = "contact@sumzupp.com";

}
