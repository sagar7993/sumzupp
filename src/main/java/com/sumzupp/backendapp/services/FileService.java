package com.sumzupp.backendapp.services;

import com.sumzupp.backendapp.beans.*;
import com.sumzupp.backendapp.entities.*;
import com.sumzupp.backendapp.enums.FileTypeEnum;
import com.sumzupp.backendapp.enums.S3BucketType;
import com.sumzupp.backendapp.helpers.AwsS3FileUploadHelper;
import com.sumzupp.backendapp.utils.Commons;
import com.sumzupp.backendapp.utils.Constants;
import com.sumzupp.backendapp.utils.DateOperations;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.apache.commons.collections4.MapUtils;
import org.apache.http.util.TextUtils;
import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Decoder;

import javax.imageio.ImageIO;
import javax.validation.constraints.NotNull;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

/**
 * Created by akash.mercer on 24-Jul-17.
 */

@Service
public class FileService {
    private static final String TAG = "fileUploadService : ";

    @Autowired
    private UserService userService;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private SolutionOptionService solutionOptionService;

    @Autowired
    private AssignmentService assignmentService;

    @Autowired
    private StudentAssignmentQuestionService studentAssignmentQuestionService;

    @Autowired
    private TeacherInstituteService teacherInstituteService;

    @Autowired
    private QuestionPaperService questionPaperService;

    @Autowired
    private MailService mailService;

    private static Logger debugLogger = LoggerFactory.getLogger("debugLogs");

    private static Logger errorLogger = LoggerFactory.getLogger("errorLogs");

    public HtmlToPdfBean generateAssignmentOrAssignmentAnalysisPdf(String assignmentId, HttpServletRequest request) throws IOException, InterruptedException {
        HtmlToPdfBean htmlToPdfBean = new HtmlToPdfBean();
        String osName = System.getProperty("os.name");
        String requestUrl = request.getRequestURL().toString();
        String sumzuppBaseUrl = Commons.getSumzuppBaseUrl(requestUrl);
        String savePath = osName.startsWith("Windows") ? Constants.WINDOWS_ASSIGNMENT_SAVE_PATH : Constants.UBUNTU_ASSIGNMENT_SAVE_PATH;
        File pdfFile = new File(savePath, assignmentId + ".pdf");
        String urlPath = sumzuppBaseUrl + "pdf/assignment/" + assignmentId + ".html";
        String wxpath = osName.startsWith("Windows") ? Constants.WINDOWS_WKHTML_PATH : Constants.UBUNTU_WKHTML_PATH;
        wxpath += " --javascript-delay 5000";
        String command = wxpath + " " + urlPath + " " + pdfFile.getPath();
        debugLogger.debug("executing command = " + command);
        Process process = Runtime.getRuntime().exec(command);
        if (process.waitFor() == 0) {
            htmlToPdfBean.setStatus(1);
            htmlToPdfBean.setPdfFilePath(pdfFile.getPath());
            htmlToPdfBean.setPdfFile(pdfFile);
            htmlToPdfBean.setPdfDownloadUrl(sumzuppBaseUrl + "pdf/assignment/" + assignmentId + ".pdf");
        } else {
            htmlToPdfBean.setStatus(0);
            htmlToPdfBean.setMessage(Constants.SOMETHING_WENT_WRONG);
        }
        return htmlToPdfBean;
    }

    public HtmlToPdfBean getAssignmentHtml(String assignmentId) throws IOException {
        HtmlToPdfBean htmlToPdfBean = new HtmlToPdfBean(0, Constants.SOMETHING_WENT_WRONG);

        AssignmentBean retrievedAssignment = assignmentService.findByAssignment(assignmentId);

        if (retrievedAssignment == null) {
            errorLogger.error(TAG + "Error in finding Assignment with id : " + assignmentId);
            return htmlToPdfBean;
        }

        htmlToPdfBean.setAssignmentId(retrievedAssignment.getAssignmentId());

        return saveAssignmentHtmlFileFromHtmlString(htmlToPdfBean.getAssignmentId(), retrievedAssignment);
    }

