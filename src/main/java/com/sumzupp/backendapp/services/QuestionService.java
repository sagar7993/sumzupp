package com.sumzupp.backendapp.services;

import com.sumzupp.backendapp.beans.*;
import com.sumzupp.backendapp.entities.*;
import com.sumzupp.backendapp.repositories.QuestionRepository;
import com.sumzupp.backendapp.utils.Commons;
import com.sumzupp.backendapp.utils.Constants;
import com.sumzupp.backendapp.utils.FileUtils;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by akash.mercer on 07-Jul-17.
 */

@Service
public class QuestionService {
    private static final String TAG = "QuestionService : ";

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private SubjectService subjectService;

    @Autowired
    private SubjectChapterService subjectChapterService;

    @Autowired
    private QuestionTypeService questionTypeService;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private StandardService standardService;

    @Autowired
    private StandardDivisionService standardDivisionService;

    @Autowired
    private SolutionOptionService solutionOptionService;

    @Autowired
    private FileService fileService;

    private static Logger debugLogger = LoggerFactory.getLogger("debugLogs");

    private static Logger errorLogger = LoggerFactory.getLogger("errorLogs");

    public void saveAll(List<Question> questions) {
        questionRepository.save(questions);
    }

    @Transactional(rollbackFor = {Exception.class})
    public StatusBean editQuestion(QuestionBean questionBean) {

        StatusBean statusBean = new StatusBean(0, Constants.SOMETHING_WENT_WRONG);
        Boolean questionImageSaved = false; Boolean answerImageSaved = false; Integer answerImageCounter = 0;
        String osName = System.getProperty("os.name");
        String filePath = osName.startsWith("Windows") ? Constants.WINDOWS_ASSIGNMENT_IMAGE_FILE_PATH : Constants.UBUNTU_ASSIGNMENT_IMAGE_FILE_PATH;
        List<SolutionOption> solutionOptionList = new ArrayList<>();

        Question question = questionService.findById(questionBean.getQuestionId());
        if (question == null) {
            errorLogger.error(TAG + "Error in finding question with id : " + questionBean.getQuestionId());
            statusBean.setMessage(TAG + "Error in finding question with id : " + questionBean.getQuestionId());
            return statusBean;
        }
        if(questionBean.getQuestionImageUrls() == null) {
            question.setQuestionImageUrls(null);
            questionImageSaved = true;
        } else {
            if(questionBean.getQuestionImageUrls().contains("https://s3.ap-south-1.amazonaws.com/question-bank-images/")) {
                question.setQuestionImageUrls(questionBean.getQuestionImageUrls());
                questionImageSaved = true;
            } else {
                String questionBase64Image = questionBean.getQuestionImageUrls().split(",")[1];
                try {
                    if(fileService.saveImage(questionBase64Image, questionBean.getQuestionId(), "png", filePath)) {
                        JSONArray jsonArray = new JSONArray();
                        File file = new File(filePath + questionBean.getQuestionId() + ".png");
                        try {
                            jsonArray = fileService.pushImageToS3(jsonArray, file);
                        } catch (Exception e) {
                            errorLogger.error(TAG + "Error in saving question image to s3 bucket with id : " + questionBean.getQuestionId());
                            statusBean.setMessage(TAG + "Error in saving question image to s3 bucket with id : " + questionBean.getQuestionId());
                            return statusBean;
                        }
                        question.setQuestionImageUrls(jsonArray.toString());
                        questionImageSaved = true;
                        file.delete();
                    } else {
                        errorLogger.error(TAG + "Error in saving question image with id : " + questionBean.getQuestionId());
                        statusBean.setMessage(TAG + "Error in saving question image with id : " + questionBean.getQuestionId());
                        return statusBean;
                    }
                } catch (Exception e) {
                    errorLogger.error(TAG + "Error in saving question image with id : " + questionBean.getQuestionId());
                    statusBean.setMessage(TAG + "Error in saving question image with id : " + questionBean.getQuestionId());
                    return statusBean;
                }
            }
        }
        if(questionImageSaved) {
            question.setQuestionText(questionBean.getQuestionText());
            for(SolutionOptionBean solutionOptionBean : questionBean.getSolutionOptionBeans()) {
                SolutionOption solutionOption = solutionOptionService.findById(solutionOptionBean.getSolutionOptionId());
                if (solutionOption == null) {
                    errorLogger.error(TAG + "Error in solutionOption question with id : " + solutionOptionBean.getSolutionOptionId());
                    statusBean.setMessage(TAG + "Error in finding solutionOption with id : " + solutionOptionBean.getSolutionOptionId());
                    return statusBean;
                }
                if(solutionOptionBean.getSolutionOptionImageUrls() == null) {
                    solutionOption.setSolutionOptionImageUrls(null);
                    answerImageSaved = true;
                    answerImageCounter++;
                } else {
                    if(solutionOptionBean.getSolutionOptionImageUrls().contains("https://s3.ap-south-1.amazonaws.com/question-bank-images/")) {
                        solutionOption.setSolutionOptionImageUrls(solutionOptionBean.getSolutionOptionImageUrls());
                        answerImageSaved = true;
                        answerImageCounter++;
                    } else {
                        String solutionOptionBase64Image = solutionOptionBean.getSolutionOptionImageUrls().split(",")[1];
                        try {
                            if(fileService.saveImage(solutionOptionBase64Image, solutionOptionBean.getSolutionOptionId(), "png", filePath)) {
                                JSONArray jsonArray = new JSONArray();
                                File file = new File(filePath + solutionOptionBean.getSolutionOptionId() + ".png");
                                try {
                                    jsonArray = fileService.pushImageToS3(jsonArray, file);
                                } catch (Exception e) {
                                    errorLogger.error(TAG + "Error in saving answer image to s3 bucket with id : " + solutionOptionBean.getSolutionOptionId());
                                    statusBean.setMessage(TAG + "Error in saving answer image to s3 bucket with id : " + solutionOptionBean.getSolutionOptionId());
                                    return statusBean;
                                }
                                solutionOption.setSolutionOptionImageUrls(jsonArray.toString());
                                answerImageSaved = true;
                                answerImageCounter++;
                                file.delete();
                            } else {
                                errorLogger.error(TAG + "Error in saving question image with id : " + questionBean.getQuestionId());
                                statusBean.setMessage(TAG + "Error in saving question image with id : " + questionBean.getQuestionId());
                                return statusBean;
                            }
                        } catch (Exception e) {
                            errorLogger.error(TAG + "Error in saving question image with id : " + questionBean.getQuestionId());
                            statusBean.setMessage(TAG + "Error in saving question image with id : " + questionBean.getQuestionId());
                            return statusBean;
                        }
                    }
                }
                if(answerImageSaved) {
                    solutionOption.setSolutionOptionText(solutionOptionBean.getSolutionOptionText());
                    solutionOption.setSolution(solutionOptionBean.getSolution());
                    solutionOptionList.add(solutionOption);
                }
            }
            try {
                solutionOptionService.saveAll(solutionOptionList);
                try {
                    questionRepository.save(question);
                    if(answerImageCounter == solutionOptionList.size()) {
                        statusBean.setStatus(1);
                    }
                } catch (Exception e) {
                    errorLogger.error(TAG + "Error in saving Question with error : " + e.getMessage());
                    statusBean.setMessage(TAG + "Error in saving Questions with error : " + e.getMessage());
                    throw e;
                }
            } catch (Exception e) {
                errorLogger.error(TAG + "Error in saving solutionOptionList with error : " + e.getMessage());
                statusBean.setMessage(TAG + "Error in saving solutionOptionList with error : " + e.getMessage());
                throw e;
            }
        }
        return statusBean;
    }

