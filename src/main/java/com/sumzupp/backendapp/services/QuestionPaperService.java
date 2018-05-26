package com.sumzupp.backendapp.services;

import com.sumzupp.backendapp.beans.QuestionPapersBean;
import com.sumzupp.backendapp.beans.StatusBean;
import com.sumzupp.backendapp.entities.QuestionPaper;
import com.sumzupp.backendapp.entities.QuestionPaperType;
import com.sumzupp.backendapp.entities.SubjectChapter;
import com.sumzupp.backendapp.entities.User;
import com.sumzupp.backendapp.enums.S3BucketType;
import com.sumzupp.backendapp.helpers.AwsS3FileUploadHelper;
import com.sumzupp.backendapp.repositories.QuestionPaperRepository;
import com.sumzupp.backendapp.utils.Constants;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by akash.mercer on 28-Oct-17.
 */

@Service
public class QuestionPaperService {
    private static final String TAG = "QuestionPaperService : ";

    @Autowired
    private QuestionPaperRepository questionPaperRepository;

    @Autowired
    private QuestionPaperTypeService questionPaperTypeService;

    @Autowired
    private SubjectChapterService subjectChapterService;

    @Autowired
    private UserService userService;

    private static Logger debugLogger = Logger.getLogger("debugLogs");

    private static Logger errorLogger = Logger.getLogger("errorLogs");

    public StatusBean uploadQuestionPaper(String fileUri, String fileName, File questionPaperFile) {
        StatusBean statusBean = new StatusBean();

        try {
            AwsS3FileUploadHelper.getInstance().uploadFile(S3BucketType.QUESTION_PAPERS, questionPaperFile);
        } catch (Exception e) {
            errorLogger.error(TAG + "Error in updating SolutionOption image url with error : " + e.getMessage());

            statusBean.setStatus(0);
            statusBean.setMessage(e.getMessage());

            throw e;
        }

        String questionPaperUrl;

        if (questionPaperFile.getName().contains(" ")) {
            questionPaperUrl = Constants.AWS_QUESTION_PAPERS_BUCKET_URL + questionPaperFile.getName().replace(" ", "+");
        } else {
            questionPaperUrl = Constants.AWS_QUESTION_PAPERS_BUCKET_URL + questionPaperFile.getName();
        }

        try {
            statusBean = save(fileUri, fileName, questionPaperUrl);
        } catch (Exception e) {
            errorLogger.error(TAG + "Error in creating Question Paper for url : " + questionPaperUrl  + " with error : " + e.getMessage());

            statusBean.setStatus(0);
            statusBean.setMessage(e.getMessage());

            throw e;
        }

        statusBean.setStatus(1);
        return statusBean;
    }

    public StatusBean save(String fileUri, String fileName, String questionPaperUrl) {
        StatusBean statusBean = new StatusBean();

        String[] fileNameParts = fileUri.split("_");

        SubjectChapter retrievedSubjectChapter = subjectChapterService.findById(fileNameParts[0]);

        if (retrievedSubjectChapter == null){
            errorLogger.error(TAG + "Error in finding SubjectChapter with id: " + fileNameParts[0]);

            statusBean.setStatus(0);
            statusBean.setMessage(Constants.SOMETHING_WENT_WRONG);
            return statusBean;
        }

        User retrievedInstitute = userService.findById(fileNameParts[1]);

        if (retrievedInstitute == null){
            errorLogger.error(TAG + "Error in finding Institute with id: " + fileNameParts[1]);

            statusBean.setStatus(0);
            statusBean.setMessage(Constants.SOMETHING_WENT_WRONG);
            return statusBean;
        }

        QuestionPaperType retrievedQuestionPaperType = questionPaperTypeService.findByType(1);

        QuestionPaper questionPaper = new QuestionPaper();
        questionPaper.setQuestionPaperName(fileName);
        questionPaper.setQuestionPaperUrl(questionPaperUrl);
        questionPaper.setQuestionPaperType(retrievedQuestionPaperType);
        questionPaper.setSubjectChapter(retrievedSubjectChapter);
        questionPaper.setInstitute(retrievedInstitute);

        try {
            questionPaperRepository.save(questionPaper);
        } catch (Exception e) {
            errorLogger.error(TAG + "Error in creation QuestionPaper with error : " + e.getMessage());

            statusBean.setStatus(0);
            statusBean.setMessage(Constants.SOMETHING_WENT_WRONG);

            return statusBean;
        }

        return statusBean;
    }

    public QuestionPapersBean findByUserAndSubjectChapter(String userId, String subjectChapterId) {
        QuestionPapersBean questionPapersBean = new QuestionPapersBean();

        User retrievedUser = userService.findById(userId);

        if (retrievedUser == null) {
            errorLogger.error(TAG + "Error in finding User with id : " + userId);

            questionPapersBean.setStatus(0);
            questionPapersBean.setMessage(Constants.SOMETHING_WENT_WRONG);

            return questionPapersBean;
        }

        SubjectChapter retrievedSubjectChapter = subjectChapterService.findById(subjectChapterId);

        if (retrievedSubjectChapter == null) {
            errorLogger.error(TAG + "Error in finding SubjectChapter with id : " + subjectChapterId);

            questionPapersBean.setStatus(0);
            questionPapersBean.setMessage(Constants.SOMETHING_WENT_WRONG);

            return questionPapersBean;
        }

        // TODO: 10-Sep-17 Below Institute Id is hard-coded on purpose
        List<String> instituteIds = new ArrayList<>();
        instituteIds.add(retrievedUser.getStandardDivision().getInstitute().getId());
        instituteIds.add("7");

        questionPapersBean.setQuestionPaperBeans(questionPaperRepository.findBySubjectChapterAndInstitutes(retrievedSubjectChapter, instituteIds));

        questionPapersBean.setStatus(1);
        return questionPapersBean;
    }
}
