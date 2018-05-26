package com.sumzupp.backendapp.controllers;

import com.sumzupp.backendapp.beans.HtmlToPdfBean;
import com.sumzupp.backendapp.beans.StatusBean;
import com.sumzupp.backendapp.services.FileService;
import com.sumzupp.backendapp.utils.Commons;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by akash.mercer on 24-Jul-17.
 */

@RestController
@RequestMapping(value = "/file")
public class FileController {

    @Autowired
    private FileService fileService;

    // FIXME: 28-Oct-17 Web front-end API needs to change fileName -> fileUri & fileUri -> fileName as well as below param matching
    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
    public @ResponseBody RedirectView uploadFileHandler(@RequestParam("fileUri") String fileName, @RequestParam("fileName") String fileUri, @RequestParam("fileType") Integer fileType, @RequestParam("file") MultipartFile file, HttpServletRequest request) throws Exception {
        StatusBean statusBean = fileService.uploadFileHandler(fileName, fileUri, fileType, file);
        String requestUrl = request.getRequestURL().toString();
        String redirectUrl = Commons.getRedirectUrlByRequestUrlAndUploadedFileType(requestUrl, fileType);
        String redirectUrlWithQueryParameters = redirectUrl + "?status=" + statusBean.getStatus() + "&message=" + statusBean.getMessage();
        return new RedirectView(redirectUrlWithQueryParameters);
    }

    @RequestMapping(value = "/uploadQuestionBankImages", method = RequestMethod.GET)
    public @ResponseBody StatusBean uploadQuestionBankImages() {
        return fileService.uploadQuestionBankImages(new ArrayList<>(), new ArrayList<>());
    }

    @RequestMapping(value = "/getAssignmentHtml/{assignmentId}", method = RequestMethod.GET)
    public @ResponseBody HtmlToPdfBean getAssignmentHtml(@PathVariable("assignmentId") String assignmentId) throws IOException {
        return fileService.getAssignmentHtml(assignmentId);
    }

    @RequestMapping(value = "/getAssignmentAnalysisHtml/{assignmentId}/{teacherId}", method = RequestMethod.GET)
    public @ResponseBody HtmlToPdfBean getAssignmentAnalysisHtml(@PathVariable("assignmentId") String assignmentId, @PathVariable("teacherId") String teacherId, HttpServletRequest request) throws IOException {
        return fileService.getAssignmentAnalysisHtml(assignmentId, teacherId);
    }

    @RequestMapping(value = "/getAssignmentAnalysisPdf/{assignmentId}/{teacherId}", method = RequestMethod.GET)
    public @ResponseBody HtmlToPdfBean getAssignmentAnalysisPdf(@PathVariable("assignmentId") String assignmentId, @PathVariable("teacherId") String teacherId, HttpServletRequest request) throws IOException, InterruptedException {
        return fileService.getAssignmentAnalysisPdf(assignmentId, teacherId, request);
    }

    @RequestMapping(value = "/sendAssignmentAnalysisPdfEmail/{assignmentId}/{teacherId}", method = RequestMethod.GET)
    public @ResponseBody StatusBean sendAssignmentAnalysisPdfEmail(@PathVariable("assignmentId") String assignmentId, @PathVariable("teacherId") String teacherId, HttpServletRequest request) throws IOException, InterruptedException {
        return fileService.sendAssignmentAnalysisPdfEmail(assignmentId, teacherId, request);
    }

    @RequestMapping(value="/getAssignmentOrAssignmentAnalysisPdf/{assignmentId}", method=RequestMethod.GET)
    public void getAssignmentOrAssignmentAnalysisPdf(@PathVariable("assignmentId") String assignmentId, HttpServletRequest request, HttpServletResponse response) throws IOException, InterruptedException {
        HtmlToPdfBean htmlToPdfBean = fileService.generateAssignmentOrAssignmentAnalysisPdf(assignmentId, request);
        if (htmlToPdfBean.getStatus() == 1) {
            response.setContentType("application/pdf");
            response.setContentLength((int) htmlToPdfBean.getPdfFile().length());
            response.setHeader("Content-Disposition", "attachment; filename=\"" + assignmentId + ".pdf" + "\"");
            StreamUtils.copy(new FileInputStream(htmlToPdfBean.getPdfFile()), response.getOutputStream());
            response.flushBuffer();
        }
    }

}