    public Question findById(String questionId) {
        return questionRepository.findOne(questionId);
    }

    public QuestionBean findByQuestionId(String questionId) {
        QuestionBean questionBean = questionRepository.findByQuestionId(questionId);
        questionBean.setSolutionOptionBeans(solutionOptionService.findByQuestionId(questionBean.getQuestionId()));
        return questionBean;
    }

    public List<Question> findByIds(List<String> questionIds) {
        return questionRepository.findByIds(questionIds);
    }

    public List<QuestionDateBean> getQuestionsByStandardAndSubjectChapter(GetQuestionBankBean getQuestionBankBean) {
        String standardId = getQuestionBankBean.getStandardId();

        String subjectChapterId = getQuestionBankBean.getSubjectChapterId();

        List<QuestionBean> questionBeans = questionRepository.findByStandardAndSubjectChapter(standardId, subjectChapterId);

        for(QuestionBean questionBean : questionBeans) {
            questionBean.setSolutionOptionBeans(solutionOptionService.findByQuestionId(questionBean.getQuestionId()));
        }

        Map<String, List<QuestionBean>> questionBeansMap = new HashMap<>();

        for (QuestionBean questionBean : questionBeans) {
            String key = questionBean.getDate();

            if (questionBeansMap.containsKey(key)) {
                questionBeansMap.get(key).add(questionBean);
            } else {
                List<QuestionBean> newQuestionBeans = new ArrayList<>();
                newQuestionBeans.add(questionBean);
                questionBeansMap.put(key, newQuestionBeans);
            }
        }

        List<QuestionDateBean> questionDateBeans = new ArrayList<>();

        for (Map.Entry<String, List<QuestionBean>> questionBeansEntry : questionBeansMap.entrySet()){
            QuestionDateBean questionDateBean = new QuestionDateBean();
            questionDateBean.setDate(questionBeansEntry.getKey());
            questionDateBean.setQuestionBeans(questionBeansEntry.getValue());

            questionDateBeans.add(questionDateBean);
        }

        return questionDateBeans;
    }