    public HtmlToPdfBean getAssignmentAnalysisHtml(String assignmentId, String teacherId) throws IOException {
        HtmlToPdfBean htmlToPdfBean = new HtmlToPdfBean(0, Constants.SOMETHING_WENT_WRONG);

        AssignmentBean retrievedAssignment = assignmentService.findByAssignment(assignmentId);

        if (retrievedAssignment == null) {
            errorLogger.error(TAG + "Error in finding Assignment with id : " + assignmentId);
            return htmlToPdfBean;
        }

        htmlToPdfBean.setAssignmentId(retrievedAssignment.getAssignmentId());

        return saveAnalysisHtmlFileFromHtmlString(htmlToPdfBean.getAssignmentId(), teacherId, retrievedAssignment);
    }

    public HtmlToPdfBean getAssignmentAnalysisPdf(String assignmentId, String teacherId, HttpServletRequest request) throws IOException, InterruptedException {
        HtmlToPdfBean htmlToPdfBean = getAssignmentAnalysisHtml(assignmentId, teacherId);

        if (htmlToPdfBean.getStatus() == 0) {
            errorLogger.error(TAG + "Error in finding Assignment with id : " + assignmentId);

            htmlToPdfBean.setStatus(0);
            htmlToPdfBean.setMessage(Constants.SOMETHING_WENT_WRONG);
            return htmlToPdfBean;
        } else {
            htmlToPdfBean = generateAssignmentOrAssignmentAnalysisPdf(assignmentId, request);

            if (htmlToPdfBean.getStatus() == 1) {
                return htmlToPdfBean;
            } else {
                errorLogger.error(TAG + "Error in finding Assignment with id : " + assignmentId);

                htmlToPdfBean.setStatus(0);
                htmlToPdfBean.setMessage(Constants.SOMETHING_WENT_WRONG);
                return htmlToPdfBean;
            }
        }
    }

    public StatusBean sendAssignmentAnalysisPdfEmail(String assignmentId, String teacherId, HttpServletRequest request) throws IOException, InterruptedException {
        StatusBean statusBean = new StatusBean();

        User retrievedTeacher = userService.findById(teacherId);

        if (retrievedTeacher == null) {
            errorLogger.error(TAG + "Error in finding Teacher with id : " + teacherId);

            statusBean.setStatus(0);
            statusBean.setMessage(Constants.SOMETHING_WENT_WRONG);
            return statusBean;
        }

        HtmlToPdfBean fileDownloadBean = getAssignmentAnalysisPdf(assignmentId, teacherId, request);

        if (fileDownloadBean.getStatus() == 0) {
            errorLogger.error(TAG + "Error in finding Assignment with id : " + assignmentId);

            statusBean.setStatus(0);
            statusBean.setMessage(Constants.SOMETHING_WENT_WRONG);
            return statusBean;
        }

        mailService.sendFile(retrievedTeacher.getEmail(), fileDownloadBean.getPdfFilePath());

        if (!StringUtils.isEmpty(retrievedTeacher.getEmail())) {
            try {
                mailService.sendFile(retrievedTeacher.getEmail(), fileDownloadBean.getPdfFilePath());
                statusBean.setStatus(1);
            } catch (Exception e) {
                errorLogger.error(TAG + "Error in mailing PDF file to email : " + retrievedTeacher.getEmail());

                statusBean.setStatus(0);
                statusBean.setMessage(Constants.SOMETHING_WENT_WRONG);
            }
        } else {
            statusBean.setStatus(0);
            statusBean.setMessage(Constants.EMAIL_DOES_NOT_EXIST);
        }

        return statusBean;
    }

