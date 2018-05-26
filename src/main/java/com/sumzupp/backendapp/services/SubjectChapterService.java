package com.sumzupp.backendapp.services;

import com.sumzupp.backendapp.beans.AddChapterBean;
import com.sumzupp.backendapp.beans.StatusBean;
import com.sumzupp.backendapp.beans.SubjectChapterBean;
import com.sumzupp.backendapp.beans.SubjectChaptersBean;
import com.sumzupp.backendapp.entities.Board;
import com.sumzupp.backendapp.entities.Standard;
import com.sumzupp.backendapp.entities.Subject;
import com.sumzupp.backendapp.entities.SubjectChapter;
import com.sumzupp.backendapp.repositories.SubjectChapterRepository;
import com.sumzupp.backendapp.utils.Constants;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by akash.mercer on 09-Jul-17.
 */

@Service
public class SubjectChapterService {
    private static final String TAG = "SubjectChapterService : ";

    @Autowired
    private SubjectChapterRepository subjectChapterRepository;

    @Autowired
    private StandardService standardService;

    @Autowired
    private BoardService boardService;

    @Autowired
    private SubjectService subjectService;

    private static Logger debugLogger = Logger.getLogger("debugLogs");

    private static Logger errorLogger = Logger.getLogger("errorLogs");

    public SubjectChapter findById(String id) {
        return subjectChapterRepository.findOne(id);
    }

    public SubjectChaptersBean findBySubject(String subjectId, String studentId) {
        System.out.println("subject Id : " + subjectId);
        SubjectChaptersBean subjectChaptersBean = new SubjectChaptersBean();
        subjectChaptersBean.setSubjectChapterBeans(subjectService.findByStudent(studentId, subjectId).getSubjectBeans().get(0).getSubjectChapterBeans());
        subjectChaptersBean.setStatus(1);
        return subjectChaptersBean;
    }

    public List<SubjectChapter> findByIds(List<String> subjectChapterIdList) {
        return subjectChapterRepository.findByIds(subjectChapterIdList);
    }

    public List<SubjectChapterBean> findBySubjectIdsAndBoardAndStandard(List<String> subjectIds, Board board, Standard standard) {
        return subjectChapterRepository.findBySubjectIdsAndBoardAndStandard(subjectIds, board, standard);
    }

    public List<SubjectChapterBean> findBySubjectAndBoardAndStandard(String subjectId, String boardId, String standardId) {
        List<String> subjectIdList = new ArrayList<>();
        subjectIdList.add(subjectId);

        Board retrievedBoard = boardService.findById(boardId);

        Standard retrievedStandard = standardService.findById(standardId);

        return subjectChapterRepository.findBySubjectIdsAndBoardAndStandard(subjectIdList, retrievedBoard, retrievedStandard);
    }

    public List<SubjectChapterBean> findBySubjectAndStandard(String subjectId, String standardId) {
        List<String> subjectIdList = new ArrayList<>();
        subjectIdList.add(subjectId);

        Standard retrievedStandard = standardService.findById(standardId);

        return subjectChapterRepository.findBySubjectIdsAndStandard(subjectIdList, retrievedStandard);
    }

    public StatusBean save(AddChapterBean addChapterBean) {
        StatusBean statusBean = new StatusBean(0, Constants.SOMETHING_WENT_WRONG);

        Board retrievedBoard = boardService.findById(addChapterBean.getBoardId());

        Standard retrievedStandard = standardService.findById(addChapterBean.getStandardId());

        Subject retrievedSubject = subjectService.findById(addChapterBean.getSubjectId());

        if (retrievedBoard == null) {
            errorLogger.error(TAG + "Error in finding board with id : " + addChapterBean.getBoardId());
            return statusBean;
        }

        if (retrievedStandard == null) {
            errorLogger.error(TAG + "Error in finding standard with id : " + addChapterBean.getStandardId());
            return statusBean;
        }

        if (retrievedStandard == null) {
            errorLogger.error(TAG + "Error in finding subject with id : " + addChapterBean.getSubjectId());
            return statusBean;
        }

        SubjectChapter subjectChapter = new SubjectChapter();
        subjectChapter.setBoard(retrievedBoard);
        subjectChapter.setStandard(retrievedStandard);
        subjectChapter.setSubject(retrievedSubject);
        subjectChapter.setSubjectChapterName(addChapterBean.getChapterName());
        subjectChapter.setSubjectChapterNumber(addChapterBean.getChapterNumber());

        subjectChapterRepository.save(subjectChapter);

        statusBean.setStatus(1);
        return statusBean;
    }
}
