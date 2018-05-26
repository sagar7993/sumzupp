package com.sumzupp.backendapp.utils;

import com.sumzupp.backendapp.enums.FileTypeEnum;

import java.text.DecimalFormat;

/**
 * Created by akash.mercer on 21-Jun-17.
 */
public class Commons {

    public static DecimalFormat amountDecimalFormat = new DecimalFormat("#.##");

    public static String getResponseBaseUrl(String requestUrl) {

        if(requestUrl.indexOf("localhost") > 0) {
            return Constants.LOCALHOST_URL;
        } else if(requestUrl.indexOf("dev.sumzupp.com") > 0) {
            return Constants.DEV_URL;
        } else {
            return Constants.PROD_URL;
        }

    }

    public static String getSumzuppBaseUrl(String requestUrl) {

        if(requestUrl.indexOf("localhost") > 0) {
            return Constants.LOCALHOST_BASE_URL;
        } else if(requestUrl.indexOf("dev.sumzupp.com") > 0) {
            return Constants.DEV_BASE_URL;
        } else {
            return Constants.PROD_BASE_URL;
        }

    }

    public static String getRedirectUrlByRequestUrlAndUploadedFileType(String requestUrl, Integer fileType) {
        String responseBaseUrl = getResponseBaseUrl(requestUrl);

        if (fileType == FileTypeEnum.STUDENT_DATA_SHEET.getType()) {
            return responseBaseUrl + "uploadStudentData";
        } else if (fileType == FileTypeEnum.TEACHER_DATA_SHEET.getType()) {
            return responseBaseUrl + "uploadTeacherData";
        } else if (fileType == FileTypeEnum.QUESTION_SHEET.getType()) {
            return responseBaseUrl + "uploadQuestionSheet";
        } else if (fileType == FileTypeEnum.QUESTION_PAPER.getType()) {
            return responseBaseUrl + "uploadQuestionPaper";
        } else {
            return null;
        }

    }

}