    private static String readFile(String path, Charset encoding) throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }

    public HtmlToPdfBean saveAssignmentHtmlFileFromHtmlString(String fileNameWithoutExtension, AssignmentBean assignmentBean) throws IOException {
        HtmlToPdfBean htmlToPdfBean = new HtmlToPdfBean(0, Constants.SOMETHING_WENT_WRONG);
        String osName = System.getProperty("os.name");

        String templateRootPath = osName.startsWith("Windows") ? Constants.WINDOWS_ASSIGNMENT_TEMPLATE_PATH : Constants.UBUNTU_ASSIGNMENT_TEMPLATE_PATH;
        String generatedHTMLFileRootPath = osName.startsWith("Windows") ? Constants.WINDOWS_PDF_FILE_PATH : Constants.UBUNTU_PDF_FILE_PATH;

        File generatedHTMLFileDirectory = new File(generatedHTMLFileRootPath);
        String generatedHTMLFilePath = generatedHTMLFileDirectory.getAbsolutePath() + File.separator + fileNameWithoutExtension + ".html";

        String styleTagHTMLContent = readFile(templateRootPath + "style.html", StandardCharsets.UTF_8);
        String optionSolutionHTMLContent = readFile(templateRootPath + "option_answer_repeater.html", StandardCharsets.UTF_8);
        String mainAssignmentHTMLContent = readFile(templateRootPath + "assignment.html", StandardCharsets.UTF_8);

        mainAssignmentHTMLContent = mainAssignmentHTMLContent.replace("STYLE_TAG", styleTagHTMLContent);
        mainAssignmentHTMLContent = mainAssignmentHTMLContent.replace("ASSIGNMENT_TITLE", assignmentBean.getAssignmentTitle());
        mainAssignmentHTMLContent = mainAssignmentHTMLContent.replace("ASSIGNMENT_DIVISIONS", StringUtils.collectionToCommaDelimitedString(assignmentBean.getStandardDivisionNames()));
        mainAssignmentHTMLContent = mainAssignmentHTMLContent.replace("ASSIGNMENT_CHAPTER", assignmentBean.getSubjectChapterName());
        mainAssignmentHTMLContent = mainAssignmentHTMLContent.replace("ASSIGNMENT_SUBJECT", assignmentBean.getSubject());
        mainAssignmentHTMLContent = mainAssignmentHTMLContent.replace("ASSIGNMENT_MARKS", String.valueOf(assignmentBean.getMarks()));
        mainAssignmentHTMLContent = mainAssignmentHTMLContent.replace("ASSIGNMENT_DEADLINE", DateOperations.getTomorrowDateStringInFrontEndFormatFromDate(new Date(assignmentBean.getDeadlineDate())));

        String allQuestionsContent = ""; int questionCounter = 0;
        String[] alphabets = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
        for(QuestionBean questionBean : assignmentBean.getQuestionBeans()) {
            questionCounter++; String allOptionsContent = ""; int optionCounter = 0;
            String questionRepeaterHTMLContent = readFile(templateRootPath + "question_repeater.html", StandardCharsets.UTF_8);
            String questionTextRepeaterHTMLContent = readFile(templateRootPath + "question_text_repeater.html", StandardCharsets.UTF_8);
            questionTextRepeaterHTMLContent = questionTextRepeaterHTMLContent.replace("QUESTION_NUMBER_INDEX", String.valueOf(questionCounter));
            questionTextRepeaterHTMLContent = questionTextRepeaterHTMLContent.replace("ASSIGNMENT_QUESTION_TEXT", questionBean.getQuestionText());
            questionRepeaterHTMLContent = questionRepeaterHTMLContent.replace("ASSIGNMENT_QUESTION_TEXT_REPEATER", questionTextRepeaterHTMLContent);
            for(SolutionOptionBean solutionOptionBean : questionBean.getSolutionOptionBeans()) {
                String optionRepeaterHTMLContent = readFile(templateRootPath + "option_repeater.html", StandardCharsets.UTF_8);
                optionRepeaterHTMLContent = optionRepeaterHTMLContent.replace("ASSIGNMENT_QUESTION_OPTION_INDEX", alphabets[optionCounter]);
                optionRepeaterHTMLContent = optionRepeaterHTMLContent.replace("ASSIGNMENT_QUESTION_OPTION_TEXT", solutionOptionBean.getSolutionOptionText());
                if(solutionOptionBean.getSolution()) {
                    optionRepeaterHTMLContent = optionRepeaterHTMLContent.replace("ASSIGNMENT_QUESTION_OPTION_ANSWER", optionSolutionHTMLContent);
                } else {
                    optionRepeaterHTMLContent = optionRepeaterHTMLContent.replace("ASSIGNMENT_QUESTION_OPTION_ANSWER", "");
                }
                allOptionsContent += optionRepeaterHTMLContent; optionCounter++;
            }
            questionRepeaterHTMLContent = questionRepeaterHTMLContent.replace("ASSIGNMENT_QUESTION_OPTION_REPEATER", allOptionsContent);
            allQuestionsContent += questionRepeaterHTMLContent;
        }
        mainAssignmentHTMLContent = mainAssignmentHTMLContent.replace("ASSIGNMENT_QUESTION_REPEATER", allQuestionsContent);
        try {
            PrintWriter out = new PrintWriter(generatedHTMLFilePath);
            out.println(mainAssignmentHTMLContent);
            out.close();
            htmlToPdfBean.setHtmlString(mainAssignmentHTMLContent);
            htmlToPdfBean.setAssignmentId(assignmentBean.getAssignmentId());
            htmlToPdfBean.setStatus(1);
        } catch(Exception e) {
            htmlToPdfBean.setMessage(e.getMessage());
        }
        return htmlToPdfBean;
    }

    public HtmlToPdfBean saveAnalysisHtmlFileFromHtmlString(String fileNameWithoutExtension, String teacherId, AssignmentBean assignmentBean) throws IOException {
        HtmlToPdfBean htmlToPdfBean = new HtmlToPdfBean(0, Constants.SOMETHING_WENT_WRONG);
        String osName = System.getProperty("os.name");

        String templateRootPath = osName.startsWith("Windows") ? Constants.WINDOWS_ASSIGNMENT_TEMPLATE_PATH : Constants.UBUNTU_ASSIGNMENT_TEMPLATE_PATH;
        String generatedHTMLFileRootPath = osName.startsWith("Windows") ? Constants.WINDOWS_PDF_FILE_PATH : Constants.UBUNTU_PDF_FILE_PATH;

        File generatedHTMLFileDirectory = new File(generatedHTMLFileRootPath);
        String generatedHTMLFilePath = generatedHTMLFileDirectory.getAbsolutePath() + File.separator + fileNameWithoutExtension + ".html";

        String styleTagHTMLContent = readFile(templateRootPath + "style.html", StandardCharsets.UTF_8);
        String mainAssignmentHTMLContent = readFile(templateRootPath + "analysis.html", StandardCharsets.UTF_8);

        mainAssignmentHTMLContent = mainAssignmentHTMLContent.replace("STYLE_TAG", styleTagHTMLContent);
        mainAssignmentHTMLContent = mainAssignmentHTMLContent.replace("ASSIGNMENT_TITLE", assignmentBean.getAssignmentTitle());
        mainAssignmentHTMLContent = mainAssignmentHTMLContent.replace("ASSIGNMENT_DIVISIONS", StringUtils.collectionToCommaDelimitedString(assignmentBean.getStandardDivisionNames()));
        mainAssignmentHTMLContent = mainAssignmentHTMLContent.replace("ASSIGNMENT_CHAPTER", assignmentBean.getSubjectChapterName());
        mainAssignmentHTMLContent = mainAssignmentHTMLContent.replace("ASSIGNMENT_SUBJECT", assignmentBean.getSubject());
        mainAssignmentHTMLContent = mainAssignmentHTMLContent.replace("ASSIGNMENT_MARKS", String.valueOf(assignmentBean.getMarks()));
        mainAssignmentHTMLContent = mainAssignmentHTMLContent.replace("ASSIGNMENT_DEADLINE", DateOperations.getTomorrowDateStringInFrontEndFormatFromDate(new Date(assignmentBean.getDeadlineDate())));

        Assignment assignment = assignmentService.findById(assignmentBean.getAssignmentId());
        User assignmentTeacher = userService.findById(teacherId);
        User assignmentInstitute = new User();
        if(assignmentTeacher.getUserType().getType() == 2) {
            TeacherInstitute teacherInstitute = teacherInstituteService.findByTeacher(assignmentTeacher);
            assignmentInstitute = teacherInstitute.getInstitute();
        } else if(assignmentTeacher.getUserType().getType() == 4) {
            assignmentInstitute = assignmentTeacher;
        } else if(assignmentTeacher.getUserType().getType() == 5) {
            User loggedInTeacher = userService.findById(teacherId);
            if (loggedInTeacher.getUserType().getType() == 2) {
                TeacherInstitute teacherInstitute = teacherInstituteService.findByTeacher(loggedInTeacher);
                assignmentInstitute = teacherInstitute.getInstitute();
            } else if(loggedInTeacher.getUserType().getType() == 4 || loggedInTeacher.getUserType().getType() == 5) {
                assignmentInstitute = loggedInTeacher;
            }
        }
        List<StudentAssignmentQuestion> studentAssignmentQuestionList = studentAssignmentQuestionService.findByAssignmentAndInstitute(assignment, assignmentInstitute);
        List<QuestionBean> questionBeanList = assignmentBean.getQuestionBeans();
        if(CollectionUtils.isEmpty(studentAssignmentQuestionList)) {
            htmlToPdfBean.setStatus(0);
            htmlToPdfBean.setMessage("No student has attempted this assignment till now.");
            return htmlToPdfBean;
        }
        Map<User, List<StudentAssignmentQuestion>> asignmentQuestionStudentAssignmentQuestionMap = generateStudentAssignmentQuestionMap(studentAssignmentQuestionList, questionBeanList, assignmentInstitute);
        if(MapUtils.isEmpty(asignmentQuestionStudentAssignmentQuestionMap)) {
            htmlToPdfBean.setStatus(0);
            htmlToPdfBean.setMessage("No student has attempted this assignment till now.");
            return htmlToPdfBean;
        }
        int studentCounter = 0; String tbody = "", theadRepeater = "";
        int numberOfQuestions = assignmentBean.getQuestionBeans().size();
        List<String> noQuestionsAttempted = new ArrayList<>();
        List<String> allWrongAnswers = new ArrayList<>();
        List<String> allRightAnswers = new ArrayList<>();
        String tbodySummaryRepeater = "", noQuestionsAttemptedRepeater = "", allWrongAnswersRepeater = "", allRightAnswersRepeater = "";
        for(int i = 1; i <= numberOfQuestions; i++) {
            theadRepeater += "<th>Q." + i + "</th>";
        }
        for(User student : asignmentQuestionStudentAssignmentQuestionMap.keySet()) {
            if(student.getAdmin()) {
                continue;
            }
            studentCounter++; boolean firstColumn = true;
            String tbodyRepeater = "<tr>";
            int totalAttempted = 0, totalSkipped = 0, totalCorrect = 0, totalWrong = 0; String totalScore = "";
            for (StudentAssignmentQuestion studentAssignmentQuestion : asignmentQuestionStudentAssignmentQuestionMap.get(student)) {
                if(firstColumn) {
                    tbodyRepeater += "<td>" + studentCounter + "</td>"
                            + "<td>" + student.getName().toUpperCase() + "</td>";
                }
                String isCorrect = studentAssignmentQuestion.getAttempted() ? (studentAssignmentQuestion.getCorrect() ? "Y" : "N") : "S";
                String color = studentAssignmentQuestion.getAttempted() ? (studentAssignmentQuestion.getCorrect() ? "#21CE3E" : "red") : "blue";
                tbodyRepeater += "<td style=\"color:" + color + ";font-weight:bold;\">" + isCorrect + "</td>";
                tbody += tbodyRepeater;
                firstColumn = false;
                tbodyRepeater = "";
                if(studentAssignmentQuestion.getAttempted()) {
                    totalAttempted++;
                    if(studentAssignmentQuestion.getCorrect()) {
                        totalCorrect++;
                    } else {
                        totalWrong++;
                    }
                } else {
                    totalSkipped++;
                }
                totalScore = totalCorrect + " / " + asignmentQuestionStudentAssignmentQuestionMap.get(student).size();
            }
            if(totalAttempted == 0) {
                noQuestionsAttempted.add(student.getName());
                noQuestionsAttemptedRepeater += "<li><h6>" + student.getName() + "</h6></li>";
            }
            if(totalWrong == asignmentQuestionStudentAssignmentQuestionMap.get(student).size()) {
                allWrongAnswers.add(student.getName());
                allWrongAnswersRepeater += "<li><h6>" + student.getName() + "</h6></li>";
            }
            if(totalCorrect == asignmentQuestionStudentAssignmentQuestionMap.get(student).size()) {
                allRightAnswers.add(student.getName());
                allRightAnswersRepeater += "<li><h6>" + student.getName() + "</h6></li>";
            }
            tbodySummaryRepeater += "<tr><td>" + studentCounter + "</td>"
                    + "<td>" + student.getName().toUpperCase() + "</td>"
                    + "<td>" + totalAttempted + "</td>"
                    + "<td>" + totalSkipped + "</td>"
                    + "<td>" + totalCorrect + "</td>"
                    + "<td>" + totalWrong + "</td>"
                    + "<td>" + totalScore + "</td></tr>";
            tbody += "</tr>";
        }
        if(noQuestionsAttemptedRepeater.length() == 0) {
            noQuestionsAttemptedRepeater = "N / A";
        }
        if(allWrongAnswersRepeater.length() == 0) {
            allWrongAnswersRepeater = "N / A";
        }
        if(allRightAnswersRepeater.length() == 0) {
            allRightAnswersRepeater = "N / A";
        }
        mainAssignmentHTMLContent = mainAssignmentHTMLContent.replace("NO_QUESTIONS_ATTEMPTED_REPEATER", noQuestionsAttemptedRepeater);
        mainAssignmentHTMLContent = mainAssignmentHTMLContent.replace("ALL_WRONG_ANSWERS_REPEATER", allWrongAnswersRepeater);
        mainAssignmentHTMLContent = mainAssignmentHTMLContent.replace("ALL_RIGHT_ANSWERS_REPEATER", allRightAnswersRepeater);

        mainAssignmentHTMLContent = mainAssignmentHTMLContent.replace("THEAD_QUESTION_REPEATER", theadRepeater);
        mainAssignmentHTMLContent = mainAssignmentHTMLContent.replace("TBODY_QUESTION_REPEATER", tbody);
        mainAssignmentHTMLContent = mainAssignmentHTMLContent.replace("TBODY_STUDENT_REPEATER", tbodySummaryRepeater);

        try {
            PrintWriter out = new PrintWriter(generatedHTMLFilePath);
            out.println(mainAssignmentHTMLContent);
            out.close();
            htmlToPdfBean.setHtmlString(mainAssignmentHTMLContent);
            htmlToPdfBean.setAssignmentId(assignmentBean.getAssignmentId());
            htmlToPdfBean.setStatus(1);
        } catch(Exception e) {
            htmlToPdfBean.setMessage(e.getMessage());
        }
        return htmlToPdfBean;
    }

    private Map<User, List<StudentAssignmentQuestion>> generateStudentAssignmentQuestionMap(List<StudentAssignmentQuestion> studentAssignmentQuestionList, List<QuestionBean> questionBeanList, User institute) {
        Map<User, List<StudentAssignmentQuestion>> map = new HashMap<>();
        Map<String, Integer> questionOrderMap = new HashMap<>();
        int counter = 1;
        for(QuestionBean questionBean : questionBeanList) {
            questionOrderMap.put(questionBean.getQuestionId(), counter);
            counter++;
        }
        for (StudentAssignmentQuestion studentAssignmentQuestion : studentAssignmentQuestionList) {
            if (map.containsKey(studentAssignmentQuestion.getStudent())) {
                List<StudentAssignmentQuestion> assignmentQuestions = map.get(studentAssignmentQuestion.getStudent());
                if (!assignmentQuestions.contains(studentAssignmentQuestion)) {
                    assignmentQuestions.add(studentAssignmentQuestion);
                    Collections.sort(assignmentQuestions, (o1, o2) -> questionOrderMap.get(o1.getAssignmentQuestion().getQuestion().getId()).compareTo(questionOrderMap.get(o2.getAssignmentQuestion().getQuestion().getId())));
                    map.put(studentAssignmentQuestion.getStudent(), assignmentQuestions);
                }
            } else {
                List<StudentAssignmentQuestion> assignmentQuestions = new ArrayList<>();
                assignmentQuestions.add(studentAssignmentQuestion);
                map.put(studentAssignmentQuestion.getStudent(), assignmentQuestions);
            }
        }
        return map;
    }

    public StatusBean uploadFileHandler(String fileUri, String fileName, Integer fileType, MultipartFile file) throws Exception {
        StatusBean statusBean = new StatusBean();
        if (!file.isEmpty()) {
            String osName = System.getProperty("os.name");
            byte[] bytes = file.getBytes();
            String rootPath;
            if (fileType == FileTypeEnum.STUDENT_DATA_SHEET.getType()) {
                rootPath = osName.startsWith("Windows") ? Constants.WINDOWS_STUDENT_DATA_FILE_PATH : Constants.UBUNTU_STUDENT_DATA_FILE_PATH;
            } else if (fileType == FileTypeEnum.TEACHER_DATA_SHEET.getType()) {
                rootPath = osName.startsWith("Windows") ? Constants.WINDOWS_TEACHER_DATA_FILE_PATH : Constants.UBUNTU_TEACHER_DATA_FILE_PATH;
            } else if (fileType == FileTypeEnum.QUESTION_SHEET.getType()) {
                rootPath = osName.startsWith("Windows") ? Constants.WINDOWS_QUESTION_SHEET_FILE_PATH : Constants.UBUNTU_QUESTION_SHEET_FILE_PATH;
            } else if (fileType == FileTypeEnum.QUESTION_PAPER.getType()) {
                rootPath = osName.startsWith("Windows") ? Constants.WINDOWS_QUESTION_PAPER_FILE_PATH : Constants.UBUNTU_QUESTION_PAPER_FILE_PATH;
            } else {
                errorLogger.error(TAG + "Tried to send unknown file type : " + fileType);
                statusBean.setStatus(0);
                statusBean.setMessage("Tried to send unknown file type : " + fileType);
                return statusBean;
            }
            File directory = new File(rootPath);
            if (!directory.exists()) {
                directory.mkdirs();
            }
            File serverFile = new File(directory.getAbsolutePath() + File.separator + fileUri);
            BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
            stream.write(bytes);
            stream.close();
            if (fileType == FileTypeEnum.STUDENT_DATA_SHEET.getType()) {
                String welcomeHtmlPath = osName.startsWith("Windows") ? Constants.WINDOWS_STUDENT_WELCOME_PATH : Constants.UBUNTU_STUDENT_WELCOME_PATH;

                File welcomeHtmlDirectory = new File(welcomeHtmlPath);

                if (!welcomeHtmlDirectory.exists()) {
                    if (welcomeHtmlDirectory.mkdir()) {
                        for(File tempfile: welcomeHtmlDirectory.listFiles()) {
                            if (!tempfile.isDirectory()) {
                                tempfile.delete();
                            }
                        }
                        statusBean = userService.readStudentsDataSheet(fileUri);

                        if (statusBean.getStatus() == 1) {
                            statusBean.setMessage(Constants.STUDENT_DATA_SHEET_SAVED);
                        }
                    } else {
                        statusBean.setMessage("Error in creating " + welcomeHtmlPath + " directory");
                    }
                } else {
                    for(File tempfile: welcomeHtmlDirectory.listFiles()) {
                        if (!tempfile.isDirectory()) {
                            tempfile.delete();
                        }
                    }

                    statusBean = userService.readStudentsDataSheet(fileUri);

                    if (statusBean.getStatus() == 1) {
                        statusBean.setMessage(Constants.STUDENT_DATA_SHEET_SAVED);
                    }
                }
            } else if (fileType == FileTypeEnum.TEACHER_DATA_SHEET.getType()) {
                statusBean = userService.readTeachersDataSheet(fileUri);
                if (statusBean.getStatus() == 1) {
                    statusBean.setMessage(Constants.TEACHER_DATA_SHEET_SAVED);
                }
            } else if (fileType == FileTypeEnum.QUESTION_SHEET.getType()) {
                statusBean = questionService.readQuestionSheet(fileUri);
                if (statusBean.getStatus() == 1) {
                    statusBean.setMessage(Constants.QUESTION_SHEET_SAVED);
                }
            } else if (fileType == FileTypeEnum.QUESTION_PAPER.getType()) {
                statusBean = questionPaperService.uploadQuestionPaper(fileUri, fileName, serverFile);

                if (statusBean.getStatus() == 1) {
                    statusBean.setMessage(Constants.QUESTION_PAPER_SAVED);
                }
            }
        } else {
            errorLogger.error(TAG + "Tried to upload empty file : " + fileUri);
            statusBean.setStatus(0);
            statusBean.setMessage("Tried to upload empty file : " + fileUri);
        }
        return statusBean;
    }

    public Boolean saveImage(String base64String, String fileName, String extension, String filePath) throws IOException {
        BufferedImage image; byte[] imageByte; BASE64Decoder decoder = new BASE64Decoder();
        imageByte = decoder.decodeBuffer(base64String);
        ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
        image = ImageIO.read(bis); bis.close();
        File outputFile = new File(filePath + fileName + "." + extension);
        return ImageIO.write(image, extension, outputFile);
    }

    /**
     *
     * @param jsonArray
     * @param file
     * @return
     * @throws Exception
     */
    public JSONArray pushImageToS3(JSONArray jsonArray, File file) throws Exception {
        AwsS3FileUploadHelper.getInstance().uploadFile(S3BucketType.QUESTION_BANK_IMAGES, file);

        if (file.getName().contains(" ")) {
            jsonArray.put(Constants.AWS_QUESTION_BANK_IMAGES_BUCKET_URL + file.getName().replace(" ", "+"));
        }  else {
            jsonArray.put(Constants.AWS_QUESTION_BANK_IMAGES_BUCKET_URL + file.getName());
        }
        return jsonArray;
    }

    /**
     *
     * @param questions
     *              Already saved Questions list
     * @param solutionOptions
     *              Already saved SolutionOptions list
     * @return StatusBean
     *              where 1 indicates success and 0 indicated failure
     */
    public StatusBean uploadQuestionBankImages(@NotNull List<Question> questions, @NotNull List<SolutionOption> solutionOptions) {
        StatusBean statusBean = new StatusBean();

        for (Question question : questions) {
            if (!CollectionUtils.isEmpty(question.getQuestionImageFilePaths())) {
                JSONArray jsonArray = new JSONArray();

                //Upload Question images
                for (String filePath : question.getQuestionImageFilePaths()) {
                    File file = new File(filePath);

                    try {
                        AwsS3FileUploadHelper.getInstance().uploadFile(S3BucketType.QUESTION_BANK_IMAGES, file);

                        if (file.getName().contains(" ")) {
                            jsonArray.put(Constants.AWS_QUESTION_BANK_IMAGES_BUCKET_URL + file.getName().replace(" ", "+"));
                        }  else {
                            jsonArray.put(Constants.AWS_QUESTION_BANK_IMAGES_BUCKET_URL + file.getName());
                        }
                    } catch (Exception e) {
                        statusBean.setStatus(0);
                        statusBean.setMessage(e.getMessage());

                        throw e;
                    }
                }

                question.setQuestionImageUrls(jsonArray.toString());

                for (SolutionOption solutionOption : solutionOptions) {
                    if (!TextUtils.isEmpty(solutionOption.getSolutionOptionImageFilePath())) {
                        File file = new File(solutionOption.getSolutionOptionImageFilePath());

                        //Upload SolutionOption image
                        try {
                            AwsS3FileUploadHelper.getInstance().uploadFile(S3BucketType.QUESTION_BANK_IMAGES, file);

                            if (file.getName().contains(" ")) {
                                solutionOption.setSolutionOptionImageUrls(Constants.AWS_QUESTION_BANK_IMAGES_BUCKET_URL + file.getName().replace(" ", "+"));
                            } else {
                                solutionOption.setSolutionOptionImageUrls(Constants.AWS_QUESTION_BANK_IMAGES_BUCKET_URL + file.getName());
                            }
                        } catch (Exception e) {
                            statusBean.setStatus(0);
                            statusBean.setMessage(e.getMessage());

                            throw e;
                        }
                    }
                }
            }
        }

        try {
            questionService.saveAll(questions);
        } catch (Exception e) {
            errorLogger.error(TAG, "Error in updating Question image url with error : " + e.getMessage());

            statusBean.setStatus(0);
            statusBean.setMessage(e.getMessage());

            throw e;
        }

        try {
            solutionOptionService.saveAll(solutionOptions);
        } catch (Exception e) {
            errorLogger.error(TAG, "Error in updating SolutionOption image url with error : " + e.getMessage());

            statusBean.setStatus(0);
            statusBean.setMessage(e.getMessage());

            throw e;
        }

        statusBean.setStatus(1);
        return statusBean;
    }

}