    public List<QuestionBean> getQuestionsByBoardAndStandardAndSubjectChapter(GetQuestionBankBean getQuestionBankBean) {
        String subjectChapterId = getQuestionBankBean.getSubjectChapterId();
        String standardDivisionId = getQuestionBankBean.getStandardDivisionId();
        String boardId = getQuestionBankBean.getBoardId();
        String standardId = standardDivisionService.findById(standardDivisionId).getStandard().getId();
        List<QuestionBean> questionBeans = questionRepository.findByBoardAndStandardAndSubjectChapter(boardId, standardId, subjectChapterId);
        for(QuestionBean questionBean : questionBeans) {
            questionBean.setSolutionOptionBeans(solutionOptionService.findByQuestionId(questionBean.getQuestionId()));
        }
        return questionBeans;
    }

    @Transactional(rollbackFor = {Exception.class})
    public StatusBean readQuestionSheet(String fileName) {
        StatusBean statusBean = new StatusBean();
        String[] fileNameParts = fileName.split("_");
        Subject retrievedSubject = subjectService.findByType(Integer.parseInt(fileNameParts[0]));

        if (retrievedSubject == null) {
            errorLogger.error(TAG + "Error in finding Subject with id : " + fileNameParts[0] + " in file : " + fileName);
            statusBean.setStatus(0);
            statusBean.setMessage(TAG + "Error in finding Subject with id : " + fileNameParts[0] + " in file : " + fileName);
            return statusBean;
        }

        SubjectChapter retrievedSubjectChapter = subjectChapterService.findById(fileNameParts[1]);

        if (retrievedSubjectChapter == null) {
            errorLogger.error(TAG + "Error in finding SubjectChapter with id : " + fileNameParts[1] + " in file : " + fileName);
            statusBean.setStatus(0);
            statusBean.setMessage(TAG + "Error in finding SubjectChapter with id : " + fileNameParts[1] + " in file : " + fileName);
            return statusBean;
        }

        Map<Integer, QuestionType> questionTypeMap = getQuestionTypeMap();

        String osName = System.getProperty("os.name");
        String path = osName.startsWith("Windows") ? Constants.WINDOWS_QUESTION_SHEET_FILE_PATH : Constants.UBUNTU_QUESTION_SHEET_FILE_PATH;
        File file = new File(path + File.separator + fileName);

        XSSFWorkbook xssfWorkbook;

        try {
            xssfWorkbook = FileUtils.getWorkbookFromFile(file);
        } catch (Exception e) {
            errorLogger.error(TAG + "Error in opening Workbook file : " + file.getAbsolutePath());
            statusBean.setStatus(0);
            statusBean.setMessage(TAG + "Error in opening Workbook file : " + file.getAbsolutePath());
            return statusBean;
        }

        XSSFSheet xssfSheet = FileUtils.getSheetFromWorkBook(xssfWorkbook);

        if (xssfSheet == null) {
            errorLogger.error(TAG + "No Sheet found in Workbook file : " + file.getAbsolutePath());
            statusBean.setStatus(0);
            statusBean.setMessage(TAG + "No Sheet found in Workbook file : " + file.getAbsolutePath());
            return statusBean;
        }

        int lastRowIndex = xssfSheet.getLastRowNum();
        List<Question> questions = new ArrayList<>();
        List<SolutionOption> solutionOptions = new ArrayList<>();
        int i = 1;

        try {
            for (i = 1; i <= lastRowIndex; i++) {
                XSSFRow xssfRow = xssfSheet.getRow(i);
                Question question = new Question();
                if (xssfRow.getCell(2) == null) {
                    break;
                }
                question.setQuestionText(xssfRow.getCell(2).getStringCellValue());
                question.setMarks(1);
                question.setSubjectChapter(retrievedSubjectChapter);
                xssfRow.getCell(10).setCellType(CellType.BOOLEAN);
                question.setFormula(xssfRow.getCell(10).getBooleanCellValue());
                xssfRow.getCell(8).setCellType(CellType.STRING);
                question.setQuestionType(questionTypeMap.get(Integer.parseInt(xssfRow.getCell(8).getStringCellValue())));
                //Build SolutionOptions for given Question
                solutionOptions.addAll(getSolutionOptionsForQuestion(xssfRow, question));
                questions.add(question);
            }
        } catch (Exception e) {
            errorLogger.error(TAG + "Error in looping through rows of excel sheet at row number = " + i);
            statusBean.setStatus(0);
            statusBean.setMessage(TAG + "Error in looping through rows of excel sheet at row number = " + i);
            return statusBean;
        }

        try {
            questionService.saveAll(questions);
            try {
                solutionOptionService.saveAll(solutionOptions);
            } catch (Exception e) {
                errorLogger.error(TAG + "Error in saving SolutionOptions with error : " + e.getMessage());
                statusBean.setStatus(0);
                statusBean.setMessage(TAG + "Error in saving SolutionOptions with error : " + e.getMessage());
                throw e;
            }
        } catch (Exception e) {
            errorLogger.error(TAG + "Error in saving Questions with error : " + e.getMessage());
            statusBean.setStatus(0);
            statusBean.setMessage(TAG + "Error in saving Questions with error : " + e.getMessage());
            throw e;
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

    private Map<Integer, QuestionType> getQuestionTypeMap() {
        List<QuestionType> questionTypes = questionTypeService.fetchAll();

        Map<Integer, QuestionType> questionTypeMap = new HashMap<>();

        for (QuestionType questionType : questionTypes) {
            questionTypeMap.put(questionType.getType(), questionType);
        }

        return questionTypeMap;
    }

    private List<SolutionOption> getSolutionOptionsForQuestion(XSSFRow xssfRow, Question question) {
        List<SolutionOption> solutionOptions = new ArrayList<>();

        xssfRow.getCell(7).setCellType(CellType.STRING);

        xssfRow.getCell(9).setCellType(CellType.BOOLEAN);

        if (xssfRow.getCell(3) != null) {
            xssfRow.getCell(3).setCellType(CellType.STRING);
            if (!StringUtils.isEmpty(xssfRow.getCell(3).getStringCellValue())) {
                solutionOptions.add(getSolutionOptionFromCell(xssfRow.getCell(3), question, xssfRow.getCell(7).getStringCellValue(), xssfRow.getCell(9).getBooleanCellValue()));
            }
        }

        if (xssfRow.getCell(4) != null) {
            xssfRow.getCell(4).setCellType(CellType.STRING);
            if (!StringUtils.isEmpty(xssfRow.getCell(4).getStringCellValue())) {
                solutionOptions.add(getSolutionOptionFromCell(xssfRow.getCell(4), question, xssfRow.getCell(7).getStringCellValue(), xssfRow.getCell(9).getBooleanCellValue()));
            }
        }

        if (xssfRow.getCell(5) != null) {
            xssfRow.getCell(5).setCellType(CellType.STRING);
            if (!StringUtils.isEmpty(xssfRow.getCell(5).getStringCellValue())) {
                solutionOptions.add(getSolutionOptionFromCell(xssfRow.getCell(5), question, xssfRow.getCell(7).getStringCellValue(), xssfRow.getCell(9).getBooleanCellValue()));
            }
        }

        if (xssfRow.getCell(6) != null) {
            xssfRow.getCell(6).setCellType(CellType.STRING);
            if (!StringUtils.isEmpty(xssfRow.getCell(6).getStringCellValue())) {
                solutionOptions.add(getSolutionOptionFromCell(xssfRow.getCell(6), question, xssfRow.getCell(7).getStringCellValue(), xssfRow.getCell(9).getBooleanCellValue()));
            }
        }

        return solutionOptions;
    }

    private SolutionOption getSolutionOptionFromCell(XSSFCell xssfCell, Question question, String solution, Boolean formula) {
        SolutionOption solutionOption = new SolutionOption();

        String solutionOptionText = null;

        try {
            solutionOptionText = Commons.amountDecimalFormat.format(Double.parseDouble(xssfCell.getStringCellValue()));
        } catch (NumberFormatException e) {
            //Do Nothing
        }

        solutionOption.setSolutionOptionText(solutionOptionText != null ? solutionOptionText : xssfCell.getStringCellValue());
        solutionOption.setSolution(solutionOption.getSolutionOptionText().equals(solution));
        solutionOption.setFormula(formula);
        solutionOption.setQuestion(question);
        return solutionOption;
    }
}
